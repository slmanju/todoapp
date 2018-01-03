package com.manjula.todo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "todos")
@Entity
public class Todo {

    private Long id;
    private String title;
    private String summary;

}
