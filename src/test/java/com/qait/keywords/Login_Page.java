package com.qait.keywords;

import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.qait.tests.BasicUiElement.Home_page_test;

public class Login_Page extends GetPage {

	WebDriver driver;
	String pagename = "Login_Page", url, temp;

	public Login_Page(WebDriver driver) {
		super(driver, "Login_Page");
		this.driver = driver;
	}

	public void enterUserName(String username) {
		isElementDisplayed("username");
		element("username").clear();
		element("username").sendKeys(username);
	}

	public String getUserName() {
		isElementDisplayed("username");
		return element("username").getAttribute("value");
	}

	void enterPassword(String password) {
		isElementDisplayed("password");
		element("password").clear();
		element("password").sendKeys(password);
	}

	String getPassword() {
		isElementDisplayed("password");
		return element("password").getAttribute("value");
	}

	void clickOnSighInButton() {
		isElementDisplayed("signinbtn1");
		element("signinbtn1").click();
	}

	public void clickOnHRISLogo() {
		isElementDisplayed("img_logo");
		element("img_logo").click();
	}

	public boolean checkLoginStatus() {
		try {
			return isElementDisplayed("checkContent", "Login Here");
		} catch (Exception ex) {
			return false;
		}
	}

	public void login(String username, String password) {
		enterUserName(username);
		enterPassword(password);
		clickOnSighInButton();
		logMessage("User Logged in to Application Successfully !!\n");
		isFeedbackFormDisplayed();

		//redirectToHomePage();

	}

	public void isFeedbackFormDisplayed() {
		try {
			executeJavascript("document.getElementsByClassName('nav navbar-nav navbar-right')[0].getElementsByTagName('i')[7].click();");
		} catch (WebDriverException wde) {

		} catch (Exception e) {

		}
		try {
			element("btn_feedback").isDisplayed();
			clickUsingXpathInJavaScriptExecutor(element("btn_feedback"));
			msg.log("Go from feedback page to home page !!");
		} catch (Exception e) {
			msg.log("Feedback form not displayed !!");
		}

	}

	public void loginForUserCreatedAccount(String username, String password) {
		enterUserName(username);
		enterPassword(password);
		clickOnSighInButton();
		redirectToHomePage();
		isFeedbackFormDisplayed();
		System.out.println("*****" + checkLoginStatus());
		if (checkLoginStatus()) {
			logMessage("User Again Logged in to Application Successfully because that user sign-in first time!!\n");
			enterUserName(username);
			enterPassword(password);
			clickOnSighInButton();
			redirectToHomePage();
			isFeedbackFormDisplayed();
		} else {
			logMessage("User Logged in to Application Successfully !!\n");

		}

	}

	public void redirectToHomePage() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		switchToDefaultContent();
		try {
			executeJavascript("document.getElementsByClassName('profile-btn')[0].click()");
			executeJavascript("document.getElementsByClassName('fa fa-sign-out')[0].click()");
		} catch (WebDriverException wde) {
			msg.log("Already in home page !!");
		}
	}

	public void clickOnRememberMeButton() {
		isElementDisplayed("chk_rememberMe");
		element("chk_rememberMe").click();
	}

	public void verifyRemenberMeFunctionality(String username, String password) {
		clickOnGivenModule("Login Panel");
		enterUserName(username);
		enterPassword(password);
		clickOnRememberMeButton();
		clickOnSighInButton();
		element("lnk_logout").click();
		wait.hardWait(3);
		Assert.assertEquals(getUserName(), username);
		Assert.assertEquals(getPassword(), password);
		logMessage("Step: verified remember me functionality !!\n");
	}

	public void clickOnGivenModule(String moduleName) {
		isElementDisplayed("defaultModules", moduleName);
		element("defaultModules", moduleName).click();
	}

	public void verifyModule(String moduleName) {
		Assert.assertTrue(isElementDisplayed("defaultModules", moduleName));
		logMessage("ASSERT PASSED: Verified module '" + moduleName
				+ "' is displayed !!");
	}

	public void verifyLoginPageLoadsSuccessfully() {
		Assert.assertEquals(getResponseCode(YamlReader.getData("baseurl")),
				true);
		logMessage("ASSERT PASSED: Verified page load successfully !!");
	}

	public boolean getResponseCode(String url) {
		boolean isValid = false;
		try {
			URL u = new URL(url);
			HttpURLConnection h = (HttpURLConnection) u.openConnection();
			h.setRequestMethod("GET");
			h.connect();
			if (h.getResponseCode() == 200) {
				isValid = true;
			} else {
				logMessage("link : " + url + " is broken.\n");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return isValid;
	}

	public void verifyScrollerOfEmployeeSpeak() {
		int location1, location2;
		location1 = driver
				.findElement(
						By.cssSelector("#scrollerSpeak:nth-child(1)>p:nth-child(1)"))
				.getLocation().getY();
		wait.hardWait(2);
		location2 = driver
				.findElement(
						By.cssSelector("#scrollerSpeak:nth-child(1)>p:nth-child(1)"))
				.getLocation().getY();
		Assert.assertNotEquals(location1, location2);
		logMessage("ASSERT PASSED: Verified Scrolling functionality !!");
	}

	public void verifyScrollerOfCelebrations() {
		int size = elements("list_celebration").size();
		if (size > 5) {
			int location1, location2;
			location1 = driver
					.findElement(
							By.cssSelector("#empscroll:nth-child(1)>div:nth-child(1)"))
					.getLocation().getY();
			wait.hardWait(2);
			location2 = driver
					.findElement(
							By.cssSelector("#empscroll:nth-child(1)>div:nth-child(1)"))
					.getLocation().getY();
			Assert.assertNotEquals(location1, location2, msg.failForAssert(""));
			msg.pass("");
			msg.fail("");
		}
		if (size == 0) {
			Assert.assertTrue(element("txt_celebrationMsg").getText().contains(
					YamlReader.getData("celebrationMsg")));
		}
		logMessage("ASSERT PASSED: Verified scroller functionality in celebration !!\n");
	}

	public void verifyPageRefreshOnClickingHRISLogo() {
		temp = getUserName();
		enterUserName(YamlReader.getData("username"));
		clickOnHRISLogo();
		wait.waitForPageToLoadCompletely();
		Assert.assertEquals(getUserName(), temp);
		logMessage("ASSERT PASSED: Verified HRIS Logo Clicking Functionality !!");
	}

	public void clickOnFooterLink(String footerName) {
		isElementDisplayed("lnk_" + footerName);
		element("lnk_" + footerName).click();
		logMessage("Step: click on " + footerName + " link");
	}

	public void verifyFooterLink(String footerName) {
		temp = driver.getWindowHandle();
		clickOnFooterLink(footerName);
		wait.waitForPageToLoadCompletely();
		switchWindow();
		Assert.assertEquals(getResponseCode(getCurrentURL()), true);
		logMessage("ASSERT PASSED: Verified footer link opens successfully\n");
		Assert.assertTrue(getPageTitle().contains(
				YamlReader.getData("title." + footerName)));
		driver.close();
		driver.switchTo().window(temp);
		logMessage("ASSERT PASSED: Verified footere link page title\n");
	}

	String getCopyrightMessage() {
		isElementDisplayed("txt_copyright");
		return element("txt_copyright").getText();
	}

	String getEventsMessage() {
		isElementDisplayed("checkContent", "Test of Events starts in");
		return element("checkContent", "Test of Events starts in").getText();
	}

	public void verifyCopyrightMessage() {
		Assert.assertEquals(getCopyrightMessage(),
				YamlReader.getData("copyright"));
		logMessage("ASSERT PASSED: Verified copyright message!!\n");
	}

	public void verifyEventsMessage() {
		Assert.assertEquals(getEventsMessage(), "Test of Events starts in");
		logMessage("ASSERT PASSED: Verified Events message!!\n");
	}

	public void verifyTabIsPresent(String tabName) {
		isElementDisplayed("txt_tabHeading", tabName);
		logMessage("Step: verified tab " + tabName + " is present !!\n");
	}

	public void verifyDailyMessageTitle() {
		isElementDisplayed("txt_dailyMsg");
		Assert.assertTrue(element("txt_dailyMsg").getText().contains(
				YamlReader.getData("dailyMsg")));
		logMessage("ASSERT PASSED: Verified Daily Message Title!! \n");
	}

	public void verifyDateDisplaysAtLoginPanel() {
		Assert.assertEquals(getDateFromLoginPanel(), getDateFromSystem());
		logMessage("ASSERT PASSED: Verified date displays at Login Panel !!\n");
	}

	String getDateFromLoginPanel() {
		isElementDisplayed("txt_dateAtLoginPanel");
		return element("txt_dateAtLoginPanel").getText();
	}

	String getDateFromSystem() {
		switch (Integer.parseInt(DateUtil
				.getCurrentdateInStringWithGivenFormate("dd")) % 10) {
		case 1:
			return (DateUtil
					.getCurrentdateInStringWithGivenFormate("EEEE : dd") + "st" + DateUtil
						.getCurrentdateInStringWithGivenFormate(" MMMM yyyy"));

		case 2:
			return (DateUtil
					.getCurrentdateInStringWithGivenFormate("EEEE : dd") + "nd" + DateUtil
						.getCurrentdateInStringWithGivenFormate(" MMMM yyyy"));

		case 3:
			return (DateUtil
					.getCurrentdateInStringWithGivenFormate("EEEE : dd") + "rd" + DateUtil
						.getCurrentdateInStringWithGivenFormate(" MMMM yyyy"));

		default:

			return (DateUtil
					.getCurrentdateInStringWithGivenFormate("EEEE : dd") + "th" + DateUtil
						.getCurrentdateInStringWithGivenFormate(" MMMM yyyy"));
		}
	}

	public void verifyUrl(String expectedUrl) {

		Assert.assertEquals(getCurrentURL(), expectedUrl);
		logMessage("ASSERT PASSED: Verified Url !!\n");
	}

	public void verifyHeadingsUnderHRPolicy() {
		for (WebElement ele : elements("list_hrPolicy")) {
			if (!ele.getText().contains("Updated on")) {
				Assert.fail("Policy "
						+ element("txt_hrPolicyName", ele.getText())
						+ " is not updated");
			}
		}
		msg.pass("Verified All policies are updated !!");
	}

	public void verifyGivenParaIsPresent(String pName) {
		isElementDisplayed("txt_paragraph", pName);
		logMessage("Step: verified paragraph " + pName + " is present !!\n");
	}
}
