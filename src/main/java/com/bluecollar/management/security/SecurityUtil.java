package com.bluecollar.management.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getLoggedInUserId() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal)) {
            throw new RuntimeException("Unauthenticated request");
        }

        UserPrincipal principal =
                (UserPrincipal) auth.getPrincipal();

        return principal.getUserId();
    }
}
