package com.adeva.task.repository;

import com.adeva.task.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides all CRUD operations for Book table.
 * Uses Spring's CrudRepository interface for auto code generation.
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByName(String name);

    List<Book> findByCountry(String country);

    List<Book> findByPublisher(String publisher);

    List<Book> findByReleaseDate(LocalDate releaseDate);
}
