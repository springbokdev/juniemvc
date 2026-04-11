# Task List: Customer Entity and Controller Implementation

## 1. Persistence Layer
1. [x] Create `space.springbok.juniemvc.entities.Customer` extending `BaseEntity` with specified properties.
2. [x] Define `@OneToMany` relationship with `BeerOrder` in `Customer` entity.
3. [x] Update `BeerOrder` entity to replace `customerRef` with `@ManyToOne` relationship to `Customer`.
4. [x] Update `@Builder` and constructors in `BeerOrder` to support the new `Customer` relationship.
5. [x] Create `space.springbok.juniemvc.repositories.CustomerRepository` interface.
6. [x] Create Flyway migration script `V2__add_customer_table.sql` for `customer` table and `beer_order` foreign key.

## 2. Business Logic Layer
7. [x] Create `space.springbok.juniemvc.models.CustomerDto` with Jakarta Validation and Lombok.
8. [x] Create `space.springbok.juniemvc.mappers.CustomerMapper` using MapStruct.
9. [x] Define `space.springbok.juniemvc.services.CustomerService` interface.
10. [x] Implement `space.springbok.juniemvc.services.CustomerServiceJPA` with transactional boundaries and constructor injection.

## 3. Web Layer
11. [x] Create `space.springbok.juniemvc.controllers.CustomerController` with package-private visibility.
12. [x] Implement GET `/api/v1/customer` (list all).
13. [x] Implement GET `/api/v1/customer/{customerId}` (get by ID).
14. [x] Implement POST `/api/v1/customer` (save new).
15. [x] Implement PUT `/api/v1/customer/{customerId}` (update by ID).
16. [x] Implement DELETE `/api/v1/customer/{customerId}` (delete by ID).

## 4. Documentation
17. [x] Create `openapi/openapi/paths/api_v1_customer.yaml` path definition.
18. [x] Create `openapi/openapi/paths/api_v1_customer_{customerId}.yaml` path definition.
19. [x] Create `openapi/openapi/components/schemas/Customer.yaml` schema definition.
20. [x] Reference new paths and schema in `openapi/openapi/openapi.yaml`.
21. [x] Validate OpenAPI specification using `npm test`.

## 5. Testing
22. [x] Implement `CustomerRepositoryTest` using `@DataJpaTest`.
23. [x] Implement `CustomerServiceJPATest` for business logic.
24. [x] Implement `CustomerControllerTest` using `MockMvc`.
25. [x] Implement `CustomerControllerIT` (Integration Test) using `@SpringBootTest`.

## 6. Verification and Cleanup
26. [x] Run all tests and ensure they pass.
27. [x] Verify project guidelines (constructor injection, package-private visibility, etc.).
28. [x] Check for N+1 query issues and ensure OSIV is disabled.
29. [x] Verify OpenAPI specification.
