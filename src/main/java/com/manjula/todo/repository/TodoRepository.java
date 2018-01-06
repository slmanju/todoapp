package com.manjula.todo.repository;

import com.manjula.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findById(Long id);

    @Query("select todo from Todo todo")
    Optional<List<Todo>> findAllTodos();

}
