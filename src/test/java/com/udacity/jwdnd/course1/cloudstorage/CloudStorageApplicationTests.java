package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialsPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static final String SUCCESS_MESSAGE = "Your changes were successfully saved. Click here to continue.";
	public String baseURL;
	public String username = "testuser";
	public String password = "easypass";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.baseURL = "http://localhost:" + this.port;
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void signUp() {
		String firstName = "Victor";
		String lastName = "Test";
		driver.get(this.baseURL + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.doSignUp(firstName, lastName, this.username, this.password);
	}

	public void login(){
		driver.get(this.baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.doLogin(this.username, this.password);
	}

	@Test
	public void getSignUpPage() {
		driver.get(this.baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get(this.baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testRegistrationAndLogin() {
		signUp();
		login();
		// Do logout
		driver.get(this.baseURL + "/login?logout");
		// Check that we were able to logout, this can only be achieved if we signed up and logged out
		Assertions.assertEquals("You have been logged out", driver.findElement(By.id("logout-msg")).getText());
	}

	@Test
	public void createNoteTest() {
		// Stub
		signUp();
		login();
		NotesPage notesPage = new NotesPage(driver);

		// Test
		createNote(notesPage, "Test note title", "Test note description");

		// Verify
		Assertions.assertEquals(SUCCESS_MESSAGE, driver.findElement(By.id("successMsg")).getText());
	}

	@Test
	public void editNoteTest() {
		// Stub
		signUp();
		login();
		NotesPage notesPage = new NotesPage(driver);

		createNote(notesPage, "Test note title", "Test note description");
		Assertions.assertEquals(SUCCESS_MESSAGE, driver.findElement(By.id("successMsg")).getText());
		driver.get(baseURL + "/home");

		// Test
		notesPage.openNotesTab();
		notesPage.editNote("Modified note title", "Test note description");
		driver.get(baseURL + "/home");
		notesPage.openNotesTab();

		// Verify
		Assertions.assertEquals("Modified note title",  driver.findElement(By.id("noteTitle")).getAttribute("innerHTML"));
	}

	@Test
	public void deleteNoteTest() {
		// Stub
		signUp();
		login();
		NotesPage notesPage = new NotesPage(driver);
		createNote(notesPage, "Test Note title", "Test note description");
		Assertions.assertEquals(SUCCESS_MESSAGE, driver.findElement(By.id("successMsg")).getText());
		driver.get(baseURL + "/home");
		notesPage.openNotesTab();

		// Test
		notesPage.deleteNote();
		driver.get(baseURL + "/home");
		notesPage.openNotesTab();

		// Verify
		Assertions.assertEquals(false, notesPage.notesExist());
	}

	@Test
	public void createCredentialTest() {
		// Stub
		signUp();
		login();

		// Test
		CredentialsPage credentialsPage = new CredentialsPage(driver);
		createCredential(credentialsPage);

		// Verify
		Assertions.assertEquals(SUCCESS_MESSAGE, driver.findElement(By.id("successMsg")).getText());
		driver.get(this.baseURL + "/home");
		credentialsPage.openCredentialsTab();
		Assertions.assertEquals("github.io",  driver.findElement(By.id("credentialUrl")).getAttribute("innerHTML"));
		Assertions.assertEquals("test",  driver.findElement(By.id("credentialUser")).getAttribute("innerHTML"));
	}

	@Test
	public void editCredentialTest() {
		// Stub
		signUp();
		login();
		CredentialsPage credentialsPage = new CredentialsPage(driver);
		createCredential(credentialsPage);
		Assertions.assertEquals(SUCCESS_MESSAGE, driver.findElement(By.id("successMsg")).getText());
		driver.get(this.baseURL + "/home");
		credentialsPage.openCredentialsTab();

		// Test
		credentialsPage.editCredential("github.com", "Edited username", "Edited password");

		// Verify
		driver.get(this.baseURL + "/home");
		credentialsPage.openCredentialsTab();
		Assertions.assertEquals("github.com",  driver.findElement(By.id("credentialUrl")).getAttribute("innerHTML"));
	}

	@Test
	public void deleteCredentialTest() {
		// Stub
		signUp();
		login();
		CredentialsPage credentialsPage = new CredentialsPage(driver);
		createCredential(credentialsPage);
		Assertions.assertEquals(SUCCESS_MESSAGE, driver.findElement(By.id("successMsg")).getText());
		driver.get(this.baseURL + "/home");
		credentialsPage.openCredentialsTab();

		// Test
		credentialsPage.deleteCredential();
		driver.get(this.baseURL + "/home");

		// Verify
		credentialsPage.openCredentialsTab();
		Assertions.assertEquals(false, credentialsPage.userHasCredentials());
	}

	public void createNote(NotesPage notesPage, String title, String description) {
		notesPage.addNote(title, description);
	}

	public void createCredential(CredentialsPage credentialsPage) {
		credentialsPage.addCredential("github.io", "test", "weakpassword");
	}

}
