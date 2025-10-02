# Stage 1: Build the JAR with Gradle
FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace

# Copy only build files first for dependency caching
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle/ gradle/
RUN gradle dependencies --no-daemon

# Copy source and build
COPY src/ src/
RUN gradle bootJar --no-daemon

# Stage 2: Run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]