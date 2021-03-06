package com.qait.tests.SttDept;

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
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class HRIS_STT07 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, startDate, empName, username, managerId = "1021";
	int monthBefore, weekNo;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthBefore = Integer.parseInt(YamlReader.getData("MonthBefore"));
		weekNo = Integer.parseInt(YamlReader.getData("WeekNo1"));
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");

		test.launchApplication();
		empId = DataReadWrite.getProperty("SttDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("SttDepartmentEmployeeFullName");
		savePunchesForWeek();

	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches.add("eightHour");
		punches.add("eightHour");
		punches.add("eightHour");
		punches.add("eightHour");
		punches.add("twoHour");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(monthBefore,
				weekNo);
		System.out.println("****"
				+ YamlReader.getData("Punches.twoHour.inTime"));
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWhichExistsInWeekToBeTested() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
	}

	@Test(dependsOnMethods = "Step02_CancelAllLeaveWhichExistsInWeekToBeTested")
	public void Step04_UpdateOnsitePunchesForGivenWeek() {

		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "stt");
	}

	@Test(dependsOnMethods = "Step04_UpdateOnsitePunchesForGivenWeek")
	public void Step05_HSLAppliedSuccessfully() {

		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Sick Leave");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));
		test.leavePage.selectDuration("Half Day");
		test.leavePage.clickApplyButton();
		test.pimPage.acceptAlert();

		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage.verifyHLWPMessageForGivenDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4), empId);
	}

	@Test(dependsOnMethods = "Step05_HSLAppliedSuccessfully", alwaysRun = true)
	public void Step05_cancelAllAppliedLeaves() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);

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
