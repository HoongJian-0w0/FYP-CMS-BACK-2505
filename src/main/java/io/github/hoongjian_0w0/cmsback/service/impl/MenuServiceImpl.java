package io.github.hoongjian_0w0.cmsback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.hoongjian_0w0.cmsback.entity.Menu;
import io.github.hoongjian_0w0.cmsback.mapper.MenuMapper;
import io.github.hoongjian_0w0.cmsback.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.github.hoongjian_0w0.cmsback.common.util.MenuTree.genMenuTree;

/**
 * Menu Table Service Implementation Class
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getParents() {
        String[] type = {"0", "1"};
        List<String> strings = Arrays.asList(type);
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda()
                .in(Menu::getType, strings)
                .orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = baseMapper.selectList(query);
        Menu menu = new Menu();
        menu.setType("Primary Menu");
        menu.setLabel("Primary Menu");
        menu.setPid(-1L);
        menu.setId(0L);
        menuList.add(menu);
        List<Menu> menuTree = genMenuTree(menuList, -1L);
        return menuTree;
    }
}
