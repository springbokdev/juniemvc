## Change Requirements
Add a new entity to the project called Customer.

The Customer entity should have the following properties:
- name - not null
- email
- phone number
- address line 1 - not null
- address line 2
- city - not null
- state - not null
- postal code - not null

The Customer entity should extend the BaseEntity, and has a OneToMany relationship with BeerOrder.

Add a flyway migration script for the new Customer JPA entity.

Add Java DTOs, Mapper, Spring Data repositories, service and service implementation to support a
Spring MVC RESTful CRUD controller. Add tests for all components. Update the OpenAPI documentation for the new controller
operations. Verify all tests are passing.