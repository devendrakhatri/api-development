package com.adeva.task.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.util.List;

/**
 * Representing book received from external api.
 *
 * NOTE: Let's not keep same book object for internal book, for extensibility purpose.
 */
@JsonPropertyOrder({"name", "isbn", "authors", "number_of_pages", "publisher", "country", "release_date"})
public class ExternalBook {

    // Default names in JSON
    private String name;
    private String isbn;
    private List<String> authors;
    private String publisher;
    private String country;

    // Custom names in JSON
    @JsonProperty("number_of_pages")
    private int numOfPages;
    @JsonProperty("release_date")
    private LocalDate releaseDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
