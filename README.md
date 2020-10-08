# Http server with threads - Java

This is a proyect implemented in Java where you can reserve and buy "Tickets" for an Event. The main idea was work with some concurrency concepts like mutual exclusion, semaphores, and synchronization between java threads. The server creates a new Thread for every GET request received from a client.

## Entrypoints

* localhost:4000/reserva
* localhost:4000/compra
* localhost:4000/cancela
