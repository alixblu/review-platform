package com.example.productservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17");
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateProduct() {
        String requestBody = """
            {
              "name": "CeraVe Foaming Cleanser",
              "categoryEnum": "CLEANSER",
              "brand_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
              "ingredients": "Ceramides, Niacinamide",
              "skinTypeEnum": ["OILY"],
              "concernTypeEnum": ["ACNE"],
              "description": "Gentle foaming cleanser that removes oil without disrupting the skin barrier.",
              "imageUrl": "https://example.com/images/cerave-foaming-cleanser.jpg",
              "price": 250000
            }
        """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("result.id", Matchers.notNullValue())
                .body("result.name", Matchers.equalTo("CeraVe Foaming Cleanser"))
                .body("result.brand_id", Matchers.equalTo("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .body("result.categoryEnum", Matchers.equalTo("CLEANSER"))
                .body("result.ingredients", Matchers.equalTo("Ceramides, Niacinamide"))
                .body("result.skinTypeEnum", Matchers.hasItem("OILY"))
                .body("result.concernTypeEnum", Matchers.hasItem("ACNE"))
                .body("result.description", Matchers.equalTo("Gentle foaming cleanser that removes oil without disrupting the skin barrier."))
                .body("result.imageUrl", Matchers.equalTo("https://example.com/images/cerave-foaming-cleanser.jpg"))
                .body("result.price", Matchers.equalTo(250000))
                .body("result.status", Matchers.equalTo("ACTIVE"));
    }
}
