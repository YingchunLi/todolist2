package com.yingchunli.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void givenTodos_whenGetTodos_thenRetunTodoJsonArray() throws Exception {
        Todo todo = new Todo(
                "milk",
                "remember the milk",
                Date.from(Instant.parse("2018-12-31T00:00:00.000Z")),
                Todo.Status.Pending
        );
        List<Todo> allTodos = Arrays.asList(todo);

        given(todoService.getTodos()).willReturn(allTodos);


        mvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(todo.getName())))
                .andExpect(jsonPath("$[0].description", is(todo.getDescription())))
                .andExpect(jsonPath("$[0].status", is(todo.getStatus().toString())));

        then(todoService).should().getTodos();
    }
}
