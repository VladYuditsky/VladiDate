package com.locs;

import org.openqa.selenium.By;

public class VladiLocs {

	public final By username = By.name("username"); // pof //okc
	public final By password = By.name("password"); // pof //twitter //okc
	public final By userNameField = By.id("username"); //used in IG also

	// POF
	public final By pofSignInLink = By.xpath("//a[text()='Sign In']");
	public final By pofMyMatchesButton = By.xpath("//a[contains(text(), 'My Matches')]");
	public final By pofUltraMatchButton = By.xpath("//a[contains(text(), 'Ultra')]");
	public final By pofUltraMatchesProfiles = By.xpath("//center/div[contains(@class, 'row')]");
	public final By pofUltraMatchesCount = By.xpath("//center/div[contains(@class, 'row')]//tbody//td[@class='numeral']");
	public final By pofMatchNumberUltraMatch = By.xpath("//center/div[contains(@class, 'row')]//tbody//td[@class='numeral']");
	public final By pofSendMessageTextArea = By.id("send-message-textarea");
	public final By pofUltraMatchProfilePic = By.id("mp");
	public final By pofSendMessageButton = By.id("send-quick-message-submit");
	public final By pofRefineMatches = By.xpath("//input[@value='Refine Matches']");
	public final By pofMeetMe = By.id("mainmenu_meetme");
	public final By pofYesMeetMe = By.id("meet-me-button-yes-var");
	public final By willRespondHeader = By.xpath("//span[text()='Will Respond']");

	// OKC
	public final By okcSignInButtonHome = By.className("splashdtf-header-signin-splashButton");
	public final By okcSignInButton = By.xpath("//input[@type='submit']");
	public final By okcBrowseMatchesButton = By.xpath("//a//span[contains(text(), 'Browse Matches')]");
	public final By okcBrowseMatchesUsers = By.xpath("//a[@class='match-results-card']//span[@class='userInfo-username-name']");
	public final By okcLikeButton = By.id("like-button");
	public final By okcMessageTextArea = By.xpath("//textarea[@class='messenger-composer']");
	public final By okcSuccessMessageSent = By.xpath("//h3[text()='Done!']");
	public final By okcMessageUserButton = By.xpath("//div[@id='profile_actions']//button//span[text()='Message']");

}
