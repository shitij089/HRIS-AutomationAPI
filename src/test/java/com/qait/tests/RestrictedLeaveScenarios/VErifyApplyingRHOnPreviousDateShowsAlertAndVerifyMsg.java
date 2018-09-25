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

public class VErifyApplyingRHOnPreviousDateShowsAlertAndVerifyMsg {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, marriageDate, anniversaryDate, startDate,
			timeValue, alertText;
	int monthAfter, weekNumber;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		System.setProperty("department", "se");
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = YamlReader.getData("restrictedLeave.empId");
		empName = YamlReader.getData("restrictedLeave.empName");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		anniversaryDate = DateUtil
				.getPreviousDateForGivenDateModuleInGivenFormat("yyyy-MM-dd",
						"day", 1);
		monthAfter = 0;
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_AddMarriageDetails() {
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		marriageDate = DateUtil.getPreviousDateFromGivenDateFoGivenDateModule(
				"yyyy-MM-dd", "year", anniversaryDate, 1);
		System.out.println("marriage date = " + marriageDate);
		test.pimPage.enterMarriageDetails(marriageDate);
	}

	@Test(dependsOnMethods = "Step02_AddMarriageDetails")
	public void Step03_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				anniversaryDate, empName, empId, monthAfter);
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
		test.leavePage.selectFromDate(anniversaryDate);
		test.leavePage.selectEndDate(anniversaryDate);
	}

	@Test(dependsOnMethods = "Step04_ApplyRestrictedeaveFromUserForGivenDate")
	public void Step05_VerifyTPopUpMessage() {
		alertText = test.leavePage
				.getPopUpTextAfterClickingOnApplyButtonOfLeave();
		test.timePage.verifyfirstValueContainsSecond(alertText,
				YamlReader.getData("msg.RHInPreviousDate"));
		test.launchApplication();
		test.loginPage.login(UserName, PassWord);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass
	public void Close_Test_Session() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				anniversaryDate, empName, empId, monthAfter);
		test.closeBrowserSession();
	}
}
