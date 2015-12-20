package com.example.vaadintodo.models;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Todo implements Serializable {
	
	private Long id;
	
	private String name;
	
	private String summary;
	
	private Date dueDate;
	
	private Integer priority;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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
