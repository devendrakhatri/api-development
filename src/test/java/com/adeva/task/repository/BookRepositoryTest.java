package com.adeva.task.repository;

import com.adeva.task.domain.Author;
import com.adeva.task.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @Before
    public void createAndInsertBook() {
        book = buildBook();

        entityManager.persist(book);
        entityManager.flush();
    }

    @Test
    public void findById() {
        //when
        Optional<Book> testBook = bookRepository.findById(book.getId());

        //then
        assertTrue(testBook.isPresent());
        assertEquals(testBook.get().getId(), book.getId());
        assertEquals(testBook.get().getAuthors().get(0).getId(), book.getAuthors().get(0).getId());
        assertEquals(testBook.get().getAuthors().get(0).getName(), book.getAuthors().get(0).getName());
    }

    @Test
    public void findByName() {
        //when
        List<Book> testBook = bookRepository.findByName(book.getName());

        //then
        assertEquals(testBook.size(), 1);
        assertEquals(testBook.get(0).getName(), book.getName());
    }

    @Test
    public void findByCountry() {
        //when
        List<Book> testBook = bookRepository.findByCountry(book.getCountry());

        //then
        assertEquals(testBook.size(), 1);
        assertEquals(testBook.get(0).getCountry(), book.getCountry());
    }

    @Test
    public void findByPublisher() {
        //when
        List<Book> testBook = bookRepository.findByPublisher(book.getPublisher());

        //then
        assertEquals(testBook.size(), 1);
        assertEquals(testBook.get(0).getPublisher(), book.getPublisher());
    }

    @Test
    public void findByReleaseDate() {
        //when
        List<Book> testBook = bookRepository.findByReleaseDate(book.getReleaseDate());

        //then
        assertEquals(testBook.size(), 1);
        assertEquals(testBook.get(0).getReleaseDate(), book.getReleaseDate());
    }

    @Test
    public void findById_notFound() {
        //when
        Optional<Book> testBook = bookRepository.findById(-9999L);

        //then
        assertFalse(testBook.isPresent());
    }

    @Test
    public void findByNameNotFound() {
        //when
        List<Book> testBook = bookRepository.findByName("Invalid");

        //then
        assertEquals(testBook.size(), 0);
    }

    @Test
    public void findByCountryNotFound() {
        //when
        List<Book> testBook = bookRepository.findByCountry("Invalid");

        //then
        assertEquals(testBook.size(), 0);
    }

    @Test
    public void findByPublisherNotFound() {
        //when
        List<Book> testBook = bookRepository.findByPublisher("Invalid");

        //then
        assertEquals(testBook.size(), 0);
    }

    @Test
    public void findByReleaseDateNotFound() {
        //when
        List<Book> testBook = bookRepository.findByReleaseDate(LocalDate.of(9999, 1, 1));

        //then
        assertEquals(testBook.size(), 0);
    }


    private Book buildBook() {
        Book book = new Book();
        book.setName("A Game of Thrones");
        book.setIsbn("12356");
        Author author = new Author();
        author.setName("An author");
        book.setAuthors(Collections.singletonList(author));
        book.setPublisher("Oyo");
        book.setCountry("USA");
        book.setNumOfPages(100);
        book.setReleaseDate(LocalDate.of(2019, 3, 1));
        return book;
    }

}