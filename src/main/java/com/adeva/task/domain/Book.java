package com.adeva.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@JsonPropertyOrder({"id", "name", "isbn", "authors", "number_of_pages", "publisher", "country", "release_date"})
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL) // Include in JSON response only if non-null.
    private Long id;

    private String name;
    private String isbn;
    private String publisher;
    private String country;

    // Custom names in JSON
    @JsonProperty("number_of_pages")
    private Integer numOfPages;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @OneToMany(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors;

    public void copyNonNullFieldsFrom(Book from) {
        this.id = from.id == null ? this.id : from.id;
        this.name = from.name == null ? this.name : from.name;
        this.isbn = from.isbn == null ? this.isbn : from.isbn;
        this.authors = from.authors == null ? this.authors : from.authors;
        this.publisher = from.publisher == null ? this.publisher : from.publisher;
        this.country = from.country == null ? this.country : from.country;
        this.numOfPages = from.numOfPages == null ? this.numOfPages : from.numOfPages;
        this.releaseDate = from.releaseDate == null ? this.releaseDate : from.releaseDate;
    }

    @JsonIgnore
    public boolean isAnyFieldNull() {
        return Stream.of(name, isbn, authors, publisher, country, numOfPages, releaseDate)
                .allMatch(Objects::isNull);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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

    public Integer getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(Integer numOfPages) {
        this.numOfPages = numOfPages;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
