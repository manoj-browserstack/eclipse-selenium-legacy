import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserStack {

	public static WebDriver driver;

	public static void main(String[] args) throws MalformedURLException {

		WebDriverManager.chromedriver().setup();

		// declare BrowserStack credentails as environment variables
		final String USERNAME = (System.getenv("BROWSERSTACK_USERNAME") != null)
				? System.getenv("BROWSERSTACK_USERNAME")
				: "username";
		final String AUTOMATE_KEY = (System.getenv("BROWSERSTACK_ACCESS_KEY") != null)
				? System.getenv("BROWSERSTACK_ACCESS_KEY")
				: "accesskey";
		// declare remote URL in a variable
		final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
		// intialize Selenium WebDriver
		MutableCapabilities capabilities = new MutableCapabilities();

		capabilities.setCapability("browserName", "Chrome");
		HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
		browserstackOptions.put("os", "Windows");
		browserstackOptions.put("osVersion", "7");
		browserstackOptions.put("browserVersion", "latest");
		browserstackOptions.put("local", "false");
		browserstackOptions.put("seleniumVersion", "4.10.0");
		capabilities.setCapability("bstack:options", browserstackOptions);

		driver = new RemoteWebDriver(new URL(URL), capabilities);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
		driver.get("https://bstackdemo.com/");

		driver.findElement(By.xpath("//span[@class=\"checkmark\" and text() = \"Samsung\"]")).click();
		String s20Price = driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20\"]//following-sibling::div/div/b[text() = \"999\"]"))
				.getText();

		String s20UltraPrice = driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20 Ultra\"]//following-sibling::div/div/b[text() = \"1399\"]"))
				.getText();

		int totalPrice = Integer.parseInt(s20Price) + Integer.parseInt(s20UltraPrice);

		System.out.println(totalPrice);

		driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20 Ultra\"]//following-sibling::div[@class=\"shelf-item__buy-btn\"]"))
				.click();

		driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20\"]//following-sibling::div[@class=\"shelf-item__buy-btn\"]"))
				.click();

		driver.findElement(By.xpath("//div[@class=\"buy-btn\" and text() = \"Checkout\"]")).click();

		driver.findElement(By.xpath("//*[@id=\"react-select-2-input\"]")).sendKeys("demouser", Keys.ENTER);
		driver.findElement(By.xpath("//*[@id=\"react-select-3-input\"]")).sendKeys("testingisfun99", Keys.ENTER);

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		try {
			driver.findElement(By.id("login-btn")).click();
		} catch (ElementClickInterceptedException e) {
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Login failed due to invalid password\"}}");
		}

		String checkOut = driver.findElement(By.className("cart-priceItem-value")).getText();
		System.out.println(checkOut);

		int finalPrice = Integer.parseInt(checkOut.substring(1, 5));
		System.out.println(finalPrice);

		// To mark the test as passed

		if (totalPrice == finalPrice) {
			System.out.println("Total price is matching with final price");
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"<reason>\"}}");
			// To mark the test as failed
		}

		else {
			System.out.println("Final is different from total price");
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"<reason>\"}}");

		}

		driver.quit();
	}
}
