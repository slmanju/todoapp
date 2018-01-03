package com.manjula.todo.service;

import com.manjula.todo.dto.TodoDto;
import com.manjula.todo.model.Todo;
import com.manjula.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Long save(TodoDto todoDto) {
        Todo saved = todoRepository.save(Todo.valueOf(todoDto));
        return saved.getId();
    }

    @Override
    public TodoDto findById(Long id) {
        Todo found = todoRepository.findOne(id);
        return found.view();
    }

    @Override
    public void update(TodoDto todoDto) {
        todoRepository.save(Todo.valueOf(todoDto));
    }

    @Override
    public void delete(Long id) {
        todoRepository.delete(id);
    }

    @Override
    public List<TodoDto> findAll() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(Todo::view).collect(Collectors.toList());
    }

}
