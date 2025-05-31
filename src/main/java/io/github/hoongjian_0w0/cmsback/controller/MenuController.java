package io.github.hoongjian_0w0.cmsback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import io.github.hoongjian_0w0.cmsback.service.IMenuService;
import io.github.hoongjian_0w0.cmsback.entity.Menu;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Menu Table Controller
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping
    public Result getAllMenu() {
        return Result.ok().data("list", menuService.list()).message("Fetched All Menu");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.ok().data("item", menuService.getById(id)).message("Fetched Menu by ID");
    }

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        try {
                menuService.save(menu);
            return Result.ok().message("Menu Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Save Menu");
        }
    }

    @PutMapping
    public Result update(@RequestBody Menu menu) {
        if (menuService.updateById(menu)) {
            return Result.ok().message("Menu Updated Successfully");
        }
        throw new ServiceException(ResultCode.NOT_FOUND, "Update Failed: Menu Not Found");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (menuService.removeById(id)) {
            return Result.ok().message("Menu Deleted Successfully");
        }
        return Result.error().message("Failed to Delete Menu");
    }

    @DeleteMapping("/del/batch/{ids}")
    public Result deleteBatch(@PathVariable List<Integer> ids) {
        if (menuService.removeByIds(ids)) {
            return Result.ok().message("Menus Deleted Successfully");
        }
        return Result.error().message("Failed to Batch Delete Menus");
    }

    @GetMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<Menu> page = menuService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.ok().data("page", page).message("Paged Menu List");
    }
}
