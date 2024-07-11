# FinSync - User Wealth Management 

This application is to accumulate the users wealth and summarize all the wealth across the finance sectors like accounts, deposits, MFs, stocks etc..and it is a Spring boot based application which uses Spring boot version 3.1.3 with Java 17

## Development

Spring boot anf Java 17

Database we used for structured data postgres and for unstructured data Mongo database

During the development we consider only local enviroment so used profile 'local'

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing

After starting the application it is accessible under `localhost:8081`.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Start your application with the following command - here with the profile `local`:

```
java -Dspring.profiles.active=local -jar ./target/spring-boot-boiler-plate-0.0.1-SNAPSHOT.jar
```

## Health Check and Swagger URL's
For Local:
```
http://localhost:8081/swagger-ui/index.html
http://localhost:8081/actuator/health
```