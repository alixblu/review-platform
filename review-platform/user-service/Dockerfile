# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY common-lib ./common-lib
COPY auth-service ./auth-service
COPY user-service ./user-service
COPY product-service ./product-service
COPY post-service ./post-service
COPY admin-service ./admin-service
COPY api-gateway ./api-gateway
RUN mvn -B -q -DskipTests -pl user-service -am package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/user-service/target/*.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]



