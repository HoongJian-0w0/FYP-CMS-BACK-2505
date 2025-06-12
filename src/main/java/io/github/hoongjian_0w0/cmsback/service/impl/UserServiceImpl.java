package io.github.hoongjian_0w0.cmsback.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.hoongjian_0w0.cmsback.entity.User;
import io.github.hoongjian_0w0.cmsback.entity.UserRole;
import io.github.hoongjian_0w0.cmsback.mapper.UserMapper;
import io.github.hoongjian_0w0.cmsback.service.IUserRoleService;
import io.github.hoongjian_0w0.cmsback.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User table Service Implementation Class
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public <E extends IPage<User>> E page(E page, Wrapper<User> queryWrapper) {
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

}
