# Multi-Platform Sample Repository

Repository that provides an example of how components can be written to be packaged and deployed
different ways, i.e., as Spring applications, Docker containers, inside Karaf (OSGi container), etc.

The modules have the following overall structure:

```text
multi-platform
+- components
|  +- quotes
|  |  +- service-api
|  |  +- service-impl
|  +- greetings
|     +- service-api
|     +- service-impl
+- apis
|  +- quotes
|  |  +- clients
|  |  +- server
|  |  +- pact
|  +- greetings
|     +- clients
|     +- server
|     +- pact
+- distros
   +- spring
   |  +- single-app
   |  +- quotes-app
   |  +- greetings-app
   +- docker
   |  +- quotes-docker
   |  +- greetings-docker
   |  +- docker-compose.yml
   +- osgi
      +- third-party
      |  +- micrometer-core
      +- quotes
      |  +- quotes-api-bundle
      |  +- quotes-bundle
      +- greetings
      |  +- greetings-api-bundle
      |  +- greetings-bundle
      +- greetings-webapp
         +- greetings-endpoint-bundle
```

## Components Module

The `components` sub-modules contain the business logic for the different components of the system.
These modules are written so they can be reused in different distributions and composed as needed
using dependency injection. In order to remain deployment independent, they do not assume a
specific dependency injection framework (Spring, Aries Blueprint, Guice, etc.) will be used to
wire the components together and do not use any framework specific annotations.

Standard APIs such as slf4j and micrometer that abstract common tasks such as logging and metrics
collection are used. No specific implementation is assumed however to allow the components to be
packaged and deployed to environments that provide different implementations of those common
services.

The components that need access to persistent storage and messaging (SQL or noSQL databases, caches,
message brokers, etc.) provide data access objects (DAOs) and adaptors to abstract those away
and allow distributions to select specific data stores and message brokers based on the
environment they will be deployed to.

To remain independent of the communication mechanism, the components do not make any assumptions
as to how they will be called (e.g., REST, Protobuf or GraphQL endpoint, message queue, OSGi
service call, etc.). 

Since components are not meant to be deployed directly, these modules do not contain any deployment
specific files (Spring configuration files, OSGi bundles, Karaf feature files, Dockerfiles, etc.)
and do not create deployable distributions (e.g., Docker images, OSGi bundles or Kar files, etc.).
In a continuous delivery environment, their release would not trigger any automated deployments.

Unit testing is usually sufficient, especially for simple components. For more complex complex
ones, component level testing could be done in addition by manually wiring the different
classes part of the component and testing the component as a single unit. It is important to
remember however that contract and end-to-end testing is performed in the distribution modules.

> As a general rule, components that share a common object model and are part of a single
[Bounded Context](https://www.martinfowler.com/bliki/BoundedContext.html) should be kept in a
single repository and released together.

## APIs Module

The `apis` sub-modules contain the definition for the different REST endpoints used to expose
the components or group of components defined in the `components` sub-modules. The APIs are
defined using [OpenAPI](https://www.openapis.org/). Client libraries and server stubs are
generated from the OpenAPI specifications. The modules also contain
[PACT](https://docs.pact.io/) contract definitions which are used to generate test drivers to
validate server implementations, as well as server stubs that can be used by client services
as server replacements during testing.

> APIs are typically source controlled in their own repository and released independently to
allow for their version to evolve independently of the implementation.

## Distributions Module

The `distros` modules are used to create environment specific distributions.

For instance, two distribution modules exist under the `spring` sub-module to create independent
Quotes and Greetings SpringBoot applications, which can be run at the command line or from your
favorite IDE. Each distribution module pulls-in the proper REST endpoint API modules (`quotes` or
`greetings`), and provides implementations for the third-party tools (logging and metrics) and
data storage 

The `single-app` module, on the other hand, creates a single SpringBoot application that contains
both Quotes and Greetings components and only exposes the Greetings REST endpoint.

The `distros` module contains samples of how the different components could be packaged and
deployed.

Each sub-module composes and packages different components as deployable units.

For instance, the `spring` module shows how the components can be wired using Spring
to create a single SpringBoot application, or two independent ones that communicate over a REST
endpoint.

The `docker` module under `distros` shows how the SpringBoot applications can be packaged as
Docker images and orchestrated using a single `docker-compose` file.

The `osgi` module shows how the same reusable components can be packaged as OSGi bundles and
deployed to an OSGi container like Karaf.

These sub-modules should be kept in repositories that package the same components in a similar
fashion. For instance, a repository could create a SpringBoot application that provides a REST
endpoint for one or more components

Finally, the `apis` module

This is only a sample repository. Each team and application should create multiple, independently
releasable repositories based on how they package and deliver their applications and services.

The following top-level modules provide information about different independent pieces of the
overall sample application and possible deployment options.

**services**

The `services` module contains the different service components. Each service component is
packaged and deployed in different forms by the other modules.

**osgi**

The `osgi` module packages the components defined in the `services` as OSGi bundles that can be
deployed as OSGi services inside an OSGi container.

**spring**

The `spring` module packages the `services` components as Spring Boot applications.

**docker**

The `docker` module packages the Spring Boot applications created by the `spring` module as
Docker images and provides a Docker Compose configuration file to run them as a multi-service
deployment under Docker.

## Third-Party Libraries and Frameworks

The code in these modules use [Immutables](https://immutables.org) to simplify the creation of
immutable classes and objects.

They also use the [Micrometer](https://micrometer.io) generic interfaces to capture metrics.

## Building Multi-Platform

To build all modules, simply install Maven 3.5 or later and run

```bash
> mvn install
```

Each module describes how to run their specific version of the service components.
