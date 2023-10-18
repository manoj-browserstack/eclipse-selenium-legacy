import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JavaTarining {

	public static WebDriver driver;

	public static void main(String[] args) throws MalformedURLException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		// visit the browser stack demo site
		driver.get("https://bstackdemo.com/");

		// Maximize the browser window
		driver.manage().window().maximize();

		// Set an implicit wait for 3000 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));

		// Open the website
		driver.get("https://bstackdemo.com/");

		// Click on the "Samsung" checkbox
		driver.findElement(By.xpath("//span[@class=\"checkmark\" and text() = \"Samsung\"]")).click();

		// Get the price of the Samsung Galaxy S20
		String s20Price = driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20\"]//following-sibling::div/div/b[text() = \"999\"]"))
				.getText();

		// Get the price of the Samsung Galaxy S20 Ultra
		String s20UltraPrice = driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20 Ultra\"]//following-sibling::div/div/b[text() = \"1399\"]"))
				.getText();

		// Calculate the total price by parsing the prices as integers
		int totalPrice = Integer.parseInt(s20Price) + Integer.parseInt(s20UltraPrice);

		// Print the total price
		System.out.println(totalPrice);

		// Click on the "Buy" button for Galaxy S20 Ultra
		driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20 Ultra\"]//following-sibling::div[@class=\"shelf-item__buy-btn\"]"))
				.click();

		// Click on the "Buy" button for Galaxy S20
		driver.findElement(By.xpath(
				"//p[@class=\"shelf-item__title\" and text() = \"Galaxy S20\"]//following-sibling::div[@class=\"shelf-item__buy-btn\"]"))
				.click();

		// Click on the "Checkout" button
		driver.findElement(By.xpath("//div[@class=\"buy-btn\" and text() = \"Checkout\"]")).click();

		// Enter username and password for login
		driver.findElement(By.id("react-select-2-input")).sendKeys("demouser");
		driver.findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("react-select-3-input")).sendKeys("testingisfun99", Keys.ENTER);

		driver.findElement(By.id("login-btn")).click();

		// Get the total price from the cart
		String checkOut = driver.findElement(By.className("cart-priceItem-value")).getText();

		// Print the total price
		System.out.println(checkOut);

		// Parse the final price as an integer
		int finalPrice = Integer.parseInt(checkOut.substring(1, 5));

		// Print the final price
		System.out.println(finalPrice);

		// Check if the total price matches the final price
		if (totalPrice == finalPrice) {
			System.out.println("Total price is matching with final price");
		} else {
			System.out.println("Final is different from total price");
		}

		// Quit the browser
		driver.quit();

	}

}
