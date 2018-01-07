package com.manjula.todo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manjula.todo.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedHandler implements AuthenticationEntryPoint {

    private ObjectMapper mapper = new ObjectMapper();

    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException)
            throws IOException, ServletException {

        String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String message = (authorizationToken != null) ? "Invalid token" : authenticationException.getMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpServletResponse.SC_UNAUTHORIZED)
                .errorMessage(message).build();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(mapper.writeValueAsString(errorResponse));

    }

}
