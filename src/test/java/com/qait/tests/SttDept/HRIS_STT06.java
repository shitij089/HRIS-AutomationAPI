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

public class HRIS_STT06 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, startDate, empName, username, managerId = "1021";
	int monthAfter, weekNo;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthAfter = Integer.parseInt(YamlReader.getData("MonthAfter"));
		weekNo = Integer.parseInt(YamlReader.getData("WeekNo1"));
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");
		test.launchApplication();
		empId = DataReadWrite.getProperty("SttDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("SttDepartmentEmployeeFullName");
		savePunchesForWeek();
		username = DataReadWrite.getProperty("SttDepartmentEmployeeFirstName")
				+ DataReadWrite.getProperty("SttDepartmentEmployeeLastName");

	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches.add("eightHourPlus");
		punches.add("eightHourPlus");
		punches.add("nineHour");
		punches.add("nineHour");
		punches.add("zeroHour");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(monthAfter,
				weekNo);
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWhichExistsInWeekToBeTested() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthAfter);
	}

	@Test(dependsOnMethods = "Step02_CancelAllLeaveWhichExistsInWeekToBeTested")
	public void Step03_AssignManagerToAnyEmployeeId() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToPIMTab();
		test.pimPage.searchForRecordWithEmpId();
		test.pimPage.enterValueForSearch(empId);
		test.pimPage.clickSearchButton();
		test.pimPage.clickOnSearchResult();
		test.pimPage.clickOnReportToTab();
		test.pimPage.reportToGivenManager(managerId);
		test.pimPage.acceptAlert();
	}

	@Test(dependsOnMethods = "Step03_AssignManagerToAnyEmployeeId")
	public void Step04_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "stt");
	}

	@Test(dependsOnMethods = "Step04_UpdateOnsitePunchesForGivenWeek")
	public void Step05_CreateCredentialForNewEmployee() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToAdminTab();
		test.adminPage.navigateToUsers_ESSUsersTab();
		test.adminPage.searchUserName(username, empId);
		test.adminPage.clickEditSaveBtn();
		test.adminPage.enterNewPasswordAndConfirm(empId);
		test.adminPage.clickSaveBtn();

	}

	@Test(dependsOnMethods = "Step05_CreateCredentialForNewEmployee")
	public void Step06_LoginWithUserAccountIdForApplyPTO() {
		test.launchApplication();
		test.loginPage.loginForUserCreatedAccount(username, empId);

	}

	@Test(dependsOnMethods = "Step06_LoginWithUserAccountIdForApplyPTO")
	public void Step07_VerifyPTOAppliedSuccessfullyByEmployee() {
		test.homePage.navigateToLeaveTab();
		test.leavePage.navigateToApplyTab();
		test.leavePage.selectLeaveType("PTO");
		test.leavePage.selectFromDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));
		test.leavePage.selectEndDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));

		test.leavePage.clickApplyButton1();
		test.pimPage.acceptAlert();

		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthAfter);
		test.timePage.verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", startDate, 4), empId, "PTO");
	}

	@Test(dependsOnMethods = "Step07_VerifyPTOAppliedSuccessfullyByEmployee")
	public void Step08_LoginWithManagerIdForCancelLeave() {
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("manager.username"),
				YamlReader.getData("manager.password"));
		test.homePage.navigateToLeaveTab();

	}

	@Test(dependsOnMethods = "Step08_LoginWithManagerIdForCancelLeave")
	public void Step09_verifyCancelLeaveByManager() {
		test.homePage.navigateToTimeTab();
		test.homePage.navigateToLeaveTab();
		test.leavePage
				.clickOnApprovalLeave(DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"MMMyyyy",
						"month",
						DateUtil.getCurrentdateInStringWithGivenFormate("MMMyyyy"),
						-monthAfter));
		test.leavePage.searchEmpIdForCancelLeaveByManager(empName, DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 4));

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
