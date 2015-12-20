package com.example.vaadintodo.constants;

import java.util.HashMap;
import java.util.Map;

public enum TodoPriorities {
	
	LOW(1, "Low"),
	NORMAL(2, "Normal"),
	HIGH(3, "High");

	private int id;
	private String name;
	private TodoPriorities(int id, String name) {
		this.id = id;
		this.name= name;
	}
	
	private static Map<Integer, TodoPriorities> valueMap = new HashMap<>();
	private static Map<String, TodoPriorities> valueByNameMap = new HashMap<>();
	static {
		for(TodoPriorities todoPriorities: TodoPriorities.values()) {
			valueMap.put(todoPriorities.id, todoPriorities);
			valueByNameMap.put(todoPriorities.name, todoPriorities);
		}
	}
	
	public static TodoPriorities getById(final Integer id) {
		if(id == null) return null;
		return valueMap.get(id);
	}
	
	public static TodoPriorities getByName(final String name) {
		if(name == null) return null;
		return valueByNameMap.get(name);
	}

	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}
}
