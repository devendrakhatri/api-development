package com.adeva.task.api.rest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String bookContentJson = "{\n" +
            "            \"name\": \"My First Book\",\n" +
            "            \"isbn\": \"123-3213243567\",\n" +
            "            \"authors\": [\n" +
            "                \"John Doe\"\n" +
            "            ],\n" +
            "            \"number_of_pages\": 350,\n" +
            "            \"publisher\": \"Acme Books\",\n" +
            "            \"country\": \"United States\",\n" +
            "            \"release_date\": \"2019-08-01\"\n" +
            "}";

    private static final AtomicBoolean booksCreated = new AtomicBoolean(false);

    @Before
    public void setup() throws Exception {

        if(booksCreated.get()) {
            return; // Books already inserted.
        }

        // Insert 3 books
        IntStream.range(0, 3).forEach(i -> insertBook());

        booksCreated.set(true);
    }

    private void insertBook() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookContentJson);

        try {
            this.mockMvc.perform(request)
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createBook() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookContentJson);

        validateCreatedBook(request);
    }

    private void validateCreatedBook(MockHttpServletRequestBuilder request) throws Exception {

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(201)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data[0].book.name", Matchers.is("My First Book")))
                .andExpect(jsonPath("$.data[0].book.isbn", Matchers.is("123-3213243567")))
                .andExpect(jsonPath("$.data[0].book.authors[0]", Matchers.is("John Doe")))
                .andExpect(jsonPath("$.data[0].book.number_of_pages", Matchers.is(350)))
                .andExpect(jsonPath("$.data[0].book.publisher", Matchers.is("Acme Books")))
                .andExpect(jsonPath("$.data[0].book.country", Matchers.is("United States")))
                .andExpect(jsonPath("$.data[0].book.release_date", Matchers.is("2019-08-01")));
    }

    @Test
    public void fetchAllBook() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books");

        validateFindBook(request);
    }

    private void validateFindBook(MockHttpServletRequestBuilder request) throws Exception {
        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data[0].name", Matchers.is("My First Book")))
                .andExpect(jsonPath("$.data[0].isbn", Matchers.is("123-3213243567")))
                .andExpect(jsonPath("$.data[0].authors[0]", Matchers.is("John Doe")))
                .andExpect(jsonPath("$.data[0].number_of_pages", Matchers.is(350)))
                .andExpect(jsonPath("$.data[0].publisher", Matchers.is("Acme Books")))
                .andExpect(jsonPath("$.data[0].country", Matchers.is("United States")))
                .andExpect(jsonPath("$.data[0].release_date", Matchers.is("2019-08-01")));
    }

    private void validateFindBookById(MockHttpServletRequestBuilder request) throws Exception {
        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.data.name", Matchers.is("My First Book")))
                .andExpect(jsonPath("$.data.isbn", Matchers.is("123-3213243567")))
                .andExpect(jsonPath("$.data.authors[0]", Matchers.is("John Doe")))
                .andExpect(jsonPath("$.data.number_of_pages", Matchers.is(350)))
                .andExpect(jsonPath("$.data.publisher", Matchers.is("Acme Books")))
                .andExpect(jsonPath("$.data.country", Matchers.is("United States")))
                .andExpect(jsonPath("$.data.release_date", Matchers.is("2019-08-01")));
    }

    @Test
    public void fetchBookById() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books/1");

        validateFindBookById(request);
    }


    @Test
    public void fetchBookByName() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?name=My First Book");

        validateFindBook(request);
    }

    @Test
    public void fetchBookByCountry() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?country=United States");

        validateFindBook(request);
    }

    @Test
    public void fetchBookByPublisher() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?publisher=Acme Books");

        validateFindBook(request);
    }

    @Test
    public void fetchBookByReleaseDate() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?release_date=20190801");

        validateFindBook(request);
    }

    @Test
    public void updateBook() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch("/api/v1/books/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number_of_pages\": 100}");

        validateUpdatedBook(request);
    }

    private void validateUpdatedBook(MockHttpServletRequestBuilder request) throws Exception {

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.message", Matchers.is("The book My First Book was updated successfully")))
                .andExpect(jsonPath("$.data.number_of_pages", Matchers.is(100)))
                .andExpect(jsonPath("$.data.name", Matchers.is("My First Book")))
                .andExpect(jsonPath("$.data.isbn", Matchers.is("123-3213243567")))
                .andExpect(jsonPath("$.data.authors[0]", Matchers.is("John Doe")))
                .andExpect(jsonPath("$.data.publisher", Matchers.is("Acme Books")))
                .andExpect(jsonPath("$.data.country", Matchers.is("United States")))
                .andExpect(jsonPath("$.data.release_date", Matchers.is("2019-08-01")));
    }

    @Test
    public void deleteBook() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/books/3");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(204)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.message", Matchers.is("The book My First Book was deleted successfully")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }

    @Test
    public void fetchBookById_NotExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books/111");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }

    @Test
    public void fetchBookByName_NotExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?name=Unknown");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }

    @Test
    public void fetchBookByCountry_NotExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?country=Unknown");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }

    @Test
    public void fetchBookByPublisher_NotExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?publisher=Unknown");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }

    @Test
    public void fetchBookByReleaseDate_NotExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/books?release_date=99990101");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(200)))
                .andExpect(jsonPath("$.status", Matchers.is("success")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }


    @Test
    public void updateBook_notExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch("/api/v1/books/111")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number_of_pages\": 100}");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(404)))
                .andExpect(jsonPath("$.status", Matchers.is("failure")))
                .andExpect(jsonPath("$.message", Matchers.is("No book not found with id:111")));
    }


    @Test
    public void deleteBook_NotExist() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/books/111");

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status_code", Matchers.is(404)))
                .andExpect(jsonPath("$.status", Matchers.is("failure")))
                .andExpect(jsonPath("$.message", Matchers.is("No book not found with id 111")))
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }
}