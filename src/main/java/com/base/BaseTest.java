package com.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTest extends BasePage {

	// initialize logger
	public final static Logger log = Logger.getLogger(BaseTest.class.getName());
	PropertyController properties = new PropertyController();
	// initialize locators
	public void quitBrowser() {

		log.log(Level.INFO, "Closing Webdriver...");
		BasePage.driver.close();
		if (properties.getBrowserProperty().equalsIgnoreCase("Chrome")) {
			BasePage.driver.quit();
		}
	}

	// using properties
	public void startDatingAppInBrowser(String browser, String app) {

		PropertyController properties = new PropertyController();
		boolean headless = properties.getHeadlessProperty();

		// read what browser is chosen and if user wants headless
		switch (browser.toLowerCase()) {

		// ********************* CHROME **************************************
		case "chrome":
			log.info("Initializing Chrome WebDriver...");
			try {
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				// headless condition
				if (headless) {
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
							"--ignore-certificate-errors", "--silent");
					driver = new ChromeDriver(options);
				} else {
					//ChromeOptions options = new ChromeOptions();
					//options.addArguments("--kiosk");
					//driver = new ChromeDriver(options);
					driver=new ChromeDriver();
				}

				log.log(Level.INFO, "Opening " + app + " on Chrome...");				
				switch(app.toLowerCase()) {
					case "okc": 
						driver.get("https://www.okcupid.com");
						break;
					case "pof":
						driver.get("https://www.pof.com");
						break;
				}
				
				// full screen
				driver.manage().window().maximize();
			} catch (Exception e) {
				log.log(Level.FATAL, "Chrome driver not found in repo...");
				e.printStackTrace();
			}
			break;

		// ********************* EDGE **************************************
		case "edge":
			log.info("Initializing Edge WebDriver...");
			try {
				System.setProperty("webdriver.edge.driver", "src/main/resources/MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
				
				log.log(Level.INFO, "Opening " + app + " on Edge...");				
				switch(app.toLowerCase()) {
					case "okc": 
						driver.get("https://www.okcupid.com");
						break;
					case "pof":
						driver.get("https://www.pof.com");
						break;
				}
				// full screen
				driver.manage().window().maximize();
			} catch (Exception e) {
				log.log(Level.FATAL, "Edge driver not found in repo...");
				e.printStackTrace();
			}
			break;

		// ********************* FIREFOX **************************************
		case "firefox":
			log.info("Initializing Firefox WebDriver...");
			try {
				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
				log.log(Level.INFO, "Opening " + app + " on Firefox...");				
				if (headless) {

					FirefoxBinary firefoxBinary = new FirefoxBinary();
					firefoxBinary.addCommandLineOptions("--headless");
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setBinary(firefoxBinary);
					driver = new FirefoxDriver(firefoxOptions);

				} else {
					driver = new FirefoxDriver();
				}
				switch(app.toLowerCase()) {
				case "okc": 
					driver.get("https://www.okcupid.com");
					break;
				case "pof":
					driver.get("https://www.pof.com");
					break;
			}
				// full screen
				driver.manage().window().maximize();
			} catch (Exception e) {
				log.log(Level.FATAL, "Firefox driver not found in repo...");
				e.printStackTrace();
			}

			// firefox security screen, you need to permanently add exception on first load
			break;

		default:
			log.log(Level.INFO, "Specified browser parameter did not match: Chrome, Edge, Firefox");
			break;
		}
	}
}
