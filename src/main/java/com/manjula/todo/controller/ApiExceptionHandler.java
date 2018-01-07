package com.manjula.todo.controller;

import com.manjula.todo.exception.ErrorResponse;
import com.manjula.todo.exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ErrorResponse handleBadRequest(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> errors = bindingResult.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), errors);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ErrorResponse handleMessageNotReadable(HttpMessageNotReadableException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Required request body is missing");
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public ErrorResponse handleNoHandler(NoHandlerFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public ErrorResponse handleMethodNotSupported(Exception exception) {
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), exception.getMessage());
    }

    @ExceptionHandler(value = { BadCredentialsException.class, AuthenticationException.class})
    public ErrorResponse handleUnauthorized(BadCredentialsException exception) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(value = { Exception.class })
    public ErrorResponse handleInternalError(Exception exception) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

}
