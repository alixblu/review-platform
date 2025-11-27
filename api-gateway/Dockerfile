# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy parent pom
COPY pom.xml ./

# Copy api-gateway module
COPY api-gateway/pom.xml ./api-gateway/
COPY api-gateway/src ./api-gateway/src

# Build only api-gateway (standalone, no parent reactor)
WORKDIR /workspace/api-gateway
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/api-gateway/target/*.jar /app/app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app/app.jar"]


