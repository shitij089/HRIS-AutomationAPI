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

public class HRIS_71 {
	TestSessionInitiator test;
	String UserName, PassWord;
	int monthBefore, weekNo;
	String date, empId, empName, currentDate, lastDate, startDate, startDate1,
			curTo1DayPreviousDate;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = DataReadWrite.getProperty("FemaleSEDepartmentEmployeeId");
		empName = DataReadWrite
				.getProperty("FemaleSEDepartmentEmployeeFullName");
		currentDate = DateUtil
				.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd");
		curTo1DayPreviousDate = DateUtil
				.getPreviousDateForGivenDateModuleInGivenFormat("yyyy-MM-dd",
						"day", 1);
		lastDate = DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
				"yyyy-MM-dd", "day", 89);
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");
		savePunchesForWeek();
	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches = test.timePage.savePunches(lastDate, curTo1DayPreviousDate);
		System.out.println(punches.size());
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);

	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		String d1 = test.leavePage
				.verifyThereShouldNoLeaveInWeekHavingStartDateAsGiven1(
						lastDate, empName, empId, 3);
		String d2 = test.leavePage
				.verifyThereShouldNoLeaveInWeekHavingStartDateAsGiven1(d1,
						empName, empId, 2);
		String d3 = test.leavePage
				.verifyThereShouldNoLeaveInWeekHavingStartDateAsGiven1(d2,
						empName, empId, 1);
		test.leavePage.verifyThereShouldNoLeaveInWeekHavingStartDateAsGiven1(
				d3, empName, empId, 0);
	}

	@Test(dependsOnMethods = "Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step03_UpdateMarriageInformation() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		test.pimPage.enterMarriageDetails(DateUtil
				.getPreviousDateForGivenDateModuleInGivenFormat("yyyy-MM-dd",
						"month", 6));
	}
	
	@Test(dependsOnMethods = "Step03_UpdateMarriageInformation")
	public void Step04_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(lastDate, punches, "se");
	}

	@Test(dependsOnMethods = "Step04_UpdateOnsitePunchesForGivenWeek")
	public void Step05_ApplieMaternityLeaveAndVerifyThereShouldNotBeSW() {
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToAssignLeaveTab();
		test.leavePage.selectLeaveType("Maternity leave");
		test.leavePage.selectEmploy(empId);
		test.leavePage.selectFromDate(lastDate);
		test.leavePage.selectEndDate(curTo1DayPreviousDate);
		test.leavePage.clickApplyButton();
		test.pimPage.acceptAlert();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(3);
		test.timePage.verifyTharThereIsNoSandwitchBetweenMAternityLeaveApplied(
				lastDate, curTo1DayPreviousDate);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(2);
		test.timePage.verifyTharThereIsNoSandwitchBetweenMAternityLeaveApplied(
				lastDate, curTo1DayPreviousDate);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(1);
		test.timePage.verifyTharThereIsNoSandwitchBetweenMAternityLeaveApplied(
				lastDate, curTo1DayPreviousDate);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.verifyTharThereIsNoSandwitchBetweenMAternityLeaveApplied(
				lastDate, curTo1DayPreviousDate);
	}

	@Test(dependsOnMethods = "Step05_ApplieMaternityLeaveAndVerifyThereShouldNotBeSW", alwaysRun = true)
	public void Step06_cancelAllAppliedLeaves() {
		test.leavePage.verifyThereShouldNoLeaveInWeekHavingStartDateAsGiven1(
				lastDate, empName, empId, 3);
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
