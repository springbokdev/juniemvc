package space.springbok.juniemvc.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.springbok.juniemvc.exceptions.NotFoundException;
import space.springbok.juniemvc.models.BeerOrderShipmentDto;
import space.springbok.juniemvc.services.BeerOrderShipmentService;

import java.util.List;

/**
 * Controller for managing BeerOrderShipment operations.
 */
@RestController
@RequestMapping("/api/v1/beer-order-shipment")
@RequiredArgsConstructor
class BeerOrderShipmentController {

    private final BeerOrderShipmentService shipmentService;

    /**
     * List all beer order shipments.
     *
     * @return a list of BeerOrderShipmentDto.
     */
    @GetMapping
    List<BeerOrderShipmentDto> listShipments() {
        return shipmentService.listShipments();
    }

    /**
     * Get a beer order shipment by its ID.
     *
     * @param shipmentId the ID of the shipment to retrieve.
     * @return the shipment as BeerOrderShipmentDto.
     * @throws NotFoundException if the shipment is not found.
     */
    @GetMapping("/{shipmentId}")
    BeerOrderShipmentDto getShipmentById(@PathVariable("shipmentId") Integer shipmentId) {
        return shipmentService.getShipmentById(shipmentId).orElseThrow(NotFoundException::new);
    }

    /**
     * Save a new beer order shipment.
     *
     * @param shipmentDto the BeerOrderShipmentDto to save.
     * @return the saved BeerOrderShipmentDto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BeerOrderShipmentDto saveNewShipment(@Valid @RequestBody BeerOrderShipmentDto shipmentDto) {
        return shipmentService.saveNewShipment(shipmentDto);
    }

    /**
     * Update an existing beer order shipment by its ID.
     *
     * @param shipmentId  the ID of the shipment to update.
     * @param shipmentDto the updated BeerOrderShipmentDto.
     * @return the updated BeerOrderShipmentDto.
     * @throws NotFoundException if the shipment is not found.
     */
    @PutMapping("/{shipmentId}")
    BeerOrderShipmentDto updateShipment(@PathVariable("shipmentId") Integer shipmentId,
                                       @Valid @RequestBody BeerOrderShipmentDto shipmentDto) {
        return shipmentService.updateShipment(shipmentId, shipmentDto).orElseThrow(NotFoundException::new);
    }

    /**
     * Delete a beer order shipment by its ID.
     *
     * @param shipmentId the ID of the shipment to delete.
     * @throws NotFoundException if the shipment to delete is not found.
     */
    @DeleteMapping("/{shipmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteShipmentById(@PathVariable("shipmentId") Integer shipmentId) {
        if (!shipmentService.deleteShipmentById(shipmentId)) {
            throw new NotFoundException();
        }
    }
}
