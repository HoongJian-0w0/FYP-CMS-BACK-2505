package io.github.hoongjian_0w0.cmsback.security.filter;

import com.alibaba.fastjson.JSON;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.security.LoginUserDetails;
import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Skip login endpoint — no JWT validation needed here
        String uri = request.getRequestURI();
        if (uri.contains("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 2. Get Authorization header from request
        String authHeader = request.getHeader("Authorization");
        // 3. Validate Authorization header presence and prefix
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new ServiceException(ResultCode.UNAUTHORIZED, "Authentication failed, Token missing.");
        }
        // 4. Extract the token string after "Bearer "
        String token = authHeader.substring(7);
        LoginUserDetails loginUserDetails = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String loginUserDetailsString = claims.getSubject();
            loginUserDetails = JSON.parseObject(loginUserDetailsString, LoginUserDetails.class);
        } catch (Exception e) {
            // 8. If any parsing error occurs, treat as unauthorized
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
        // 9. Create authentication token and store it in the security context
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
