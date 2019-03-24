package com.adeva.task.api.rest;

import com.adeva.task.constants.Status;
import com.adeva.task.domain.ExternalBook;
import com.adeva.task.domain.RestResponse;
import com.adeva.task.service.ExternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API to serve external book.
 */
@RestController
@RequestMapping("/api")
public class ExternalBookController {

    @Autowired
    private ExternalBookService externalBookService;

    @RequestMapping(value = "/external-books", method = RequestMethod.GET)
    public RestResponse fetchBook(@RequestParam(value = "name") String name) {

        List<ExternalBook> books = externalBookService.fetchBooks(name);

        // Wrap into app's custom RestResponse.
        return new RestResponse(HttpStatus.OK, Status.SUCCESS, books);
    }
}
