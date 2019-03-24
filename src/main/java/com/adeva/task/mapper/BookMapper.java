package com.adeva.task.mapper;

import com.adeva.task.bean.IceAndFireBook;
import com.adeva.task.domain.ExternalBook;

/**
 * Maps service-provider's book to application's ExternalBook
 */
public class BookMapper {

    /**
     * Adapter to convert external api's book to our ExternalBook
     * @param iceAndFireBook
     * @return
     */
    public static ExternalBook from(IceAndFireBook iceAndFireBook) {

        ExternalBook book = new ExternalBook();

        book.setName(iceAndFireBook.getName());
        book.setIsbn(iceAndFireBook.getIsbn());
        book.setAuthors(iceAndFireBook.getAuthors());
        book.setNumOfPages(iceAndFireBook.getNumOfPages());
        book.setPublisher(iceAndFireBook.getPublisher());
        book.setCountry(iceAndFireBook.getCountry());
        book.setReleaseDate(iceAndFireBook.getReleaseDate());

        return book;
    }
}
