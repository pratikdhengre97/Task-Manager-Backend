package com.yourapp.taskmanager.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);


            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetails = userDetailsService.loadUserByUsername(email);

                List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
                if(role != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                }

                var auth = new org.springframework.security.authentication
                        .UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    } catch (Exception e) {
        System.out.println("JWT ERROR: " + e.getMessage());
    }

        filterChain.doFilter(request, response);
    }

    private static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }
}