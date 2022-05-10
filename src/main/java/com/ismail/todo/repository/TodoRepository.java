package com.ismail.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ismail.todo.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>
{ 
    List<Todo> findByUsername(String username);
}
