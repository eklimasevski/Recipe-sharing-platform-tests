package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProfileTest {
    @Test
    void whenPutCategories_ThenReturn200andBody() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testas",
                            "email": "Testukas1234@gmail.com",
                            "password": "Testukas1!",
                            "firstName": "Testukas",
                            "lastName": "Testauskas",
                            "gender": "Male"
                        }
                        """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/113")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200)
                .body("id", equalTo(113), "email", equalTo("Testukas1234@gmail.com"));
    }

    @Test
    void whenPutCategoriesWithExistDisplayName_ThenReturn200() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Test",
                            "email": "Testukas1234@gmail.com",
                            "password": "Testukas1!",
                            "firstName": " ",
                            "lastName": " ",
                            "gender": " "
                        }
                        """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/113")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(500);
    }
    @Test
    void whenPutCategoriesWithLowerCaseName_ThenReturn200() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testass",
                            "email": "Testukas1234@gmail.com",
                            "password": "Testukas1!",
                            "firstName": "testas",
                            "lastName": "testauskas",
                            "gender": "Male"
                        }
                        """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/113")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
    void whenPutCategoriesWithOffensiveWordsName_ThenReturn200() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                                {
                                    "displayName": "Testass",
                                    "email": "Testukas1234@gmail.com",
                                    "password": "Testukas1!",
                                    "firstName": "Damn",
                                    "lastName": "Damn",
                                    "gender": "Male"
                                }
                                """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/113")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200);
    }
    @Test
    void whenPutCategoriesWithAnotherUserId_ThenReturn401andBody() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testas",
                            "email": "Testukas1234@gmail.com",
                            "password": "Testukas1!",
                            "firstName": "Testukas",
                            "lastName": "Testauskas",
                            "gender": "Male"
                        }
                        """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/4")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(401)
                .body("error", equalTo("User is not authorized to update this user."));
    }
    @Test
    void whenPutCategoriesWithNotExistId_ThenReturn401andBody() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testas",
                            "email": "Testukas1234@gmail.com",
                            "password": "Testukas1!",
                            "firstName": "Testukas",
                            "lastName": "Testauskas",
                            "gender": "Male"
                        }
                        """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/777")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(401)
                .body("error", equalTo("User is not authorized to update this user."));
    }
    @Test
    void whenPutCategoriesWithNotCorrectGender_ThenReturn400andBody() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testas",
                            "email": "Testukas1234@gmail.com",
                            "password": "Testukas1!",
                            "firstName": "Testukas",
                            "lastName": "Testauskas",
                            "gender": "Random"
                        }
                        """,
                        "application/json")
                .when()
                .request("PUT", "/update-user/113")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200);
    }
}
