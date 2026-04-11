# Task List: Customer Entity and Controller Implementation

## 1. Persistence Layer
1. [ ] Create `space.springbok.juniemvc.entities.Customer` extending `BaseEntity` with specified properties.
2. [ ] Define `@OneToMany` relationship with `BeerOrder` in `Customer` entity.
3. [ ] Update `BeerOrder` entity to replace `customerRef` with `@ManyToOne` relationship to `Customer`.
4. [ ] Update `@Builder` and constructors in `BeerOrder` to support the new `Customer` relationship.
5. [ ] Create `space.springbok.juniemvc.repositories.CustomerRepository` interface.
6. [ ] Create Flyway migration script `V2__add_customer_table.sql` for `customer` table and `beer_order` foreign key.

## 2. Business Logic Layer
7. [ ] Create `space.springbok.juniemvc.models.CustomerDto` with Jakarta Validation and Lombok.
8. [ ] Create `space.springbok.juniemvc.mappers.CustomerMapper` using MapStruct.
9. [ ] Define `space.springbok.juniemvc.services.CustomerService` interface.
10. [ ] Implement `space.springbok.juniemvc.services.CustomerServiceJPA` with transactional boundaries and constructor injection.

## 3. Web Layer
11. [ ] Create `space.springbok.juniemvc.controllers.CustomerController` with package-private visibility.
12. [ ] Implement GET `/api/v1/customer` (list all).
13. [ ] Implement GET `/api/v1/customer/{customerId}` (get by ID).
14. [ ] Implement POST `/api/v1/customer` (save new).
15. [ ] Implement PUT `/api/v1/customer/{customerId}` (update by ID).
16. [ ] Implement DELETE `/api/v1/customer/{customerId}` (delete by ID).

## 4. Documentation
17. [ ] Create `openapi/openapi/paths/api_v1_customer.yaml` path definition.
18. [ ] Create `openapi/openapi/paths/api_v1_customer_{customerId}.yaml` path definition.
19. [ ] Create `openapi/openapi/components/schemas/Customer.yaml` schema definition.
20. [ ] Reference new paths and schema in `openapi/openapi/openapi.yaml`.
21. [ ] Validate OpenAPI specification using `npm test`.

## 5. Testing
22. [ ] Implement `CustomerRepositoryTest` using `@DataJpaTest`.
23. [ ] Implement `CustomerServiceJPATest` for business logic.
24. [ ] Implement `CustomerControllerTest` using `MockMvc`.
25. [ ] Implement `CustomerControllerIT` (Integration Test) using `@SpringBootTest`.

## 6. Verification and Cleanup
26. [ ] Run all tests and ensure they pass.
27. [ ] Verify project guidelines (constructor injection, package-private visibility, etc.).
28. [ ] Check for N+1 query issues and ensure OSIV is disabled.
