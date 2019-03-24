package com.adeva.task.api.rest;

import com.adeva.task.constants.Status;
import com.adeva.task.domain.Book;
import com.adeva.task.domain.RestResponse;
import com.adeva.task.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Provides all REST APIs for Book.
 */
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public RestResponse list() {

        List<Book> books = bookService.findAll();

        return new RestResponse(HttpStatus.OK, Status.SUCCESS, books);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse findById(@PathVariable Long id) {

        Optional<Book> books = bookService.find(id);
        return books
                .map(book -> new RestResponse(HttpStatus.OK, Status.SUCCESS, book))
                .orElseGet(() -> new RestResponse(HttpStatus.OK, Status.SUCCESS, Collections.emptyList()));

    }

    @RequestMapping(method = RequestMethod.GET, params = "name")
    public RestResponse findByName(@RequestParam String name) {

        List<Book> books = bookService.findByName(name);

        return new RestResponse(HttpStatus.OK, Status.SUCCESS, books);
    }

    @RequestMapping(method = RequestMethod.GET, params = "country")
    public RestResponse findByCountry(@RequestParam String country) {

        List<Book> books = bookService.findByCountry(country);

        return new RestResponse(HttpStatus.OK, Status.SUCCESS, books);
    }

    @RequestMapping(method = RequestMethod.GET, params = "publisher")
    public RestResponse findByPublisher(@RequestParam String publisher) {

        List<Book> books = bookService.findByPublisher(publisher);

        return new RestResponse(HttpStatus.OK, Status.SUCCESS, books);
    }

    @RequestMapping(method = RequestMethod.GET, params = "release_date")
    public RestResponse findByReleaseDate(@RequestParam(name = "release_date") Integer releaseDate) {

        // Parse from YYYYMMDD to LocalDate
        LocalDate date = LocalDate.parse(releaseDate.toString(), BASIC_ISO_DATE);

        List<Book> books = bookService.findByReleaseDate(date);

        return new RestResponse(HttpStatus.OK, Status.SUCCESS, books);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public RestResponse update(@PathVariable Long id, @RequestBody Book book) {

        Optional<Book> existingBook = bookService.find(id);
        String name = existingBook.map(Book::getName).orElse("N/A"); // Need to retain name for message.

        Optional<Book> updatedBook = bookService.patch(id, book);
        if(updatedBook.isPresent()) {

            String message = "The book " + name + " was updated successfully";
            return new RestResponse(HttpStatus.OK, Status.SUCCESS, message, updatedBook);

        } else {
            String message = "No book not found with id:" + id;
            return new RestResponse(HttpStatus.NOT_FOUND, Status.FAILURE, message, Collections.emptyList());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable Long id) {

        Optional<Book> existingBook = bookService.find(id);
        if(existingBook.isPresent()) {

            String name = existingBook.get().getName(); // Need to retain name for message.
            bookService.delete(id);

            String message = "The book " + name + " was deleted successfully";
            return new RestResponse(HttpStatus.NO_CONTENT, Status.SUCCESS, message, Collections.emptyList());

        } else {
            String message = "No book not found with id " + id;
            return new RestResponse(HttpStatus.NOT_FOUND, Status.FAILURE, message, Collections.emptyList());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@RequestBody Book book) {

        // Return BAD_REQUEST if book is null OR any of the field is null.
        if(book == null || book.isAnyFieldNull()) {
            return new RestResponse(HttpStatus.BAD_REQUEST, Status.FAILURE, "Bad inputs", Collections.emptyList());
        }

        Book savedBook = bookService.create(book);
        savedBook.setId(null); // As per requirement, id is not part of response. Hence, setting it null to get excluded.

        // Requirement is to array of book - with key as 'book'.
        List<Map<String, Book>> customResponse = Collections.singletonList(
                Collections.singletonMap("book", savedBook)
        );

        return new RestResponse(HttpStatus.CREATED, Status.SUCCESS, customResponse);
    }
}
