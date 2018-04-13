package by.htp.library.example;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MailRuSendEmailTest
{

	private WebDriver driver;
	private final static String targetEmail = "qaq959@mail.ru";
	private final static String targetLogin = "qaq959";
	private final static String targetPassword = "Epam12345N";
	private final static String senderEmail = "tathtp@mail.ru";
	private final static String senderLogin = "tathtp";
	private final static String senderPassword = "Klopik123";

	@BeforeMethod
	public void loginToEmailBox() throws InterruptedException
	{
		openPage("http://mail.ru");
	}

	@AfterMethod
	public void afterTest()
	{
		driver.close();
	}

	@Test
	public void sendEmailTest() throws InterruptedException
	{
		loginAsUser(senderEmail, senderPassword);
		sendAnEmail(targetEmail, generateRandomSubject());
		String expectedValue = driver.findElement(By.xpath("//span[@class='message-sent__info']")).getText();
		assertTrue(expectedValue.equals(targetEmail), "Expected value doesn't match the email " + targetEmail);
	}

	@Test
	public void backAndForwardTest() throws InterruptedException
	{
		loginAsUser(targetLogin, targetPassword);
		FluentWait wait = new FluentWait<>(driver).withTimeout(Duration.ofDays(1)).pollingEvery(Duration.ofSeconds(1)).ignoring(
				NoSuchElementException.class);
		ExpectedCondition condition = ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='b-letters']//*[contains(@class, 'item_unread')]"));
		WebElement unreadInboxEmail = (WebElement) wait.until(condition);

//		WebElement firstInbox = driver.findElement(By.xpath("//div[@class='b-datalist__body']/child::*[1]"));
//		if (firstInbox.getAttribute("class").contains(String.valueOf("item_unread")))
//		{
//		}
			unreadInboxEmail.click();
			WebElement replyButton = driver.findElement(
					By.xpath("//span[text()='Ответить' and @class='b-toolbar__btn__text b-toolbar__btn__text_pad']"));
			replyButton.click();
			driver.switchTo().activeElement().sendKeys(Keys.CONTROL, Keys.RETURN);
			//			Thread.sleep(5000);
		WebElement continueButton = driver.findElement(By.xpath("//span[text()='Продолжить']//parent::*[@class='btn btn_stylish btn_main confirm-ok']"));
//			WebElement continueButton = list.get(1);
			continueButton.click();
		}

	private String generateRandomSubject()
	{
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		return "Automation test subject" + String.valueOf(n);
	}

	private void sendAnEmail(String targetEmail, String subject)
	{
		WebElement newLetterButton = (new WebDriverWait(driver, 10).until(
				ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-name='compose' and @title='Написать письмо (N)']"))));
		newLetterButton.click();
		WebElement inputFieldTo = driver.findElement(By.xpath("//textarea[@data-original-name='To']"));
		inputFieldTo.sendKeys(targetEmail);
		driver.findElement(By.xpath("//input[@data-bem='b-input' and @name='Subject']")).sendKeys(subject);
		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@id,'composeEditor_ifr')]")));
		driver.findElement(By.xpath("//body[@id='tinymce']")).sendKeys("Automation test body");
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[text()='Отправить']")).click();
	}

	private void loginAsUser(String email, String password) throws InterruptedException
	{
		WebElement loginInput = driver.findElement(By.id("mailbox:login"));
		WebElement passwordInput = driver.findElement(By.id("mailbox:password"));
		loginInput.sendKeys(email);
		passwordInput.sendKeys(password);
		passwordInput.submit();
		Thread.sleep(5000);
	}

	private void openPage(String url)
	{
		System.setProperty("webdriver.chrome.driver", "C:\\AutomationTools\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.DAYS);
		driver.get(url);
	}

	private void signOut()
	{
		// driver.findElement(By.xpath("//*[@data-title='выход']")).click();
		(new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-title='выход']")))).click();
	}
}
