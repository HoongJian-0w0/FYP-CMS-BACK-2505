package io.github.hoongjian_0w0.cmsback.service.impl;

import com.alibaba.fastjson.JSON;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.security.LoginUserDetails;
import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.github.hoongjian_0w0.cmsback.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDTO loginDTO) {
        // 1. Wrap the Authentication object with username and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        // 2. Perform authentication
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // 3. If authentication failed, throw error
        if(Objects.isNull(authenticate)) {
            throw new ServiceException(ResultCode.UNAUTHORIZED, "Username or password incorrect.");
        }
        // 4. Extract user info from authentication
        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();

        System.out.println("LoginUserDetails: " + loginUserDetails);
        // 5. Convert loginUser to JSON string (e.g. using FastJSON)
        String loginUserString = JSON.toJSONString(loginUserDetails);
        // 6. Generate JWT using custom utility class
        String jwt = JwtUtil.createJWT(loginUserString, null);
        return jwt;
    }
}
