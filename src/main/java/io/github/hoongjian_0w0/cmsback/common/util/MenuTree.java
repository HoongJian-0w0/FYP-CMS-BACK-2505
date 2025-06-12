package io.github.hoongjian_0w0.cmsback.common.util;

import io.github.hoongjian_0w0.cmsback.entity.Menu;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuTree {

    public static List<Menu> genMenuTree(List<Menu> menus, Long pid) {
        List<Menu> menuList = new ArrayList<>();
        Optional.ofNullable(menus).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getPid().equals(pid))
                .forEach(item -> {
                    Menu menu = new Menu();
                    BeanUtils.copyProperties(item, menu);
                    menu.setLabel(item.getTitle());
                    menu.setValue(item.getId());
                    List<Menu> children = genMenuTree(menuList, item.getId());
                    menu.setChildren(children);
                    menuList.add(menu);
                });
        return menuList;
    }

}
