package com.manjula.todo.controller;

import com.manjula.todo.config.security.JwtUtils;
import com.manjula.todo.dto.JwtDto;
import com.manjula.todo.dto.LoginDto;
import com.manjula.todo.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
        // Perform the authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        );
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // hardcoded, should use a proper user service
        String token = JwtUtils.generateToken(JwtDto.builder().username("manjula").userId(1L).role("ADMIN").build());
        return ResponseEntity.ok(TokenDto.builder().token(token).build());
    }

}
