package com.manjula.todo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class TodoDto {

    private Long id;
    @NotEmpty(message = "Title can not be empty")
    private String title;
    @NotEmpty(message = "Summary can not be empty")
    @Length(min = 10, message = "Summary should be at least {min} characters long")
    private String summary;

}
