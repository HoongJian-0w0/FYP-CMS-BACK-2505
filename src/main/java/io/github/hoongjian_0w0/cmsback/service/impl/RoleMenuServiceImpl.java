package io.github.hoongjian_0w0.cmsback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.hoongjian_0w0.cmsback.dto.RoleMenuDTO;
import io.github.hoongjian_0w0.cmsback.entity.RoleMenu;
import io.github.hoongjian_0w0.cmsback.mapper.RoleMenuMapper;
import io.github.hoongjian_0w0.cmsback.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Role–Menu Mapping Table Service Implementation Class
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public boolean saveRoleMenu(RoleMenuDTO roleMenuDTO) {
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId, roleMenuDTO.getRoleId());
        this.baseMapper.delete(query);

        if (roleMenuDTO.getMenuIds() == null || roleMenuDTO.getMenuIds().isEmpty()) {
            return true;
        }

        return this.baseMapper.saveRoleMenu(roleMenuDTO);
    }

}
