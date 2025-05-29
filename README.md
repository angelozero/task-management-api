# Task Management Api

## Done
- 01 - Docker
- 02 - CRUD
- 03 - Pageable
- 04 - Sort by field ( Task ---> "description" )
- 05 - Intercept Handler Errors
- 06 - Integration tests ( all routes )
- 07 - Unit tests
- 08 - Integration wit external Api (PokeAPI)
- 09 - Circuit Breaker (https://www.youtube.com/watch?v=3-ChrD3Zosg)
- 10 - gRPC - GRAPHQL (https://www.youtube.com/watch?v=SPu77SaK-Hk)
- 11 - Integrations tests (REST / GRAPHQL)
- 12 - Cash to the service for PokeApi using Redis (Docker)
- 13 - Sealed and non sealed class 

## Doing
- Dual datasource (DataSourceOrchestration.md)
  - RabbitMQ (RabbitMQ.md)
  - [img.png](images/rabbitmq-interface.png)
  - Saves an event to the database ( write database )
  - Triggers an event in the queue
  - Consumes the event from the queue
  - Saves the event to the second database ( read database )
  - If a read service was triggered, updates the saved event in the second database ( read database )
  - Consumes events from the second database for querying ( read database )

## To do
- JWT Authentication with Keycloak
  - Keycloak
    - issue with object list in token
    - override auth token
    - custom conditional in a sub flow (https://github.com/jdelker/keycloak-conditional-authenticators/blob/master/src/main/java/de/jdelker/keycloak/authentication/authenticators/conditional/ConditionalHeaderAuthenticator.java)

- design patterns 
  - chain of responsibility!