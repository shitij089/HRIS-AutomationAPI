package com.qait.tests.ResourceManagement;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class SeniorManagerTest {
	TestSessionInitiator test;
	String temp;
	String empID, empId;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empId="1023";
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
	}
	@Test
	public void Step03_VerifySrManagerSubTabLoadSuccessfully() {
			test.homePage.navigateToResourceManagementTab();
			test.resourceManagementpage.navigateToSrManagerTab();
	}
	@Test
	public void Step04_VerifyInputSearchBoxWorkingFine() {
			test.resourceManagementpage.searchUserInSrMAnagerSubtab(empId);
			test.resourceManagementpage.verifySearchResults(empId);
	
	}
	//@Test
	public void Step05_VerifycolumnSearchBoxWorkingFine() {
			test.resourceManagementpage.searchUserInSrMAnagerSubtab(empId);
			test.resourceManagementpage.verifySearchResults(empId);
			test.resourceManagementpage.verifyEditInputValueSavedOrNot("3011");
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
