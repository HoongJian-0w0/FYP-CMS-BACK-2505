package io.github.hoongjian_0w0.cmsback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import io.github.hoongjian_0w0.cmsback.service.IRoleMenuService;
import io.github.hoongjian_0w0.cmsback.entity.RoleMenu;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role–Menu Mapping Table Controller
 */
@RestController
@RequestMapping("/role-menu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    @GetMapping
    public Result getAllRoleMenu() {
        return Result.ok().data("list", roleMenuService.list()).message("Fetched All RoleMenu");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.ok().data("item", roleMenuService.getById(id)).message("Fetched RoleMenu by ID");
    }

    @PostMapping
    public Result save(@RequestBody RoleMenu roleMenu) {
        try {
                roleMenuService.save(roleMenu);
            return Result.ok().message("RoleMenu Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Save RoleMenu");
        }
    }

    @PutMapping
    public Result update(@RequestBody RoleMenu roleMenu) {
        if (roleMenuService.updateById(roleMenu)) {
            return Result.ok().message("RoleMenu Updated Successfully");
        }
        throw new ServiceException(ResultCode.NOT_FOUND, "Update Failed: RoleMenu Not Found");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (roleMenuService.removeById(id)) {
            return Result.ok().message("RoleMenu Deleted Successfully");
        }
        return Result.error().message("Failed to Delete RoleMenu");
    }

    @DeleteMapping("/del/batch/{ids}")
    public Result deleteBatch(@PathVariable List<Integer> ids) {
        if (roleMenuService.removeByIds(ids)) {
            return Result.ok().message("RoleMenus Deleted Successfully");
        }
        return Result.error().message("Failed to Batch Delete RoleMenus");
    }

    @GetMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<RoleMenu> page = roleMenuService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.ok().data("pagination", page).message("Paged RoleMenu List");
    }
}
