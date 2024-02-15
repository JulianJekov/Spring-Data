package org.example._05_springdataintroexercises;

import org.example._05_springdataintroexercises.entities.Author;
import org.example._05_springdataintroexercises.entities.Book;
import org.example._05_springdataintroexercises.repositories.AuthorRepository;
import org.example._05_springdataintroexercises.repositories.BookRepository;
import org.example._05_springdataintroexercises.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAuthors();
//        this.seedService.seedCategories();
//        this.seedService.seedBooks();

//        booksAfter2000();
//        allAuthorsWithBookBefore1990();

//        allAuthorsOrderByBooksDesc();

//        findBooksByAuthor();
    }

    private void findBooksByAuthor() {
        List<Book> allByAuthor = bookRepository.findAllByAuthorOrderByReleaseDateDescTitle(findAuthorByFirstAndLastName());

        allByAuthor.forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));

    }

    private Author findAuthorByFirstAndLastName() {
        String firstName = "George";
        String lastName = "Powell";

        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
    }

    private void allAuthorsOrderByBooksDesc() {
        List<Author> authors = this.authorRepository.findAll();
        authors
                .stream()
                .sorted((a, b) -> b.getBooks().size() - a.getBooks().size())
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName() + " " + a.getBooks().size()));

    }

    private void allAuthorsWithBookBefore1990() {
        LocalDate before1990 = LocalDate.of(1990, 1, 1);
        Set<Author> authors = this.authorRepository.findByBooksReleaseDateBefore(before1990);
        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void booksAfter2000() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);
        List<Book> books = bookRepository.findByReleaseDateAfter(year2000);

        books.forEach(b -> System.out.println(b.getTitle()));
    }

}
