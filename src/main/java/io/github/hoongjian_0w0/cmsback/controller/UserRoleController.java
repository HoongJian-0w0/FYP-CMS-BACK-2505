package io.github.hoongjian_0w0.cmsback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import io.github.hoongjian_0w0.cmsback.service.IUserRoleService;
import io.github.hoongjian_0w0.cmsback.entity.UserRole;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User–Role Mapping Table Controller
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    @GetMapping
    public Result getAllUserRole() {
        return Result.ok().data("list", userRoleService.list()).message("Fetched All UserRole");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.ok().data("item", userRoleService.getById(id)).message("Fetched UserRole by ID");
    }

    @PostMapping
    public Result save(@RequestBody UserRole userRole) {
        try {
            userRoleService.save(userRole);
            return Result.ok().message("UserRole Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Save UserRole");
        }
    }

    @PutMapping
    public Result update(@RequestBody UserRole userRole) {
        if (userRoleService.updateById(userRole)) {
            return Result.ok().message("UserRole Updated Successfully");
        }
        throw new ServiceException(ResultCode.NOT_FOUND, "Update Failed: UserRole Not Found");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (userRoleService.removeById(id)) {
            return Result.ok().message("UserRole Deleted Successfully");
        }
        return Result.error().message("Failed to Delete UserRole");
    }

    @DeleteMapping("/del/batch/{ids}")
    public Result deleteBatch(@PathVariable List<Integer> ids) {
        if (userRoleService.removeByIds(ids)) {
            return Result.ok().message("UserRoles Deleted Successfully");
        }
        return Result.error().message("Failed to Batch Delete UserRoles");
    }

    @GetMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<UserRole> page = userRoleService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.ok().data("pagination", page).message("Paged UserRole List");
    }
}
