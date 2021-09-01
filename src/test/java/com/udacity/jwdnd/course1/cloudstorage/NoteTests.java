package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NoteSection;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.thymeleaf.util.StringUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium and JUnit Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteTests {

    private NoteSection noteSection;
    private ResultPage resultPage;
    private static WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void beforeAll() {
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

        // access Home page: Note tab
        noteSection = new NoteSection(driver);
        noteSection.goToNoteTab();
        Thread.sleep(2_000);

        // initialize ResultPage object
        resultPage = new ResultPage(driver);
    }

    /**
     * Write a test that creates a note, and verifies it is displayed.
     */
    @Test
    @Order(1)
    public void testCreateNote() throws InterruptedException {

        String noteTitle = "testNoteTitle";
        String noteDescription = "testNoteDescription";

        // create a note
        noteSection.clickAddNote();
        Thread.sleep(1_000);
        noteSection.fillNoteForm(noteTitle, noteDescription);
        Thread.sleep(1_000);
        noteSection.clickSaveNote();
        Thread.sleep(1_000);

        // verify the created note is displayed
        resultPage.goToHomeLink();
        Thread.sleep(1_000);
        noteSection.goToNoteTab();
        Thread.sleep(1_000);
        noteSection.clickEditNote();
        Thread.sleep(1_000);
        assertEquals("testNoteTitle", noteSection.getNoteTitle());
        assertEquals("testNoteDescription", noteSection.getNoteDescription());

    }

    /**
     * Write a test that edits an existing note and verifies that the changes are displayed.
     */
    @Test
    @Order(2)
    public void testEditNote() throws InterruptedException {

        // edit an existing note
        noteSection.clickEditNote();
        Thread.sleep(1_000);
        noteSection.fillNoteForm("New", "New");
        Thread.sleep(1_000);
        noteSection.clickSaveNote();
        Thread.sleep(1_000);

        // verify the changes are displayed
        resultPage.goToHomeLink();
        Thread.sleep(1_000);
        noteSection.goToNoteTab();
        Thread.sleep(1_000);
        noteSection.clickEditNote();
        Thread.sleep(1_000);
        assertEquals("testNoteTitleNew", noteSection.getNoteTitle());
        assertEquals("testNoteDescriptionNew", noteSection.getNoteDescription());

    }

    /**
     * Write a test that deletes a note and verifies that the note is no longer displayed.
     */
    @Test
    @Order(3)
    public void testDeleteNote() throws InterruptedException {

        // delete a note
        noteSection.clickDeleteNote();
        Thread.sleep(1_000);

        // verify the deleted note is no longer displayed
        resultPage.goToHomeLink();
        Thread.sleep(1_000);
        noteSection.goToNoteTab();
        Thread.sleep(1_000);
        assertTrue(StringUtils.isEmpty(noteSection.getNoteTitle()));

    }

}

