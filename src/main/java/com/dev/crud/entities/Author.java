package com.dev.crud.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "books")
    private int books;

    protected Author() {}

    public Author(String name) {
		this.name = name;
        // this.id = UUID.randomUUID();
	}

	public String getName() {
		return name;
	}

    public UUID getId() {
		return id;
	}

    public int getBooks() {
        return books;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(int books) {
        this.books = books;
    }
}
