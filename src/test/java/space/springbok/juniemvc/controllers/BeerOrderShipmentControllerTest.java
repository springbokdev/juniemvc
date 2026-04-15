package space.springbok.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import space.springbok.juniemvc.models.BeerOrderShipmentDto;
import space.springbok.juniemvc.services.BeerOrderShipmentService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerOrderShipmentController.class)
class BeerOrderShipmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerOrderShipmentService shipmentService;

    BeerOrderShipmentDto testDto;

    @BeforeEach
    void setUp() {
        testDto = BeerOrderShipmentDto.builder()
                .id(1)
                .shipmentDate(LocalDateTime.now())
                .carrier("FedEx")
                .trackingNumber("123456789")
                .beerOrderId(1)
                .build();
    }

    @Test
    void listShipments() throws Exception {
        given(shipmentService.listShipments()).willReturn(Collections.singletonList(testDto));

        mockMvc.perform(get("/api/v1/beer-order-shipment")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].carrier", is("FedEx")));
    }

    @Test
    void getShipmentById() throws Exception {
        given(shipmentService.getShipmentById(any(Integer.class))).willReturn(Optional.of(testDto));

        mockMvc.perform(get("/api/v1/beer-order-shipment/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.carrier", is("FedEx")));
    }

    @Test
    void getShipmentByIdNotFound() throws Exception {
        given(shipmentService.getShipmentById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beer-order-shipment/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveNewShipment() throws Exception {
        BeerOrderShipmentDto newDto = BeerOrderShipmentDto.builder()
                .shipmentDate(LocalDateTime.now())
                .carrier("UPS")
                .trackingNumber("987654321")
                .beerOrderId(1)
                .build();

        given(shipmentService.saveNewShipment(any(BeerOrderShipmentDto.class))).willReturn(testDto);

        mockMvc.perform(post("/api/v1/beer-order-shipment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.carrier", is("FedEx")));
    }

    @Test
    void updateShipment() throws Exception {
        given(shipmentService.updateShipment(any(Integer.class), any(BeerOrderShipmentDto.class))).willReturn(Optional.of(testDto));

        mockMvc.perform(put("/api/v1/beer-order-shipment/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.carrier", is("FedEx")));

        verify(shipmentService).updateShipment(any(Integer.class), any(BeerOrderShipmentDto.class));
    }

    @Test
    void deleteShipmentById() throws Exception {
        given(shipmentService.deleteShipmentById(any(Integer.class))).willReturn(true);

        mockMvc.perform(delete("/api/v1/beer-order-shipment/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(shipmentService).deleteShipmentById(any(Integer.class));
    }

    @Test
    void testSaveShipmentValidationFailed() throws Exception {
        BeerOrderShipmentDto invalidDto = BeerOrderShipmentDto.builder().build();

        mockMvc.perform(post("/api/v1/beer-order-shipment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("Validation failed")))
                .andExpect(jsonPath("$.errors", hasSize(3)));
    }
}
