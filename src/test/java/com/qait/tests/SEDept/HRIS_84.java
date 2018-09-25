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
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class HRIS_84 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate, startDate1;
	int monthBefore, weekNo1, weekNo2;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthBefore = Integer.parseInt(YamlReader.getData("MonthBefore"));
		weekNo1 = Integer.parseInt(YamlReader.getData("WeekNo1"));
		weekNo2 = Integer.parseInt(YamlReader.getData("WeekNo2"));
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");

		empId = DataReadWrite.getProperty("SEDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("SEDepartmentEmployeeFullName");
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
		punches.add("eightHour");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(monthBefore,
				weekNo1);
		startDate1 = test.homePage.getStartDateOfWeekToBeTested(monthBefore,
				weekNo2);
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate1, empName, empId, monthBefore);

	}

	@Test(dependsOnMethods = "Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step03_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "se");
	}

	@Test(dependsOnMethods = "Step03_UpdateOnsitePunchesForGivenWeek")
	public void Step04_VerifySWAfterAppliedPTOAndSickLeaveConsecutiveDaysSuccessfully() {
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("PTO");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 0));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 2));
		test.leavePage.clickApplyButton();
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Sick Leave");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 3));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));
		test.leavePage.clickApplyButton();
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Business OOTO");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate1, 0));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate1, 0));
		test.leavePage.selectDropDownForFromTimeToToTime("08:00", "16:00");
		test.leavePage.setCommentMessage("due to a/c outage");
		test.leavePage.clickApplyButton();
		test.pimPage.acceptAlert();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage.verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", startDate, 5), empId, "OFF");
		test.timePage.verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", startDate, 6), empId, "OFF");
	}

	@Test(dependsOnMethods = "Step04_VerifySWAfterAppliedPTOAndSickLeaveConsecutiveDaysSuccessfully", alwaysRun = true)
	public void Step05_cancelAllAppliedLeaves() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate1, empName, empId, monthBefore);
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
