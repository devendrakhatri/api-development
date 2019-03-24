package com.adeva.task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Helper class to make REST calls to external service.
 * Open for enhancements like retries, timeouts, etc.
 */
@Component
public class HttpClientHelper {

    private static final Logger log = LogManager.getLogger(HttpClientHelper.class);

    private RestTemplate restTemplate;
    private HttpEntity<String> entity;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        entity = new HttpEntity<String>("parameters", headers);
    }

    public <T> T get(String url, Class<T> responseClass) {

        log.info("Executing GET for url {}", url);
        URI uri = URI.create(url);

        ResponseEntity<T> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, responseClass);
        return exchange.getBody();
    }

    public static String encode(String bookName) {
        return UriUtils.encode(bookName, StandardCharsets.UTF_8);
    }

}
