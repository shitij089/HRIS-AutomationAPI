package com.qait.tests.RestrictedLeaveScenarios;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class VerifyRHCanBeAppliedInCurrentDate {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, marriageDate, startDate,timeValue;
	int monthAfter,weekNumber;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		System.setProperty("department", "se");
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = YamlReader.getData("restrictedLeave.empId");
		empName= YamlReader.getData("restrictedLeave.empName");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = DateUtil.getCurrentdateInStringWithGivenFormate("yyyy-MM-dd");
		monthAfter=0;
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_AddMarriageDetails() {
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		marriageDate = DateUtil.getPreviousDateForGivenDateModuleInGivenFormat("yyyy-MM-dd","year",1);
		System.out.println("marriage date = " + marriageDate);
		test.pimPage.enterMarriageDetails(marriageDate);
	}

	@Test(dependsOnMethods = "Step02_AddMarriageDetails")
	public void Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthAfter);
	}

	@Test(dependsOnMethods = "Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step04_ApplyRestrictedeaveFromUserForGivenDate() {
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("restrictedLeave.username"),
				YamlReader.getData("restrictedLeave.password"));
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToApplyTab();
		test.leavePage.selectLeaveType("Restricted Leave");
		test.leavePage.selectGivenDate("Marriage Anniversary");
		test.leavePage.selectFromDate(startDate);
		test.leavePage.selectEndDate(startDate);
		test.leavePage.clickApplyButton1();
		test.pimPage.acceptAlert();
	}

	@Test(dependsOnMethods = "Step04_ApplyRestrictedeaveFromUserForGivenDate")
	public void Step05_VerifyThatThereIsNoSandwitchInSatSunWhichOccurBetweenChildCareLeaveLeaveApplied() {
		test.launchApplication();
		test.loginPage.login(UserName, PassWord);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthAfter);
		timeValue=test.timePage.getInfoAtGivenDate(startDate, empId);
		test.timePage.verifyfirstValueContainsSecond(timeValue, "Approved RH Request");
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	 @AfterClass
	public void Close_Test_Session() {
		 test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
		 startDate, empName, empId,monthAfter);
		 test.closeBrowserSession();
	}
}

