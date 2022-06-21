package com.dev.crud.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_author"))
    private Author author;

    protected Book() {}

    public Book(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

    public UUID getId() {
		return id;
	}

    public Author getAuthor() {
        return author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
