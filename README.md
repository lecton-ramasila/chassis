# Chassis - Spring Boot Application

A Spring Boot application with Swagger, Prometheus monitoring, and Grafana dashboards.

## Features

- **Spring Boot** - Web framework
- **Swagger/OpenAPI** - API documentation
- **Prometheus** - Metrics collection
- **Grafana** - Metrics visualization
- **Docker** - Containerization

## Quick Start

### Build and Run Locally
```bash
./gradlew bootRun
```

### Build and Run with Docker Compose
```bash
./gradlew bootJar
docker-compose up --build
```

## Endpoints

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/health
- **Metrics**: http://localhost:8080/actuator/prometheus
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)

## API Endpoints

- `GET /api/health` - Health check
- `GET /api/info` - Application info