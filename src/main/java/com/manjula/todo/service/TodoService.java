package com.manjula.todo.service;

import com.manjula.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {

    Long save(TodoDto todoDto);

    TodoDto findById(Long id);

    void update(Long id, TodoDto todoDto);

    void delete(Long id);

    List<TodoDto> findAll();

}
