package com.ismail.todo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ismail.todo.model.Todo;


public interface ITodoService
{
    List<Todo> getTodosByUser(String username);
    
    Optional<Todo> getTodoById(long id);
    
    void updateTodo(Todo todo);
    

    void deleteTodo(long id);
    
    void addTodo(Todo todo);
    
    void addTodo(String name, String desc, Date targetDate, boolean isDone);    
    
}
