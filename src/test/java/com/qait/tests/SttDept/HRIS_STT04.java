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
import com.qait.automation.utils.YamlReader;

public class HRIS_STT04 {
	TestSessionInitiator test;
	String UserName, PassWord;
	String date, empId, empName, startDate;
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
		empId = DataReadWrite.getProperty("SttDepartmentEmployeeId");
		empName = DataReadWrite.getProperty("SttDepartmentEmployeeFullName");
		savePunchesForWeek();
	}
	@BeforeMethod
	public void methodName(Method m){
		test.msg.log("*********************"+m.getName()+"************************");
	}
	public void savePunchesForWeek() {
		punches.add("sevenHour");
		punches.add("sevenHour");
		punches.add("sevenHour");
		punches.add("sevenHour");
		punches.add("sevenHour");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		startDate = test.homePage.getStartDateOfWeekToBeTested(
				monthBefore, weekNo);
	}

	@Test(dependsOnMethods = "Step01_loginIntoApplication")
	public void Step02_CancelAllLeaveWhichExistsInWeekToBeTested() {
		test.leavePage.cancelAllLeavesInWeekHavingWeekStartDateAsGiven(
				startDate, empName, empId, monthBefore);
	}

	@Test(dependsOnMethods="Step02_CancelAllLeaveWhichExistsInWeekToBeTested")
	public void Step03_UpdateOnsitePunchesForGivenWeek() {
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunchesForWeek(startDate, punches, "stt");
	}

	
	@Test(dependsOnMethods="Step03_UpdateOnsitePunchesForGivenWeek")
	public void Step04_VerifyLessWeekHoursHalfLWPMessage()
	{
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.enterEmpId(empId);
		test.timePage.expandMonthDropDown();
		test.timePage.clickOnMonthThatIsNMonthBeforeCurrentMonth(monthBefore);
		test.timePage.VerifyMsgWhichOccurWhenTotalWeeklyHoursBecomesLessThen40(empId, startDate);
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
