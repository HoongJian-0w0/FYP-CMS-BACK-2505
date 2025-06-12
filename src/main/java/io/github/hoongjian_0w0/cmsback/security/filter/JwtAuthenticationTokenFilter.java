package io.github.hoongjian_0w0.cmsback.security.filter;

import com.alibaba.fastjson.JSON;
import io.github.hoongjian_0w0.cmsback.security.LoginUserDetails;
import io.github.hoongjian_0w0.cmsback.security.exception.UserAuthenticationException;
import io.github.hoongjian_0w0.cmsback.security.handler.AnonymousAuthenticationHandler;
import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.github.hoongjian_0w0.cmsback.security.util.TokenExtractor;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String uri = request.getRequestURI();
            if (!uri.equals("/auth/login") && !uri.equals("/auth/captcha")) {
                this.validateToken(request);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            anonymousAuthenticationHandler.commence(request, response, e);
        }
    }

    private void validateToken(HttpServletRequest request) {
        // 1. Get Authorization header from request
        String token = TokenExtractor.extractToken(request);

        String redisStr = stringRedisTemplate.opsForValue().get("token_" + token);
        if(StringUtils.isEmpty(redisStr)){
            throw new UserAuthenticationException("Invalid or expired JWT token");
        }

        LoginUserDetails loginUserDetails = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String loginUserDetailsString = claims.getSubject();
            loginUserDetails = JSON.parseObject(loginUserDetailsString, LoginUserDetails.class);
        } catch (Exception e) {
            throw new UserAuthenticationException("Invalid or expired JWT token");
        }
        // 4. Create authentication token and store it in the security context
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
