package com.example.testapp.filter;

import com.example.testapp.SecurityConstants;
import com.example.testapp.provider.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if(request.getMethod().equalsIgnoreCase(SecurityConstants.OPTIONS_HTTP_METHOD)){
        response.setStatus(HttpStatus.OK.value());
    }
    else{
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_HEADER)){
            filterChain.doFilter(request,response);
            return;
        }
        else{
            String token = authorizationHeader.substring(SecurityConstants.TOKEN_HEADER.length());
            String username = jwtTokenProvider.getSubject(token);
            if(jwtTokenProvider.isTokenValid(username,token)){
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username,authorities,request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                SecurityContextHolder.clearContext();
            }
        }
    }
    filterChain.doFilter(request,response);
    }
}
