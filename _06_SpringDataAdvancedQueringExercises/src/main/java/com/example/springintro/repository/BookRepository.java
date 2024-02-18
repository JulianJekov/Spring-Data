package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle
            (String author_firstName, String author_lastName);

    @Query("select b.title from Book b where b.ageRestriction = :restriction")
    List<String> findBookTitleByAgeRestriction(AgeRestriction restriction);

    @Query("select b.title from Book b where b.editionType = :editionType and b.copies < :copies")
    List<String> findBookTitleByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal higherPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllBookByTitleContains(String lettersToContain);

    List<Book> findAllByAuthorLastNameStartsWith(String lettersToStartWith);

    @Query("select count(b) from Book b where length(b.title) > :length")
    int countBooksByTitleLengthMoreThan(int length);

    @Query("select b.title as title, b.editionType as editionType, b.ageRestriction as ageRestriction, " +
            "b.price as price from Book b where b.title = :title")
    BookSummary findBookByTitle(String title);


    @Query("update Book b set b.copies = b.copies + :amount where b.releaseDate > :after")
    @Modifying
    int increaseBooksCopiesAfter(LocalDate after, int amount);

    @Modifying
    int deleteAllByCopiesLessThan(int copies);
}
