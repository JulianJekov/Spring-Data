package org.example._05_springdataintroexercises.repositories;

import org.example._05_springdataintroexercises.entities.Author;
import org.example._05_springdataintroexercises.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByReleaseDateAfter(LocalDate releaseDate);
    List<Book> findAllByAuthorOrderByReleaseDateDescTitle(Author author);
}
