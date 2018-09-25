package com.qait.tests.SEDept;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class VerifyNoSandwitchBetweenAppliedChildCareLeaves {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate1, startDate2, previousdate[],
			endDate;
	int monthBefore, weekNo1, weekNo2;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthBefore = Integer.parseInt(YamlReader.getData("MonthBefore"));
		weekNo1 = Integer.parseInt(YamlReader.getData("WeekNo1"));
		weekNo2 = Integer.parseInt(YamlReader.getData("WeekNo2"));
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = DataReadWrite.getProperty("SEDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("SEDepartmentEmployeeFullName");
		savePunchesForWeek();
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");

	}

	public void savePunchesForWeek() {
		punches.add("eightHour");
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
		startDate1 = test.homePage.getStartDateOfWeekToBeTested(monthBefore,
				weekNo1);
		startDate2 = test.homePage.getStartDateOfWeekToBeTested(monthBefore,
				weekNo2);
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_AddChildDetails() {
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		previousdate = DateUtil.getPreviousDate("year", 1);
		endDate = previousdate[0] + "-" + previousdate[1] + "-"
				+ previousdate[2];
		test.pimPage.enterChildInfo("Test", endDate);
	}

	@Test(dependsOnMethods = "Step02_AddChildDetails")
	public void Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate1, empName, empId, monthBefore);
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate2, empName, empId, monthBefore);
	}

	@Test(dependsOnMethods = "Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step04_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate1, 3), punches, "se");
	}

	@Test(dependsOnMethods = "Step04_UpdateOnsitePunchesForGivenWeek")
	public void Step05_ApplyChildCareLeaveForGivenDate() {
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Child Care Leave");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate1, 4));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate1, 4));
		test.leavePage.clickApplyButton();
		test.pimPage.acceptAlert();
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Child Care Leave");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(startDate2);
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate2, 1));
		test.leavePage.clickApplyButton();
		test.pimPage.acceptAlert();
	}

	@Test(dependsOnMethods = "Step05_ApplyChildCareLeaveForGivenDate")
	public void Step06_VerifyThatThereIsNoSandwitchInSatSunWhichOccurBetweenChildCareLeaveLeaveApplied() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage.verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", startDate1, 4), empId, "CCL");
		test.timePage.verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", startDate1, 5), empId, "OFF");
		test.timePage.verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", startDate1, 6), empId, "OFF");

	}

	@Test(dependsOnMethods = "Step06_VerifyThatThereIsNoSandwitchInSatSunWhichOccurBetweenChildCareLeaveLeaveApplied", alwaysRun = true)
	public void Step05_cancelAllAppliedLeaves() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate1, empName, empId, monthBefore);
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate2, empName, empId, monthBefore);
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
