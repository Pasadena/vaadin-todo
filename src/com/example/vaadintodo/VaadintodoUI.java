package com.example.vaadintodo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.example.vaadintodo.components.TodoModal;
import com.example.vaadintodo.converters.TodoPriorityIdToNameConverter;
import com.example.vaadintodo.models.Todo;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
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
	
	private BeanContainer<Long, Todo> todos;
	private Table todoList;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setWidth(80, Unit.PERCENTAGE);
		layout.addStyleName("main-content");
		setContent(layout);
		
		final Component header = getHeader();
		layout.addComponent(header);
		layout.setExpandRatio(header, 1);
		
		final Component content = getMainContent();
		layout.addComponent(content);
		layout.setExpandRatio(content, 2);
		
		final Component footer = getFooter();
		layout.addComponent(footer);
		layout.setExpandRatio(footer, 1);
		layout.setComponentAlignment(header, Alignment.TOP_CENTER);
		layout.setComponentAlignment(footer, Alignment.TOP_CENTER);
	}
	
	private VerticalLayout getHeader() {
		final VerticalLayout header = new VerticalLayout();
		header.setSizeFull();
		header.setId("header");
		
		final Label headerText = new Label("TODOS: Get your stuff together");
		headerText.addStyleName("page-header");
		header.setWidth(null);
		header.addComponent(headerText);
		header.setComponentAlignment(headerText, Alignment.MIDDLE_CENTER);
		return header;
	}
	
	protected VerticalLayout getMainContent() {
		final VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		Button button = new Button("Create new item");
		button.addStyleName("primary");
		button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				openModal("Create a todo", new Todo());
				
			}
		});
		content.addComponent(button);
		content.addComponent(getTodoList());
		return content;
	}
	
	private void openModal(final String title, final Todo modalEntity) {
		final TodoModal todoModal = new TodoModal(title, modalEntity);
		UI.getCurrent().addWindow(todoModal);
		todoModal.addCloseListener(new Window.CloseListener() {
			@Override
			public void windowClose(CloseEvent event) {
				addTodoToList(modalEntity);
			}
		});
	}
	
	private HorizontalLayout getFooter() {
		final HorizontalLayout footer = new HorizontalLayout();
		final Link viewSourcesLink = new Link("View sources in Github", new ExternalResource("https://github.com/Pasadena/vaadin-todo"));
		viewSourcesLink.setTargetName("_blank");
		viewSourcesLink.setWidth(null);
		viewSourcesLink.addStyleName("page-footer");
		footer.addComponent(viewSourcesLink);
		footer.setComponentAlignment(viewSourcesLink, Alignment.MIDDLE_CENTER);
		return footer;
	}
	
	private Table getTodoList() {
		this.todoList = new Table("The todos", this.getTodoContainer());
		this.todoList.setWidth(80, Unit.PERCENTAGE);
		if(todoList.getItemIds().isEmpty()) {
			todoList.setVisible(false);
		}
		todoList.setPageLength(0);
		
		todoList.setColumnHeader("name", "Name");
		todoList.setColumnHeader("summary", "Summary");
		todoList.setColumnHeader("dueDate", "Due Date");
		todoList.setColumnHeader("priority", "Priority");
		todoList.setColumnHeader("status", "Status");
		todoList.setVisibleColumns("status", "name", "summary", "priority", "dueDate");
		
		todoList.setStyleName("todo-table");
		
		todoList.setConverter("priority", new TodoPriorityIdToNameConverter());
		todoList.setConverter("dueDate", new StringToDateConverter() {
			@Override
			public DateFormat getFormat(Locale locale){
				return new SimpleDateFormat("dd.MM.yyyy");
			}
			
		});
		todoList.addGeneratedColumn("Edit", new Table.ColumnGenerator() {
			
			@Override
			@SuppressWarnings(value = "unchecked")
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final Button button = new Button("Edit");
				button.addStyleName("primary");
				BeanContainer<String, Todo> tableContainer = (BeanContainer<String, Todo>)source.getContainerDataSource();
				button.addClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						openModal("Edit todo", tableContainer.getItem(itemId).getBean());
						
					}
				});
				return button;
			}
		});
		todoList.addGeneratedColumn("Delete", new Table.ColumnGenerator() {
			
			@Override
			@SuppressWarnings(value = "unchecked")
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final Button button = new Button("Delete", event -> {
					BeanContainer<String, Todo> tableContainer = (BeanContainer<String, Todo>)source.getContainerDataSource();
					Todo todo = tableContainer.getItem(itemId).getBean();
					tableContainer.removeItem(itemId);
					todo = null;
					source.setVisible((!tableContainer.getItemIds().isEmpty()));
					todoList.refreshRowCache();
				});
				button.addStyleName("danger");
				return button;
			}
		});
		
		todoList.addGeneratedColumn("Mark completed", new Table.ColumnGenerator() {
			
			@Override
			@SuppressWarnings(value = "unchecked")
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final Button button = new Button("Complete", event -> {
					BeanContainer<String, Todo> tableContainer = (BeanContainer<String, Todo>)source.getContainerDataSource();
					Todo todo = tableContainer.getItem(itemId).getBean();
					todo.setStatus(Todo.TodoStatuses.COMPLETED.getName());
					todoList.refreshRowCache();
				});
				button.addStyleName("primary");
				return button;
			}
		});
		return todoList;
	}
	
	private BeanContainer<Long, Todo> getTodoContainer() {
		if(todos != null) {
			return todos;
		}
		todos = new BeanContainer<Long, Todo>(Todo.class);
		todos.setBeanIdProperty("id");
		return todos;
	}
	
	private void addTodoToList(Todo row) {
		if(getTodoContainer().containsId(row.getId())) {
			todoList.refreshRowCache();
		} else {
			getTodoContainer().addBean(row);
		}
		if(!todoList.isVisible()) {
			todoList.setVisible(true);
		}
	}
	
}