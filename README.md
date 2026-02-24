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