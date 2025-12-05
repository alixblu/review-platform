# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy parent pom
COPY pom.xml ./

# Copy all modules
COPY common-lib ./common-lib
COPY auth-service ./auth-service
COPY user-service ./user-service
COPY product-service ./product-service
COPY post-service ./post-service
COPY admin-service ./admin-service
COPY chat-service ./chat-service
COPY api-gateway ./api-gateway

# Build only chat-service and its dependencies
RUN mvn -B -q -DskipTests -pl chat-service -am package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/chat-service/target/*.jar /app/app.jar

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
