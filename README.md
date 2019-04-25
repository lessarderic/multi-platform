# Multi-Platform Sample Repository

Repository that provides an example of how components  and services can be written to be packaged
different ways (e.g., Spring applications, Docker containers, inside Karaf (OSGi container), etc.)
and deployed to different environments.

The modules have the following overall structure:

```text
multi-platform
+- quotes-api
|  +- java
|  +- rest
|     +- specs
|     +- docs
|     +- clients
|        +- java
|     +- server
+- greetings-api
|  +- java
|  +- rest
|     +- specs
|     +- docs
|     +- clients
|        +- java
|     +- server
+- quotes
|  +- impl
|  +- distros
|     +- spring
|     +- docker
|     +- osgi
+- greetings
|  +- impl
|  +- distros
|     +- spring
|     +- docker
|     +- osgi
+- thirdparty
|  +- micrometer-osgi
+- sample-deployments
   +- spring
   |  +- single-app.jar
   +- docker
   |  +- docker-compose.yaml
   +- karaf
      +- greetings.kar
```

Except for the `sample-deployments` module, each top-level module represents a separate repository.
For instance, the `quotes` and `greetings` would be two distinct GitHub repositories that can be
built, packaged and deployed on their own.

## API Modules

The `quotes-api` and `greetings-api` modules contain the definition for the Java, REST or any other
APIs exposed by a component or group of components.

The Java APIs are defined so clean OSGi bundles can be created from them.

The REST APIs are defined using [OpenAPI](https://www.openapis.org/). Client libraries, server
stubs, static documentation are generated from the OpenAPI specifications. This is also where
[PACT](https://docs.pact.io/) contract definitions would exist. Those would be used to generate
compliance test kit (CTK) to validate server implementations, as well as server mocks that can be
used by client services as server replacements during testing.
                                             
> APIs are typically source controlled in their own repository and released independently to
allow for their version to change independently of the implementation.

## Implementation Modules

Those modules contain the implementation of each service defined in the API Modules.
They include an `impl` module that contains the service's business logic. The code in this
module is written so it can be reused in different distributions and composed as needed
using dependency injection. In order to remain deployment independent, it does not assume any
specific dependency injection framework (Spring, Aries Blueprint, Guice, etc.) and do not use any
framework specific annotations.

Standard APIs such as slf4j and [Micrometer](https://micrometer.io) that abstract common tasks such
as logging and metrics collection are used. No specific implementation is assumed however to allow
the services to be packaged and deployed to environments that provide different implementations of
those common services.

The code in these modules also uses [Immutables](https://immutables.org) to simplify the creation of
immutable classes and objects.

To remain independent of the communication mechanism, the services do not make any assumptions
as to how they will be called (e.g., REST, Protobuf or GraphQL endpoint, message queue, OSGi
service call, etc.).

Service implementation modules that need access to persistent storage and messaging (SQL or noSQL
databases, caches, message brokers, etc.) provide data access objects (DAOs) and adaptors to
abstract those away. This allows each distribution to select specific data stores and message
brokers based on the environment they will be deployed to.

TODO - Should DAO and other platform service abstraction layers use things like Spring Data? If so, should they exist in the same module as the rest of the business logic?

> As a general rule, service implementations should be kept in a single repository and released as
a single unit so they can be versioned and deployed independently of each other.

## Third-Party Modules

The `thirdparty` module contains the module needed to package micrometer as an OSGi bundle and is
presented as an example of how such a common API can be reused in a Spring application as well as
in an OSGi one.

## Sample Deployment Modules

These modules show how the Quotes and Greetings services can be deployed to different environments.

The `spring` sub-module packages the two services into a single SpringBoot application.

The `docker` sub-module provides a single `docker-compose.yaml` file that can be used to start
separate Docker containers from the two service images, as well as any other required external
service (e.g., Cassandra).

Finally, the `karaf` sub-modules creates a Karaf Kar file that ce easily be dropped into a
Karaf container to start both services and expose the Greetings REST endpoint.

## Building Multi-Platform

To build all modules, simply install Maven 3.5 or later and run

```bash
> mvn install
```

Each module describes how to run their specific version of the service components.
