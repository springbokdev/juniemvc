# juniemvc

juniemvc is a Spring Boot 3.5.6 application developed with Java 21, demonstrating modern web development practices with Spring MVC, Data JPA, MapStruct, and Lombok.

## Tech Stack

- **Java Version:** 21
- **Framework:** Spring Boot 3.5.6
- **Build Tool:** Maven
- **Persistence:** Spring Data JPA with H2 (Runtime)
- **Utilities:** Lombok (1.18.30), MapStruct (1.6.3)
- **Testing:** Spring Boot Starter Test (JUnit 5, Mockito)

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven (or use the provided `./mvnw`)

### Running the Application

To run the application locally:

```bash
./mvnw spring-boot:run
```

The application will start on the default port (usually 8080).

### API Endpoints

The core API provides endpoints for managing beers:

- `GET /api/v1/beer` - List all beers.
- `GET /api/v1/beer/{id}` - Get a beer by ID.
- `POST /api/v1/beer` - Create a new beer.
- `PUT /api/v1/beer/{id}` - Update an existing beer.
- `DELETE /api/v1/beer/{id}` - Delete a beer.

## Development

### Building the Project

To clean and build the project:

```bash
./mvnw clean install
```

### Running Tests

To run all tests:

```bash
./mvnw test
```

To run a specific test class:

```bash
./mvnw test -Dtest=BeerControllerTest
```

## Project Structure

- `space.springbok.juniemvc.controllers`: REST Controllers.
- `space.springbok.juniemvc.entities`: JPA Entities.
- `space.springbok.juniemvc.repositories`: Spring Data JPA repositories.
- `space.springbok.juniemvc.services`: Service interfaces and implementations.

For more detailed developer guidelines, see [.junie/guidelines.md](.junie/guidelines.md).
