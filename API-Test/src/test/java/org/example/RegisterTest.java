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
    void whenPostUser_thenReturn201AndBody() throws SQLException {
        //        given
        Response response = given().body(
                        """
                                {
                                    "displayName": "Test06",
                                    "email": "Testukas06@gmail.com",
                                    "password": "aA1=ddr",
                                    "firstName": "Testukass",
                                    "lastName": "Testass",
                                    "gender": "Female"
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
                        equalTo("Test06"),
                        "email",
                        equalTo("Testukas06@gmail.com"),
                        "firstName",
                        equalTo("Testukass"),
                        "lastName",
                        equalTo("Testass"),
                        "gender",
                        equalTo("Female"),
                        "username",
                        equalTo("Testukas06@gmail.com"))
                .extract()
                .response();

        long id = response.jsonPath().getLong("id");

        cleanUpDatabase(id);
    }

    @Test
    void whenPostAlreadyExistingUser_thenReturn200() {}

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
