package com.qait.tests.InvalidHours;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.YamlReader;

public class SE_VerifyInvalidHoursNotCountInWorkingHours {

	TestSessionInitiator test;
	String date, empId,startDate,timeInfo;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		empId = DataReadWrite.getProperty("SEDepartmentEmployeeId");
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("username"),YamlReader.getData("password"));
		startDate = test.homePage.getStartDateOfWeekToBeTested(
				1, 2);
	}
	
	@Test
	public void Step01_insertPunchesWithIncomingEarlyHour()
	{
		test.launchApplication(YamlReader.getData("databaseUrl"));
		test.database_page.login();
		test.database_page.gotoTable();
		test.database_page.updatePunches(empId, startDate,"07:00:00");
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("username"),YamlReader.getData("password"));
		test.homePage.navigateToTimeTab();
		test.timePage.clickQaitPunchesTab();
		test.timePage.sendKeysAfterQaitPunches(empId,startDate,startDate,"15:00:00");
	}

	@Test(dependsOnMethods="Step01_insertPunchesWithIncomingEarlyHour")
	public void Step02_verifyThatIncomingHourNotCountInWorkingHours()
	{
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(1);
		timeInfo=test.timePage.getInfoAtGivenDate(startDate, empId);
		test.timePage.verifyfirstValueContainsSecond(timeInfo,"Incoming Early Hour(s): 01:00");
	}
	
	@Test(dependsOnMethods="Step02_verifyThatIncomingHourNotCountInWorkingHours")
	public void Step03_insertPunchesWithLeavingLateHour()
	{
		test.launchApplication(YamlReader.getData("databaseUrl"));
		test.database_page.login();
		test.database_page.gotoTable();
		test.database_page.updatePunches(empId, startDate,"14:00:00");
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("username"),YamlReader.getData("password"));
		test.homePage.navigateToTimeTab();
		test.timePage.clickQaitPunchesTab();
		test.timePage.sendKeysAfterQaitPunches(empId,startDate,startDate,"21:00:00");
	}

	@Test(dependsOnMethods="Step03_insertPunchesWithLeavingLateHour")
	public void Step04_verifyThatLeavingLateHourNotCountInWorkingHours()
	{
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(1);
		timeInfo=test.timePage.getInfoAtGivenDate(startDate, empId);
		test.timePage.verifyfirstValueContainsSecond(timeInfo,"Leaving Late Hour(s): 01:00");
	}
	
	@Test(dependsOnMethods="Step04_verifyThatLeavingLateHourNotCountInWorkingHours")
	public void Step05_insertPunchesWithLeavingLateHour()
	{
		test.launchApplication(YamlReader.getData("databaseUrl"));
		test.database_page.login();
		test.database_page.gotoTable();
		test.database_page.updatePunches(empId, startDate,"09:00:00");
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("username"),YamlReader.getData("password"));
		test.homePage.navigateToTimeTab();
		test.timePage.clickQaitPunchesTab();
		test.timePage.sendKeysAfterQaitPunches(empId,startDate,startDate,"19:00:00");
	}

	@Test(dependsOnMethods="Step05_insertPunchesWithLeavingLateHour")
	public void Step06_verifyThatLeavingLateHourNotCountInWorkingHours()
	{
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(1);
		timeInfo=test.timePage.getInfoAtGivenDate(startDate, empId);
		test.timePage.verifyfirstValueContainsSecond(timeInfo,"Addl. Work Hour(s) : 01:00");
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
