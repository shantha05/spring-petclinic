package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingController.class)
class GreetingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/greetings returns greeting message and 200 OK")
    void greet_ReturnsGreetingMessage() throws Exception {
        mockMvc.perform(get("/api/greetings")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", is("Hello, World!")));
    }

    @Test
    @DisplayName("Non-GET methods on /api/greetings return 405 Method Not Allowed")
    void nonGetMethods_ReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/api/greetings"))
            .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/greetings"))
            .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete("/api/greetings"))
            .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(patch("/api/greetings"))
            .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(options("/api/greetings"))
            .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(head("/api/greetings"))
            .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(request("TRACE", "/api/greetings"))
            .andExpect(status().isMethodNotAllowed());
    }
}