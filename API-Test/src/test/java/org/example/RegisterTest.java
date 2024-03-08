package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegisterTest {

    @Test
    void whenPostNewUser_thenReturn201AndBody() throws SQLException {
        //        given
        Response response = given().body(
                        """
                                {
                                    "displayName": "Test",
                                    "email": "Testukas@gmail.com",
                                    "password": "aA1=ddr",
                                    "firstName": "Testukas",
                                    "lastName": "Testas",
                                    "gender": "Male"
                                }
                                """)
                .contentType(ContentType.JSON)
                //                when
                .when()
                .request("POST", "/register")
                //                then
                .then()
                .assertThat()
                .statusCode(201)
                .body(
                        "displayName",
                        equalTo("Test"),
                        "email",
                        equalTo("Testukas@gmail.com"),
                        "firstName",
                        equalTo("Testukas"),
                        "lastName",
                        equalTo("Testas"),
                        "gender",
                        equalTo("Male"),
                        "username",
                        equalTo("Testukas@gmail.com"))
                .extract()
                .response();

        long id = response.jsonPath().getLong("id");

        cleanUpDatabase(id);
    }

    @Test
    void whenPostAlreadyExistingUser_thenReturn400AndBody() {
        given().body(
                        """
                {
                "displayName": "Test1",
                "email": "Testukas1@gmail.com",
                "password": "aA1=ddr",
                "firstName": "Testukas",
                "lastName": "Testas",
                "gender": "Male"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body(
                        "displayName",
                        equalTo("User with display name Test1 already exists"),
                        "email",
                        equalTo("User with email Testukas1@gmail.com already exists"));
    }

    @Test
    void whenPostAlreadyExistingUserDisplayName_thenReturn400andBody() {
        given().body(
                        """
                {
                "displayName": "Test1",
                "email": "Testukas1@gmail.com",
                "password": "aA1=ddr",
                "firstName": "Testukas",
                "lastName":"Testas",
                "gender":"Male"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("displayName", equalTo("User with display name Test1 already exists"));
    }

    @Test
    void whenPostAlreadyExistingUserEmail_thenReturn400andBody() {
        given().body(
                        """
                {
                "displayName": "Test1",
                "email": "Testukas1@gmail.com",
                "password": "aA1=ddr",
                "firstName": "Testukas",
                "lastName": "Testas",
                "gender": "Male"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("email", equalTo("User with email Testukas1@gmail.com already exists"));
    }

    @Test
    void whenPostUserWithInvalidEmailFormat_thenReturn400AndBody() {
        given().body(
                        """
                {
                "displayName": "Test1",
                "email": "Testukas1gmail.com",
                "password": "aA1=ddr",
                "firstName": "Testukas",
                "lastName": "Testas",
                "gender": "Male"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("email", equalTo("Please provide a valid email address"));
    }

    @Test
    void whenPostUserWithInvalidPasswordFormat_thenReturn400AndBody() {
        given().body(
                        """
                {
                "displayName": "Test1",
                "email": "Testukas1@gmail.com",
                "password": "Abcda1",
                "firstName": "Tetukas",
                "lastName": "Testas",
                "gender": "Male"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("password", equalTo("Password must contain at least one special character"));
    }

    @Test
    void whenPostUserWithoutFirstName_thenReturn400andBody() {
        given().body(
                        """
                {
                "displayName": "Test",
                "email": "Testukas@gmail.com",
                "password": "aA1=ddr",
                "lastName": "Testas",
                "gender": "Male"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("firstName", equalTo("First name cannot be empty"));
    }

    @Test
    void whenPostUserWithInvalidGenderFormat_thenReturn400AndBody() {
        given().body(
                        """
                {
                "displayName": "Test",
                "email": "Testukas@gmail.com",
                "password": "aA1=ddr",
                "lastName": "Testas",
                "gender": "Mal"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("gender", equalTo("Gender must be Male, Female, or Other"));
    }

    public void cleanUpDatabase(long id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/rsp", "sa", "123456");
        String deleteQuery = "DELETE FROM users WHERE id = " + id;

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
