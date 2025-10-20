package com.example.productservice;

import com.example.productservice.service.ProductService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

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

    static {
        postgreSQLContainer.start();

    }

    @Test
//    @Disabled("No configuration yet")
    void shouldCreateProduct() {
        String requestbody = """
        {
          "name": "CeraVe Foaming Cleanser",
          "categoryEnum": "CLEANSER",
          "ingredients": "Ceramides, Niacinamide",
          "skinTypeEnum": [
            "OILY"
          ],
          "concernTypeEnum": [
            "ACNE"
          ],
          "description": "Gentle foaming cleanser that removes oil without disrupting the skin barrier.",
          "imageUrl": "https://example.com/images/cerave-foaming-cleanser.jpg",
          "price": 250000
        }
        """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestbody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("CeraVe Foaming Cleanser"))
                .body("brand", Matchers.nullValue())
                .body("categoryEnum", Matchers.equalTo("CLEANSER"))
                .body("ingredients", Matchers.equalTo("Ceramides, Niacinamide"))
                .body("skinTypeEnum", Matchers.hasItem("OILY"))
                .body("concernTypeEnum", Matchers.hasItem("ACNE"))
                .body("description", Matchers.equalTo("Gentle foaming cleanser that removes oil without disrupting the skin barrier."))
                .body("imageUrl", Matchers.equalTo("https://example.com/images/cerave-foaming-cleanser.jpg"))
                .body("price", Matchers.equalTo(250000))
                .body("rating", Matchers.isA(Number.class))
                .body("status", Matchers.equalTo("ACTIVE"));

    }
}

