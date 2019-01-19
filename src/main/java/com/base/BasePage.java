package com.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.locs.VladiLocs;

public class BasePage {

	public static WebDriver driver;
	static WebDriverWait wait;
	static VladiLocs loc = new VladiLocs();
	static JavascriptExecutor je = (JavascriptExecutor) driver;

	//******************** COMMON METHODS ***********************
	// explicit wait
	public void explicitWaitForElement(By by, int time) {

		wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public static void waitUntilTextChanges(By by, String toText, int time) {

		wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(by, toText));
	}

	public void clickButton(By by) {

		driver.findElement(by).click();
	}

	public String getTextOfElement(By by) {

		return driver.findElement(by).getText();
	}
	
	public List<WebElement> findCommonElements(By by){
		return driver.findElements(by);
	}

	public void enterText(By by, String text) {

		driver.findElement(by).sendKeys(text);
	}

	public void scrollToElement(By by) {

		WebElement element = driver.findElement(by);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void clickButtonByText(String text) {
		explicitWaitForElement(By.xpath("//button[text()='" + text + "']"), 10);
		clickButton(By.xpath("//button[text()='" + text + "']"));
	}

	public void clickLinkByText(String text) {
		explicitWaitForElement(By.xpath("//a[contains(text(), '" + text + "')]"), 10);
		clickButton(By.xpath("//a[contains(text(), '" + text + "')]"));
	}

	public void changeURL(String url) {
		driver.navigate().to(url);
	}

	public void goBackUrl() {
		driver.navigate().back();
	}


	public int countSimilarElements(By by) {

		return driver.findElements(by).size();
	}

}
