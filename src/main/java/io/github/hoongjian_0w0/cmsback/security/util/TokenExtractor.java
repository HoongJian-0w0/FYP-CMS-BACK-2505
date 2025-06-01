package io.github.hoongjian_0w0.cmsback.security.util;

import io.github.hoongjian_0w0.cmsback.security.exception.UserAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class TokenExtractor {

    /**
     * Extracts the Bearer token from the Authorization header or "token" query param.
     *
     * @param request HttpServletRequest
     * @return The extracted JWT token string
     * @throws UserAuthenticationException if token is missing or invalid
     */
    public static String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // Fallback: check token query parameter
        if (!StringUtils.hasText(authHeader)) {
            String tokenParam = request.getParameter("token");
            if (StringUtils.hasText(tokenParam)) {
                authHeader = "Bearer " + tokenParam;
            }
        }

        // Extract token from "Bearer xxx"
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            if (StringUtils.hasText(token)) {
                return token;
            }
        }

        throw new UserAuthenticationException("Authentication failed, Token missing.");
    }
}
