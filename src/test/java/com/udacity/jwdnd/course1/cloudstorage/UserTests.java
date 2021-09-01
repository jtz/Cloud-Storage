package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Selenium and JUnit Tests for User Signup, Login, and Unauthorized Access Restrictions.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTests {

    private WebDriver driver;
    private String baseURL;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     * Write a test that verifies that an unauthorized user can only access the login and signup pages.
     */
    @Test
    @Order(1)
    public void testUnauthorizedAccessRestrictions() {

        // test signup page access
        driver.get(baseURL + "/signup");
        assertEquals("Sign Up", driver.getTitle());

        // test login page access
        driver.get(baseURL + "/login");
        assertEquals("Login", driver.getTitle());

        // test home page access, unauthorized user will be redirected to login page
        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());

    }

    /**
     * Write a test that signs up a new user, logs in, verifies that the home page is accessible,
     * logs out, and verifies that the home page is no longer accessible.
     */
    @Test
    @Order(2)
    public void testSignupLoginAndLogout() throws InterruptedException {

        String username = "testUser";
        String password = "testPassword";
        String firstName = "testFirstName";
        String lastName = "testLastName";

        // signup a new user
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password);
        Thread.sleep(2_000);
        assertEquals("Sign Up", driver.getTitle());

        // login to access home page
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        Thread.sleep(2_000);
        assertEquals("Home", driver.getTitle());

        // logout and cannot access home page
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        Thread.sleep(2_000);
        assertEquals("Login", driver.getTitle());
    }

}

