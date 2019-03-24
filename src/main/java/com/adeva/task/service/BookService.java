package com.adeva.task.service;

import com.adeva.task.domain.Book;
import com.adeva.task.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Single point of contact for all book related service.
 * Encapsulate underlying storage, makes it easy to switch to another repository.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> find(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAll() {

        Iterable<Book> books = bookRepository.findAll();
        return toList(books);
    }

    public List<Book> findByName(String name) {

        Iterable<Book> books = bookRepository.findByName(name);
        return toList(books);
    }

    public List<Book> findByCountry(String country) {

        Iterable<Book> books = bookRepository.findByCountry(country);
        return toList(books);
    }

    public List<Book> findByPublisher(String publisher) {

        Iterable<Book> books = bookRepository.findByPublisher(publisher);
        return toList(books);
    }

    public List<Book> findByReleaseDate(LocalDate releaseDate) {

        Iterable<Book> books = bookRepository.findByReleaseDate(releaseDate);
        return toList(books);
    }

    public Optional<Book> patch(Long id, Book toBeReferred) {

        Optional<Book> savedBook = find(id);

        if(savedBook.isPresent()) {
            Book book = savedBook.get();
            book.copyNonNullFieldsFrom(toBeReferred); // It copies NON-NULL fields from toBeReferred to book.

            Book updatedBook = update(book);
            return Optional.of(updatedBook);
        }

        return savedBook;
    }

    private List<Book> toList(Iterable<Book> books) {
        return StreamSupport.stream(books.spliterator(), false)
                .collect(Collectors.toList());
    }
}
