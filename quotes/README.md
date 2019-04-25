# Quotes Service

This module contains the implementation for the Quotes Service API found in the `quotes-api`
top-level module.

It contains two sub-modules, `impl` for the service implementation, and `distros` for all the
different ways the service can be packaged and distributed.

## Implementation

The `impl` module contains the `QuoteServiceImpl` class, which implements the `QuoteService` Java
interface found in the top-level `quotes-api` module. The service implementation class has no
references to any dependency injection or platform specific services or tools.

## Distributions

### SpringBoot Application

The Quotes service is currently packaged as a SpringBoot application (`spring` module) and
can be running the following command:

```bash
> cd multi-platform/quotes/distros/spring
> java -Dserver.port=4001 -jar target/spring-quotes-app-0.1.0.jar
```

To test that the service is up and running, point a browser at http://localhost:4001/quote, or
run the following cURL:

```bash
> curl -X GET "http://localhost:4001/quote" -H "accept: application/json"
```

### Docker

The service is also packaged as a Docker image and can be run as a Docker application. To do so,
run

```bash
> dr run -p 4001:8080  multi-platform/quotes-distros-docker:0.1.0
```

This will expose the Quotes service on port `4001`, and make it accessible from a browser at
http://localhost:4001/quote.

The following cURL can also be used to request a random quote from the service:

```bash
> curl -X GET "http://localhost:4001/quote" -H "accept: application/json"
```
