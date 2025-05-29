package io.github.hoongjian_0w0.cmsback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import io.github.hoongjian_0w0.cmsback.service.IUserService;
import io.github.hoongjian_0w0.cmsback.entity.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User table Controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('cms-user-fetch')")
    public Result getAllUser() {
        return Result.ok().data("list", userService.list()).message("Fetched All User");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('cms-user-fetch-one')")
    public Result getById(@PathVariable Integer id) {
        return Result.ok().data("item", userService.getById(id)).message("Fetched User by ID");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('cms-user-create')")
    public Result save(@RequestBody User user) {
        try {
            userService.save(user);
            return Result.ok().message("User Saved Successfully");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Save User");
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('cms-user-update')")
    public Result update(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Result.ok().message("User Updated Successfully");
        }
        throw new ServiceException(ResultCode.NOT_FOUND, "Update Failed: User Not Found");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('cms-user-delete')")
    public Result delete(@PathVariable Integer id) {
        if (userService.removeById(id)) {
            return Result.ok().message("User Deleted Successfully");
        }
        return Result.error().message("Failed to Delete User");
    }

    @DeleteMapping("/del/batch/{ids}")
    @PreAuthorize("hasAuthority('cms-user-delete-batch')")
    public Result deleteBatch(@PathVariable List<Integer> ids) {
        if (userService.removeByIds(ids)) {
            return Result.ok().message("Users Deleted Successfully");
        }
        return Result.error().message("Failed to Batch Delete Users");
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('cms-user-page')")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.ok().data("page", page).message("Paged User List");
    }

}
