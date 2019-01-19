package POF;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.base.BaseTest;
import com.base.CommonUtils;
import com.base.PropertyController;
import com.locs.VladiLocs;

public class POFUltraMatchesTest {

	static VladiLocs loc = new VladiLocs();
	static BaseTest vladi = new BaseTest();
	PropertyController properties = new PropertyController();
	CommonUtils utils = new CommonUtils();
	CommonPOF pof = new CommonPOF();
	static ArrayList<String> alreadySent = null;
	static ArrayList<String> messages;
	String browser = properties.getBrowserProperty();

	String username = "";
	
	@BeforeEach
	public void startPOF() throws IOException {
		
		alreadySent = CommonUtils.readListFile("POFUltraMatches.txt");
		if(alreadySent.isEmpty()) {
			alreadySent = new ArrayList<String>();
		} 
		vladi.startDatingAppInBrowser(browser, "pof");
	}
	
	@AfterEach
	public void tearDown() throws IOException {
		CommonUtils.writeListToFile(alreadySent, "POFUltraMatches.txt");
		vladi.quitBrowser();
	}
	
	@Test
	@DisplayName("Ultra Matches feature in POF")
	public void pofUltraMatches() {
		
		// load messages
		messages = properties.getMessageToSend();
		
		pof.signInPOF();
		// go to ultra match
		vladi.explicitWaitForElement(loc.pofMyMatchesButton, 20);
		vladi.clickButton(loc.pofMyMatchesButton);
		vladi.explicitWaitForElement(loc.pofUltraMatchButton, 15);
		vladi.clickButton(loc.pofUltraMatchButton);

		// count in center tag profiles
		int profiles = vladi.countSimilarElements(loc.pofUltraMatchesProfiles);
		System.out.println("Found " + profiles + " profiles in Ultra Match!");

		// go through each profile
		for (int i = 1; i <= profiles; i++) {

			// perform the following on each profile
			try {
				vladi.explicitWaitForElement(loc.pofRefineMatches, 30);
				vladi.scrollToElement(By.xpath("//center/div[" + i + "]//td[2]//img"));
				// vladi.explicitWaitForElement(By.xpath("//center/div[" + i + "]//td[2]//img"),
				// 5);
				vladi.clickButton(By.xpath("//center/div[" + i + "]//td[2]//img"));
				Thread.sleep(2000);

				// inside user profile
				username = vladi.getTextOfElement(loc.userNameField);
				System.out.println("Clicked on: " + username);

				// check if message already sent to this user
				if (alreadySent.contains(username)) {

					System.out.println("Already sent message to user: " + username);
					System.out.println("Going back to Ultra Matches...");
					vladi.goBackUrl();
				} else {

					vladi.explicitWaitForElement(loc.pofUltraMatchProfilePic, 20);
					vladi.explicitWaitForElement(loc.pofSendMessageTextArea, 20);
					vladi.scrollToElement(loc.pofSendMessageTextArea);
					// send random messages
					vladi.enterText(loc.pofSendMessageTextArea, messages.get(utils.getRandomNumberInRange(0, 4)));
					vladi.clickButton(loc.pofSendMessageButton);

					alreadySent.add(username);
					System.out.println("Added user to the list of messaged users...");
					System.out.println("The updated user list is: \n" + alreadySent);
				}

				// validate for the user has too many messages
				try {
					// navigates back to main pof page
					vladi.explicitWaitForElement(loc.pofMyMatchesButton, 10);
				} catch (Exception e) {

					vladi.explicitWaitForElement(By.xpath("//a[text()='inbox']"), 20);
					vladi.clickButton(By.xpath("//a[text()='inbox']"));
				}

				vladi.clickButton(loc.pofMyMatchesButton);
				vladi.explicitWaitForElement(loc.pofUltraMatchButton, 15);
				vladi.clickButton(loc.pofUltraMatchButton);
			} catch (Exception e) {
				e.printStackTrace();
				Assertions.fail();
			}

		}
	}
}
