package com.qait.keywords;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class Home_Page extends GetPage {

	WebDriver driver;
	String pagename = "Home_Page";

	public Home_Page(WebDriver driver) {
		super(driver, "Home_Page");
		this.driver = driver;
	}

	public void navigateToLeaveTab() {
		wait.hardWait(3);
		switchToDefaultContent();
		wait.hardWait(3);
		clickUsingXpathInJavaScriptExecutor(element("headertag", "Leave"));
		msg.log("Nevigated to leave tab");

	}

	public void naviagteToLeaveTabForStandardUser() {
		wait.hardWait(2);
		switchToDefaultContent();
		element("headertag", "Leave").click();
		msg.log("Nevigated to leave tab for sandard user");
		wait.hardWait(3);
	}

	public void navigateToTimeTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		clickUsingXpathInJavaScriptExecutor(element("headertag", "Time"));
		msg.log("Nevigated to time tab");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		switchToFrame(element("iframe1"));
		wait.waitForElementToBeVisible(element("txt_hourForGivenDay",
				DateUtil.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd")));
	}

	public void navigateToPIMTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		clickUsingXpathInJavaScriptExecutor(element("headertag", "PIM"));
		msg.log("Nevigated to pim tab");
	}

	public void navigateToAdminTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		clickUsingXpathInJavaScriptExecutor(element("headertag", "Admin"));
		msg.log("Nevigated to admin tab");
	}

	public void allProcessOfEventCounter() {
		switchToDefaultContent();
		clickUsingXpathInJavaScriptExecutor(element("link_sideBarLinkAtHome",
				"List Of Events"));
		msg.log("clicked on list of events link!!!");
		switchToDefaultContent();
		switchToFrame(element("iframe1"));
		executeJavascript("document.getElementsByTagName('img')[0].click()");
		element("input_afterAddButton", "eventDate").clear();
		element("input_afterAddButton", "eventDate").sendKeys(
				"2016-11-27 18:00");
		element("input_afterAddButton", "eventName").click();
		element("input_afterAddButton", "eventName").sendKeys("Test of Events");
		clickUsingXpathInJavaScriptExecutor(element("checkBox_checkCouter"));
		wait.hardWait(3);
		element("button_save").click();

	}

	public void logOut() {
		switchToDefaultContent();
		element("link_logout").click();
	}

	public void navigateToResourceManagementTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		element("resourcemngmnttag").click();
		msg.log("Nevigated to resource management tab");

	}

	public String getStartDateOfWeekToBeTested(int MonthsBefore, int weekNumber) {
		Time_Page timePage = new Time_Page(driver);
		switchToDefaultContent();
		navigateToTimeTab();
		timePage.expandMonthDropDown();
		timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(MonthsBefore);
		return timePage.getStartDateOfGivenWeek(weekNumber);
	}

	public boolean getDisplayAllTab() {

		return element("button_tab", "Admin").isDisplayed()
				&& element("button_tab", "PIM").isDisplayed()
				&& element("button_tab", "Time").isDisplayed()
				&& element("button_tab", "Leave").isDisplayed()
				&& element("button_tab", "Recruitment").isDisplayed()
				&& element("button_tab", "Reports").isDisplayed()
				&& element("button_tab", "EAR").isDisplayed()
				&& element("button_tab", "MRBS").isDisplayed()
				&& element("button_tab", "Current").isDisplayed()
				&& element("button_tab", "Employee").isDisplayed()
				&& element("button_tab", "Role").isDisplayed()
				&& element("button_tab", "Fun").isDisplayed()
				&& element("button_tab", "Resource").isDisplayed()
				&& element("button_tab", "Onsite").isDisplayed();

	}

	public void verifyDisplayTabMessage() {
		switchToDefaultContent();
		Assert.assertEquals(getDisplayAllTab(), true);
		logMessage("ASSERT PASSED: Displayed all tab!!\n");
	}

	public boolean getDisplayednextNPrevButton() {
		switchToDefaultContent();
		switchToFrame(0);
		switchToFrame(0);
		return element("button_next").isDisplayed()
				&& element("button_pre").isDisplayed();
	}

	public void VerifyNextNPreCalenderButtonDisplay() {
		Assert.assertEquals(getDisplayednextNPrevButton(), true);
		msg.log("verified next and prev button!!!");
	}

	public void verifyAllEmployeeBirthday() {
		switchToDefaultContent();
		switchToFrame(0);
		int n = 1;
		int i = elements("size_birthday").size();
		do {
			if (i % 2 != 0)
				element("label_birthday", "" + n).isDisplayed();
			n++;
			System.out.println("@@@@" + n);
		} while (i != n);
	}

	public void verifySubTabDisplay() {
		switchToDefaultContent();
		boolean b = element("button_tab", "HR Documents").isDisplayed()
				&& element("button_tab", "My Position").isDisplayed()
				&& element("button_tab", "List Of Holidays").isDisplayed()
				&& element("button_tab", "Photos").isDisplayed()
				&& element("button_tab", "Employee Speak").isDisplayed()
				&& element("button_tab", "Medical Bill").isDisplayed()
				&& element("button_tab", "List Of Events").isDisplayed()
				&& element("button_tab", "Annual Feedback").isDisplayed()
				&& element("button_tab", "Submit Grievance").isDisplayed()
				&& element("button_tab", "Grievance(s)").isDisplayed()
				&& element("button_tab", "Medical Insurance").isDisplayed();
		Assert.assertEquals(b, true);
		msg.log("verified  all subtab are showing!!!");
	}

	public void verifyLabels() {
		switchToDefaultContent();
		switchToFrame(0);
		boolean b = element("text_label", "Thought of the day:").isDisplayed()
				&& element("text_label", "Personal Information").isDisplayed()
				&& element("text_label", "Reminder(s)").isDisplayed()
				&& element("text_label", "Birthday(s)").isDisplayed()
				&& element("text_label", "Information(s)").isDisplayed()
				&& element("text_label", "QA InfoTech Events Calendar")
						.isDisplayed()
				&& element("text_label", "JobAnniversary(s)").isDisplayed();
		Assert.assertEquals(b, true);
		msg.log("verified  all labels as thought of the day,reminders,birthday etc.  are displaying!!!");
	}

	public void verifyRemindersContent() {
		switchToDefaultContent();
		switchToFrame(0);
		boolean b = element("text_RemindersContent", "" + 1).isDisplayed()
				&& element("text_RemindersContent", "" + 2).isDisplayed()
				&& element("text_RemindersContent", "" + 3).isDisplayed()
				&& element("text_RemindersContent", "" + 4).isDisplayed()
				&& element("text_RemindersContent", "" + 5).isDisplayed()
				&& element("text_RemindersContent", "" + 6).isDisplayed();
		Assert.assertEquals(b, true);
		msg.log("verified all content of Reminder(s) Information !!!");
	}

	public void verifyPersonalInfoContent() {
		switchToDefaultContent();
		switchToFrame(0);
		boolean b = element("txt_PersonalInfoContent", "" + 1).isDisplayed()
				&& element("txt_PersonalInfoContent", "" + 2).isDisplayed()
				&& element("img_Personal").isDisplayed();

		Assert.assertEquals(b, true);
		msg.log("verified all content of Personal Information!!!");
	}

	public void verifyInfoLabelContent() {
		switchToDefaultContent();
		switchToFrame(0);
		boolean b = element("text_RemindersContent", "" + 7).isDisplayed()
				&& element("text_RemindersContent", "" + 8).isDisplayed()
				&& element("text_RemindersContent", "" + 9).isDisplayed()
				&& element("text_RemindersContent", "" + 10).isDisplayed()
				&& element("text_RemindersContent", "" + 11).isDisplayed();
		Assert.assertEquals(b, true);
		msg.log("verified all content of Information(s) !!!");
	}

	public void verifyFooterLinkAsQainfotech() {
		switchToDefaultContent();
		element("text_label", "QA InfoTech").isDisplayed();
		element("text_label", "QA InfoTech").click();
		switchWindow();
		element("logo_qainfotech").isDisplayed();
	}

	public void verifyAllUpcomingEvents() {
		switchToDefaultContent();
		switchToFrame(0);
		switchToFrame(0);
		long i = (long) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByClassName('fc-event-hori').length");
		for (int j = 1; j <= i; j++) {
			element("events_label", "" + j).isDisplayed();
		}
	}

	public void verifyInfoFromPersonalInfoTab(String infoName, String InfoValue) {
		switchToDefaultContent();
		switchToFrame(element("iframe1"));
		isElementDisplayed("txt_personalInfo", infoName);
		if (element("txt_personalInfo", infoName).getText().toLowerCase()
				.contains(InfoValue.toLowerCase())) {
			logMessage("verified that name is present !!");
		} else {
			Assert.fail("Expected was "
					+ InfoValue.toLowerCase()
					+ " not contains in "
					+ element("txt_personalInfo", infoName).getText()
							.toLowerCase());
		}
	}

	public void clickOnHRISLogo() {
		isElementDisplayed("img_logo");
		element("img_logo").click();
		wait.waitForPageToLoadCompletely();
	}

	public void verifyWelcomeMsgIsPresent() {
		switchToDefaultContent();
		switchToFrame(element("iframe1"));
		String userNAme = element("txt_personalInfo", "Name").getText();
		switchToDefaultContent();
		isElementDisplayed("txt_welcome");
		if (element("txt_welcome").getText().contains(userNAme.split(" ")[0])) {
			msg.pass("Verified welcome msg !!");
		} else
			Assert.fail("Msg not displays appropriately");
	}

	public void verifyFooterText() {
		Assert.assertEquals(element("txt_footer", "1").getText(),
				YamlReader.getData("msg.footerText1"), "Expected was \""
						+ YamlReader.getData("msg.footerText1")
						+ "\" but found \""
						+ (element("txt_footer", "1").getText() + "\""));
		/*
		 * Assert.assertEquals(element("txt_footer", "2").getText(),
		 * YamlReader.getData("msg.footerText2"), "Expected was \"" +
		 * YamlReader.getData("msg.footerText2") + "\" but found \"" +
		 * (element("txt_footer", "2").getText() + "\""));
		 */
	}

	public void clickOnGivenTab(String tabName) {
		isElementDisplayed("button_Tab", tabName);
		element("button_Tab", tabName).click();
		msg.log("Click on tab " + tabName);
	}

	public void verifyHRDocsHeadingTitle() {
		switchToDefaultContent();
		switchToFrame(element("iframe1"));
		Assert.assertEquals(element("txt_headingHRDoc").getText(),
				"HR Documents", "Expected was HR Documents but found"
						+ element("txt_headingHRDoc").getText());
		msg.pass("Verified HR docs heading title !!");

	}

	public void verifiedAllDocumentsContainsAddedDataAndDescriptionTab() {
		switchToDefaultContent();
		switchToFrame(element("iframe1"));
		for (WebElement ele : elements("txt_documentHeading")) {
			if (!ele.getText().contains("Added Date Description")) {
				Assert.fail("Added or Date Description tab is not displayed !!");
			}
		}
		msg.pass("Verified all documents contains added data and description tab");
	}

	public void verifyMyPositionPageLoadingSuccessfully() {
		switchToDefaultContent();
		element("button_Tab", "My Position").click();
		// switchToFrame(0);
		driver.switchTo().frame(driver.findElement(By.id("rightMenu")));
		boolean b = element("button_Tab", "Chief Executive Officer")
				.isDisplayed()
				&& element("button_Tab", "VP, Testing Center of Excellence")
						.isDisplayed()
				&& element("button_Tab", "Chief Information Officer")
						.isDisplayed();
		Assert.assertEquals(b, true);

	}

	public void verifyOpenPopUpAfterClickingMyPositionPageContent() {
		switchToDefaultContent();
		element("button_Tab", "My Position").click();
		// switchToFrame(0);
		driver.switchTo().frame(driver.findElement(By.id("rightMenu")));
		element("button_Tab", "Chief Executive Officer").click();
		executeJavascript("document.getElementsByClassName('close')[0].click()");
		wait.hardWait(2);
		element("button_Tab", "VP, Testing Center of Excellence").click();
		executeJavascript("document.getElementsByClassName('close')[0].click()");
		wait.hardWait(2);
		element("button_Tab", "Chief Information Officer").click();
		executeJavascript("document.getElementsByClassName('close')[0].click()");

	}

	/*
	 * public void navigateToTab(String element_name, String replacement) {
	 * wait.hardWait(2); switchToDefaultContent();
	 * 
	 * element(element_name, replacement).click();
	 * msg.log("Navigated to repoting tab"); wait.hardWait(3);
	 * 
	 * }
	 */
}
