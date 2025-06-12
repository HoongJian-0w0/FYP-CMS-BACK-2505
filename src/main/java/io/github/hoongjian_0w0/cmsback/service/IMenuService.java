package io.github.hoongjian_0w0.cmsback.service;

import io.github.hoongjian_0w0.cmsback.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Menu Table Service Class
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getParents();

}
