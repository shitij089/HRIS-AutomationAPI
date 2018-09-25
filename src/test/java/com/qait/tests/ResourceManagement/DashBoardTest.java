package com.qait.tests.ResourceManagement;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class DashBoardTest {
	TestSessionInitiator test;
	String temp, UserName, PassWord, empName, empId;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
	}

	@Test
	public void Step02_VerifySuccessfullyNavigateToRMSPage() {
		test.homePage.navigateToResourceManagementTab();
		test.resourceManagementpage.verifyRMSPageLoadsSuccessfully();
	}

	@Test
	public void Step03_VerifyPageTitle() {
		test.resourceManagementpage.verifyPageTitleExact(YamlReader
				.getData("title.rmsPage"));
	}

	@Test
	public void Step04_VerifyFooterText() {
		test.resourceManagementpage.veriyfFooterText();
	}

	// @Test
	public void Step05_VerifyClickingOnHRISLogoNavigatesBackToHRISLandingPage() {
		test.resourceManagementpage.clickOnHRISLogo();
		test.homePage.verifyInfoFromPersonalInfoTab("Name", UserName);
	}

	@Test
	public void Step06_VerifyDefaultDateInRMS() {
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.clickOnDashBoard();
		test.resourceManagementpage.verifyDefaultDateInRMS();
	}

	@Test
	public void Step07_VerifyClickingOnLeftOrRightButtonCalenderShowsCorrectDate() {
		test.resourceManagementpage
				.clickingOnLeftOrRightButtonOFCalender("left");
		test.resourceManagementpage
				.verifyClickingOnLeftOrRightButtonCalenderShowsCorrectDate("left");
		test.resourceManagementpage.pageRefresh();
		test.resourceManagementpage
				.clickingOnLeftOrRightButtonOFCalender("right");
		test.resourceManagementpage
				.verifyClickingOnLeftOrRightButtonCalenderShowsCorrectDate("right");
	}

	// @Test
	public void Step08_VerifyGivenCategoryEmployeePopUp() {
		test.resourceManagementpage
				.clickOnGivenCategoryEmployeeLink("Non-Billable(Present)");
		test.resourceManagementpage
				.verifyTitleForGivenCategoryEmployee("Non-Billable(Present)");
		test.resourceManagementpage.clickOnCloseButtonOfPopUp();
		test.resourceManagementpage
				.clickOnGivenCategoryEmployeeLink("Shadow(Present)");
		test.resourceManagementpage
				.verifyTitleForGivenCategoryEmployee("Shadow(Present)");
		test.resourceManagementpage.clickOnCloseButtonOfPopUp();
	}

	// @Test
	public void Step09_VerifyEngineersCategoryEmployeePopUp() {
		test.resourceManagementpage
				.clickOnGivenCategoryEmployeeLink("ENGINEERS");
		test.resourceManagementpage
				.verifyTitleForGivenCategoryEmployee("All Employee List");
		empName = test.resourceManagementpage.getEmployeeInfo("name");
		empId = test.resourceManagementpage.getEmployeeInfo("id");
		test.resourceManagementpage.clickOnGivenEmployee(empName);
		test.resourceManagementpage.clickOnCloseButtonOfPopUp();
	}

	@Test
	public void Step10_VerifySearchTextInMonthlyTabOfProjectDashboard() {
		test.resourceManagementpage.clickOnMonthlyButton();
		test.resourceManagementpage
				.verifySearchTextInMonthlyTabOfProjectDashboard(
						"Shivam Tiwari", "3940");
	}

	@Test
	public void Step11_VerifyProjectLinkInMonthlyTabOfProjectDashboardShowsInformationCorrectly() {
		test.resourceManagementpage
				.verifyProjectsLinkInMonthlyTabOfProjectDashboard();
	}

	@Test
	public void Step12_VerifyInvoiceDataTableInfo() {
		test.resourceManagementpage.clickOnLinkHavingText("Invoices Data");
		test.resourceManagementpage.verifyInvoiceDataInfo("4");
		test.resourceManagementpage.verifyInvoiceDataInfo("6");
		test.resourceManagementpage.verifyInvoiceDataInfo("7");
	}

	@Test
	public void Step13_VerifyOutStandingInvoiceData() {
		test.resourceManagementpage
				.clickOnLinkHavingText("Outstanding Invoices");
		test.resourceManagementpage.verifyOutstandingInvoiceData("1");
		test.resourceManagementpage.verifyOutstandingInvoiceData("2");
	}

	@Test
	public void Step14_VerifyJoined_YetToJoinInformation()
	{
		test.resourceManagementpage
		.clickOnLinkHavingText("Joined/Yet to Join");
		test.resourceManagementpage.verifyJoined_YetToJoinData();
		
		
	}
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		
	}

	// @AfterClass
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}

}
