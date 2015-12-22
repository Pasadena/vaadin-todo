package com.example.vaadintodo.services;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadintodo.models.Todo;

public class TodoServiceImpl implements TodoService {
	
	private static long id = 0L;
	
	private List<Todo> database;
	
	public TodoServiceImpl() {
		this.database = new ArrayList<>();
	}

	@Override
	public void saveTodo(Todo todo) {
		if (todo == null) throw new IllegalArgumentException("Cannot save a Todo that is null!");
		todo.setId(++id);
		database.add(todo);
	}

	@Override
	public void updateTodo(Todo todo) {
		database.stream().filter(existingTodo -> existingTodo.getId().compareTo(todo.getId()) ==0).map(existingTodo -> {
			existingTodo = todo;
			return existingTodo;
		});
	}

}
