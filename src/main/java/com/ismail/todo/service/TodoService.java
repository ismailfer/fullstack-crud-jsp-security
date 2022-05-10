package com.ismail.todo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ismail.todo.model.Todo;
import com.ismail.todo.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService implements ITodoService
{
    @Autowired
    private TodoRepository repository;

    @Override
    public List<Todo> getTodosByUser(String username)
    {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Todo> getTodoById(long id)
    {
        return repository.findById(id);
    }

    @Override
    public void updateTodo(Todo todo)
    {
        repository.save(todo);
    }

    @Override
    public void deleteTodo(long id)
    {
        Optional<Todo> todoOpt = repository.findById(id);

        if (todoOpt.isPresent())
        {
            repository.delete(todoOpt.get());
        }
    }

    @Override
    public void addTodo(Todo todo)
    {
        log.info("addTodo() " + todo);
        
        repository.save(todo);
    }
    
    @Override
    public void addTodo(String username, String desc, Date targetDate, boolean isDone)
    {
        log.info("addTodo() ");
        
        Todo todo = Todo.builder().username(username).description(desc).targetDate(targetDate).isDone(isDone).build();

        log.info("addTodo() " + todo);
        
        repository.save(todo);
    }
}