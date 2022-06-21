package com.dev.crud.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByNameStartsWithIgnoreCase(String name);

    List<Book> findByAuthor(Author author);
}
