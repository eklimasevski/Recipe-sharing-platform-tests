package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class Categories {
    @Test
    void whenPostCategories_thenReturn200() throws SQLException {
        Response response = given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .body(
                        """
                                {
                                    "name": "DeleteAfterPost"
                                }
                                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("DeleteAfterPost"))
                .extract()
                .response();
        long id = response.jsonPath().getLong("id");

        cleanUpDatabase(id);
    }

    @Test
    void whenGetCategories_thenReturn200() throws SQLException {
        given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .when()
                .request("GET", "/categories")
                .then()
                .assertThat()
                .statusCode(200)
                .body(
                        "id",
                        contains(34, 3, 4, 1, 33),
                        "name",
                        contains(
                                "APITestas", "Kategorijos", "Kategorijosfdfg", "Kategorijostestk", "Krijosfdfsdfsdfg"));
    }

    @Test
    void whenPostCategoriesWitoutAdminRole_ThenReturn403() {
        given().auth()
                .basic("Testukas123@gmail.com", "Testukas123!")
                .body("""
                {
                "name": "BeAdmin"
                }""")
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(403)
                .body(
                        "status",
                        equalTo(403),
                        "error",
                        equalTo("Forbidden"),
                        "message",
                        equalTo("Forbidden"),
                        "path",
                        equalTo("/categories"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/SymbolsForCategories")
    void whenPostCategoriesWithSymbols_thenReturn400(String input) throws SQLException {
        given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .body(
                        """
                                {
                                    "name": "%s"
                                }
                                """
                                .formatted(input))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(400);
        // 4-20 symbl, uniq
    }

    @Test
    void whenPostCategoriesExistName_ThenReturn400() {
        given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .body("""
                {
                "name": "Kategorijos"
                }""")
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Category with this name already exists"));
    }

    @Test
    void whenPostCategoriesWithMoreThat20characters_ThenReturn400() {
        given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .body("""
                {
                "name": "TestTestTestTestTestt"
                }""")
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("Name must be from 4 to 20 characters"));
    }

    @Test
    void whenPostCategoriesWithLessThat4characters_ThenReturn400() {
        given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .body("""
                {
                "name": "Tes"
                }""")
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("Name must be from 4 to 20 characters"));
    }

    @Test
    void whenPostCategoriesWithoutAuth_ThenReturn401() {
        given().body("""
                {
                "name": "Test"
                }""")
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    void whenPostCategoriesWhitFirstLowerCase() {
        given().auth()
                .basic("Testukas1@gmail.com", "Testukas123!")
                .body("""
                {
                "name": "test"
                }""")
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/categories")
                .then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("Name must start with an uppercase letter and contain only letters and spaces"));
    }

    public void cleanUpDatabase(long id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/rsp", "sa", "123456");
        String deleteQuery = "DELETE FROM categories WHERE id = " + id;

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
