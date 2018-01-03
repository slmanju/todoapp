package com.manjula.todo.model;

import com.manjula.todo.dto.TodoDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "todos")
@Entity
public class Todo {

    private Long id;
    private String title;
    private String summary;

    public TodoDto view() {
        TodoDto dto = new TodoDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

    public static Todo valueOf(TodoDto dto) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(dto, todo);
        return todo;
    }

}
