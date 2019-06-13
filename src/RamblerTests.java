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
		// ������������� ���� � WebDriver
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	@Test
	// �������� ����� ������� �������� � ���� ��������� ����
	public void emailLoginEmty() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='����� ������ ���� �� 3 �� 32 ��������']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// �������� ����� ������ �� ������������ �����: ����������� ����� ����� 1 ������
	public void emailLoginLessThanMinLength() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("qw");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='����� ������ ���� �� 3 �� 32 ��������']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// �������� ����� ������ � ����������� ������
	public void emailLoginMinLenght() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("zxc");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='����� ������ ���� �� 3 �� 32 ��������']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test
	// �������� ����� ������ � ������������ ������
	public void emailLoginMaxLenght() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ez9xlWp37l63GCLNjpyS3f9C2tw6ghoc");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='����� ������ ���� �� 3 �� 32 ��������']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test
	// �������� ����� ������ �� ������������ �����: ������������ ����� ���� 1 ������
	public void emailLoginMaxLenghtPlusOneSymbol() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ez9xlWp37l63GCLNjpyS3f9C2tw6ghoc2");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='����� ������ ���� �� 3 �� 32 ��������']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// �������� ������ �� ������������ �������� (���������)
	public void emailLoginMustNotContainCyrillicChars() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("�������");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();

		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='������������ �����']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// �������� ������ ��� ������ ������ �� ������ �������������
	public void emailLoginMustNotBeginWithAnUnderscore() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("_Ivanova");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='������������ �����']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test
	// �������� ������, ��������� ������ ������ �� ������ ���� ������ ��������������
	public void emailLoginLastCharNotUnderscore() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ivanova_");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='������������ �����']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test
	// �������� ������, ����� �� ������ ��������� ������ @
	public void emailLoginMustNotContainDogChar() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("khi@hjijh");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='������������ �����']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test
	// �������� ������, ����� �� ������ ��������� 2 ���������� ������ ������ ���� �������
	public void emailLoginMustNotContain2IdenticalConsecutiveSpecialChars() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ivanova..Tatyana");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='������������ �����']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
	
	@Test
	// �������� ������, ����� �������� ��������� �������, ���� ������� (����� � ������ �������������), �����
	public void emailLoginCorrect() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("Ivanova.Tatyana_1993");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		List<WebElement> emailErrorElements = driver.findElements(By.xpath(".//*[text()='������������ �����']/.."));

		Assert.assertTrue(emailErrorElements.size() == 0);
	}
	
	@Test	
	// �������� ������, �������� �� ��������� ������ 
	public void emailLoginExist() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.click();
		loginField.sendKeys("admin");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		
		WebElement emailErrorElement = driver.findElement(By.xpath(".//*[text()='�������� ���� ���������� ��� �����������. ���������� ������']/.."));
		
		Assert.assertNotNull(emailErrorElement);
		Thread.sleep(1000);
	}
		
	@Test	
	// �������� ������ �� ������ �������� 
	public void passwordEmpty() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='������ ������ ��������� �� 8 �� 32 ��������, �������� ���� �� ���� ��������� ��������� �����, ���� �������� � ���� �����']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		Thread.sleep(1000);
	}
	
	@Test	
	// ����� ������ ������ �� 1 ������ ���������� ���������� �����  
	public void passwordLengthLessThanMinLength() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='������ ������ ��������� �� 8 �� 32 ��������, �������� ���� �� ���� ��������� ��������� �����, ���� �������� � ���� �����']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		Thread.sleep(1000);
	}
		
	@Test	
	// ����� ������ ���������� ����������
	public void passwordLengthMin() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh2");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		List<WebElement> newPasswordErrorElements = driver.findElements(By.xpath(".//*[text()='������ ������ ��������� �� 8 �� 32 ��������, �������� ���� �� ���� ��������� ��������� �����, ���� �������� � ���� �����']/.."));
		
		Assert.assertTrue(newPasswordErrorElements.size() == 0);
	}
	
	@Test	
	// ����� ������ ����������� ����������
	public void passwordLengthMax() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh2Qw24Kdh2Qw24Kdh2Qw24Kdh2");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		List<WebElement> newPasswordErrorElements = driver.findElements(By.xpath(".//*[text()='������ ������ ��������� �� 8 �� 32 ��������, �������� ���� �� ���� ��������� ��������� �����, ���� �������� � ���� �����']/.."));
		
		Assert.assertTrue(newPasswordErrorElements.size() == 0);
	}
	
	@Test	
	// ����� ������ ������ �� 1 ������ ����������� ���������� �����
	public void passwordLengthMoreThanMaximumLength() throws Exception {
		driver.get("https://id.rambler.ru/login-20/mail-registration");
		
		WebElement newPasswordField = driver.findElement(By.id("newPassword"));
		newPasswordField.click();
		newPasswordField.sendKeys("Qw24Kdh2Qw24Kdh2Qw24Kdh2Qw24Kdh12");
		
		WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
		confirmPasswordField.click();
		
		WebElement newPasswordErrorElement = driver.findElement(By.xpath(".//*[text()='������ ������ ��������� �� 8 �� 32 ��������, �������� ���� �� ���� ��������� ��������� �����, ���� �������� � ���� �����']/.."));
		
		Assert.assertNotNull(newPasswordErrorElement);
		Thread.sleep(1000);
	}
	
	
}
