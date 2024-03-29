package org.example.bookshopsystem.repositories;


import org.example.bookshopsystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Set<Author> findByBooksReleaseDateBefore(LocalDate publishDate);
    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
