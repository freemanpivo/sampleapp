package com.freemanpivo.insurancechallenge.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freemanpivo.insurancechallenge.IntegrationTest;
import com.freemanpivo.insurancechallenge.api.dto.ProductRequestDto;
import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.domain.Price;
import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductIdentifier;
import com.freemanpivo.insurancechallenge.core.port.in.CreateProduct;
import com.freemanpivo.insurancechallenge.core.port.in.UpdateProduct;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@IntegrationTest
@ActiveProfiles("integration-test")
class ProductControllerTest {
    @Autowired
    private MockMvc apiLayer;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CreateProduct createUsecase;
    @MockBean
    private UpdateProduct updateUsecase;

    private final ProductRequestDto validRequest = new ProductRequestDto("Nome", "VIDA", BigDecimal.ONE);
    private final ProductIdentifier id = new ProductIdentifier();
    private final Price price = new Price(validRequest.basePrice(), Category.from(validRequest.category()));
    private final Product validProduct = Product.create(validRequest.toProductSample(), price, id);

    @Test
    void testPostRequest() throws Exception {
        when(createUsecase.create(validRequest.toProductSample())).thenReturn(validProduct);

        final var response = apiLayer.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.preco_tarifado", Matchers.notNullValue()));
    }

    @Test
    void testPutRequest() throws Exception {
        when(updateUsecase.update(validProduct.id(), validRequest.toProductSample())).thenReturn(validProduct);

        final var response = apiLayer.perform(put("/products/{product_id}", validProduct.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.preco_tarifado", Matchers.notNullValue()));
    }
}