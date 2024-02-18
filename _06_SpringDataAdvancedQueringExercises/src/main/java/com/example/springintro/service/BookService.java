package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBooksTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllBooksTitlesByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllBooksWithPriceLowerThanOrHigherThan(float lowerPrice, float higherPrice);

    List<String> findAllBookTitlesByReleaseDataNotIn(int year);

    List<Book> findAllBooksBeforeReleaseData(String date);

    List<Book> findAllTitlesContains(String lettersToContain);

    List<Book> findAllByAuthorLastNameStartsWith(String lettersToStartWith);

    int countAllBooksWithTitleLongerThan(int length);


    BookSummary findBookByTitle(String title);


    int increaseBooksCopiesAfter(String date, int amount);

    int removeBooksWithCopiesLessThan(int copies);
}
