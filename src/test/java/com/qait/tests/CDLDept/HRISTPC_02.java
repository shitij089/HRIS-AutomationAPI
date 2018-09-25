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
import com.qait.automation.utils.YamlReader;

public class HRISTPC_02 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate,department;
	int monthBefore, weekNumber;
	List<String> punches1 = new ArrayList<String>();
	List<String> punches2 = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthBefore = Integer.parseInt(YamlReader.getData("MonthBefore"));
		weekNumber = Integer.parseInt(YamlReader.getData("WeekNo1"));
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");
		test.launchApplication();
		empId = DataReadWrite.getProperty("CdlDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("CdlDepartmentEmployeeFullName");
		savePunchesForWeek();
		department="cdl";
	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches1.add("eightHour");
		punches1.add("eightHour");
		punches1.add("eightHour");
		punches1.add("eightHour");
		punches1.add("eightHour");
		punches2.add("eightHour");
		punches2.add("eightHour");
		punches2.add("eightHour");
		punches2.add("sevenHourPlus");
		punches2.add("sevenHourPlus");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(
				monthBefore, weekNumber);
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWhichExistsInWeekToBeTested() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
	}

	@Test(dependsOnMethods = "Step02_CancelAllLeaveWhichExistsInWeekToBeTested")
	public void Step03_UpdateOnsitePunchesForGivenWeekForCase1() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches1, department);
	}

	@Test(dependsOnMethods = "Step03_UpdateOnsitePunchesForGivenWeekForCase1")
	public void Step04_VerifiedThereIsOneHLWPIFAllWorkingDayHourIsGreaterThen8AndTotalWeekHourLessThen50() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage
				.verifyHLWPForWeekHavingDayHourGreaterOrEqual8AndWeekHourLess50(
						empId, startDate);
	}

	@Test(dependsOnMethods = "Step04_VerifiedThereIsOneHLWPIFAllWorkingDayHourIsGreaterThen8AndTotalWeekHourLessThen50")
	public void Step05_UpdateOnsitePunchesForCase2() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches2, "cdl");
	}

	@Test(dependsOnMethods = "Step05_UpdateOnsitePunchesForCase2")
	public void Step06_verificationOfHLWPForWeekHavingDayHourBetween5And8AndWeekHourLess50() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage
				.verifyHLWPForWeekWithHourLessThan50AndWeekDayHourAreBetween5And8(
						empId, startDate,department);
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
