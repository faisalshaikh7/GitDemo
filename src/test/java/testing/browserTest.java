package testing;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class browserTest {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--incognito");

		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			driver.get("https://www.yesbank.in");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='yes_bank_logo']")));
			Thread.sleep(2000); // Let page settle

			// Get all anchor tags
			List<WebElement> anchorTags = driver.findElements(By.tagName("a"));
			Set<String> urls = new HashSet<>();

			for (WebElement element : anchorTags) {
				String href = element.getAttribute("href");
				if (href != null && !href.trim().isEmpty() && (href.startsWith("http") || href.startsWith("https"))) {
					urls.add(href.trim());
				}
			}

			System.out.println("🔗 Total unique URLs found: " + urls.size());

			for (String url : urls) {
				try {
					int statusCode = getHTTPStatusCode(url);
					if (statusCode >= 200 && statusCode < 400) {
						System.out.println("✅ " + statusCode + " --> " + url);
					} else {
						System.out.println("❌ " + statusCode + " --> " + url);
					}
				} catch (IOException e) {
					System.out.println("⚠️ Error --> " + url + " | " + e.getMessage());
				}
			}

		} finally {
			driver.quit();
		}
	}

	// Method to get HTTP response status code
	private static int getHTTPStatusCode(String urlString) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
		connection.setRequestMethod("GET");
//		connection.setConnectTimeout(5000);
//		connection.setReadTimeout(5000);
		connection.connect();
		return connection.getResponseCode();
	}
}
