package com.dev.crud;

import com.dev.crud.entities.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
@Route("/book")
@PageTitle("CRUD App | Books")
public class BookView extends VerticalLayout {

	private final BookRepository repo;

	private final BookEditor editor;

	final Grid<Book> grid;

	final TextField filter;

	private final Button addNewBtn;

	private final Button homeBtn;

	public BookView(BookRepository repo, BookEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Book.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New book", VaadinIcon.PLUS.create());
		this.homeBtn = new Button("Home");

		// Build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		Div menu = new Div(homeBtn);
		add(menu, actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("name", "author.name");
		grid.getColumnByKey("name").setHeader("Book");
		grid.getColumnByKey("author.name").setHeader("Author");

		filter.setPlaceholder("Filter by name");

		homeBtn.addClickListener(e ->
			 homeBtn.getUI().ifPresent(ui ->
				   ui.navigate("/"))
		);

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listBooks(e.getValue()));

		// Connect selected Book to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editBook(e.getValue());
		});

		// Instantiate and edit new Book the new button is clicked
		addNewBtn.addClickListener(e -> editor.editBook(new Book("")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listBooks(filter.getValue());
		});

		// Initialize listing
		listBooks(null);
	}

	// tag::listBooks[]
	private void listBooks(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listBooks[]

}
