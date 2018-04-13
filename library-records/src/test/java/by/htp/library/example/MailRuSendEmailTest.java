package by.htp.library.example;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
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
	private final static String allowedSenderEmail = "tathtp@mail.ru";

	@BeforeMethod
	public void loginToEmailBox() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\AutomationTools\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.DAYS);
	}

	@AfterMethod
	public void afterTest()
	{
		driver.close();
	}

	@Test
	public void sendEmailTest() throws InterruptedException
	{
		openPage("http://mail.ru");
		loginAsUser(senderEmail, senderPassword);
		sendAnEmail(targetEmail, generateRandomSubject());
		String expectedValue = driver.findElement(By.xpath("//span[@class='message-sent__info']")).getText();
		assertTrue(expectedValue.equals(targetEmail), "Expected value doesn't match the email " + targetEmail);
	}

	@Test
	public void backAndForwardTest() throws InterruptedException
	{
		openPage("http://mail.ru");
		loginAsUser(targetLogin, targetPassword);

		while (true)
		{
			waitForNewEmailAndOpen();
			if (isSenderEmailAllowed(allowedSenderEmail))
			{
				replySimply();
				navigateToInbox();
			}
			else
			{
				navigateToInbox();
			}
		}
	}

	private boolean isSenderEmailAllowed(String allowedSenderEmail)
	{
		if (getSenderEmail().equals(allowedSenderEmail))
		{
			return true;
		}
		return false;
	}

	private String getSenderEmail()
	{
		//span[@class='b-contact-informer-target js-contact-informer' and parent::*[@class='b-letter__head__addrs__from']]
		String senderEmail = driver.findElement(
				By.xpath("//span[@class='b-contact-informer-target js-contact-informer' and parent::*[@class='b-letter__head__addrs__from']]"))
				.getAttribute("data-contact-informer-email");
		return senderEmail;
	}

	private void navigateToInbox() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@class='b-nav__item__text b-nav__item__text_unread']")).click();
		driver.switchTo().defaultContent();
	}

	private void replySimply() throws InterruptedException
	{
		Thread.sleep(1000); //если убрать этот слип будет валиться StaleElementReferenceException
		WebDriverWait wait = new WebDriverWait(driver, 10);
		ExpectedCondition condition = ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//span[text()='Ответить' and @class='b-toolbar__btn__text b-toolbar__btn__text_pad']")));
		WebElement replyButton = (WebElement) wait.until(condition);
		replyButton.click();
		driver.switchTo().activeElement().sendKeys(Keys.CONTROL, Keys.RETURN);
		WebElement continueButton = driver.findElement(
				By.xpath("//span[text()='Продолжить']//parent::*[@class='btn btn_stylish btn_main confirm-ok']"));
		continueButton.click();
	}

	private void waitForNewEmailAndOpen()
	{
		FluentWait wait = new FluentWait<>(driver).withTimeout(Duration.ofDays(1)).pollingEvery(Duration.ofSeconds(1)).ignoring(
				NoSuchElementException.class);
		ExpectedCondition condition = ExpectedConditions.visibilityOf(
				driver.findElement(By.xpath("//div[@id='b-letters']//*[contains(@class, 'item_unread')]")));
		WebElement unreadInboxEmail = (WebElement) wait.until(condition);
		unreadInboxEmail.click();
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
		driver.get(url);
	}

	private void signOut()
	{
		(new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-title='выход']")))).click();
	}
}
