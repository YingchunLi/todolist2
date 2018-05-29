package com.yingchunli.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public List<Todo> index() {
        return todoService.getTodos();
    }

    @PostMapping("/todos")
    public Todo save(@Valid @RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }
}
