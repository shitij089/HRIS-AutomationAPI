package com.qait.tests.ItDept;

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

public class HRISIT_01 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate,timeValueForGivenDate;
	int monthBefore,weekNo;
	List<String> punches = new ArrayList<String>();
	int flag;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthBefore=Integer.parseInt(YamlReader.getData("MonthBefore"));
		weekNo=Integer.parseInt(YamlReader.getData("WeekNo1"));
		System.out.println("**************Class Name -- \""+this.getClass().getSimpleName()+"\" ***************");
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = DataReadWrite.getProperty("ItDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("ItDepartmentEmployeeFullName");
		savePunchesForWeek();
	}

	public void savePunchesForWeek() {
		punches.add("eightHour");
		punches.add("eightHourPlus");
		punches.add("eightHourPlus");
		punches.add("nineHour");
		punches.add("nineHour");
		punches.add("zeroHour");
	}
	@BeforeMethod
	public void methodName(Method m){
		System.out.println("*********************"+m.getName()+"************************");
	}
	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(
				monthBefore, weekNo);
	}

	@Test(dependsOnMethods="Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(startDate, empName, empId,monthBefore);
	}
	
	@Test(dependsOnMethods="Step02_CancelAllLeaveWichExistsInWeekHAvingStartDate")
	public void Step03_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "it");
	}
	
	@Test(dependsOnMethods="Step03_UpdateOnsitePunchesForGivenWeek")
	public void Step04_VerifyThereShouldNotBeAnyLWPIfTotalWeekHourIsGreaterThen43() {
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage
				.VerifyNoLWPIfTotalWeekHourIsGreaterThanRequiredHour(
						empId, startDate);
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
