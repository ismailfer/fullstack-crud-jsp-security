package com.ismail.todo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "todos")
public class Todo
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    @Size(min = 10, message = "Enter at least 10 characters")
    private String description;

    private Date targetDate;
    
    private boolean isDone;

    public Todo(String username, @Size(min = 10, message = "Enter at least 10 characters") String description, Date targetDate, boolean isDone)
    {
        super();
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.isDone = isDone;
    }
    
    
    public boolean getIsDone()
    {
        return isDone;
    }
    
    public void setIsDone(boolean isDone)
    {
        this.isDone = isDone;
    }
}
