package com.example.vaadintodo.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Todo implements Serializable {
	
	private String name;
	
	private String summary;
	
	private LocalDate dueDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("{")
				.append("Name: " + this.getName() + ",")
				.append("Summary: " + this.getSummary() + ",")
				.append("Due Date: " +dueDate.toString())
				.append("}")
				.toString();
	}

}
