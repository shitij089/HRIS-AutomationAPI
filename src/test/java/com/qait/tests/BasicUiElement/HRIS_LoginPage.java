package com.qait.tests.BasicUiElement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class HRIS_LoginPage {

	TestSessionInitiator test;
	String temp;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

	
	@Test
	public void Step01_VerifyLoginPageLoadsSuccessfully()
	{
		test.launchApplication();
		test.loginPage.verifyLoginPageLoadsSuccessfully();
	}
	
	//@Test
	public void Step02_VerifyPageTitle() {
		test.loginPage.verifyPageTitleExact(YamlReader
				.getData("title.loginPage"));
	}

	@Test
	public void Step03_VerifyDefaultModules() {
		test.loginPage.verifyModule("Login Panel");
		test.loginPage.verifyModule("About HRIS");
		test.loginPage.verifyModule("Celebration");
		test.loginPage.verifyModule("HR Policy");
		test.loginPage.verifyModule("Did you know?");
	}

	@Test
	public void Step04_VerifyLogin() {
		test.loginPage.login(YamlReader.getData("username"),
				YamlReader.getData("password"));
		test.homePage.verifyPageTitleExact("QAIT HRIS");
	}

	@Test
	public void Step05_VerifyRememberMeFunctionality() {
		test.launchApplication();
		test.loginPage.verifyRemenberMeFunctionality(
				YamlReader.getData("username"), YamlReader.getData("password"));
	}

	@Test
	public void Step06_VerifyEmployeeSpeakBoxScrollUp()
	{
		test.loginPage.verifyScrollerOfEmployeeSpeak();
	}
	
	@Test
	public void Step07_VerifyCelebrationsScrollUp()
	{
		test.launchApplication();
		test.loginPage.verifyScrollerOfCelebrations();
	}
		
	
	@Test
	public void Step07_VerifyOnClickHRISLogoFunctionality()
	{
		test.loginPage.verifyPageRefreshOnClickingHRISLogo();
	}
	
	@Test
	public void Step08_VerifyReportABugPage()
	{
		test.loginPage.verifyFooterLink("reportABug");
	}
	
	@Test
	public void Step09_VerifyAccessPayrollPage()
	{
		test.loginPage.verifyFooterLink("accessPayroll");
	}
	
	@Test
	public void Step10_VerifyCopyrightMessage()
	{
	    test.loginPage.verifyCopyrightMessage();	
	}
	
	@Test
	public void Step11_VerifyLoginPanelAttribute()
	{
		test.loginPage.clickOnGivenModule("Login Panel");
		test.loginPage.verifyTabIsPresent("Login Here");
		test.loginPage.verifyTabIsPresent("Celebrations");
		test.loginPage.verifyTabIsPresent("Notification");
		test.loginPage.verifyDateDisplaysAtLoginPanel();
		test.loginPage.verifyDailyMessageTitle();
	}
	
	@Test
	public void Step12_VerifAboutHRISAttribute()
	{
		test.loginPage.clickOnGivenModule("About HRIS");
		test.loginPage.verifyTabIsPresent("Use HRIS to");
		test.loginPage.verifyTabIsPresent("HRIS Updates");
		test.loginPage.clickOnFooterLink("HRISRelease");
		test.loginPage.verifyUrl(YamlReader.getData("HRISReleaseUrl"));
		test.loginPage.verifyPageTitleExact(YamlReader
				.getData("title.HRISRelease"));
	}
	
	@Test
	public void Step11_VerifyCelebrationAttribute()
	{
		test.launchApplication();
		test.loginPage.clickOnGivenModule("Celebration");
		test.loginPage.verifyTabIsPresent("Birthday(s)");
		test.loginPage.verifyTabIsPresent("JobAnniversary(s)");
		test.loginPage.verifyTabIsPresent("New joinee(s)");
	}
	
	//@Test
	public void Step12_VerifyHRPolicyTab()
	{
		test.loginPage.clickOnGivenModule("HR Policy");
		test.loginPage.verifyTabIsPresent("HR Policy");
		test.loginPage.verifyHeadingsUnderHRPolicy();
	}
	
	@Test
	public void Step13_VerifyDidYouknowTab()
	{
		test.loginPage.clickOnGivenModule("Did you know?");
		test.loginPage.verifyTabIsPresent("Did you know?");
        test.loginPage.verifyGivenParaIsPresent("HR Updates");
        test.loginPage.verifyGivenParaIsPresent("Admin Updates");
    }
	
	
	
}
