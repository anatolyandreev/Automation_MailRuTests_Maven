package by.htp.library.example;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitsExample {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "c:/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.DAYS);
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		ExpectedCondition condition = ExpectedConditions.alertIsPresent();
		
		WebElement myDynamicElement = (WebElement) wait.until(condition);
				
		ExpectedCondition conditionClickable = ExpectedConditions.elementToBeClickable(By.id("el1"));
		
		WebElement elementSecond = (WebElement) wait.until(conditionClickable);

		
		
		
	}

}
