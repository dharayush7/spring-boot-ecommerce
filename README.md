# Ecommerce Perfume Backend

A robust Spring Boot backend application for an e-commerce platform specializing in perfume products. This application provides a complete RESTful API for managing perfume products, user authentication, and order processing.

## 🚀 Features

- User Authentication and Authorization
- Product Management
- Order Processing
- Shopping Cart Functionality
- Secure API Endpoints
- Database Integration
- Docker Support
- Actuator Monitoring

## 🛠️ Technology Stack

- **Java 24**: Latest Java version for optimal performance
- **Spring Boot 3.4.5**: Core framework for building the application
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For database operations
- **PostgreSQL**: Primary database
- **Lombok**: For reducing boilerplate code
- **Maven**: Build and dependency management
- **Docker**: Containerization support
- **Spring Actuator**: Application monitoring and metrics

## 📋 Prerequisites

- Java 24 or higher
- Maven 3.6 or higher
- Docker and Docker Compose
- PostgreSQL (if running without Docker)

## 🚀 Getting Started

### Local Development Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/ecommerce-perfume.git
   cd ecommerce-perfume
   ```

2. Start the PostgreSQL database using Docker:

   ```bash
   docker-compose up -d
   ```

3. Build the application:

   ```bash
   ./mvnw clean install
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Docker Setup

1. Build the Docker image:

   ```bash
   docker build -t ecommerce-perfume .
   ```

2. Run the application with Docker Compose:
   ```bash
   docker-compose up
   ```

## 📁 Project Structure

```
src/main/java/com/ayushdhar/ecommerce_perfume/
├── config/           # Configuration classes
├── controller/       # REST API controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── error/           # Custom error handling
├── lib/             # Utility classes and helpers
├── middleware/      # Custom middleware components
├── repository/      # JPA repositories
├── response/        # API response models
├── services/        # Business logic services
└── EcommercePerfumeApplication.java  # Main application class
```

## 📊 Database Configuration

The application uses PostgreSQL with the following default configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce
    username: user
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## 🔐 Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- CORS configuration
- Request rate limiting

## 📈 Monitoring

The application includes Spring Actuator endpoints for monitoring:

- Health check: `/actuator/health`
- Metrics: `/actuator/metrics`
- Application info: `/actuator/info`

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

## 📝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- Your Name - Initial work

## 🙏 Acknowledgments

- Spring Boot Team
- PostgreSQL Team
- All contributors
