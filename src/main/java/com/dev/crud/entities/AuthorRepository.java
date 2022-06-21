package com.dev.crud.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    List<Author> findByNameStartsWithIgnoreCase(String name);

    List<Author> findByBooks(int books);
}
