package com.manjula.todo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manjula.todo.dto.JwtDto;
import com.manjula.todo.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            try {
                JwtDto jwtDto = JwtUtils.parseToken(requestHeader.substring(7));
                setSecurityContext(jwtDto);
            } catch (ExpiredJwtException exception) {
                writeError((HttpServletResponse) response);
                return;
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        chain.doFilter(request, response);
    }

    private void setSecurityContext(JwtDto jwtDto) {
        if (jwtDto != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String role = jwtDto.getRole();
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(() -> role);
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            jwtDto.getUsername(), null, grantedAuthorities));
        }
    }

    private void writeError(HttpServletResponse response) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpServletResponse.SC_UNAUTHORIZED)
                .errorMessage("Token Expired").build();

        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().println(mapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
