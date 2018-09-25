package com.qait.tests.SEDept;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class HRIS_28 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId;
	String empName, username, startDate, managerId = "1021";

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = "3936";
		empName = "Rohit Yadav";
		startDate = DateUtil
				.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		test.msg.log("User Logged in to Application Successfully");
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_AssignManagerToAnyEmployeeId() {
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		test.pimPage.clickOnReportToTab();
		test.pimPage.reportToGivenManager(managerId);
		test.pimPage.acceptAlert();
	}

	@Test(dependsOnMethods = "Step02_AssignManagerToAnyEmployeeId")
	public void Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelLeaveForGivenDate(startDate, empName, empId, 0);

	}

	@Test(dependsOnMethods = "Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step03_LoginWithManager() {
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("manager.username"),
				YamlReader.getData("manager.password"));
	}

	@Test(dependsOnMethods = "Step03_LoginWithManager")
	public void Step04_VerifyPtoCanApplyInPresentDate() {

		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("PTO");
		test.leavePage.selectEmployeeByManagerToAssignLeave(empId);
		test.leavePage.selectFromDate(DateUtil
				.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd"));
		test.leavePage.selectEndDate(DateUtil
				.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd"));
		test.leavePage.clickApplyButton();
		test.homePage.switchToDefaultContent();
	}

	@Test(dependsOnMethods = "Step04_VerifyPtoCanApplyInPresentDate")
	public void Step05_VerifyPTOMessageWhichApplyOnSameDay() {
		test.launchApplication();
		test.loginPage.login(UserName, PassWord);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.verifyfirstValueContainsSecond(test.timePage
				.getInfoAtGivenDate(DateUtil
						.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd"),
						empId), "Approved PTO Request");
	}

	@Test(dependsOnMethods = "Step05_VerifyPTOMessageWhichApplyOnSameDay", alwaysRun = true)
	public void Step06_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelLeaveForGivenDate(startDate, empName, empId, 0);

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
