package io.github.hoongjian_0w0.cmsback.controller;

import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.mapper.UserMapper;
import io.github.hoongjian_0w0.cmsback.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        String jwt = authService.login(loginDTO);
        System.out.println("JWT: " + jwt);
        if(StringUtils.hasLength(jwt)) {
            return Result.ok().message("Login Success").data("token", jwt);
        }
        throw new ServiceException(ResultCode.UNAUTHORIZED, "Login Failed");
    }

}
