package com.qait.tests.SEDept;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class HRIS_90 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, empUserName, startDate1, startDate2,
			previousdate[], endDate;
	int monthAfter, weekNo1, weekNo2;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthAfter = Integer.parseInt(YamlReader.getData("MonthAfter"));
		weekNo1 = Integer.parseInt(YamlReader.getData("WeekNo1"));
		weekNo2 = Integer.parseInt(YamlReader.getData("WeekNo2"));

		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");

		empId = YamlReader.getData("credential.SEDeptPass");
		empUserName = YamlReader.getData("credential.SEDeptUname");
		empName = YamlReader.getData("credential.SEDeptFullName");
		savePunchesForWeek();
	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("eightHour");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate1 = test.homePage.getStartDateOfWeekToBeTested(monthAfter,
				weekNo1);

	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate1, punches, "se");
	}

	@Test(dependsOnMethods = "Step02_UpdateOnsitePunchesForGivenWeek")
	public void Step03_ApplyWFHForGivenDate() {
		test.launchApplication();
		test.loginPage.login(empUserName, empId);
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToApplyTab();
		test.leavePage.selectLeaveType("WFH");
		test.leavePage.selectFromDate(startDate1);
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate1, 6));
		test.leavePage.selectProject("HR Support");
		test.leavePage.setCommentMessage("No comment");
	}

	@Test(dependsOnMethods = "Step03_ApplyWFHForGivenDate")
	public void Step04_VerifyMsgWhichOccurWhenApplyMoreThen5WFHAndCancelAllAppliedLeave() {
		test.leavePage.verifyMsgAfterApplyLeave("invalidWFHMsg");
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
