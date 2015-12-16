package com.example.vaadintodo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
		setContent(layout);

		
		layout.addComponent(getHeader());
		layout.addComponent(getMainContent());
		layout.addComponent(getFooter());
	}
	
	private HorizontalLayout getHeader() {
		final HorizontalLayout header = new HorizontalLayout();
		header.addComponent(new Label("TODOS: Get your stuff together"));
		return header;
	}
	
	protected VerticalLayout getMainContent() {
		final VerticalLayout content = new VerticalLayout();
		Button button = new Button("Create new item");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				content.addComponent(new Label("This one will open Create Todo -modal"));
			}
		});
		content.addComponent(button);
		return content;
	}
	
	private HorizontalLayout getFooter() {
		final HorizontalLayout footer = new HorizontalLayout();
		final Link viewSourcesLink = new Link("View sources in Github", new ExternalResource("https://github.com/Pasadena/vaadin-todo"));
		viewSourcesLink.setTargetName("_blank");
		footer.addComponent(viewSourcesLink);
		return footer;
	}

}