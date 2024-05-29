package com.lys.schedulemanagement.jwt;

import com.lys.schedulemanagement.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic="JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private UserDetailsServiceImpl userDetailsService;
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.getTokenFromRequest(request);
        if(token != null && jwtTokenProvider.validateToken(token)){
            Claims info = jwtTokenProvider.getUserInfoFromToken(token);
            try{
                setAuthentication(info.getSubject());
            }catch (Exception e){
                logger.error(e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
