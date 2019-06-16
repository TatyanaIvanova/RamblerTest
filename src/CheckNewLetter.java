import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckNewLetter {
	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@After
	public void tearDown() throws Exception
	{
		driver.quit();
	}
	
	private void sleep() throws Exception
	{
		Thread.sleep(2000);
	}
	
	@Test
	public void checkNewLetter() throws Exception
	{
		driver.get("https://id.rambler.ru/login-20/login");
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		
		Thread.sleep(5000);
		
		WebElement loginField = driver.findElement(By.id("login"));
		loginField.sendKeys("TARA65eR@rambler.ru");
		sleep();
		
		WebElement passwordField = driver.findElement(By.id("password"));
		passwordField.sendKeys("qh83!F2D");
		sleep();
		
		WebElement loginButton = driver.findElement(By.xpath("//button[@data-cerber-id='login_form::main::login_button']"));
		loginButton.click();
		sleep();
		
		WebDriverWait userNameWait = new WebDriverWait(driver, 10, 1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//h1[text()='Лось Тюленев']/..")));
		sleep();
		
		List<WebElement> emailCounterElements = driver.findElements(By.xpath("//a[@data-cerber-topline='projects::mail']/span"));
		
		if (emailCounterElements.size() == 0)
		{
		    System.out.println("У вас нет новых сообщений!");
		}
		else
		{
		    System.out.println("У вас есть новые сообщения!");
		}
	}
	
}
