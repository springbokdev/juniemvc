package space.springbok.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.springbok.juniemvc.models.BeerDto;
import space.springbok.juniemvc.models.BeerPatchDto;
import space.springbok.juniemvc.services.BeerService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerDto testBeer;

    @BeforeEach
    void setUp() {
        testBeer = BeerDto.builder()
                .id(1)
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();
    }

    @Test
    void listBeers() throws Exception {
        Page<BeerDto> page = new PageImpl<>(List.of(testBeer));
        given(beerService.listBeers(null, null, null, null)).willReturn(page);

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].beerName", is("Galaxy Cat")));
    }

    @Test
    void listBeersWithParams() throws Exception {
        Page<BeerDto> page = new PageImpl<>(List.of(testBeer));
        given(beerService.listBeers(anyString(), anyString(), any(Integer.class), any(Integer.class))).willReturn(page);

        mockMvc.perform(get("/api/v1/beer")
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", "IPA")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].beerName", is("Galaxy Cat")));
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
        BeerDto newBeer = BeerDto.builder()
                .beerName("New Beer")
                .beerStyle("IPA")
                .upc("1234567")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(50)
                .build();
        BeerDto savedBeer = BeerDto.builder()
                .id(1)
                .beerName("New Beer")
                .beerStyle("IPA")
                .upc("1234567")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(50)
                .build();

        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("New Beer")));
    }

    @Test
    void updateBeerById() throws Exception {
        given(beerService.updateBeerById(any(Integer.class), any(BeerDto.class))).willReturn(Optional.of(testBeer));

        mockMvc.perform(put("/api/v1/beer/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Galaxy Cat")));

        verify(beerService).updateBeerById(any(Integer.class), any(BeerDto.class));
    }

    @Test
    void patchBeerById() throws Exception {
        BeerPatchDto patchDto = BeerPatchDto.builder()
                .beerName("Patched Name")
                .build();

        BeerDto patchedBeer = BeerDto.builder()
                .id(1)
                .beerName("Patched Name")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();

        given(beerService.patchBeerById(any(Integer.class), any(BeerPatchDto.class))).willReturn(Optional.of(patchedBeer));

        mockMvc.perform(patch("/api/v1/beer/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Patched Name")));

        verify(beerService).patchBeerById(any(Integer.class), any(BeerPatchDto.class));
    }

    @Test
    void deleteBeerById() throws Exception {
        given(beerService.deleteBeerById(any(Integer.class))).willReturn(true);

        mockMvc.perform(delete("/api/v1/beer/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeerById(any(Integer.class));
    }

    @Test
    void testSaveBeerBlankName() throws Exception {
        BeerDto beerDto = BeerDto.builder().build();

        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(testBeer);

        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }
}
