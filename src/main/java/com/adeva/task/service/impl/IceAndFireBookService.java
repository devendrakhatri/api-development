package com.adeva.task.service.impl;

import com.adeva.task.bean.IceAndFireBook;
import com.adeva.task.domain.ExternalBook;
import com.adeva.task.mapper.BookMapper;
import com.adeva.task.service.ExternalBookService;
import com.adeva.task.util.HttpClientHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.adeva.task.util.HttpClientHelper.encode;

/**
 * Implements ice & Fire book api.
 */
@Service
public class IceAndFireBookService extends ExternalBookService {

    private static final Logger log = LogManager.getLogger(IceAndFireBookService.class);

    @Value("${ice.and-fire.api.base.url}")
    private String baseUrl;

    @Value("${ice.and-fire.api.books}")
    private String contextPath;

    @Autowired
    private HttpClientHelper httpClientHelper;

    @Override
    public List<ExternalBook> fetch(String bookName) {

        String url = baseUrl + contextPath + "?name=" + encode(bookName);

        IceAndFireBook[] book = httpClientHelper.get(url, IceAndFireBook[].class);

        if(book == null || book.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.stream(book)
                .map(BookMapper::from) // Convert to domain object
                .collect(Collectors.toList());
    }

}
