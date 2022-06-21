package com.dev.crud;

import com.dev.crud.entities.*;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class AuthorEditor extends VerticalLayout implements KeyNotifier {

	private final AuthorRepository repository;
	private final BookRepository bookRepository;

	/* The currently edited author */
	private Author author;

	/* Field to edit properties in Author entity */
	TextField name = new TextField("Name");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Author> binder = new Binder<>(Author.class);
	private ChangeHandler changeHandler;

	@Autowired
	public AuthorEditor(AuthorRepository repository, BookRepository bookRepository) {
		this.repository = repository;
		this.bookRepository = bookRepository;

		add(name, actions);

		// Bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());

		// Wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editAuthor(author));
		setVisible(false);
	}

	void delete() {
		try {
			List<Book> authorBooks = bookRepository.findByAuthor(author);
			for (Book authorBook : authorBooks) {
				bookRepository.delete(authorBook);
			}
		} catch (org.springframework.dao.InvalidDataAccessApiUsageException e) {}
		repository.delete(author);
		changeHandler.onChange();
	}

	void save() {
		repository.save(author);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editAuthor(Author a) {
		if (a == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = a.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			author = repository.findById(a.getId()).get();
		}
		else {
			author = a;
		}
		cancel.setVisible(persisted);

        // name.setReadOnly(false);

		// Bind author properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(author);

		setVisible(true);

		// Focus name initially
		name.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete is clicked
		changeHandler = h;
	}

}
