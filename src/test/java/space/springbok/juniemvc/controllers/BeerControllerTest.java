package space.springbok.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import space.springbok.juniemvc.entities.Beer;
import space.springbok.juniemvc.services.BeerService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = Beer.builder()
                .id(1)
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .build();
    }

    @Test
    void listBeers() throws Exception {
        given(beerService.listBeers()).willReturn(Collections.singletonList(testBeer));

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].beerName", is("Galaxy Cat")));
    }

    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(any(Integer.class))).willReturn(Optional.of(testBeer));

        mockMvc.perform(get("/api/v1/beer/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Galaxy Cat")));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beer/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveNewBeer() throws Exception {
        Beer newBeer = Beer.builder().beerName("New Beer").build();
        Beer savedBeer = Beer.builder().id(1).beerName("New Beer").build();

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("New Beer")));
    }
}
