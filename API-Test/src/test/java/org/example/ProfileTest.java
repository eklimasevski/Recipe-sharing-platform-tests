package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProfileTest {
    @Test
    void whenPutProfile_ThenReturn200andBody() {
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
                            "password": "Testukas123!",
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
    void whenPutProfileWithExistDisplayName_ThenReturn200() {
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
                            "password": "Testukas123!",
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
                .statusCode(400)
                .body("error",equalTo("User with this display name already exists."));
    }

    @Test
    void whenPutProfileWithLowerCaseName_ThenReturn200() {
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
                            "password": "Testukas123!",
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
                .statusCode(400)
                .body(
                        "lastName",
                        equalTo("Last name must start out of a letter in uppercase"),
                        "firstName",
                        equalTo("First name must start out of a letter in uppercase"));
    }

    @Test
    void whenPutProfileWithOffensiveWordsName_ThenReturn200() {
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
                                    "password": "Testukas123!",
                                    "firstName": "fuck",
                                    "lastName": "damn",
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
                .statusCode(400);
    }

    @Test
    void whenPutProfileWithAnotherUserId_ThenReturn401andBody() {
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
                            "password": "Testukas123!",
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
    void whenPutProfileWithNotExistId_ThenReturn401andBody() {
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
                            "password": "Testukas123!",
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
    void whenPutProfileWithIncorrectGender_ThenReturn400andBody() {
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
                            "password": "Testukas123!",
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
                .statusCode(400)
                .body("gender", equalTo("Gender must be Male, Female, or Other"));
    }
//    @ParameterizedTest
//    @CsvFileSource(resources = "/WrongsEmails")
//    public void whenPutProfileWithInvalidEmail(){
//        given().auth()
//                .basic("Testukas1234@gmail.com", "Testukas123!")
//                .contentType("multipart/form-data")
//                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
//                .multiPart(
//                        "userDto",
//                        """
//                        {
//                            "displayName": "Testas",
//                            "email": "%s",
//                            "password": "Testukas123!",
//                            "firstName": "Testukas",
//                            "lastName": "Testauskas",
//                            "gender": "Male"
//                        }
//                        """,
//                        "application/json")
//                .when()
//                .request("PUT", "/update-user/113")
//                .then()
//                .assertThat()
//                .log()
//                .all()
//                .statusCode(400)
//                .body("email", equalTo("Please provide a valid email address"));
//    }
    @Test
    void whenPutProfileWithAnotherUserEmail_ThenReturn400andBody() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testas",
                            "email": "Testukas123@gmail.com",
                            "password": "Testukas123!",
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
                .statusCode(400)
                .body("error",equalTo("User with this email already exists."));
    }
    @Test
    void whenPutProfileWithUpperCaseWords_ThenReturn400andBody() {
        given().auth()
                .basic("Testukas1234@gmail.com", "Testukas123!")
                .contentType("multipart/form-data")
                .multiPart("file", new File("src/test/resources/test1.jpg"), "image/jpeg")
                .multiPart(
                        "userDto",
                        """
                        {
                            "displayName": "Testas",
                            "email": "Testukas123@gmail.com",
                            "password": "Testukas123!",
                            "firstName": "TESTUKAS",
                            "lastName": "TESTUKAS",
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
                .statusCode(400)
                .body("error",equalTo("User with this email already exists."));
    }
}
