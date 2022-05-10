package com.ismail.todo.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ismail.todo.model.Todo;
import com.ismail.todo.service.ITodoService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains request handling methods for create, update, delete and list of Todos.
 * 
 * @author ismail
 */
@Controller
@Slf4j
public class TodoController
{
    @Autowired
    private ITodoService todoService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(formatter, false));
    }

    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String showListTodos(ModelMap model)
    {
        log.info("showListTodos() ");

        String name = getLoggedInUsername(model);
        
        model.put("todoList", todoService.getTodosByUser(name));

        return "list-todos";

    }

    private String getLoggedInUsername(ModelMap model)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
        {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model)
    {
        log.info("showAddTodoPage() ");

        model.addAttribute("todo", new Todo());

        return "todo";
    }

    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam long id)
    {
        log.info("deleteTodo() " + id);

        todoService.deleteTodo(id);

        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam long id, ModelMap model)
    {
        Todo todo = todoService.getTodoById(id).get();

        model.put("todo", todo);

        // put formatted date
        if (todo != null && todo.getTargetDate() != null)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            model.put("targetDateFormatted", formatter.format(todo.getTargetDate()));
        }
        else
        {
            model.put("targetDateFormatted", "");
        }

        return "todo";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result)
    {
        log.info("updateTodo() " + todo);

        if (result.hasErrors())
        {
            return "todo";
        }

        todo.setUsername(getLoggedInUsername(model));

        todoService.updateTodo(todo);

        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result)
    {
        log.info("addTodo() " + todo);

        if (result.hasErrors())
        {
            log.error("addTodo() got errors: " + result);

            return "todo";
        }

        todo.setUsername(getLoggedInUsername(model));

        todoService.addTodo(todo);

        return "redirect:/list-todos";
    }

}
