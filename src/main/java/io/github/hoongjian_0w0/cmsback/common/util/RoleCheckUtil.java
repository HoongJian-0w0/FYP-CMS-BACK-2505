package io.github.hoongjian_0w0.cmsback.common.util;

import io.github.hoongjian_0w0.cmsback.entity.Role;

import java.util.List;

public class RoleCheckUtil {

    public static boolean isSuperAdmin(List<String> roleCodes) {
        return roleCodes != null && roleCodes.stream()
                .anyMatch(code -> "SUPERADMIN".equalsIgnoreCase(code));
    }

    public static boolean isAdmin(List<String> roleCodes) {
        return roleCodes != null && roleCodes.stream()
                .anyMatch(code -> "ADMIN".equalsIgnoreCase(code) || "SUPERADMIN".equalsIgnoreCase(code));
    }

}
