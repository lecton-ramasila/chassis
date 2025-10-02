# Stage 1: Build the JAR with Gradle
FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN ./gradlew bootJar --no-daemon

# Stage 2: Run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]