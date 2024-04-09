package It.example;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.ProfilePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProfilePageTest extends BasePageTest {

    ProfilePage profilePage;

    String email = "Testukas1234@gmail.com";
    String password = "Testukas123!";
    String name = "Testas";
    String gender = "Male";

    public void updateProfileSteps(String email, String password) {
        profilePage = new ProfilePage(driver);

        profilePage.clickOnLoginButtonInNav();
        profilePage.enterEmail(email);
        profilePage.enterPassword(password);
        profilePage.clickButtonToLogin();
        profilePage.clickProfileButton();
    }

    public void fieldInput(
            String email, String password, String displayName, String firstName, String lastName, String gender) {
        profilePage.enterEmail(email);
        profilePage.enterPassword(password);
        profilePage.enterPasswordConfirm(password);
        profilePage.enterDisplayName(displayName);
        profilePage.enterFirstName(firstName);
        profilePage.enterLastName(lastName);
        profilePage.getGenderSelector(gender);
    }

    @Test
    void successfulUpdateWithoutPhoto() {
        String expectedAlertMessage = "Profile update successful!";
        updateProfileSteps(email, password);
        fieldInput(email, password, name, name, name, gender);
        profilePage.clickUpdateButton();

        String actualAletMessage =
                profilePage.getSuccessfulyRegistrationMessage().getText();

        Assertions.assertEquals(expectedAlertMessage, actualAletMessage);
    }

    @Test
    void successfulUpdate() {
        String expectedAlertMessage = "Profile update successful!";

        updateProfileSteps(email, password);

        profilePage.enterPassword(password);
        profilePage.enterPasswordConfirm(password);

        profilePage.upldoadPhoto("\\src\\test\\resources\\test1.jpg");

        profilePage.clickUpdateButton();
        String actualAletMessage =
                profilePage.getSuccessfulyRegistrationMessage().getText();

        Assertions.assertEquals(expectedAlertMessage, actualAletMessage);
    }

    @Test
    void displayNameFielValidationTest() {

        String expected = "User with this display name already exists.";

        updateProfileSteps(email, password);

        profilePage.enterPassword(password);
        profilePage.enterPasswordConfirm(password);

        profilePage.enterDisplayName("Test");

        profilePage.upldoadPhoto("\\src\\test\\resources\\test1.jpg");

        profilePage.clickUpdateButton();

        String actual = profilePage.getErrorMessage();

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void emailFielValidationTest() {

        String expected = "User with this email already exists.";

        updateProfileSteps(email, password);

        profilePage.enterPassword(password);
        profilePage.enterPasswordConfirm(password);

        profilePage.enterEmail("Testukas123@gmail.com");

        profilePage.upldoadPhoto("\\src\\test\\resources\\test1.jpg");

        profilePage.clickUpdateButton();

        String actual = profilePage.getErrorMessage();

        Assertions.assertEquals(expected, actual);

    }
    @Test
    void passwordFielValidationTest(){

        String expected = "The passwords do not match!";

        updateProfileSteps(email,password);

        profilePage.enterPassword(password);
        profilePage.enterPasswordConfirm("Testukas123");
        

        profilePage.upldoadPhoto("\\src\\test\\resources\\test1.jpg");

        profilePage.clickUpdateButton();

        String actual = profilePage.getErrorMessage();

        Assertions.assertEquals(expected,actual);

    }
}
