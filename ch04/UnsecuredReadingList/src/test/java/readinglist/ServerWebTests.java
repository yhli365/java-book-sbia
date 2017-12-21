package readinglist;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServerWebTests {

	private static FirefoxDriver browser;

	@Value("${local.server.port}")
	private int port;

	@BeforeClass
	public static void openBrowser() {
		// System.setProperty(FirefoxDriver.SystemProperty.BROWSER_BINARY,
		// "D:/yapp/bjrun/Firefox35/firefox.exe");
		browser = new FirefoxDriver();
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void addBookToEmptyList() {
		String baseUrl = "http://localhost:" + port;
		browser.get(baseUrl);

		String currentUrl = browser.getCurrentUrl();
		assertEquals(baseUrl + "/readingList", currentUrl);

		assertEquals("You have no books in your book list", browser.findElementByTagName("div").getText());

		browser.findElementByName("title").sendKeys("BOOK TITLE");
		browser.findElementByName("author").sendKeys("BOOK AUTHOR");
		browser.findElementByName("isbn").sendKeys("1234567890");
		browser.findElementByName("description").sendKeys("DESCRIPTION");
		browser.findElementByTagName("form").submit();

		WebElement dl = browser.findElementByCssSelector("dt.bookHeadline");
		assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)", dl.getText());
		WebElement dt = browser.findElementByCssSelector("dd.bookDescription");
		assertEquals("DESCRIPTION", dt.getText());
	}

	@AfterClass
	public static void closeBrowser() {
		browser.quit();
	}

}
