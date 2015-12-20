package com.example.vaadintodo.components;

import java.util.EnumSet;

import com.example.vaadintodo.constants.TodoPriorities;
import com.example.vaadintodo.converters.TodoPriorityConverter;
import com.example.vaadintodo.models.Todo;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class TodoModal extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1861736808815075977L;
	
	private final Todo todo;
	private FieldGroup todoFieldGroup;
	
	private static long id = 0L;

	public TodoModal(final String title, final Todo todo) {
		super(title);
		this.todo = todo;
		BeanItem<Todo> todoItem = new BeanItem<Todo>(this.todo);
		this.todoFieldGroup = new FieldGroup(todoItem);
		this.setWindowProperties();
		setContent(createForm());
	}
	
	private void setWindowProperties() {
		center();
		setModal(true);
		setClosable(false);
		setHeight("50%");
		setWidth("50%");
	}
	
	private FormLayout createForm() {
		final FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		
		final TextField nameField = new TextField("Name:");
		nameField.setRequired(true);
		nameField.setRequiredError("Value is mandatory");
		nameField.setNullRepresentation("");
		//nameField.addValidator(new NullValidator("Value is mandatory", false));
		nameField.setValidationVisible(true);
		formLayout.addComponent(nameField);
		
		final TextArea summaryField = new TextArea("Summary:");
		summaryField.setNullRepresentation("");
		formLayout.addComponent(summaryField);
		
		final DateField dueDateField = new DateField("Due date:"); 
		dueDateField.setDateFormat("dd.MM.yyyy");
		formLayout.addComponent(dueDateField);
		
		final ComboBox priorityList = new ComboBox("Priority:");
		priorityList.addItems(EnumSet.allOf(TodoPriorities.class));
		priorityList.setConverter(new TodoPriorityConverter());
		formLayout.addComponent(priorityList);
		
		final HorizontalLayout buttonRow = new HorizontalLayout();
		formLayout.addComponent(buttonRow);
		
		final Button saveButton = new Button("Save", event -> save());
		final Button closeButton = new Button("Close", event -> close());
		buttonRow.addComponent(saveButton);
		buttonRow.addComponent(closeButton);

		todoFieldGroup.bind(nameField, "name");
		todoFieldGroup.bind(summaryField, "summary");
		todoFieldGroup.bind(dueDateField, "dueDate");
		todoFieldGroup.bind(priorityList, "priority");
		
		return formLayout;
	}
	
	public void save() {
		try {
			todoFieldGroup.commit();
			if(todo.getId() == null) {
				todo.setId(++id);
			}
			close();
			todoFieldGroup.discard();
		} catch (FieldGroup.CommitException ce) {
			Notification.show("Unexpected error happened during save", Notification.Type.ERROR_MESSAGE);
			System.out.println(ce.getCause().getMessage());
		}
	}
	
	@SuppressWarnings(value = "unchecked")
	public Todo getModifiedTodo() {
		return ((BeanItem<Todo>)todoFieldGroup.getItemDataSource()).getBean();
	}
}
