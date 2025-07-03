package io.github.hoongjian_0w0.cmsback.vo;

import io.github.hoongjian_0w0.cmsback.entity.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AssignTreeVo {
    private List<Menu> menuList = new ArrayList<>();
    private Object[] checkList;
}
