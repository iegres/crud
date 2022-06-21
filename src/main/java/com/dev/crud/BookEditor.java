package com.dev.crud;

import com.dev.crud.entities.*;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@UIScope
@Scope("prototype")
public class BookEditor extends VerticalLayout implements KeyNotifier {

	private final BookRepository repository;
	private final AuthorRepository authorRepository;

	/* The currently edited book */
	private Book book;

	/* Fields to edit properties in Book entity */
	TextField name = new TextField("Name");
	ComboBox<Author> author = new ComboBox<>("Author");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Book> binder = new Binder<>(Book.class);
	private ChangeHandler changeHandler;

	@Autowired
	public BookEditor(BookRepository repository, AuthorRepository authorRepository) {
		this.repository = repository;
		this.authorRepository = authorRepository;

		author.setItems(authorRepository.findAll());
		author.setItemLabelGenerator(Author::getName);
		author.setPreventInvalidInput(true);

		add(name, author, actions);

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
		cancel.addClickListener(e -> editBook(book));
		setVisible(false);
	}

	void delete() {
		Author bAuthor = book.getAuthor();
		if (bAuthor != null) {
			authorRepository.findById(bAuthor.getId()).ifPresent(
				bookAuthor -> {
					bookAuthor.setBooks(bookAuthor.getBooks() - 1);
					authorRepository.save(bookAuthor);
				}
			);
		}
		repository.delete(book);
		changeHandler.onChange();
	}

	void save() {
		Author bookAuthor = book.getAuthor();
		if (bookAuthor != null) {
			bookAuthor.setBooks(bookAuthor.getBooks() + 1);
			
			UUID bookId = book.getId();
			if (bookId != null) {
				Author prevAuthor = repository.findById(bookId).get().getAuthor();
				prevAuthor.setBooks(prevAuthor.getBooks() - 1);
				authorRepository.save(prevAuthor);
			}

			authorRepository.save(bookAuthor);
			repository.save(book);
			changeHandler.onChange();
		} else {
			Notification.show("Choose an Author from the list");
		}
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editBook(Book b) {
		if (b == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = b.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			book = repository.findById(b.getId()).get();
		}
		else {
			book = b;
		}
		cancel.setVisible(persisted);

		// Bind book properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(book);

		setVisible(true);

		// Focus name initially
		name.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete is clicked
		changeHandler = h;
	}

}
