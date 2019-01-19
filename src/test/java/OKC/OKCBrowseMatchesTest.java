package OKC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.base.BaseTest;
import com.base.CommonUtils;
import com.base.PropertyController;
import com.locs.VladiLocs;

import OKC.CommonOKC;

public class OKCBrowseMatchesTest {

	static VladiLocs loc = new VladiLocs();
	static BaseTest vladi = new BaseTest();
	PropertyController properties = new PropertyController();
	CommonUtils utils = new CommonUtils();
	CommonOKC okc = new CommonOKC();
	static ArrayList<String> alreadySent = null;
	static ArrayList<String> okcUserList = new ArrayList<String>();
	static ArrayList<String> messages;
	String browser = properties.getBrowserProperty();
	String numToSwipe = properties.getMavenSwipesProperty();
	
	@BeforeEach
	public void startPOF() throws IOException {
		alreadySent = CommonUtils.readListFile("OKCBrowseMatches.txt");
		if(alreadySent.isEmpty()) {
			alreadySent = new ArrayList<String>();
		} 
		vladi.startDatingAppInBrowser(browser, "okc");
	}
	
	@AfterEach
	public void tearDown() throws IOException {
		CommonUtils.writeListToFile(alreadySent, "OKCBrowseMatches.txt");
		vladi.quitBrowser();
	}
	
	@Test
	@DisplayName("OKC Browse Matches")
	public void OKCBrowseMatches() {
		
		// load messages
		messages = properties.getMessageToSend();
		okc.signInOKC();
		// browse matches
		vladi.explicitWaitForElement(loc.okcBrowseMatchesButton, 15);
		vladi.clickButton(loc.okcBrowseMatchesButton);
				
		// find users on the page
		vladi.explicitWaitForElement(loc.okcBrowseMatchesButton, 20);
		List<WebElement> okcUsers = BaseTest.driver.findElements(loc.okcBrowseMatchesUsers);
		for(WebElement okcuser : okcUsers) {
			okcUserList.add(okcuser.getText());
		}

		System.out.println("Found these users on OkCupid Matches..." + okcUserList);
		
		// iterate through the users and send message by their names
		for (int i = 0; i < okcUserList.size(); i++) {
			String okcUser = okcUserList.get(i).split(",")[0].toString();
			System.out.println("Clicked on: " + okcUser);
			vladi.explicitWaitForElement(By.xpath("//img[@alt='" + okcUser + "']"), 15);
			vladi.clickButton(By.xpath("//img[@alt='" + okcUser + "']"));
			// if user is not in alreadySent
			if (!alreadySent.contains("'"+okcUserList.get(i).toString()+"'")) {

				try {
					vladi.explicitWaitForElement(loc.okcLikeButton, 10);
					vladi.clickButton(loc.okcLikeButton);


				// wait for compose message to come up
				vladi.explicitWaitForElement(loc.okcMessageTextArea, 10);
				vladi.enterText(loc.okcMessageTextArea, messages.get(utils.getRandomNumberInRange(0, 4)));
				vladi.clickButton(By.xpath("//span[text()='Send']"));
				// wait for success message
				vladi.explicitWaitForElement(loc.okcSuccessMessageSent, 10);
				alreadySent.add("'"+okcUserList.get(i).toString()+"'");
				System.out.println("Successfully sent message to: " + okcUserList.get(i).toString());
				System.out.println("Added user to the list of messaged users...");
				System.out.println("The updated user list is: \n" + alreadySent + "\n");
				vladi.goBackUrl();

				} catch (Exception e) {

					// already liked user
					/*
					vladi.explicitWaitForElement(loc.okcMessageUserButton, 10);
					vladi.clickButton(loc.okcMessageUserButton);
					*/
					vladi.goBackUrl();
				}
				
			} else {

				System.out.println("Already sent message to: " + okcUserList.get(i).toString());
				vladi.goBackUrl();
			}

		}
	
	
	}
}
