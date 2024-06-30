package com.miniconomy.commercial_bank_service.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class JwtUtil {

    public String matchToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString("username");
    }
}
