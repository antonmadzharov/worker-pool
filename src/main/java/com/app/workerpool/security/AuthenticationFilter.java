package com.app.workerpool.security;


import com.app.workerpool.exceptions.AuthenticationExceptionImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            Authentication authentication = AuthenticationServiceImpl.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureException | MalformedJwtException exception){
            throw new AuthenticationExceptionImpl("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationExceptionImpl("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationExceptionImpl("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationExceptionImpl("JWT claim string is empty");
        }

        filterChain.doFilter(request, response);
    }
}
