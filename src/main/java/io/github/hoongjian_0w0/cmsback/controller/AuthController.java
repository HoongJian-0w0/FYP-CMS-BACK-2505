package io.github.hoongjian_0w0.cmsback.controller;

import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        Map<String,Object> user = authService.login(loginDTO);
        if(!user.get("token").equals("") && !user.get("username").equals("")) {
            return Result.ok().message("Login Success").data("user", user);
        }
        throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Login Failed");
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        if(authService.logout(request, response)) {
            return Result.ok().message("Logout Success");
        };
        throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Logout Failed");

    }


}
