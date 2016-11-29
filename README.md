# Tendril Winter Hackathon Project

### Prerequisites
- Java 8
- Groovy
- Gradle

### Build Set-Up

Run Application: `./gradlew bootRun`

Compile Source: `./gradlew compileGroovy`

Compile Unit Tests: `./gradlew compileTestGroovy`

Run Unit Tests: `./gradlew test`

Build Archive: `./gradlew clean build`

### API Monitoring Endpoints

This API includes several built-in auditing, health and metrics endpoints provided by the [Spring Boot Actuator Module](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-enabling). Here are just a few:

- GET /health (Shows application health information)
- GET /metrics (Shows ‘metrics’ information)
- GET /dump (Performs a thread dump)
- GET /env (Exposes properties from Spring’s ConfigurableEnvironment)
- GET /trace (Displays trace information - by default the last 100 HTTP requests)


# API Documentation

// TODO
