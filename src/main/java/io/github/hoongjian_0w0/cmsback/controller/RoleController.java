package io.github.hoongjian_0w0.cmsback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import io.github.hoongjian_0w0.cmsback.service.IRoleService;
import io.github.hoongjian_0w0.cmsback.entity.Role;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role Table Controller
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.ok().data("item", roleService.getById(id)).message("Fetched Role by ID");
    }

    @PostMapping
    public Result save(@RequestBody Role role) {
        try {
            roleService.save(role);
            return Result.ok().message("Role Saved Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Save Role");
        }
    }

    @PutMapping
    public Result update(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return Result.ok().message("Role Updated Successfully");
        }
        throw new ServiceException(ResultCode.NOT_FOUND, "Update Failed: Role Not Found");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (roleService.removeById(id)) {
            return Result.ok().message("Role Deleted Successfully");
        }
        return Result.error().message("Failed to Delete Role");
    }

    @GetMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String roleName) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (!roleName.isEmpty()) {
            queryWrapper.like("name", roleName);
        }
        queryWrapper.orderByDesc("id");
        Page<Role> page = roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.ok().data("pagination", page).message("Paged Role List");
    }

}
