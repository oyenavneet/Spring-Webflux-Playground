# Learning Spring WebFlux

## Reactive vs Traditional API

**Traditional (Spring MVC)**
- Blocking, one request → one thread
- Thread waits for I/O (DB, API calls)
- Less scalable under high load

**Reactive (Spring WebFlux)**
- Non-blocking, event-loop model
- Uses `Mono` and `Flux`
- Better resource utilization
- Handles more concurrent requests with fewer threads

---

## Responsive

### Reacting Quickly
- Non-blocking I/O
- Doesn’t wait for slow operations
- Frees threads to handle other requests

### Stream Response
- Supports streaming data using `Flux`
- Sends data as it becomes available
- Useful for large datasets or real-time updates

### Cancel
- Client can cancel subscription
- Server stops processing when subscription is canceled
- Saves CPU and memory resources  

---

## Spring Data R2DBC (Reactive Relational Database Connectivity)

R2DBC is a reactive specification for relational databases.  
It provides non-blocking drivers for popular databases like PostgreSQL, MySQL, MariaDB, H2, Oracle, and ClickHouse.

### Priorities
- High performance (non-blocking I/O)
- Better scalability (handles more concurrent requests)
- Streaming support
- Backpressure support

### Limitations
- No lazy loading
- No `@OneToMany`
- No `@ManyToMany`
- No automatic ORM-style relationship management

### Spring Data R2DBC
- Reactive abstraction over R2DBC
- Supports `Repository`
- Query methods
- Projections
- `DatabaseClient` for custom queries

### Notes
- Efficient resource utilization (event-loop model)
- Supports streaming with backpressure
- Prefer writing SQL for complex queries

---

## Reactive API, Validation, Exception Handling & Integration Testing

### Spring WebFlux
- Build non-blocking REST APIs
- Uses `Mono` (0..1) and `Flux` (0…N)
- Event-loop model (better scalability)

### ReactiveCrudRepository
- Reactive version of `CrudRepository`
- Returns `Mono` and `Flux`
- Supports basic CRUD operations
- Works with Spring Data R2DBC

### Validation
- Use `@Valid` on request body
- Use annotations like `@NotNull`, `@Size`, `@Email`
- Validation errors handled reactively

### Global Exception Handling
- Use `@ControllerAdvice`
- Use `@ExceptionHandler` methods
- Return `Mono<ResponseEntity<?>>`
- Centralized error handling for APIs

### Integration Testing
- `@SpringBootTest` – Loads full application context
- `@AutoConfigureWebTestClient` – Enables `WebTestClient`
- Use `WebTestClient` to test reactive endpoints
- Supports non-blocking API testing

---

## WebFilter - Spring WebFlux

### Purpose
- Handles cross-cutting concerns:
    - Authentication
    - Authorization
    - Logging
    - Monitoring
    - Rate Limiting
    - Request/Response modification

### How It Works
- Executes before and/or after the controller
- Intercepts every incoming request
- Can modify `ServerWebExchange`
- Must return `Mono<Void>`

### Filter Chaining
- Multiple `WebFilter` can be chained
- Executed in defined order (`@Order`)
- Each filter must call `chain.filter(exchange)` to continue flow
- Can stop the request by not calling the chain

### WebFilter Attributes
- Use `exchange.getAttributes()` to store/retrieve data
- Share data between filters and controllers
- Useful for:
    - Storing authenticated user details
    - Request metadata
    - Correlation IDs

---

## Functional Endpoints

Functional Endpoints are an alternative way to expose APIs in Spring WebFlux  
(besides using `@RestController` and annotation-based mapping).

### Key Components

- **RouterFunction**
    - Defines routes (URL + HTTP method)
    - Maps request to handler function

- **HandlerFunction**
    - Contains business logic
    - Returns `Mono<ServerResponse>`

- **ServerRequest**
    - Represents incoming request

- **ServerResponse**
    - Represents outgoing response

### Features

- Fully functional programming style
- More control over routing
- Lightweight and flexible
- Good for modular or highly customized APIs

### When to Use

- When you prefer functional style over annotations
- When building lightweight reactive services
- When you need fine-grained routing control

---

## WebClient

`WebClient` is a reactive HTTP client provided by Spring WebFlux.

### Overview
- Reactor-based fluent API for making HTTP requests
- Built on top of Reactor Netty
- Supports both synchronous (block) and asynchronous usage
- Replaces `RestTemplate` in reactive applications

### Key Features
- Non-blocking and reactive
- Immutable (each configuration returns a new instance)
- Thread-safe
- Supports streaming responses (`Flux`)
- Handles backpressure

### Common Use Cases
- Calling external REST APIs
- Microservice-to-microservice communication
- Consuming streaming APIs




