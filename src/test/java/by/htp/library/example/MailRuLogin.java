package by.htp.library.example;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.mysql.cj.jdbc.Driver;

public class MailRuLogin {

	@Test
	private void loginTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "c:/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://mail.ru");

		WebElement loginInput = driver.findElement(By.id("mailbox:login"));
		WebElement passwordInput = driver.findElement(By.id("mailbox:password"));

		loginInput.sendKeys("tathtp");
		passwordInput.sendKeys("Klopik123");
		passwordInput.submit();

		WebElement mail = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='PH_user-email']")));

		assertTrue(driver.findElement(By.xpath("//*[@id='PH_user-email']")).getText().equals("tathtp@mail.ru"),
				"You're not logged in");

		Thread.sleep(2000);
		driver.close();
	}

}
