package ru.sibsutis.shop.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        // Зарегистрировать пользователя
        String registerRequest = """
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
                        .content(registerRequest))
                .andExpect(status().isOk());

        // Авторизоваться и получить токен
        String loginRequest = """
            {
                "username": "vitaly",
                "password": "pass123"
            }
        """;

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andReturn();

        token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        // Залить данные payment и item (можно через @Sql или Liquibase)
    }

    @Test
    void testCreateOrder() throws Exception {
        String orderRequest = """
            {
              "orderDetails": [
                {
                  "quantity": {
                    "value": 1,
                    "name": "штука",
                    "symbol": "шт"
                  },
                  "taxStatus": "TAX_FREE",
                  "itemId": 10
                }
              ],
              "paymentId": 1
            }
        """;

        mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
}