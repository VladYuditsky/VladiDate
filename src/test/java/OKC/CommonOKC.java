package OKC;

import com.base.BaseTest;
import com.base.PropertyController;
import com.locs.VladiLocs;

public class CommonOKC {

	static VladiLocs loc = new VladiLocs();
	static BaseTest vladi = new BaseTest();
	static PropertyController properties = new PropertyController();
	String email = properties.getOKCEmailProperty();
	String password = properties.getPasswordProperty();
	
	public void signInOKC() {
		// LOGIN
		vladi.explicitWaitForElement(loc.okcSignInButtonHome, 15);
		vladi.clickButton(loc.okcSignInButtonHome);
		vladi.explicitWaitForElement(loc.username, 10);
		vladi.enterText(loc.username, email);
		vladi.enterText(loc.password, password);
		// click sign in
		vladi.clickButton(loc.okcSignInButton);
	}
}
