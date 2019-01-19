package POF;

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

public class POFWillRespondTest {

	static VladiLocs loc = new VladiLocs();
	static BaseTest vladi = new BaseTest();
	PropertyController properties = new PropertyController();
	CommonUtils utils = new CommonUtils();
	CommonPOF pof = new CommonPOF();
	static ArrayList<String> willRespondSend = null;
	static ArrayList<String> messages;
	String browser = properties.getBrowserProperty();

	String username = "";
	
	@BeforeEach
	public void startPOF() throws IOException {
		willRespondSend = CommonUtils.readListFile("POFWillRespond.txt");
		if(willRespondSend.isEmpty()) {
			willRespondSend = new ArrayList<String>();
		} 
		vladi.startDatingAppInBrowser(browser, "pof");
	}
	
	@AfterEach
	public void tearDown() throws IOException {
		CommonUtils.writeListToFile(willRespondSend, "POFWillRespond.txt");
		vladi.quitBrowser();
	}
	
	@Test
	@DisplayName("Ultra Matches feature in POF")
	public void pofWillRespond() {
		
		int row_counter = 1;
		int col_counter = 1;
		messages = properties.getMessageToSend();
		pof.signInPOF();
		
		vladi.explicitWaitForElement(loc.pofMyMatchesButton, 20);
		vladi.clickLinkByText("Will Respond");
		vladi.explicitWaitForElement(loc.willRespondHeader, 20);
		List<WebElement> matches = vladi.findCommonElements(By.xpath("//td//img"));
		
		for(int i=0; i<=matches.size(); i++) {

			// validate col counter
			if(col_counter>4) {
				col_counter = 1;
				row_counter++;
			}
			
			vladi.scrollToElement(By.xpath("//center[" + row_counter + "]//td[" + col_counter + "]//img"));
			vladi.clickButton(By.xpath("//center[" + row_counter + "]//td[" + col_counter + "]//img"));
			vladi.explicitWaitForElement(loc.pofUltraMatchProfilePic, 20);
			
			username = vladi.getTextOfElement(loc.userNameField);
			System.out.println("Clicked on: " + username);
			
			// check if message already sent to this user
			if(willRespondSend.contains(username)) {
				
				System.out.println("Already sent message to user: " + username);
				System.out.println("Going back to Will Respond Matches...");
				vladi.goBackUrl();
			} else {
			
				vladi.explicitWaitForElement(loc.pofSendMessageTextArea, 20);
				vladi.scrollToElement(loc.pofSendMessageTextArea);
				// send random messages
				vladi.enterText(loc.pofSendMessageTextArea, messages.get(utils.getRandomNumberInRange(0, 4)));
				vladi.clickButton(loc.pofSendMessageButton);
				willRespondSend.add(username);
			}
			try {
				// navigates back to main pof page
				vladi.explicitWaitForElement(loc.pofMyMatchesButton, 20);
				vladi.clickLinkByText("Will Respond");
				vladi.explicitWaitForElement(loc.willRespondHeader, 20);
				
			} catch (Exception e) {
				
				vladi.explicitWaitForElement(By.xpath("//a[text()='inbox']"), 20);
				vladi.clickButton(By.xpath("//a[text()='inbox']"));
				vladi.explicitWaitForElement(loc.pofMyMatchesButton, 10);
			}
			
			//update col counter, row counter
			col_counter++;
			
		}
	}
}
