# Quarkus REST API

This project is a basic REST API built using Quarkus. It serves as a demonstration of how to create a simple RESTful service with Quarkus.

## Project Structure

```
quarkus-rest-api
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── rest
│   │   │               └── DummyResource.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── rest
│                       └── DummyResourceTest.java
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Prerequisites**: Ensure you have Java 11 or later and Maven installed on your machine.

2. **Clone the Repository**:
   ```
   git clone <repository-url>
   cd quarkus-rest-api
   ```

3. **Build the Project**:
   ```
   ./mvnw clean package
   ```

4. **Run the Application**:
   ```
   ./mvnw quarkus:dev
   ```

5. **Access the API**: Open your browser or use a tool like Postman to access the API at `http://localhost:8080/dummy`.

## Usage

The API currently has a single endpoint that returns dummy data. You can extend this project by adding more resources and endpoints as needed.

## Testing

Unit tests for the REST endpoints are located in the `src/test/java/com/example/rest/DummyResourceTest.java` file. You can run the tests using:

```
./mvnw test
```

## License

This project is licensed under the MIT License. See the LICENSE file for more details.