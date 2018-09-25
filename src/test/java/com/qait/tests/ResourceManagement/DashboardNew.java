package com.qait.tests.ResourceManagement;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class DashboardNew {
	
	TestSessionInitiator test;
	String temp, UserName, PassWord,empName;
	String empName1,empId;
	

	//@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
	}
	
	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId = "307346";
		empName ="FullTime  307346";
		empName1="Minesh Upadhyaya";
	}
	
	@Test
	public void Step01_VerifyLoginPageLoadsSuccessfully()
	{
		test.loginPage.verifyLoginPageLoadsSuccessfully();
	}

	@Test
	public void Step02_VerifyLogin() {
			test.loginPage.login(YamlReader.getData("username"),
					YamlReader.getData("password"));
			test.homePage.verifyPageTitleExact("QAIT HRIS");
			test.homePage.navigateToResourceManagementTab();

	}
	
	//@Test
	public void Test03_VerifySupportLinkWorksPoperly(){
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.clickOnDashBoard();
		test.resourceManagementpage.verifyUserIsAbleToClickSupportLink();
		test.resourceManagementpage.verifyCorrectDetailsAreShownOnClickingSupportLink();
		test.resourceManagementpage.verifyNamesPresentInEmpNameListNavigatesToEmpBillingCalender();
		test.resourceManagementpage.closeBillingCalender();
		test.homePage.pageRefresh();
		test.resourceManagementpage.verifyUserIsAbleToClickSupportLink();
		test.resourceManagementpage.verifyBillableEmpPresentInEmpNameListNavigatesToEmpBillingCalender();
		test.resourceManagementpage.closeBillingCalender();
	}
	
	//@Test
	public void Test04_VerifyEveryLinkIsWorkingInsideEngineersBox(){
		test.homePage.pageRefresh();
		test.resourceManagementpage.verifyLinksAreWorkingInsideEnggBox();
		//test.homePage.pageRefresh();
		//test.resourceManagementpage.verifyEmployeesHeadingLinkIsWorking();
		
	}
	
	//@Test
	public void Test05_VerifyEveryDivisionHeadingLinkIsWorkingProperly(){
		test.homePage.pageRefresh();
		test.resourceManagementpage.verifyDivisionsLinkNavigateToCorrespondingPage();	
	}
	
	@Test
	public void Test06_VerifyCorrectSubModulesDisplayedInMonthyTab(){
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.clickOnDashBoard();
		test.resourceManagementpage.clickOnMonthlyButton();
		test.resourceManagementpage.verifyCorrectSubModulesAreDisplyedInMonthly();
	}
	
	@Test
	public void Test07_VerifyCorrectDetailsAreDiplayedOnClickingProjectBox(){
		
		test.resourceManagementpage.verifyCorrectDetailsDisplayedOnClickingProjectBox();
	}
	
	@Test
	public void Test08_VerifySearchWorksProperlyInsideProjectDashboard(){
		test.resourceManagementpage.clickOnProjects();
		test.resourceManagementpage.verifySearchButtonWorksFineInsideProjects(YamlReader.getData("project_search"));
		test.resourceManagementpage.verifySearchWorksFineAccordingProjectId(YamlReader.getData("project_id_search"));
		test.resourceManagementpage.verifySearchWorksFineAccordingManagerName(YamlReader.getData("project_manager_search"));
	}
}
