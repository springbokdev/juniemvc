package space.springbok.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.springbok.juniemvc.models.BeerOrderDto;
import space.springbok.juniemvc.models.BeerOrderLineDto;
import space.springbok.juniemvc.services.BeerOrderService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerOrderService beerOrderService;

    BeerOrderDto testOrder;

    @BeforeEach
    void setUp() {
        testOrder = BeerOrderDto.builder()
                .id(1)
                .customerRef("Test Customer")
                .paymentAmount(new BigDecimal("12.99"))
                .status("NEW")
                .beerOrderLines(Set.of(BeerOrderLineDto.builder()
                        .beerId(1)
                        .orderQuantity(1)
                        .build()))
                .build();
    }

    @Test
    void listOrders() throws Exception {
        given(beerOrderService.listOrders()).willReturn(Collections.singletonList(testOrder));

        mockMvc.perform(get("/api/v1/beer-order")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerRef", is("Test Customer")));
    }

    @Test
    void getOrderById() throws Exception {
        given(beerOrderService.getOrderById(any(Integer.class))).willReturn(Optional.of(testOrder));

        mockMvc.perform(get("/api/v1/beer-order/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerRef", is("Test Customer")));
    }

    @Test
    void getOrderByIdNotFound() throws Exception {
        given(beerOrderService.getOrderById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beer-order/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveNewOrder() throws Exception {
        BeerOrderDto newOrder = BeerOrderDto.builder()
                .customerRef("New Customer")
                .paymentAmount(new BigDecimal("10.00"))
                .status("NEW")
                .beerOrderLines(Set.of(BeerOrderLineDto.builder()
                        .beerId(1)
                        .orderQuantity(1)
                        .build()))
                .build();
        BeerOrderDto savedOrder = BeerOrderDto.builder()
                .id(1)
                .customerRef("New Customer")
                .paymentAmount(new BigDecimal("10.00"))
                .status("NEW")
                .beerOrderLines(Set.of(BeerOrderLineDto.builder()
                        .id(1)
                        .beerId(1)
                        .orderQuantity(1)
                        .build()))
                .build();

        given(beerOrderService.saveNewOrder(any(BeerOrderDto.class))).willReturn(savedOrder);

        mockMvc.perform(post("/api/v1/beer-order")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerRef", is("New Customer")));
    }

    @Test
    void updateOrder() throws Exception {
        given(beerOrderService.updateOrder(any(Integer.class), any(BeerOrderDto.class))).willReturn(Optional.of(testOrder));

        mockMvc.perform(put("/api/v1/beer-order/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerRef", is("Test Customer")));

        verify(beerOrderService).updateOrder(any(Integer.class), any(BeerOrderDto.class));
    }

    @Test
    void deleteOrderById() throws Exception {
        given(beerOrderService.deleteOrderById(any(Integer.class))).willReturn(true);

        mockMvc.perform(delete("/api/v1/beer-order/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerOrderService).deleteOrderById(any(Integer.class));
    }
}
