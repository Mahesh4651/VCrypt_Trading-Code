# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-24 AS build

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Build application
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:24-jdk-jammy

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (match Spring Boot config)
EXPOSE 8081

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
