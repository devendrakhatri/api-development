package com.adeva.task.service;

import com.adeva.task.domain.ExternalBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Abstract service class to get external book, it gives flexibility to switch to another service.
 */
@Service
public abstract class ExternalBookService {

    private static final Logger LOG = LogManager.getLogger(ExternalBookService.class);

    /**
     * Handle exception
     *
     * @param name
     * @return
     */
    public List<ExternalBook> fetchBooks(String name) {

        try {
            return fetch(name); // Fetch from implementing service.

        } catch (Exception e) {
            LOG.error("Error fetching from external book service. name= " + name, e);
            return Collections.emptyList();
        }
    }

    /**
     * Contract to fetch book from external system - to be implemented by service provider.
     *
     * @param bookName
     * @return
     */
    protected abstract List<ExternalBook> fetch(String bookName);
}
