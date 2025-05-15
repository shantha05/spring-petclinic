package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingController.class)
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/greetings without name returns default greeting")
    void getGreetingWithoutName() throws Exception {
        mockMvc.perform(get("/api/greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Hello, World!")));
    }

    @Test
    @DisplayName("GET /api/greetings with valid name returns personalized greeting")
    void getGreetingWithValidName() throws Exception {
        mockMvc.perform(get("/api/greetings").param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Hello, Alice!")));
    }

    @Test
    @DisplayName("GET /api/greetings with empty name returns default greeting")
    void getGreetingWithEmptyName() throws Exception {
        mockMvc.perform(get("/api/greetings").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("Hello, World!")));
    }

    @Test
    @DisplayName("GET /api/greetings with name exceeding 50 chars returns 400")
    void getGreetingWithLongName() throws Exception {
        String longName = "a".repeat(51);
        mockMvc.perform(get("/api/greetings").param("name", longName))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", containsString("maximum length")));
    }

    @Test
    @DisplayName("GET /api/greetings with invalid characters returns 400")
    void getGreetingWithInvalidCharacters() throws Exception {
        mockMvc.perform(get("/api/greetings").param("name", "Alice!@#"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", containsString("invalid characters")));
    }
}