package com.qait.tests.BasicUiElement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class Home_page_test {
	TestSessionInitiator test;
	String UserName,PassWord;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
	}

	@Test
	public void Step02_HRIS_042_And_043()
	{
		test.homePage.navigateToTimeTab();
		test.homePage.clickOnHRISLogo();
		test.homePage.verifyInfoFromPersonalInfoTab("Name", UserName);
		test.homePage.verifyWelcomeMsgIsPresent();
	}

	@Test
	public void Step03_HRIS_045()
	{
		test.homePage.verifyFooterText();
	}

	@Test
	public void Step04_HRIS_046()
	{
		test.homePage.clickOnGivenTab("HR Documents");
		test.homePage.verifyHRDocsHeadingTitle();
	}
	

}
