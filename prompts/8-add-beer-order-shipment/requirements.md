## Change Requirements
Add a new entity to the project called `BeerOrderShipment` to track the shipment details of a `BeerOrder`.

### 1. Data Model
The `BeerOrderShipment` entity should extend `BaseEntity` and include the following properties:
- `shipmentDate`: `LocalDateTime`, not null.
- `carrier`: `String`, not null.
- `trackingNumber`: `String`, not null.

### 2. Relationships
- `BeerOrderShipment` has a `ManyToOne` relationship with `BeerOrder`.
- One `BeerOrder` can have multiple `BeerOrderShipment` records.

### 3. Database Migration
- Add a Flyway migration script (`V3__add_beer_order_shipment.sql`) to create the `beer_order_shipment` table.
- The table should include standard auditing columns (from `BaseEntity`) and a foreign key to the `beer_order` table.
- Follow the project guideline: add the foreign key constraint in a second SQL statement.

### 4. Backend Implementation
- **Entity**: Create `BeerOrderShipment` in `space.springbok.juniemvc.entities`.
- **DTO**: Create `BeerOrderShipmentDto` in `space.springbok.juniemvc.models`, extending `BaseEntityDto`.
- **Mapper**: Create `BeerOrderShipmentMapper` in `space.springbok.juniemvc.mappers` using MapStruct.
- **Repository**: Create `BeerOrderShipmentRepository` in `space.springbok.juniemvc.repositories`.
- **Service**: Create `BeerOrderShipmentService` (interface) and `BeerOrderShipmentServiceJPA` (implementation) in `space.springbok.juniemvc.services`.
- **Controller**: Create `BeerOrderShipmentController` in `space.springbok.juniemvc.controllers` providing RESTful CRUD operations under `/api/v1/beer-order-shipment`.

### 5. API Documentation
- Update the modular OpenAPI documentation in `openapi/openapi/`.
- Add the `BeerOrderShipment` schema to `openapi/openapi/components/schemas/`.
- Add the path definitions to `openapi/openapi/paths/`.
- Link the new paths and schemas in `openapi/openapi/openapi.yaml`.

### 6. Verification
- Add unit and integration tests for all new components (Controller, Service, Repository, Mapper).
- Ensure all tests pass, including existing ones.
- Follow all Spring Boot and Project guidelines (Constructor injection, Package-private visibility, ProblemDetails for exceptions, etc.).
