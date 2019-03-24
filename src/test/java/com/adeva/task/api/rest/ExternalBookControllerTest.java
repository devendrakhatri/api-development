package com.adeva.task.api.rest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExternalBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchBook_bookExists_findPublisher() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/external-books?name=A Game of Thrones"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].publisher", Matchers.is("Bantam Books")));
    }

    @Test
    public void fetchBook_bookNotExist_emptyData() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/external-books?name=No such book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Matchers.is(0)));
    }
}