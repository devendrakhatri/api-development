package com.adeva.task.mapper;

import com.adeva.task.bean.IceAndFireBook;
import com.adeva.task.domain.ExternalBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class BookMapperTest {

    @Test
    public void from() {
        IceAndFireBook iceAndFireBook = buildIceAndFireBook();

        ExternalBook book = BookMapper.from(iceAndFireBook);

        assertEquals(book.getCountry(), iceAndFireBook.getCountry());
    }

    private IceAndFireBook buildIceAndFireBook() {
        IceAndFireBook book = new IceAndFireBook();

        book.setName("A Game of Thrones");
        book.setIsbn("12356");
        book.setAuthors(Collections.singletonList("An author"));
        book.setPublisher("Oyo");
        book.setCountry("USA");
        book.setNumOfPages(100);
        book.setReleaseDate(LocalDate.of(2019, 3, 1));

        return book;
    }
}