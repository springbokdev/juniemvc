package space.springbok.juniemvc.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import space.springbok.juniemvc.models.BeerOrderDto;
import space.springbok.juniemvc.services.BeerOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beer-order")
@RequiredArgsConstructor
class BeerOrderController {

    private final BeerOrderService beerOrderService;

    @GetMapping
    List<BeerOrderDto> listOrders() {
        return beerOrderService.listOrders();
    }

    @GetMapping("/{orderId}")
    BeerOrderDto getOrderById(@PathVariable("orderId") Integer orderId) {
        return beerOrderService.getOrderById(orderId).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BeerOrderDto saveNewOrder(@Valid @RequestBody BeerOrderDto beerOrderDto) {
        return beerOrderService.saveNewOrder(beerOrderDto);
    }

    @PutMapping("/{orderId}")
    BeerOrderDto updateOrder(@PathVariable("orderId") Integer orderId, @Valid @RequestBody BeerOrderDto beerOrderDto) {
        return beerOrderService.updateOrder(orderId, beerOrderDto).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrderById(@PathVariable("orderId") Integer orderId) {
        if (!beerOrderService.deleteOrderById(orderId)) {
            throw new NotFoundException();
        }
    }
}
