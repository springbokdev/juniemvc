# Plan for Adding BeerOrderShipment Feature

This plan outlines the steps required to implement the `BeerOrderShipment` entity and its associated RESTful API components, following the project's Spring Boot guidelines and requirements defined in `prompts/8-add-beer-order-shipment/requirements.md`.

## 1. Database Layer
- **Create Flyway Migration Script**: `src/main/resources/db/migration/V3__add_beer_order_shipment.sql`.
  - Create table `beer_order_shipment` with standard auditing columns (from `BaseEntity`).
  - Define columns: `shipment_date` (TIMESTAMP), `carrier` (VARCHAR), `tracking_number` (VARCHAR), `beer_order_id` (INTEGER).
  - Add foreign key constraint to `beer_order(id)` in a separate `ALTER TABLE` statement.
- **Repository**: `space.springbok.juniemvc.repositories.BeerOrderShipmentRepository`.
  - Extend `JpaRepository<BeerOrderShipment, Integer>`.

## 2. Model and Mapping Layer
- **Entity**: `space.springbok.juniemvc.entities.BeerOrderShipment`.
  - Extend `BaseEntity`.
  - Annotate with `@Entity`, `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`.
  - Include `@Builder` that calls `super()`.
  - Properties with JPA annotations (e.g., `@ManyToOne`, `@NotNull`).
- **DTO**: `space.springbok.juniemvc.models.BeerOrderShipmentDto`.
  - Extend `BaseEntityDto`.
  - Annotate with `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@SuperBuilder`, `@EqualsAndHashCode(callSuper = true)`.
  - Apply Jakarta Validation annotations (`@NotNull`, `@NotBlank`).
- **Mapper**: `space.springbok.juniemvc.mappers.BeerOrderShipmentMapper`.
  - Use MapStruct `@Mapper`.
  - Define methods for converting between Entity and DTO, and an update method.

## 3. Service Layer
- **Interface**: `space.springbok.juniemvc.services.BeerOrderShipmentService`.
  - Define standard CRUD methods: `listShipments()`, `getShipmentById()`, `saveNewShipment()`, `updateShipment()`, `deleteShipmentById()`.
- **Implementation**: `space.springbok.juniemvc.services.BeerOrderShipmentServiceJPA`.
  - Implement the interface using `BeerOrderShipmentRepository` and `BeerOrderShipmentMapper`.
  - Use constructor injection.
  - Apply `@Transactional` (with `readOnly = true` for query methods).

## 4. Web Layer
- **Controller**: `space.springbok.juniemvc.controllers.BeerOrderShipmentController`.
  - Path: `/api/v1/beer-order-shipment`.
  - Inject `BeerOrderShipmentService`.
  - Implement HTTP methods (`GET`, `POST`, `PUT`, `DELETE`).
  - Use `@Valid` for request body validation.
  - Return `ResponseEntity` with appropriate status codes or direct DTOs where applicable.
  - Use package-private visibility for the class and its methods.

## 5. API Documentation
- **OpenAPI Schema**: `openapi/openapi/components/schemas/BeerOrderShipment.yaml`.
- **OpenAPI Path**: `openapi/openapi/paths/beer-order-shipment.yaml`.
- **Update Main File**: `openapi/openapi/openapi.yaml`.

## 6. Verification and Testing
- **Unit Tests**:
  - `BeerOrderShipmentMapperTest`: Test conversion between Entity and DTO.
  - `BeerOrderShipmentControllerTest`: Use `@WebMvcTest` and `MockMvc` to test controller endpoints and validation.
- **Integration Tests**:
  - `BeerOrderShipmentRepositoryTest`: Test JPA operations and relationships.
  - `BeerOrderShipmentServiceJPATest` (or `BeerOrderShipmentControllerIT`): Test full stack integration using `@SpringBootTest` and Testcontainers.
- **Validation**:
  - Run `npm test` in `openapi` directory to validate OpenAPI spec.
  - Run all tests to ensure no regressions.
