package io.github.hoongjian_0w0.cmsback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.entity.Menu;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.mapper.MenuMapper;
import io.github.hoongjian_0w0.cmsback.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.hoongjian_0w0.cmsback.common.util.MenuTree.genMenuTree;

/**
 * Menu Table Service Implementation Class
 */

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> list() {
        List<Menu> menuList = super.list();
        Map<Long, String> idToTitleMap = menuList.stream()
                .collect(Collectors.toMap(Menu::getId, Menu::getTitle));
        for (Menu menu : menuList) {
            if (menu.getPid() != null && idToTitleMap.containsKey(menu.getPid())) {
                menu.setPName(idToTitleMap.get(menu.getPid()));
            }
        }
        return menuList;
    }

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
        menu.setLabel("Primary Menu");
        menu.setTitle("Primary Menu");
        menu.setId(0L);
        menu.setPid(-1L);
        menu.setValue(0L);
        menuList.add(menu);
        List<Menu> menuTree = genMenuTree(menuList, -1L);
        return menuTree;
    }

    @Override
    public boolean removeById(Serializable id) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().eq(Menu::getPid, id);
        List<Menu> menuList = baseMapper.selectList(query);
        if (menuList.size() > 0) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "Cannot delete parent menu: it has child menus.");
        }
        return super.removeById(id);
    }

}

