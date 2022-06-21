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
@Route("/")
@PageTitle("CRUD App | Authors")
public class MainView extends VerticalLayout {

	private final AuthorRepository repo;

	private final AuthorEditor editor;

	final Grid<Author> grid;

	final TextField filter;

	private final Button addNewBtn;

	private final Button bookBtn;

	public MainView(AuthorRepository repo, AuthorEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Author.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New author", VaadinIcon.PLUS.create());
		this.bookBtn = new Button("Books");

		// Build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		Div menu = new Div(bookBtn);
		add(menu, actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("name", "books");
		grid.getColumnByKey("name").setHeader("Author");
		grid.getColumnByKey("books").setHeader("Number of Books");
		// grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by name or books");

		bookBtn.addClickListener(e ->
			 bookBtn.getUI().ifPresent(ui ->
				   ui.navigate("/book"))
		);

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listAuthors(e.getValue()));

		// Connect selected Author to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editAuthor(e.getValue());
		});

		// Instantiate and edit new Author the new button is clicked
		addNewBtn.addClickListener(e -> editor.editAuthor(new Author("")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listAuthors(filter.getValue());
		});

		// Initialize listing
		listAuthors(null);

		// repo.save(new Author("Tom"));
		// add(new Button("Click me", e -> Notification.show(repo.findAll().toString())));
		// this.grid.addColumn(Author::getName).setHeader("Name");
	}

	// tag::listAuthors[]
	private void listAuthors(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			try {
				grid.setItems(repo.findByBooks(Integer.parseInt(filterText)));
			} catch (NumberFormatException e) {
				grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
			}
		}
	}
	// end::listAuthors[]

}
