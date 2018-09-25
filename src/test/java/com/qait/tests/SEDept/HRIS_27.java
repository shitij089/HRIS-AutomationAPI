package com.qait.tests.SEDept;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class HRIS_27 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId;

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
		Reporter.log("User Logged in to Application Successfully", true);
	}
	
	@Test(dependsOnMethods="Step01_loginIntoApplication")
	public void Step02_VerifyPTOCanApplyInFutureDates()
	{
	 	test.homePage.navigateToLeaveTab();
	 	test.leavePage.navigateToApplyTab();
		test.leavePage.selectLeaveType("PTO");
		test.leavePage.selectFromDate(DateUtil.getDateWithNDaysBefore(2));
		test.leavePage.selectEndDate(DateUtil.getDateWithNDaysBefore(2));
		test.leavePage.verifyMsgAfterApplyLeave("invalidDatePtoMsg");   
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
