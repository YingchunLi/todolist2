package com.yingchunli.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(SpringRunner.class)
public class TodoServiceImplTest {

    @TestConfiguration
    static class TodoServiceImplTestContextConfiguration {

        @Bean
        public TodoService todoService() {
            return new TodoServiceImpl();
        }
    }

    @Autowired
    TodoService todoService;

    @MockBean
    TodoRepository todoRepository;

    @Test
    public void getTodosCallsFindAllMethodsOfRepositoryAndReturnssAListOfTodo() {

        given(todoRepository.findAll()).willReturn(Arrays.asList(
                new Todo(
                        "milk",
                        "remember the milk",
                        Date.from(Instant.parse("2018-12-31T00:00:00.000Z")),
                        Todo.Status.Pending
                )
        ));

        List<Todo> todoList = todoService.getTodos();

        then(todoRepository).should().findAll();

        assertThat(todoList).hasSize(1);
        assertThat(todoList.get(0).getName()).isEqualTo("milk");
        assertThat(todoList.get(0).getDescription()).isEqualTo("remember the milk");
        assertThat(todoList.get(0).getDueDate()).isEqualTo(Date.from(Instant.parse("2018-12-31T00:00:00.000Z")));
        assertThat(todoList.get(0).getStatus()).isEqualTo(Todo.Status.Pending);
    }

    @Test
    public void addTodoSavesTodoToRepository() {
        Todo todo =  new Todo(
                "milk",
                "remember the milk",
                Date.from(Instant.parse("2018-12-31T00:00:00.000Z")),
                Todo.Status.Pending
        );
        assertThat(todo.getId()).isNull();

        Todo savedTodo = new Todo(
                "milk",
                "remember the milk - changed",
                Date.from(Instant.parse("2018-12-31T00:00:00.000Z")),
                Todo.Status.Pending
        );
        savedTodo.setId(UUID.randomUUID());
        given(todoRepository.save(todo)).willReturn(savedTodo);

        Todo resultTodo = todoService.addTodo(todo);

        then(todoRepository).should().save(todo);

        assertThat(resultTodo.getName()).isEqualTo("milk");
        assertThat(resultTodo.getDescription()).isEqualTo("remember the milk - changed");
        assertThat(resultTodo.getDueDate()).isEqualTo(Date.from(Instant.parse("2018-12-31T00:00:00.000Z")));
        assertThat(resultTodo.getStatus()).isEqualTo(Todo.Status.Pending);
        assertThat(resultTodo.getId()).isEqualTo(savedTodo.getId());
    }
}
