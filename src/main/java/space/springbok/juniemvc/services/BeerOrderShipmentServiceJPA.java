package space.springbok.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.springbok.juniemvc.entities.BeerOrderShipment;
import space.springbok.juniemvc.mappers.BeerOrderShipmentMapper;
import space.springbok.juniemvc.models.BeerOrderShipmentDto;
import space.springbok.juniemvc.repositories.BeerOrderShipmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of BeerOrderShipmentService.
 */
@Service
@RequiredArgsConstructor
public class BeerOrderShipmentServiceJPA implements BeerOrderShipmentService {

    private final BeerOrderShipmentRepository shipmentRepository;
    private final BeerOrderShipmentMapper shipmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderShipmentDto> listShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(shipmentMapper::shipmentToShipmentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerOrderShipmentDto> getShipmentById(Integer id) {
        return shipmentRepository.findById(id)
                .map(shipmentMapper::shipmentToShipmentDto);
    }

    @Override
    @Transactional
    public BeerOrderShipmentDto saveNewShipment(BeerOrderShipmentDto shipmentDto) {
        BeerOrderShipment savedShipment = shipmentRepository.save(shipmentMapper.shipmentDtoToShipment(shipmentDto));
        return shipmentMapper.shipmentToShipmentDto(savedShipment);
    }

    @Override
    @Transactional
    public Optional<BeerOrderShipmentDto> updateShipment(Integer id, BeerOrderShipmentDto shipmentDto) {
        return shipmentRepository.findById(id).map(existingShipment -> {
            shipmentMapper.updateShipmentFromDto(shipmentDto, existingShipment);
            return shipmentMapper.shipmentToShipmentDto(shipmentRepository.saveAndFlush(existingShipment));
        });
    }

    @Override
    @Transactional
    public Boolean deleteShipmentById(Integer id) {
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
