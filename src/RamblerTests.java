import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RamblerTests {
	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		// Устанавливаем путь к WebDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	// Проверка ввода пустого значения в поле «Почтовый ящик»
	public void emailLoginEmty() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// Проверка ввода логина на недопустимую длину: минимальная длина минус 1 символ
	public void emailLoginLessThanMinLength() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("qw");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// Проверка ввода логина с минимальной длиной
	public void emailLoginMinLenght() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("zxc");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test
	// Проверка ввода логина с максимальной длиной
	public void emailLoginMaxLenght() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ez9xlWp37l63GCLNjpyS3f9C2tw6ghoc");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test
	// Проверка ввода логина на недопустимую длину: максимальная длина плюс 1 символ
	public void emailLoginMaxLenghtPlusOneSymbol() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ez9xlWp37l63GCLNjpyS3f9C2tw6ghoc2");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// Проверка логина на недопустимые значения (кириллица)
	public void emailLoginMustNotContainCyrillicChars() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Иванова");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();

		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// Проверка логина что первый символ не нижнее подчеркивание
	public void emailLoginMustNotBeginWithAnUnderscore() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("_Ivanova");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test
	// Проверка логина, последний символ логина не должен быть нижним подчеркиванием
	public void emailLoginLastCharNotUnderscore() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ivanova_");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test
	// Проверка логина, логин не должен содержать символ @
	public void emailLoginMustNotContainDogChar() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("khi@hjijh");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test
	// Проверка логина, логин не должен содержать 2 одинаковые подряд идущие спец символы
	public void emailLoginMustNotContain2IdenticalConsecutiveSpecialChars() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ivanova..Tatyana");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// Проверка логина, логин содержит латинские символы, спец символы (точку и нижнее подчеркивание), цифры
	public void emailLoginCorrect() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ivanova.Tatyana_1993");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='Недопустимый логин']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test	
	// Проверка логина, Проверка на занятость логина 
	public void emailLoginExist() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("admin");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Почтовый ящик недоступен для регистрации. Попробуйте другой']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test	
	// Проверка пароля на пустое значение 
	public void passwordEmpty() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		Thread.sleep(1000);
	}
	
	@Test	
	// Длина пароля меньше на 1 символ минимально допустимой длины  
	public void passwordLengthLessThanMinLength() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		Thread.sleep(1000);
	}
		
	@Test	
	// Длина пароля минимально допустимая
	public void passwordLengthMin() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh2");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		List<WebElement> newPasswordErrorElements = driver.findElements(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertTrue(newPasswordErrorElements.size() == 0);
	}
	
	@Test	
	// Длина пароля максимально допустимая
	public void passwordLengthMax() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh2Qw24Kdh2Qw24Kdh2Qw24Kdh2");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		List<WebElement> newPasswordErrorElements = driver.findElements(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertTrue(newPasswordErrorElements.size() == 0);
	}
	
	@Test	
	// Длина пароля больше на 1 символ максимально допустимой длины
	public void passwordLengthMoreThanMaximumLength() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh2Qw24Kdh2Qw24Kdh2Qw24Kdh12");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		Thread.sleep(1000);
	}
	
	
}
