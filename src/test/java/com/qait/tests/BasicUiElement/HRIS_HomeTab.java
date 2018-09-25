package com.qait.tests.BasicUiElement;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class HRIS_HomeTab {
	TestSessionInitiator test;
	String temp;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
	}
	
	@Test
	public void Step01_VerifyLoginPageLoadsSuccessfully()
	{
		test.loginPage.verifyLoginPageLoadsSuccessfully();
	}
	
	@Test
	public void Step02_VerifyPageTitle() {
		test.loginPage.verifyPageTitleExact(YamlReader
				.getData("title.loginPage"));
	}
	//@Test
	public void Step03_VerifyLogin() {
		test.loginPage.login(YamlReader.getData("username"),
				YamlReader.getData("password"));
		test.homePage.verifyPageTitleExact("QAIT HRIS");
	}

	//@Test
	public void Step04_VerifyEventsCounter() throws InterruptedException {
		test.homePage.allProcessOfEventCounter();
		test.homePage.logOut();
	}

	@Test
	public void Step05_VerifyEventsDetailAtLoginPage() {
		test.loginPage.verifyEventsMessage();
	}
	
	@Test
	public void Step06_HRIS_36() {
		test.loginPage.login(YamlReader.getData("username"),
				YamlReader.getData("password"));
		test.homePage.VerifyNextNPreCalenderButtonDisplay();
	
	}
	//@Test
	public void Step07_HRIS_34() {
		test.homePage.verifyAllEmployeeBirthday();
	
	}
	//@Test
	public void Step08_HRIS_29() {
		test.loginPage.login(YamlReader.getData("username"),
				YamlReader.getData("password"));
		test.homePage.verifySubTabDisplay();
	
	}
	//@Test
	public void Step09_HRIS_30() {
		test.homePage.verifyLabels();
	
	}
	//@Test
	public void Step10_HRIS_31() {
		test.homePage.verifyRemindersContent();
	
	}
	//@Test
	public void Step11_HRIS_32() {
		test.homePage.verifyPersonalInfoContent();
	
	}
	//@Test
	public void Step12_HRIS_33() {
		test.homePage.verifyInfoLabelContent();
	
	}
	//@Test
	public void Step13_HRIS_39() throws InterruptedException {
		test.homePage.verifyFooterLinkAsQainfotech();
	}
	//@Test
	public void Step14_HRIS_41() throws InterruptedException {
		test.homePage.verifyAllUpcomingEvents();
	}
	@Test
	public void Step15_HRIS_50() throws InterruptedException {
		test.homePage.verifyMyPositionPageLoadingSuccessfully();
	}
	@Test
	public void Step16_HRIS_52() throws InterruptedException {
		test.homePage.verifyOpenPopUpAfterClickingMyPositionPageContent();
	}
	@AfterMethod
    public void take_screenshot_on_failure(ITestResult result) {
        test.takescreenshot.takeScreenShotOnException(result);
    }

    @AfterClass
    public void Close_Test_Session() {
        test.closeBrowserSession();
    }
}
