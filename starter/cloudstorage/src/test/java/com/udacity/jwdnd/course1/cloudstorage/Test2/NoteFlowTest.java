package com.udacity.jwdnd.course1.cloudstorage.Test2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteFlowTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 2);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    private void signUp(){
        /* SIGN UP: START */
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys("gin");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys("gin");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("gin");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("gin");

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();
        /* SIGN UP: END */
    }

    private void login(){
        /* LOGIN: START */
        driver.get("http://localhost:" + this.port + "/login");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("gin");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("gin");

        WebElement loginBtn = driver.findElement(By.id("login-button"));
        Assertions.assertEquals("Login", loginBtn.getText());

        loginBtn.click();
        /* LOGIN: END */
    }

    @Test
    @Order(1)
    public void createAndDisplayTest(){
        signUp();
        login();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        /* ADD NOTE: START */
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        Assertions.assertEquals("Notes", noteTab.getText());
        noteTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNote")));
        WebElement addNoteBtn = driver.findElement(By.id("addNewNote"));
        addNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));

        noteTitle.click();
        noteTitle.sendKeys("Note title");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys("Note Description");

        WebElement noteForm = driver.findElement(By.id("noteForm"));
        noteForm.submit();
        /* ADD NOTE: END */

        backFromResult();

        /* CONFIRM NOTE CREATED: START */
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        noteTab = driver.findElement(By.id("nav-notes-tab"));
        Assertions.assertEquals("Notes", noteTab.getText());
        noteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Note title']")));
        noteTitle= driver.findElement(By.xpath("//th[text()='Note title']"));
        Assertions.assertEquals("Note title", noteTitle.getText());
        noteDescription = driver.findElement(By.xpath("//td[text()='Note Description']"));
        Assertions.assertEquals("Note Description", noteDescription.getText());
        /* CONFIRM NOTE CREATED: END */

    }

    @Test
    @Order(2)
    public void editNote(){
        /* EDIT NOTE: START */
        Assertions.assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//th[text()='Note title']"));
            driver.findElement(By.xpath("//td[text()='Note Description']"));
        });
        WebElement editBtn = driver.findElement(By.id("editNoteBtn"));
        Assertions.assertEquals("Edit", editBtn.getText());
        editBtn.click();

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.clear();
        noteTitle.sendKeys("Edit Note title");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.clear();
        noteDescription.sendKeys("Edit Note Description");

        WebElement noteForm = driver.findElement(By.id("noteForm"));
        noteForm.submit();
        /* EDIT NOTE: END */

        backFromResult();

        /* CONFIRM NOTE EDITED: START */
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        Assertions.assertEquals("Notes", noteTab.getText());
        noteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Edit Note title']")));
        noteTitle= driver.findElement(By.xpath("//th[text()='Edit Note title']"));
        Assertions.assertEquals("Edit Note title", noteTitle.getText());
        noteDescription = driver.findElement(By.xpath("//td[text()='Edit Note Description']"));
        Assertions.assertEquals("Edit Note Description", noteDescription.getText());
        /* CONFIRM NOTE EDITED: END */
    }

    @Test
    @Order(3)
    public void deleteAndConfirmNote(){
        /* DELETE NOTE: START */
        Assertions.assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//th[text()='Edit Note title']"));
            driver.findElement(By.xpath("//td[text()='Edit Note Description']"));
        });

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Edit Note title']")));
        WebElement noteTitle= driver.findElement(By.xpath("//th[text()='Edit Note title']"));
        Assertions.assertEquals("Edit Note title", noteTitle.getText());
        WebElement noteDescription = driver.findElement(By.xpath("//td[text()='Edit Note Description']"));
        Assertions.assertEquals("Edit Note Description", noteDescription.getText());

        WebElement deleteBtn = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[1]/a"));
        Assertions.assertEquals("Delete", deleteBtn.getText());
        deleteBtn.click();
        /* DELETE NOTE: END */

        backFromResult();

        /* CONFIRM NOTE DELETE: START */
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        Assertions.assertEquals("Notes", noteTab.getText());
        noteTab.click();

        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//th[text()='Edit Note title']")));
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//th[text()='Edit Note Description']")));
        /* CONFIRM NOTE DELETE: END */
    }

    private void backFromResult(){
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", driver.getTitle());

        WebElement success = driver.findElement(By.id("success"));
        Assertions.assertEquals("Success", success.getText());

        WebElement backHomeFromResult = driver.findElement(By.id("backHomeFromResult"));
        Assertions.assertEquals("here", backHomeFromResult.getText());
        backHomeFromResult.click();
    }

}
