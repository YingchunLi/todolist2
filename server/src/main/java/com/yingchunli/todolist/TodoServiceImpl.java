package com.yingchunli.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> getTodos() {
        return StreamSupport.stream(todoRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    @Override
    public Todo addTodo(Todo todo) {
        // assume that name can be duplicated
        return todoRepository.save(todo);
    }
}
