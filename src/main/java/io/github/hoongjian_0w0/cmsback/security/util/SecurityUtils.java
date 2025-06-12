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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUserDetails loginUser) {
            return loginUser;
        }
        throw new IllegalStateException("User not logged in or invalid principal type");
    }

}
