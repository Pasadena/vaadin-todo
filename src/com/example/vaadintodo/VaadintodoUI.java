package com.example.vaadintodo;

import javax.servlet.annotation.WebServlet;

import com.example.vaadintodo.components.TodoModal;
import com.example.vaadintodo.models.Todo;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

@SuppressWarnings("serial")
@Theme("vaadintodo")
public class VaadintodoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadintodoUI.class)
	public static class Servlet extends VaadinServlet {
	}
	
	private BeanContainer<String, Todo> todos;
	private Table todoList;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		setContent(layout);
		
		final Component header = getHeader();
		layout.addComponent(header);
		layout.setExpandRatio(header, 0.5f);
		
		final Component content = getMainContent();
		layout.addComponent(content);
		layout.setExpandRatio(content, 1.5f);
		
		final Component footer = getFooter();
		layout.addComponent(footer);
		layout.setExpandRatio(footer, 0.5f);
	}
	
	private HorizontalLayout getHeader() {
		final HorizontalLayout header = new HorizontalLayout();
		header.setSizeFull();
		header.setId("header");
		
		final Label headerText = new Label("TODOS: Get your stuff together");
		header.setWidth(null);
		header.addComponent(headerText);
		header.setComponentAlignment(headerText, Alignment.MIDDLE_CENTER);
		return header;
	}
	
	protected VerticalLayout getMainContent() {
		final VerticalLayout content = new VerticalLayout();
		Button button = new Button("Create new item");
		button.addClickListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				final TodoModal createTodoModal = new TodoModal("Create a new Todo", new Todo());
				UI.getCurrent().addWindow(createTodoModal);
				createTodoModal.addCloseListener(new Window.CloseListener() {
					
					@Override
					public void windowClose(CloseEvent event) {
						addTodoToList(((TodoModal)event.getComponent()).getModifiedTodo());
					}
				});
			}
		});
		content.addComponent(button);
		content.addComponent(getTodoList());
		return content;
	}
	
	private HorizontalLayout getFooter() {
		final HorizontalLayout footer = new HorizontalLayout();
		final Link viewSourcesLink = new Link("View sources in Github", new ExternalResource("https://github.com/Pasadena/vaadin-todo"));
		viewSourcesLink.setTargetName("_blank");
		viewSourcesLink.setWidth(null);
		footer.addComponent(viewSourcesLink);
		footer.setComponentAlignment(viewSourcesLink, Alignment.MIDDLE_CENTER);
		return footer;
	}
	
	private Table getTodoList() {
		this.todoList = new Table("The todos", this.getTodoContainer());
		if(todoList.getItemIds().isEmpty()) {
			todoList.setVisible(false);
		}
		todoList.setPageLength(0);
		return todoList;
	}
	
	private BeanContainer<String, Todo> getTodoContainer() {
		if(todos != null) {
			return todos;
		}
		todos = new BeanContainer<String, Todo>(Todo.class);
		todos.setBeanIdProperty("name");
		return todos;
	}
	
	private void addTodoToList(Todo row) {
		getTodoContainer().addBean(row);
		if(!todoList.isVisible()) {
			todoList.setVisible(true);
		}
	}
	
	
}