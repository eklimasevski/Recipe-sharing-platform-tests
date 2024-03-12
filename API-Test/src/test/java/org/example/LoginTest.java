package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    @Test
    void whenPostUser_thenReturn200() throws SQLException {
        //        given
        given().body(
                        """
                                {
                                    "email": "Testukas1@gmail.com",
                                        "password": "aA1=ddr"
                                }
                                """)
                .contentType(ContentType.JSON)
                //                when
                .when()
                .request("POST", "/login")
                //                then
                .then()
                .assertThat()
                .statusCode(200)
                .body(
                        "displayName",
                        equalTo("Test1"),
                        "email",
                        equalTo("Testukas1@gmail.com"),
                        "firstName",
                        equalTo("Testukas"),
                        "lastName",
                        equalTo("Testas"),
                        "gender",
                        equalTo("Male"),
                        "username",
                        equalTo("Testukas1@gmail.com"));
    }

    @Test
    void whenPostUserWithWrongPassword_thenReturn401() throws SQLException {
        //        given
        given().body(
                        """
                                {
                                    "email": "Testukas1@gmail.com",
                                        "password": "aA1=dd"
                                }
                                """)
                .contentType(ContentType.JSON)
                //                when
                .when()
                .request("POST", "/login")
                //                then
                .then()
                .assertThat()
                .statusCode(401)
                .body(
                        "message",
                        equalTo("The email or password provided is incorrect."));
    }

    @Test
    void whenPostUserWithWrongEmail_thenReturn401() throws SQLException {
        //        given
        given().body(
                        """
                                {
                                    "email": "Testukas@gmail.com",
                                        "password": "aA1=ddr"
                                }
                                """)
                .contentType(ContentType.JSON)
                //                when
                .when()
                .request("POST", "/login")
                //                then
                .then()
                .assertThat()
                .statusCode(401)
                .body(
                        "message",
                        equalTo("The email or password provided is incorrect."));
    }
}