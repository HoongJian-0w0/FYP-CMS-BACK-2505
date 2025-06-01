package io.github.hoongjian_0w0.cmsback.service.impl;

import com.alibaba.fastjson.JSON;
import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import io.github.hoongjian_0w0.cmsback.security.LoginUserDetails;
import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.github.hoongjian_0w0.cmsback.security.util.TokenExtractor;
import io.github.hoongjian_0w0.cmsback.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Map<String,Object> login(LoginDTO loginDTO) {
        // 1. Wrap the Authentication object with username and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        // 2. Perform authentication
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // 3. Extract user info from authentication
        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
        // 4. Convert loginUser to JSON string (e.g. using FastJSON)
        String loginUserString = JSON.toJSONString(loginUserDetails);
        // 5. Generate JWT using custom utility class
        String jwt = JwtUtil.createJWT(loginUserString, null);

        String tokenKey = "token_" + jwt;
        stringRedisTemplate.opsForValue().set(tokenKey, jwt, JwtUtil.JWT_TTL/1000, TimeUnit.SECONDS);

        Map<String,Object> user = new HashMap<>();
        user.put("token", jwt);
        user.put("username", loginUserDetails.getUsername());

        return user;
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        String token = TokenExtractor.extractToken(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            System.out.println("JWT: " + "token_"+token);
            stringRedisTemplate.delete("token_"+token);
            return true;
        }
        return false;
    }

}
