package com.demo.controller;

import com.demo.controller.response.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {
    private MainController mainController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mainController = new MainController();
        mockMvc = standaloneSetup(mainController).build();
    }

    @Test
    public void index_whenNormal_thenReturnStatusText() throws Exception {
        mockMvc.perform(get(""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.payload", is("The application is up and running. ")));
    }
}
