package com.qait.tests.CDLDept;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.YamlReader;

public class HRISTPC_19 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate;
	int monthBefore, weekNumber,dayHavingLWP;
	List<String> punches = new ArrayList<String>();
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
	}

	@BeforeMethod
	public void methodName(Method m) {
		test.msg.log("*********************" + m.getName()
				+ "************************");
	}

	public void savePunchesForWeek() {
		punches.add("nineHour");
		punches.add("eightHourPlus");
		punches.add("eightHourPlus");
		punches.add("nineHour");
		punches.add("zeroHour");
		punches2.add("eightHourPlus");
		punches2.add("eightHour");
		punches2.add("eightHour");
		punches2.add("nineHour");
		punches2.add("zeroHour");
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
	public void Step03_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "cdl");
	}

	@Test(dependsOnMethods = "Step03_UpdateOnsitePunchesForGivenWeek")
	public void Step04_VerifyThereShouldNotBeAnyLWPInFirstFourDayOfWeekHavinhHour35() {
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		dayHavingLWP=test.timePage.getDayHavingFirstLWPInWeek(empId, startDate);
		Assert.assertEquals(dayHavingLWP, 5,"Expected was 5 but found "+dayHavingLWP);
	}
	@Test(dependsOnMethods = "Step04_VerifyThereShouldNotBeAnyLWPInFirstFourDayOfWeekHavinhHour35")
	public void Step05_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches2, "cdl");
	}

	@Test(dependsOnMethods = "Step05_UpdateOnsitePunchesForGivenWeek")
	public void Step06_VerifyThereShouldBeAnyLWPInFirstFourDayOfWeekHavinhHourLess35() {
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		dayHavingLWP=test.timePage.getDayHavingFirstLWPInWeek(empId, startDate);
		Assert.assertEquals(dayHavingLWP, 3,"Expected was 3 but found "+dayHavingLWP);	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}

}
