package io.github.hoongjian_0w0.cmsback.security.util;

import io.github.hoongjian_0w0.cmsback.security.LoginUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUserDetails loginUser) {
            return loginUser.getUser().getId();
        }
        throw new IllegalStateException("User not logged in or invalid principal type");
    }

    public static LoginUserDetails getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException("SecurityContext is null");
        }

        var auth = context.getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("Authentication is null");
        }

        var principal = auth.getPrincipal();
        System.out.println("Principal type: " + (principal != null ? principal.getClass().getName() : "null"));

        if (principal instanceof LoginUserDetails loginUser) {
            return loginUser;
        }

        throw new IllegalStateException("Unexpected principal: " + principal);
    }

}
