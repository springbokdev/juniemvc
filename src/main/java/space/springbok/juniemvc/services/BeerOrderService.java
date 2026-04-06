package space.springbok.juniemvc.services;

import space.springbok.juniemvc.models.BeerOrderDto;

import java.util.List;
import java.util.Optional;

public interface BeerOrderService {

    List<BeerOrderDto> listOrders();

    Optional<BeerOrderDto> getOrderById(Integer id);

    BeerOrderDto saveNewOrder(BeerOrderDto beerOrderDto);

    Optional<BeerOrderDto> updateOrder(Integer id, BeerOrderDto beerOrderDto);

    Boolean deleteOrderById(Integer id);
}
