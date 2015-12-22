package com.example.vaadintodo.services;

import com.example.vaadintodo.models.Todo;

public interface TodoService {
	
	/**
	 * Saves provided todo.
	 * @param todo
	 */
	public void saveTodo(Todo todo);
	
	/**
	 * Updates provided todo.
	 * @param todo
	 */
	public void updateTodo(Todo todo);

}
