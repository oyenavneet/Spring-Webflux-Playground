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