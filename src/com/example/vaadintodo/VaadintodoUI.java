package com.example.vaadintodo;

import javax.servlet.annotation.WebServlet;

import com.example.vaadintodo.components.TodoModal;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("vaadintodo")
public class VaadintodoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadintodoUI.class)
	public static class Servlet extends VaadinServlet {
	}

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
				final TodoModal createTodoModal = new TodoModal("Create a new Todo");
				UI.getCurrent().addWindow(createTodoModal);
			}
		});
		content.addComponent(button);
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
}