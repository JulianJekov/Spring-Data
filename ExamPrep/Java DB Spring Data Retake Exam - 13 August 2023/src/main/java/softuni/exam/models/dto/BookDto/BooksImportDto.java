package softuni.exam.models.dto.BookDto;

import softuni.exam.models.entity.Genre;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BooksImportDto {

    @Size(min = 3, max = 40)
    private String author;

    private boolean available;

    @Size(min = 5)
    private String description;

    private Genre genre;

    @Size(min = 3, max = 40)
    private String title;

    @Positive
    private Double rating;

    public BooksImportDto() {
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }
}
