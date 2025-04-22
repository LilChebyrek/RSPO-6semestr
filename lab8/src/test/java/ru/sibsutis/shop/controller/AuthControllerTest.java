package ru.sibsutis.shop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterUser() throws Exception {
        String request = """
            {
                "username": "vitaly",
                "password": "pass123",
                "role": "REGULAR",
                "address_city": "Langepas",
                "address_street": "Tvardovskogo",
                "address_zipcode": "xz"
            }
        """;

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void testLoginUser() throws Exception {
        // Сначала зарегистрируем
        testRegisterUser();

        String request = """
            {
                "username": "vitaly",
                "password": "pass123"
            }
        """;

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }
}