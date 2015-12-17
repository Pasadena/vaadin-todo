package com.example.vaadintodo.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class TodoModal extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1861736808815075977L;

	public TodoModal(final String title) {
		super(title);
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
		
		final TextField nameButton = new TextField("Name:");
		nameButton.setRequired(true);
		formLayout.addComponent(nameButton);
		formLayout.addComponent(new TextArea("Summary:"));
		formLayout.addComponent(new DateField("Due date:"));
		
		final HorizontalLayout buttonRow = new HorizontalLayout();
		formLayout.addComponent(buttonRow);
		
		final Button saveButton = new Button("Save", event -> close());
		final Button closeButton = new Button("Close", event -> close());
		buttonRow.addComponent(saveButton);
		buttonRow.addComponent(closeButton);
		
		return formLayout;
	}
	
}
