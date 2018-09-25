package com.qait.tests.AdminDept;

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

public class HRISTPAD_03 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, startDate,empName,department;
	int monthBefore,weekNo;
	List<String> punches = new ArrayList<String>();

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		monthBefore=Integer.parseInt(YamlReader.getData("MonthBefore"));
		weekNo=Integer.parseInt(YamlReader.getData("WeekNo1"));
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.msg.log("**************Class Name -- \""
				+ this.getClass().getSimpleName() + "\" ***************");
		test.launchApplication();
		empId = DataReadWrite.getProperty("AdminDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("AdminDepartmentEmployeeFullName");
		savePunchesForWeek();
		department="Admin";
	}
	@BeforeMethod
	public void methodName(Method m){
		test.msg.log("*********************"+m.getName()+"************************");
	}
	public void savePunchesForWeek() {
		punches.add("eightHourPlus");
		punches.add("zeroHour");
		punches.add("zeroHour");
		punches.add("eightHourPlus");
		punches.add("eightHourPlus");
		punches.add("eightHourPlus");
		punches.add("eightHour");
		
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate=test.homePage.getStartDateOfWeekToBeTested(monthBefore, weekNo);
	}
	
	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWhichExistsInWeekToBeTested() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
	}
	
	@Test(dependsOnMethods="Step02_CancelAllLeaveWhichExistsInWeekToBeTested")
	public void Step03_AssignOFFForTwoDays() {
		
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickAssignHolidayTab();
		test.timePage.clickOnAdd();
		test.timePage.selectHolidayDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 1));
		test.timePage.selectHolidayName("OFF");
		test.timePage.selectHolidayDivision(YamlReader
				.getData("AdminExecutive.division"));
		test.timePage.assighEmployee(empId);
		test.timePage.clickOnSaveButton();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickAssignHolidayTab();
		test.timePage.clickOnAdd();
		test.timePage.selectHolidayDate(DateUtil
				.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd", "day",
						startDate, 2));
		test.timePage.selectHolidayName("OFF");
		test.timePage.selectHolidayDivision(YamlReader
				.getData("AdminExecutive.division"));
		test.timePage.assighEmployee(empId);
		test.timePage.clickOnSaveButton();
		test.homePage.switchToDefaultContent();
	}
	@Test(dependsOnMethods="Step03_AssignOFFForTwoDays")
	public void Step04_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, department);
	}
	
	


	@Test(dependsOnMethods="Step04_UpdateOnsitePunchesForGivenWeek")
	public void Step05_VerifyThatIfEmployeeTakesWeekoffThenItShouldBeCountedAs8WorkingHours() {

		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage.VerifyNoLWPIfTotalWeekHourIsGreaterThanRequiredHour(
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
