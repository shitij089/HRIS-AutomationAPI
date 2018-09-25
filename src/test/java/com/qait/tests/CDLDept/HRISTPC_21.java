package com.qait.tests.CDLDept;

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

public class HRISTPC_21 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate, marriageDate;
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
		empId = DataReadWrite.getProperty("CdlDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("CdlDepartmentEmployeeFullName");
		savePunchesForWeek();
	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches.add("nineHour");
		punches.add("nineHour");
		punches.add("nineHour");
		punches.add("eightHour");
		punches.add("zeroHour");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(monthBefore,
				weekNo);
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_AddMarriageDetails() {
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		marriageDate = DateUtil.getPreviousDateFromGivenDateFoGivenDateModule(
				"yyyy-MM-dd", "year", DateUtil
						.getNextDateFromGivenDateFoGivenDateModule(
								"yyyy-MM-dd", "day", startDate, 4), 1);
		test.pimPage.enterMarriageDetails(marriageDate);
	}

	@Test(dependsOnMethods = "Step02_AddMarriageDetails")
	public void Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
	}

	@Test(dependsOnMethods = "Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step04_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "admin");
	}

	@Test(dependsOnMethods = "Step04_UpdateOnsitePunchesForGivenWeek")
	public void Step05_ApplyRestrictedeaveFromUserForGivenDate() {
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Restricted Leave");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectGivenDate("Marriage Anniversary");
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));
		test.leavePage.clickApplyButton();
		test.pimPage.acceptAlert();
	}

	@Test(dependsOnMethods = "Step05_ApplyRestrictedeaveFromUserForGivenDate")
	public void Step06_VerifyThatThereIsNoLeaveIfTotalWeekAreEqualAsRequiredHours() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage.verifyfirstValueContainsSecond(test.timePage
				.getInfoAtGivenDate(DateUtil
						.getNextDateFromGivenDateFoGivenDateModule(
								"yyyy-MM-dd", "day", startDate, 4), empId),
				YamlReader.getData("msg.appliedRH"));
		test.timePage.VerifyNoLWPIfTotalWeekHourIsGreaterThanRequiredHour(
				empId, startDate);
	}

	@Test(dependsOnMethods = "Step06_VerifyThatThereIsNoLeaveIfTotalWeekAreEqualAsRequiredHours", alwaysRun = true)
	public void Step07_cancelAllAppliedLeaves() {
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
