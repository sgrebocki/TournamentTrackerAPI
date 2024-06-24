# TournamentTrackerAPI

### About

TournamentTrackerAPI is a RESTful API designed to support the functionality of the TournamentTracker application. This API provides endpoints for managing users, teams, tournaments, and matches, facilitating the organization and tracking of sports events. The API is built using modern web technologies to ensure scalability, security, and ease of use.

### Requirements

- Java 21
- MSSQL Server 2019

### Building the Application

The application uses Maven as a build tool. To build the project, run the following command:

```bash
./mvnw clean install
```

### Running the Application

To run the application, execute the following command:

```bash
./mvnw spring-boot:run
```

### Configuration

The application can be configured using the `application.yml` file located in the `/src/main/resources` directory. The file allows you to configure the following values:

```yaml
mssql:
  datasource:
    jdbc-url: ${TT_DATASOURCE_URL} #URL of the database
    driver-class-name: ${TT_DATASOURCE_DRIVER} #Driver class name for the database
    username: ${TT_DATASOURCE_USERNAME} #Username for the database
    password: ${TT_DATASOURCE_PASSWORD} #Password for the database
    
spring:
  jpa:
    show-sql: true #Show SQL queries in the console
  config:
    import: env.properties #Import environment properties

springdoc:
  swagger-ui:
    enabled: true #Enable Swagger UI

jwt:
  secret: ${TT_JWT_SECRET} #Secret key for JWT token generation
  expiration: 60 #Expiration time for JWT token in seconds
```

#### Example environment properties file:

```properties
#MSSQL
TT_DATASOURCE_URL=jdbc:sqlserver://localhost:1433;databaseName=TournamentTracker
TT_DATASOURCE_DRIVER=com.microsoft.sqlserver.jdbc.SQLServerDriver
TT_DATASOURCE_USERNAME=sa
TT_DATASOURCE_PASSWORD=Password123

#JWT
TT_JWT_SECRET=SecretKey
```