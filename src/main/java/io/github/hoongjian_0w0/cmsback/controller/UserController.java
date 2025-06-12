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

    @GetMapping()
    @PreAuthorize("hasAuthority('cms:user-fetch')")
    public Result getAllUser() {
        List<User> users = userService.list();
        if (users != null) {
            return Result.ok().data("list", userService.list()).message("Fetched All User");
        } else {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Fetch Users");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('cms:user-fetch-one')")
    public Result getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.ok().data("item", userService.getById(id)).message("Fetched User by ID");
        } else {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Fetch User by ID");
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('cms:user-create')")
    public Result save(@RequestBody User user) {
        try {
            userService.save(user);
            return Result.ok().message("User Saved Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Failed to Save User");
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('cms:user-update')")
    public Result update(@RequestBody User user) {
        try {
            userService.updateById(user);
            return Result.ok().message("User Updated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.NOT_FOUND, "Update Failed: User Not Found");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('cms:user-delete')")
    public Result delete(@PathVariable Integer id) {
        if (userService.removeById(id)) {
            return Result.ok().message("User Deleted Successfully");
        }
        return Result.error().message("Failed to Delete User");
    }

    @DeleteMapping("/del/batch/{ids}")
    @PreAuthorize("hasAuthority('cms:user-delete-batch')")
    public Result deleteBatch(@PathVariable List<Integer> ids) {
        if (userService.removeByIds(ids)) {
            return Result.ok().message("Users Deleted Successfully");
        }
        return Result.error().message("Failed to Batch Delete Users");
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('cms:user-page')")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String username,
                          @RequestParam(defaultValue = "") String email,
                          @RequestParam(defaultValue = "") String phone) {
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if (!username.isBlank()) {
                queryWrapper.like("username", username);
            }
            if (!email.isBlank()) {
                queryWrapper.like("email", email);
            }
            if (!phone.isBlank()) {
                queryWrapper.like("phone", phone);
            }
            queryWrapper.orderByDesc("id");
            Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.ok().data("pagination", page).message("Paged User List");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
