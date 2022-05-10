package com.ismail.todo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller("error")
@Slf4j
public class ErrorController
{
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex)
    {
        ModelAndView mv = new ModelAndView();
        
        mv.addObject("exception", ex.getLocalizedMessage());
        mv.addObject("url", request.getRequestURL());
        
        mv.setViewName("error");
        
        log.error("handleException: " + ex.getMessage(), ex);
        
        return mv;
    }
}
