package com.qait.tests.ResourceManagement;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ReportingTest {
	TestSessionInitiator test;
	String temp, UserName, PassWord;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
	}

	@BeforeMethod
	public void methodName(Method m) {
		System.out.println("*********************" + m.getName()
		+ "************************");
	}

	@Test
	public void Step01_loginIntoApplication() {
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
	}

	//@Test
/*	public void Step02_VerifySuccessfullyNavigateToRMSPage()
	{
		test.homePage.navigateToResourceManagementTab();
		test.resourceManagementpage.verifyRMSPageLoadsSuccessfully();
	}
	*/
	@Test
	public void Step02_VerifyEnteringNameInSearchOnTopOpensBillingCalender() throws InterruptedException{
		test.resourceManagementpage.enterEmployeeValidEmployeeInSearchOnTop(YamlReader.getData("emp_name"));
		test.resourceManagementpage.selectEmployeeFromSearchResults();
		test.resourceManagementpage.verifyBillingCalenderOfSearchEmployeeIsDisplayed(YamlReader.getData("emp_name"));
		//test.resourceManagementpage.closeBillingCalender();
	}

	@Test
	public void Step03_VerifyUserIsAbleToClickReportingTabAndVerify()
	{
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.navigateToReportingTab();
		test.resourceManagementpage.verifyReportingPageLoadsCorrectly();
		test.resourceManagementpage.verifyManageRankButtonISPresent();
		test.resourceManagementpage.verifySearchInputTextIsPresent();
		test.resourceManagementpage.verifyListOfEmpIsPresentWithReportingDetails();
	}

	
	@Test
	public void Step04_VerifyRankWeightageAcceptsPositiveNumbersOnly() throws InterruptedException{
		test.resourceManagementpage.verifyUserIsAbleToCLickManageRankButton();
		test.resourceManagementpage.verifyRankWeightageCannotBeNegative();
	}

	@Test
	public void Step05_VerifyRankWeghtageCannotBeZeroAndCannotBeRepeated(){
		test.resourceManagementpage.verifyRankWeghtageCannotBeZero();
		test.resourceManagementpage.verifyRankWeghtageCannotBeRepeated();
	}

	@Test
	public void Step06_VerifyNewRankISAddedBasedOnRankWeightage() throws InterruptedException{
		test.resourceManagementpage.verifyNewRankCanBeAdded();
	}

	@Test
	public void Step07_VerifySaveAndDeleteButtonsWorkProperly(){
		test.resourceManagementpage.verifySaveButtonWorksProperly();
		test.resourceManagementpage.verifyDeleteButtonWorksProperly();
	}

	@Test
	public void Step08_VerifySearchButtonIsWorking() throws InterruptedException{
		test.resourceManagementpage.enterTextInSearchBar(YamlReader.getData("search_emp"));
		test.resourceManagementpage.verifySearchResults(YamlReader.getData("search_emp"));
	}
	
	

	@Test
	public void Step09_VerifyCorrectOptionsAreDisplayedOnExpandingSettingsAndTags() throws InterruptedException{
	
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.verifyCorrectOptionsAreDisplayedOnExpandingSettings();
		test.resourceManagementpage.closeSlideout();
		Thread.sleep(2000);
	}
	
	@Test
	public void Step10_VerifyOptionsUnderSettingsAndTagNavigateToCorrectPage() throws InterruptedException{
		test.resourceManagementpage.verifyCorrectPAgeLoadedOnClickingQuestionsOption();
		test.resourceManagementpage.clickOnSlideout();
		Thread.sleep(2000);
		test.resourceManagementpage.verifyCorrectPAgeLoadedOnClickingQuestionnaireOption();
		test.resourceManagementpage.clickOnSlideout();
		Thread.sleep(2000);
		test.resourceManagementpage.verifyCorrectPAgeLoadedOnClickingQuestionnaireReportOption();
		test.resourceManagementpage.clickOnSlideout();
		Thread.sleep(2000);
		test.resourceManagementpage.verifyCorrectPageLoadedOnClickingReportOption();
	}
	
	@Test
	public void Test11_VerifyCorrectSubModulesAreDiplayedOnClickingSetttingsAndTags() throws InterruptedException{
		test.resourceManagementpage.clickOnSlideout();
		Thread.sleep(2000);
		test.resourceManagementpage.clickSettingsAndTags();
		test.resourceManagementpage.verifyCorrectSubModulesAreDisplyed();
		
	}
	
	@Test
	public void Test12_VerifyPermissionIsGivenToEssUser(){
		test.resourceManagementpage.VerifyUserIsAbleToEnterESSUser(YamlReader.getData("ESS_user"));
		test.resourceManagementpage.LogoutCurrentUser();
		UserName = YamlReader.getData("ESS_username");
		PassWord = YamlReader.getData("ESS_password");
		test.loginPage.login(UserName, PassWord);
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.verifyGivenPermissionReflectedForESSUser();
		test.resourceManagementpage.LogoutCurrentUser();
		UserName = YamlReader.getData("username");
		PassWord = YamlReader.getData("password");
		test.loginPage.login(UserName, PassWord);
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.clickSettingsAndTags();
		test.resourceManagementpage.VerifyPermittedESSUserGetsDeleted(YamlReader.getData("ESS_username"));	
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
