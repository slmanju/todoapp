package com.manjula.todo.service;

import com.manjula.todo.dto.TodoDto;
import com.manjula.todo.exception.ResourceNotFoundException;
import com.manjula.todo.model.Todo;
import com.manjula.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<Todo> found = todoRepository.findById(id);
        return found.map(Todo::view)
                .orElseThrow(() -> new ResourceNotFoundException(id, "Todo not found"));
    }

    @Override
    public void update(Long id, TodoDto todoDto) {
        TodoDto dto = findById(id);
        dto.setId(id);
        dto.setTitle(todoDto.getTitle());
        dto.setSummary(todoDto.getSummary());
        todoRepository.save(Todo.valueOf(dto));
    }

    @Override
    public void delete(Long id) {
        Optional<Todo> found = todoRepository.findById(id);
        if (found.isPresent()) {
            todoRepository.delete(id);
        } else {
            throw new ResourceNotFoundException(id, "Todo not found");
        }
    }

    @Override
    public List<TodoDto> findAll() {
        Optional<List<Todo>> todos = todoRepository.findAllTodos();
        return todos.map(all -> all.stream().map(Todo::view).collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(null, "Todos not found"));
    }

}
