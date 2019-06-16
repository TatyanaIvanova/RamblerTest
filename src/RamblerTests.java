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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RamblerTests {
	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception
	{
		// Устанавливаем путь к WebDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown() throws Exception
	{
		driver.quit();
	}
	
	private void openRegistrationPage() throws Exception
	{
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		// Дожидаемся чтобы страница отрисовывалась
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[text()='Регистрация Рамблер/почты']/..")));
		// если не подождать то тесты проходят через раз
		Thread.sleep(5000);
	}
	
	private void sleep() throws Exception
	{
		Thread.sleep(2000);
	}
	
	@Test
	// Проверка ввода пустого значения в поле «Почтовый ящик»
	public void emailLoginEmty() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
	
	@Test
	// Проверка ввода логина на недопустимую длину: минимальная длина минус 1 символ
	public void emailLoginLessThanMinLength() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("qw");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
	
	@Test
	// Проверка ввода логина с минимальной длиной
	public void emailLoginMinLenght() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("zxc");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test
	// Проверка ввода логина с максимальной длиной
	public void emailLoginMaxLenght() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("Ez9xlWp37l63GCLNjpyS3f9C2tw6ghoc");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test
	// Проверка ввода логина на недопустимую длину: максимальная длина плюс 1 символ
	public void emailLoginMaxLenghtPlusOneSymbol() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("Ez9xlWp37l63GCLNjpyS3f9C2tw6ghoc2");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Логин должен быть от 3 до 32 символов']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
	
	@Test
	// Проверка логина на недопустимые значения (кириллица)
	public void emailLoginMustNotContainCyrillicChars() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("Иванова");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();

		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
	
	@Test
	// Проверка логина что первый символ не нижнее подчеркивание
	public void emailLoginMustNotBeginWithAnUnderscore() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("_Ivanova");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
		
	@Test
	// Проверка логина, последний символ логина не должен быть нижним подчеркиванием
	public void emailLoginLastCharNotUnderscore() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("Ivanova_");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
		
	@Test
	// Проверка логина, логин не должен содержать символ @
	public void emailLoginMustNotContainDogChar() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("khi@hjijh");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
		
	@Test
	// Проверка логина, логин не должен содержать 2 одинаковые подряд идущие спец символы
	public void emailLoginMustNotContain2IdenticalConsecutiveSpecialChars() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("Ivanova..Tatyana");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Недопустимый логин']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
	
	@Test
	// Проверка логина, логин содержит латинские символы, спец символы (точку и нижнее подчеркивание), цифры
	public void emailLoginCorrect() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("Ivanova.Tatyana_1993");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='Недопустимый логин']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test	
	// Проверка логина, Проверка на занятость логина 
	public void emailLoginExist() throws Exception
	{
		openRegistrationPage();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("admin");
		sleep();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		sleep();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='Почтовый ящик недоступен для регистрации. Попробуйте другой']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		sleep();
	}
		
	@Test	
	// Проверка пароля на пустое значение 
	public void passwordEmpty() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test	
	// Длина пароля меньше на 1 символ минимально допустимой длины  
	public void passwordLengthLessThanMinLength() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("Qw24Kdh");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
		
	@Test	
	// Длина пароля минимально допустимая
	public void passwordLengthMin() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("Qw24Kdh2");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		List<WebElement> newPasswordErrorElements = driver.findElements(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertTrue(newPasswordErrorElements.size() == 0);
	}
	
	@Test	
	// Длина пароля максимально допустимая
	public void passwordLengthMax() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("Qw24Kdh2Qw24Kdh2Qw24Kdh2Qw24Kdh2");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		List<WebElement> newPasswordErrorElements = driver.findElements(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertTrue(newPasswordErrorElements.size() == 0);
	}
	
	@Test	
	// Длина пароля больше на 1 символ максимально допустимой длины
	public void passwordLengthMoreThanMaximumLength() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("Qw24Kdh2Qw24Kdh2Qw24Kdh2Qw24Kdh12");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test	
	// Пароль содержит одну русскую букву 
	public void passwordContainOneCyrillicChar() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("Д");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Вы вводите русские буквы']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Пароль содержит один пробел
	public void passwordContainOneSpace() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys(" ");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Символ \" \" не поддерживается. Можно использовать символы ! @ $ % ^ & * ( ) _ - +']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Проверка на отсутствие строчной буквы 
	public void passwordLackLowercaseChar() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("1234FDGH");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Проверка на отсутствие заглавной буквы 
	public void passwordLackOfCapitalChar() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("nmkl7657");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Проверка на отсутствие цифры
	public void passwordLackOfNumber() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("MDSAtreg");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль должен содержать от 8 до 32 символов, включать хотя бы одну заглавную латинскую букву, одну строчную и одну цифру']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Проверка, что пароль средней сложности 
	public void passwordOfMediumDifficulty() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("1234w7QeW");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароль средней сложности']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Проверка, что пароль сложный
	public void passwordIsComplicated() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("1234w7Q!");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Сложный пароль']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Пароль и Повтор пароля не совпадают
	public void passwordAndRepeatPasswordDoNotMatch() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("qwerty123Q");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.sendKeys("qwerty123Q6");
		sleep();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		sleep();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='Пароли не совпадают']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		sleep();
	}
	
	@Test
	// Пароль и Повтор пароля совпадают
	public void passwordAndRepeatPasswordDoMatch() throws Exception
	{
		openRegistrationPage();
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.sendKeys("qwerty123Q");
		sleep();
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.sendKeys("qwerty123Q");
		sleep();
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		sleep();
		
		List<WebElement> passwordDoNotMatchElements = driver.findElements(By.xpath(".//*[text()='Пароли не совпадают']/.."));
		Assert.assertTrue(passwordDoNotMatchElements.size() == 0);
		sleep();
	}
}
