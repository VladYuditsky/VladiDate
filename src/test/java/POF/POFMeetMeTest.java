package POF;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.base.BaseTest;
import com.base.CommonUtils;
import com.base.PropertyController;
import com.locs.VladiLocs;

public class POFMeetMeTest {

	static VladiLocs loc = new VladiLocs();
	static BaseTest vladi = new BaseTest();
	PropertyController properties = new PropertyController();
	CommonUtils utils = new CommonUtils();
	CommonPOF pof = new CommonPOF();
	static ArrayList<String> alreadySent = null;
	String browser = properties.getBrowserProperty();
	String numToSwipe = properties.getMavenSwipesProperty();
	
	@BeforeEach
	public void startPOF() throws IOException {
		
		vladi.startDatingAppInBrowser(browser, "pof");
	}
	
	@AfterEach
	public void tearDown() throws IOException {
		vladi.quitBrowser();
	}
	
	@Test
	@DisplayName("MeetMe POF")
	public void pofMeetMe() {
		pof.signInPOF();
		vladi.explicitWaitForElement(loc.pofMeetMe, 10);
		vladi.clickButton(loc.pofMeetMe);
		
		int swipes = Integer.parseInt(numToSwipe);
		for(int i=1; i<=swipes; i++) {
			
			vladi.explicitWaitForElement(loc.pofYesMeetMe, 10);
			vladi.clickButton(loc.pofYesMeetMe);
			System.out.println("You pressed Yes on " + i + " users!");

		}
	}
}
