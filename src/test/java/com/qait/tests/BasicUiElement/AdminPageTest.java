package com.qait.tests.BasicUiElement;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class AdminPageTest {

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

	@Test
	public void Step02_VerifySubTabs() {
		test.homePage.navigateToAdminTab();
		test.adminPage.verifyTabIsPresent("Company Info");
		test.adminPage.verifyTabIsPresent("Job");
		test.adminPage.verifyTabIsPresent("Qualification");
		test.adminPage.verifyTabIsPresent("Skills");
		test.adminPage.verifyTabIsPresent("Nationality");
		test.adminPage.verifyTabIsPresent("Users");
		test.adminPage.verifyTabIsPresent("Email Notifications");
		test.adminPage.verifyTabIsPresent("Project Info");
		test.adminPage.verifyTabIsPresent("Data Import/Export");
		test.adminPage.verifyTabIsPresent("Custom Fields");
		test.adminPage.verifyTabIsPresent("HR Documents");
		test.adminPage.verifyTabIsPresent("Photo Album");
		test.adminPage.verifyTabIsPresent("Notices");
		test.adminPage.verifyTabIsPresent("HRIS Charts");
		test.adminPage.verifyTabIsPresent("HRIS Alerts");
		test.adminPage.verifyTabIsPresent("HRIS Custom Report");
		test.adminPage.verifyTabIsPresent("Onsite Employees");
		test.adminPage.verifyTabIsPresent("Resource Requisite");
		test.adminPage.verifyTabIsPresent("Manage Resignation");
		test.adminPage.verifyTabIsPresent("Term Association Bonus");
		test.adminPage.verifyTabIsPresent("Custom Emails");
		test.adminPage.verifyTabIsPresent("Installment Details");
		test.adminPage.verifyTabIsPresent("Mediclaim List");
		test.adminPage.verifyTabIsPresent("IT Rosters");
		test.adminPage.verifyTabIsPresent("Leave Setting");
	}

	
	
	@Test
	public void Step03_VerifyNoneditableFieldsUnderGeneralSection() {
		
		test.adminPage.switchToFrame("rightMenu");
		test.adminPage.verifyFieldIsPresent("Company Name");
		test.adminPage.verifyFieldIsPresent("Tax ID");
		test.adminPage.verifyFieldIsPresent("Phone");
		test.adminPage.verifyFieldIsPresent("Country");
		test.adminPage.verifyFieldIsPresent("Address1");
		test.adminPage.verifyFieldIsPresent("City");
		test.adminPage.verifyFieldIsPresent("PIN Code");
		test.adminPage.verifyFieldIsPresent("Comments");
		test.adminPage.verifyFieldIsPresent("Number of Employees");
		test.adminPage.verifyFieldIsPresent("NAICS");
		test.adminPage.verifyFieldIsPresent("Fax");
		test.adminPage.verifyFieldIsPresent("Address2");
		test.adminPage.verifyFieldIsPresent("State / Province");
		
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
