package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.AuthorNamesWithTotalCopies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndsWith(String input);

    @Query("select a.firstName as firstName, a.lastName as lastName, sum(b.copies) as copies " +
            "from Author a join a.books b group by b.author order by copies desc")
    List<AuthorNamesWithTotalCopies> findAllByBooksCopies();

    @Query(value = "call bookshop_system.udp_amount_of_written_books(:authorName)", nativeQuery = true)
    int getTotalAmountOfBooksByAuthor(String authorName);
}
