package org.example.springdataautomappingobjects.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.*;
import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.DESCRIPTION_NOT_VALID;

public class GameEditDto {

    private String title;

    private BigDecimal price;

    private float size;

    private String trailer;


    private String imageUrl;

    private String description;

    private LocalDate releaseDate;

    public GameEditDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        boolean isTitleValid = Pattern.matches(TITLE_PATTERN, title);

        if (!isTitleValid) {
            throw new IllegalArgumentException(TITLE_NOT_VALID_MESSAGE);
        }
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(PRICE_IS_NEGATIVE);
        }
        this.price = price;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {

        if (size < 0) {
            throw new IllegalArgumentException(SIZE_IS_NEGATIVE);
        }
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {

        if (trailer.length() != 11) {
            throw new IllegalArgumentException(TRAILER_NOT_VALID);
        }
        this.trailer = trailer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        boolean isValidUrl = Pattern.matches(IMAGE_URL_PATTERN, imageUrl);

        if(!isValidUrl) {
            throw new IllegalArgumentException(URL_NOT_VALID);
        }

        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description.length() < 20) {
            throw new IllegalArgumentException(DESCRIPTION_NOT_VALID);
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
