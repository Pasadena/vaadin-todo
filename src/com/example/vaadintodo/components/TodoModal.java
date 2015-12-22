package com.example.vaadintodo.components;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.example.vaadintodo.constants.TodoPriorities;
import com.example.vaadintodo.converters.TodoPriorityConverter;
import com.example.vaadintodo.models.Todo;
import com.example.vaadintodo.services.TodoService;
import com.example.vaadintodo.services.TodoServiceImpl;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.server.ErrorMessage;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
	private BeanFieldGroup<Todo> todoFieldGroup;
	private final TodoService todoService;
	
	private Label errorField;
	

	public TodoModal(final String title, final Todo todo) {
		super(title);
		this.todo = todo;
		this.todoService = new TodoServiceImpl();
		BeanItem<Todo> todoItem = new BeanItem<Todo>(this.todo);
		this.todoFieldGroup = new BeanFieldGroup<Todo>(Todo.class);
		this.todoFieldGroup.setItemDataSource(todo);
		this.setWindowProperties();
		setContent(createForm());
	}
	
	private void setWindowProperties() {
		center();
		setModal(true);
		setClosable(false);
		setHeight("80%");
		setWidth("50%");
	}
	
	private FormLayout createForm() {
		final FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		
		this.errorField = new Label("Errors occurred!", ContentMode.HTML);
		errorField.setVisible(false);
		formLayout.addComponent(errorField);
		
		final TextField nameField = new TextField("Name:");
		nameField.setNullRepresentation("");
		nameField.addValidator(new NullValidator("Value is mandatory", false));
		nameField.setValidationVisible(false);
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
			this.saveOrUpdateTodo();
			close();
			todoFieldGroup.discard();
		} catch (FieldGroup.CommitException ce) {
			this.enableValidation();
			String validationErrors = getJoinedErrorMessage();
			if(!validationErrors.isEmpty()) {
				errorField.setVisible(true);
				errorField.setValue(validationErrors);
			} else {
				Notification.show("Unexpected error happened during save", Notification.Type.ERROR_MESSAGE);
			}
			System.out.println(ce.getCause().getMessage());
			
		}
	}
	
	private void enableValidation() {
		for(Field<?> field: todoFieldGroup.getFields()) {
			((AbstractField<?>)field).setValidationVisible(true);
		}
	}
	
	private String getJoinedErrorMessage() {
		List<String> errorMessages = new ArrayList<String>();
		for(Field<?> field: todoFieldGroup.getFields()) {
			ErrorMessage errorMessage = ((AbstractField<?>)field).getErrorMessage();
			if(errorMessage != null) {
				errorMessages.add(String.format("Error in field %s %s", field.getCaption(), errorMessage.toString()));
			}
		}
		return String.join("\n", errorMessages);
	}
	
	private void saveOrUpdateTodo() {
		if(todo.getId() == null) {
			todoService.saveTodo(todo);
		} else {
			todoService.updateTodo(todo);
		}
	}
	
	/**
	@SuppressWarnings(value = "unchecked")
	public Todo getModifiedTodo() {
		return ((BeanItem<Todo>)todoFieldGroup.getItemDataSource()).getBean();
	}**/
}
