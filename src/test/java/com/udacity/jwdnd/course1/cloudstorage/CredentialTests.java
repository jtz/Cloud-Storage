package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialSection;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.thymeleaf.util.StringUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium and JUnit Tests for Credential Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CredentialTests {



    private CredentialSection credentialSection;
    private ResultPage resultPage;
    private static WebDriver driver;
    private static Integer userId;

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CredentialService credentialService;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        String username = "testUser";
        String password = "testPassword";
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String baseURL = "http://localhost:" + port;

        // signup
        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password);

        // login
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // access Home page: Credential tab
        credentialSection = new CredentialSection(driver);
        credentialSection.goToCredentialsTab();
        Thread.sleep(2_000);

        // initialize ResultPage object
        resultPage = new ResultPage(driver);

        User user = userService.getUser(username);
        userId = user.getUserId();
    }

    /**
     * Write a test that creates a set of credentials, verifies that they are displayed,
     * and verifies that the displayed password is encrypted.
     */
    @Test
    @Order(1)
    public void testCreateCredential() throws InterruptedException {

        String credentialUrl = "testUrl";
        String username = "testUsername" + System.currentTimeMillis();
        String password = "testPassword";

        // creates a credential
        credentialSection.clickAddCredential();
        Thread.sleep(1_000);
        credentialSection.fillCredentialForm(credentialUrl, username, password);
        Thread.sleep(1_000);
        credentialSection.clickSaveCredential();
        Thread.sleep(1_000);

        // verify the created credential displayed
        resultPage.goToHomeLink();
        Thread.sleep(1_000);
        credentialSection.goToCredentialsTab();
        Thread.sleep(1_000);

        Credential credential = credentialService.getCredentialByUserAndUrl(userId, credentialUrl);
        System.out.println(credential.getUsername());
        Thread.sleep(1_000);

        assertEquals(credentialUrl, credential.getUrl());
		assertEquals(username, credentialSection.getUsernameText(credentialUrl));
        // verify the displayed password is encrypted
        assertEquals(encryptionService.encryptValue(password, credential.getKey()), credentialSection.getPasswordText(credentialUrl));

    }

    /**
     * Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted,
     * edits the credentials, and verifies that the changes are displayed.
     */
    @Test
    @Order(2)
    public void testViewAndEditCredential() throws InterruptedException {

        // views an existing set of credentials
        credentialSection.clickEditCredential();
        Thread.sleep(1_000);

        // verify the viewable password is unencrypted
        assertEquals("testPassword", credentialSection.getCredentialPassword());

        // edit the credential
        credentialSection.fillCredentialForm("New", "New", "New");
        Thread.sleep(1_000);
        credentialSection.clickSaveCredential();
        Thread.sleep(1_000);

        // verify the changes are displayed
        resultPage.goToHomeLink();
        Thread.sleep(1_000);
        credentialSection.goToCredentialsTab();
        Thread.sleep(1_000);
        credentialSection.clickEditCredential();
        Thread.sleep(1_000);
        assertEquals("testUrlNew", credentialSection.getCredentialUrl());
        assertEquals("testUsernameNew", credentialSection.getCredentialUsername());
        assertEquals("testPasswordNew", credentialSection.getCredentialPassword());

    }

    /**
     * Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
     */
    @Test
    @Order(3)
    public void testDeleteCredential() throws InterruptedException {

        // delete a credential
        credentialSection.clickDeleteCredential();
        Thread.sleep(1_000);

        // verify the deleted credential is no longer displayed
        resultPage.goToHomeLink();
        Thread.sleep(1_000);
        credentialSection.goToCredentialsTab();
        Thread.sleep(1_000);
        assertTrue(StringUtils.isEmpty(credentialSection.getCredentialUrl()));
    }

}





