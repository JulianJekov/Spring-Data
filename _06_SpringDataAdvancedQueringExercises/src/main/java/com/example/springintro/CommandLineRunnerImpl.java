package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //seedData();
//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        //P01
        //String ageRestriction = scanner.nextLine();
        //printAllBookTitlesByAgeRestriction(ageRestriction);

        //P02
        //printAllBookTitlesByEditionTypeAndCopiesLessThan();

        //P03
        //printBookTitleAndPriceLowerThanOrHigherThan();

        //P04
        //int year = scanner.nextInt();
        //printBookTitlesByReleaseDateNotIn(year);

        //P05
        //String date = scanner.nextLine();
        //printBookTitleEditionTypeAndPriceBeforeReleaseData(date);

        //P06
        //String endsWith = scanner.nextLine();
        //printAuthorFirstAndLastNameEndsWith(input);

        //P07
        //String lettersToContain = scanner.nextLine();
        //printBookTitlesContains(lettersToContain);

        //P08
        //String lettersToStartWith = scanner.nextLine();
        //printBookTitlesAuthorNamesWhereAuthorLastNameStartsWith(lettersToStartWith);

        //P09
        //int length = scanner.nextInt();
        //printBooksCount(length);

        //P10
        //printAuthorsCopies();

        //P11
        //String title = scanner.nextLine();
        //printBookSummary(title);

        //P12
        //String date = scanner.nextLine();
        //int amount = scanner.nextInt();
        //printUpdatedBooksInfo(date, amount);

        //P13
        //printCountOfDeletedBooks();

        //P14
        //printAmountBooksByAuthor();

    }

    private void printAmountBooksByAuthor() {
        int countBooksByAuthor = this.authorService.getTotalAmountOfBooksByAuthor("Susan Castillo");
        System.out.println(countBooksByAuthor);
    }

    private void printCountOfDeletedBooks() {
        int deleteBooks = this.bookService.removeBooksWithCopiesLessThan(250);
        System.out.println(deleteBooks);
    }

    private void printUpdatedBooksInfo(String date, int amount) {
        int updatedBooks = this.bookService.increaseBooksCopiesAfter(date, amount);
        System.out.printf("%d books are released after %s, so total of %d book copies were added%n",
                updatedBooks, date, amount * updatedBooks);
    }

    private void printBookSummary(String title) {
        BookSummary bookSummary = this.bookService.findBookByTitle(title);
        System.out.printf("%s %s %s %.2f%n",
                bookSummary.getTitle(), bookSummary.getEditionType(),
                bookSummary.getAgeRestriction(), bookSummary.getPrice());
    }

    private void printAuthorsCopies() {
        authorService.findAllAuthors()
                .forEach(a -> System.out.printf("%s %s - %d%n", a.getFirstName(), a.getLastName(), a.getCopies()));
    }

    private void printBooksCount(int length) {
        int booksCount = this.bookService.countAllBooksWithTitleLongerThan(length);
        System.out.printf("There are %d books with longer title than %d symbols%n", booksCount, length);
    }

    private void printBookTitlesAuthorNamesWhereAuthorLastNameStartsWith(String lettersToStartWith) {
        this.bookService.findAllByAuthorLastNameStartsWith(lettersToStartWith)
                .forEach(b -> System.out.printf("%s (%s %s)%n",
                        b.getTitle(),
                        b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()));
    }

    private void printBookTitlesContains(String lettersToContain) {
        this.bookService.findAllTitlesContains(lettersToContain)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void printAuthorFirstAndLastNameEndsWith(String endsWith) {
        this.authorService.findAllAuthorsByFirstNameEndsWith(endsWith)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void printBookTitleEditionTypeAndPriceBeforeReleaseData(String date) {
        this.bookService.findAllBooksBeforeReleaseData(date)
                .forEach(b -> System.out.printf("%s %s %.2f%n",
                        b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    private void printBookTitlesByReleaseDateNotIn(int year) {
        this.bookService.findAllBookTitlesByReleaseDataNotIn(year)
                .forEach(System.out::println);
    }

    private void printBookTitleAndPriceLowerThanOrHigherThan() {
        this.bookService.findAllBooksWithPriceLowerThanOrHigherThan(5, 40)
                .forEach(b -> System.out.println(b.getTitle() + " " + b.getPrice()));
    }

    private void printAllBookTitlesByEditionTypeAndCopiesLessThan() {
        bookService.findAllBooksTitlesByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void printAllBookTitlesByAgeRestriction(String ageRestriction) {
        this.bookService.findAllBooksTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
