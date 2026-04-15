## Change Requirements
Add a new entity to the project called BeerOrderShipment.

The BeerOrderShipment entity should have the following properties:
- shipmentDate - not null
- carrier - not null
- trackingNumber - not null


The BeerOrderShipment entity should extend the BaseEntity, and has a ManyToOne relationship with BeerOrder.

Add a flyway migration script for the new BeerOrderShipment JPA entity.

Add Java DTOs, Mapper, Spring Data repositories, service and service implementation to support a
Spring MVC RESTful CRUD controller. Add tests for all components. Update the OpenAPI documentation for the new controller
operations. Verify all tests are passing.