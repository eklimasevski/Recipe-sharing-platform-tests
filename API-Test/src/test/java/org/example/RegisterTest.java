package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegisterTest {
    String displayName = "Test";

    @Test
    void whenPostUser_thenReturn201AndBody() {
        //        given
        given().body(
                        """
                                {
                                    "displayName": "Test05",
                                    "email": "Testukas05@gmail.com",
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
                        equalTo("Test05"),
                        "email",
                        equalTo("Testukas05@gmail.com"),
                        "firstName",
                        equalTo("Testukass"),
                        "lastName",
                        equalTo("Testass"),
                        "gender",
                        equalTo("Female"),
                        "username",
                        equalTo("Testukas05@gmail.com"));
    }

    @Test
    void whenPostAlreadyExistingUser_thenReturn200() {}

    @BeforeEach
    public void cleanUpDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/rsp", "sa", "123456");
        String deleteQuery = "DELETE FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
