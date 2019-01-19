package POF;
import com.base.BaseTest;
import com.base.PropertyController;
import com.locs.VladiLocs;

public class CommonPOF {

	static VladiLocs loc = new VladiLocs();
	static BaseTest vladi = new BaseTest();
	PropertyController properties = new PropertyController();
	String username = properties.getUsernameProperty();
	String password = properties.getPasswordProperty();
	
	public void signInPOF() {
		// LOGIN
		vladi.explicitWaitForElement(loc.pofSignInLink, 15);
		vladi.clickButton(loc.pofSignInLink);
		vladi.explicitWaitForElement(loc.username, 20);
		vladi.enterText(loc.username, username);
		vladi.enterText(loc.password, password);
		vladi.clickButtonByText("Check Mail!");
	}
}
