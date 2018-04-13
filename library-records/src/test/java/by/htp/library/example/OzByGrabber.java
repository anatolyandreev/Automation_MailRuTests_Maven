package by.htp.library.example;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OzByGrabber {

	private WebDriver driver;

	@BeforeMethod
	public void testSetup() {
		System.setProperty("webdriver.chrome.driver", "c:/driver/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterMethod
	public void finishTest() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
	}

	@Test
	public void test1() throws InterruptedException {
		driver.get("http://oz.by");

		Thread.sleep(2000);

		WebElement element = driver.findElement(By.id("top-s"));

		element.sendKeys("Java");
		element.submit();

		List<WebElement> bookNames = new ArrayList<>();
		bookNames = driver.findElements(By.className("item-type-card__title"));
		for (WebElement element1 : bookNames) {
			System.out.println(element1.getText());
		}
		System.out.println("Number of books = " + bookNames.size());
	}

	@Test
	public void test2() throws InterruptedException, IOException {
		driver.get("http://google.by");

		WebElement elem = driver.findElement(By.id("lst-ib"));
		elem.sendKeys("java");
		elem.submit();
		Thread.sleep(3000);
		File scrFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("src\\main\\resources\\screenshots\\screenshot.png"));
		List<WebElement> results = driver.findElements(By.className("st"));
		WebElement webElement = driver.findElement(By.className("st"));
		assertTrue(webElement.getText().toLowerCase().contains("java"), "'java' word not found in results");
		// for (WebElement element : results) {
		// System.out.println(element.getText());
		// assertTrue(element.getText().toLowerCase().contains("java"),
		// "'Java' word is not found along the search results");
		// }
	}

	@Test
	public void test3() {
		driver.get("http://quizful.net");
		WebElement element = driver.findElement(By.linkText("зарегистрироваться"));
		element.click();
		List<WebElement> regForm = driver
				.findElements(By.xpath("//*[contains(@name, 'registrationForm') and @type != 'hidden']"));
		System.out.println("size " + regForm.size());
		for (WebElement el : regForm) {
			System.out.println(el.getText());
		}
	}
}