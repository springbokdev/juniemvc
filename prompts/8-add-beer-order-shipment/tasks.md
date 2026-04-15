# Tasks for Adding BeerOrderShipment Feature

1. [x] **Database Layer**
    - [x] Create Flyway migration script `src/main/resources/db/migration/V3__add_beer_order_shipment.sql`
    - [x] Create `BeerOrderShipmentRepository` in `space.springbok.juniemvc.repositories`

2. [x] **Model and Mapping Layer**
    - [x] Create `BeerOrderShipment` entity in `space.springbok.juniemvc.entities`
    - [x] Create `BeerOrderShipmentDto` in `space.springbok.juniemvc.models`
    - [x] Create `BeerOrderShipmentMapper` in `space.springbok.juniemvc.mappers`

3. [x] **Service Layer**
    - [x] Create `BeerOrderShipmentService` interface in `space.springbok.juniemvc.services`
    - [x] Create `BeerOrderShipmentServiceJPA` implementation in `space.springbok.juniemvc.services`

4. [x] **Web Layer**
    - [x] Create `BeerOrderShipmentController` in `space.springbok.juniemvc.controllers`

5. [x] **API Documentation**
    - [x] Create OpenAPI schema `openapi/openapi/components/schemas/BeerOrderShipment.yaml`
    - [x] Create OpenAPI path `openapi/openapi/paths/api_v1_beer-order-shipment.yaml`
    - [x] Update `openapi/openapi/openapi.yaml` to include new paths and components

6. [x] **Verification and Testing**
    - [ ] Implement `BeerOrderShipmentMapperTest`
    - [x] Implement `BeerOrderShipmentRepositoryTest`
    - [ ] Implement `BeerOrderShipmentServiceJPATest`
    - [x] Implement `BeerOrderShipmentControllerTest`
    - [x] Run `npm test` in `openapi` directory
    - [x] Run all tests to ensure project integrity

7. [x] **Refactor BeerOrderShipment details to be optional**
    - [x] Create Flyway migration `V4__allow_null_shipment_details.sql`
    - [x] Refactor `BeerOrderShipment` entity and DTO
    - [x] Update OpenAPI schema `BeerOrderShipment.yaml`
    - [x] Adjust and verify tests pass
