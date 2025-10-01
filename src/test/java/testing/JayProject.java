package testing;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JayProject {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		driver.get("https://botsdna.com/");

		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
				"body > section:nth-child(4) > div:nth-child(1) > div:nth-child(3) > a:nth-child(20) > div:nth-child(1) > h3:nth-child(2)")))
				.click();
		Thread.sleep(2000); // Let page settle

		Set<String> window = driver.getWindowHandles();
		Iterator<String> iterator = window.iterator();
		String parentWindow = iterator.next();
		System.out.println("Parent window id: " + parentWindow);

		String childWindow = iterator.next();
		System.out.println("childWindow window id: " + childWindow);

		driver.switchTo().window(childWindow);

		Thread.sleep(2000);
		List<WebElement> elements = driver.findElements(By.xpath("/html/body/center/table/tbody/tr/td[2]"));

		// Print out the text of each element
		for (WebElement elementss : elements) {
			System.out.println(elementss.getText());
		}
	}
}
