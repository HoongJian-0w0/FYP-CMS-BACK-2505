package io.github.hoongjian_0w0.cmsback.service;

import io.github.hoongjian_0w0.cmsback.dto.AssignTreeDTO;
import io.github.hoongjian_0w0.cmsback.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.hoongjian_0w0.cmsback.vo.AssignTreeVo;

import java.util.List;

/**
 * Menu Table Service Class
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getParents();

    List<Menu> getMenuByUserId(Long userId);

    List<Menu> getMenuByRoleId(Long roleId);

}
