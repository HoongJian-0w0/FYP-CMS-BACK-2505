package io.github.hoongjian_0w0.cmsback.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.common.util.RoleCheckUtil;
import io.github.hoongjian_0w0.cmsback.dto.AssignTreeDTO;
import io.github.hoongjian_0w0.cmsback.dto.PasswordUpdateDTO;
import io.github.hoongjian_0w0.cmsback.dto.ProfileUpdateDTO;
import io.github.hoongjian_0w0.cmsback.entity.Menu;
import io.github.hoongjian_0w0.cmsback.entity.User;
import io.github.hoongjian_0w0.cmsback.entity.UserRole;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.mapper.RoleMapper;
import io.github.hoongjian_0w0.cmsback.mapper.UserMapper;
import io.github.hoongjian_0w0.cmsback.security.util.SecurityUtils;
import io.github.hoongjian_0w0.cmsback.service.IMenuService;
import io.github.hoongjian_0w0.cmsback.service.IUserRoleService;
import io.github.hoongjian_0w0.cmsback.service.IUserService;
import io.github.hoongjian_0w0.cmsback.vo.AssignTreeVo;
import io.github.hoongjian_0w0.cmsback.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.hoongjian_0w0.cmsback.common.util.MenuTree.genMenuTree;

/**
 * User table Service Implementation Class
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public <E extends IPage<User>> E page(E page, Wrapper<User> queryWrapper) {
        // TODO OPTIMISE THIS LOGIC
        E resultPage = super.page(page, queryWrapper);
        List<User> users = resultPage.getRecords();
        if (users == null || users.isEmpty()) {
            return resultPage;
        }
        List<Long> userIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        List<UserRole> userRoleList = userRoleService.list(
            new QueryWrapper<UserRole>().in("user_id", userIds)
        );
        Map<Long, List<Long>> userRoleMap = userRoleList.stream()
                .collect(Collectors.groupingBy(
                        UserRole::getUserId,
                        Collectors.mapping(UserRole::getRoleId, Collectors.toList())
                ));
        for (User user : users) {
            List<Long> roleIds = userRoleMap.getOrDefault(user.getId(), Collections.emptyList());
            Collections.sort(roleIds);
            user.setRoleIds(roleIds);
        }
        return resultPage;
    }

    @Override
    public boolean save(User user) {
        encodePasswordIfPresent(user);
        boolean saved = super.save(user);
        if (saved) {
            saveUserRoles(user.getId(), user.getRoleIds());
        }
        return saved;
    }

    @Override
    public boolean updateById(User user) {
        encodePasswordIfPresent(user);
        boolean updated = super.updateById(user);
        if (updated) {
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            if (user.getDelFlag() == null || !user.getDelFlag()) {
                saveUserRoles(user.getId(), user.getRoleIds());
            }
        }
        return updated;
    }


    @Override
    public boolean removeById(Serializable id) {
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
        User user = new User();
        user.setId((Long) id);
        user.setDelFlag(true);
        return this.updateById(user);
    }

    /**
     * Encode password if not blank
     */
    private void encodePasswordIfPresent(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
    }

    /**
     * Save user-role relationships
     */
    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) return;

        List<UserRole> userRoles = roleIds.stream()
                .distinct()
                .map(roleId -> {
                    UserRole ur = new UserRole();
                    ur.setUserId(userId);
                    ur.setRoleId(roleId);
                    return ur;
                })
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);
    }

    @Override
    public UserInfoVo getCurrentUserInfo() {
        User user = SecurityUtils.getCurrentUser().getUser();
        UserInfoVo vo = new UserInfoVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public AssignTreeVo getAssignTree(AssignTreeDTO assignTreeDTO) {
        Long userId = assignTreeDTO.getUserId();
        Long roleId = assignTreeDTO.getRoleId();

        List<String> roleCodes = roleMapper.getRoleCodesByUserId(userId);
        List<Menu> menuList = null;

        if (RoleCheckUtil.isSuperAdmin(roleCodes)) {
            menuList = menuService.list();
        } else {
            menuList = menuService.getMenuByUserId(userId);
        }

        List<Menu> menuTree = genMenuTree(menuList, 0L);

        List<Menu> roleList = menuService.getMenuByRoleId(roleId);
        List<Long> ids = new ArrayList<>();

        Optional.ofNullable(roleList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item -> {
                    ids.add(item.getId());
                });

        AssignTreeVo vo = new AssignTreeVo();
        vo.setCheckList(ids.toArray());
        vo.setMenuList(menuTree);

        return vo;
    }

    @Override
    public UserInfoVo updateProfile(ProfileUpdateDTO dto) {
        Long currentUserId = SecurityUtils.getCurrentUser().getUser().getId();

        User user = new User();
        user.setId(currentUserId);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setNickName(dto.getNickName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setGender(dto.getGender());
        user.setAvatar(dto.getAvatar());

        this.updateById(user);

        User updatedUser = this.getById(currentUserId);
        UserInfoVo vo = new UserInfoVo();
        BeanUtils.copyProperties(updatedUser, vo);
        return vo;
    }

    @Override
    public boolean updatePassword(PasswordUpdateDTO passwordDTO) {
        Long currentUserId = SecurityUtils.getCurrentUser().getUser().getId();

        User user = userMapper.selectById(currentUserId);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "Old password is incorrect.");
        }

        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "New password and confirmation do not match.");
        }

        String encodedNewPassword = passwordEncoder.encode(passwordDTO.getNewPassword());

        int rows = userMapper.updatePassword(currentUserId, encodedNewPassword);

        return rows > 0;
    }

}
