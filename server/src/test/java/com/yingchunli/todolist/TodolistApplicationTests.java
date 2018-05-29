package com.yingchunli.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import java.time.Instant;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
public class TodolistApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TodoRepository todoRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void givenTodos_whenGetTodos_thenRetunTodoJsonArray()
			throws Exception {

		Todo todo = new Todo(
				"milk",
				"remember the milk",
				Date.from(Instant.parse("2018-12-31T00:00:00.000Z")),
				Todo.Status.Pending
		);

		todoRepository.save(todo);

		mvc.perform(get("/api/todos")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("milk")))
				.andExpect(jsonPath("$[0].description", is("remember the milk")))
				.andExpect(jsonPath("$[0].status", is("Pending")));
	}

	@Test
	public void whenAddInvalidTodo_thenReturnError() throws Exception {
		mvc.perform(post("/api/todos")
				.content("{\"description\":\"remember the milk\", \"dueDate\":\"2018-12-31T00:00:00.000Z\", \"status\":\"Pending\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void whenAddValidTodo_thenReturnAddedTodoAsJson() throws Exception {
		mvc.perform(post("/api/todos")
				.content("{\"name\": \"milk\", \"description\":\"remember the milk\", \"dueDate\":\"2018-12-31T00:00:00.000Z\", \"status\":\"Pending\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("milk"))
				.andExpect(jsonPath("$.description").value("remember the milk"))
				.andExpect(jsonPath("$.dueDate", startsWith("2018-12-31")))
				.andExpect(jsonPath("$.status").value("Pending"));
	}

}
