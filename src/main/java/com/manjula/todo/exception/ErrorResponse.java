package com.manjula.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @Builder
public class ErrorResponse {

    private int errorCode;
    private String errorMessage;
    private List<String> errors;

    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = new ArrayList<>();
    }

}
