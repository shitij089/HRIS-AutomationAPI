/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.keywords;

import java.util.List;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author vinaygupta
 */
public class Admin_Page extends GetPage {

	WebDriver driver;
	String pagename = "Home_Page";

	public Admin_Page(WebDriver driver) {
		super(driver, "Admin_Page");
		this.driver = driver;
	}

	public void navigateToUsers_ESSUsersTab() {
		wait.hardWait(2);
		element("users").isDisplayed();
		executeJavascript("document.getElementById('users').children[0].onmouseover()");
		wait.hardWait(2);
		element("rollMenuLink", "ESS Users").click();
		switchToFrame(element("iframe"));

	}
	public void VerifyAndnavigateTo_JobTabContent() {
		wait.hardWait(2);
		element("users").isDisplayed();
		executeJavascript("document.getElementById('job').children[0].onmouseover()");
		wait.hardWait(2);
		element("rollMenuLink", "Job Titles").isDisplayed();
		element("rollMenuLink", "Job Specifications").isDisplayed();
		element("rollMenuLink", "Pay Grades").isDisplayed();
		element("rollMenuLink", "Employment Status").isDisplayed();
		

	}
	public void VerifyJobTitleContent() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("txt_imgButton","Add").isDisplayed();
		element("txt_imgButton","Delete").isDisplayed();
		element("txt_imgButton","Search").isDisplayed();
		element("txt_imgButton","Clear").isDisplayed();
		executeJavascript("document.getElementById('job').children[0].onmouseover()");
		wait.hardWait(2);
		element("rollMenuLink", "Job Titles").isDisplayed();
		element("rollMenuLink", "Job Specifications").isDisplayed();
		element("rollMenuLink", "Pay Grades").isDisplayed();
		element("rollMenuLink", "Employment Status").isDisplayed();
		switchToFrame(element("iframe"));

	}
	public void enterESSUserDetails(String userName, String empID) {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));

		element("userNameText").sendKeys(userName);
		selectEmployee(empID);
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("chkIntMail").click();
		wait.hardWait(1);
		element("chkPassMail").click();
	}

	public void clickSaveBtn() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("saveBtn").click();
	}

	public void clickSaveBtnForAdd() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("saveButtonForAddUser").click();
	}

	public void clickEditSaveBtn() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("editBtn").click();
		wait.hardWait(4);
	}

	public void enterNewPasswordAndConfirm(String password) {
		wait.hardWait(1);
		element("newPswdField").sendKeys(password);
		wait.hardWait(1);
		element("confirmPswdField").sendKeys(password);
	}

	public void selectEmployee(String empId) {
		String parent = driver.getWindowHandle();
		element("selectEmployee").click();
		switchWindow();
		msg.log("Title of page = " + driver.getTitle());
		selectDropDownValueByValue(element("searchbydrpdwn"), "ID");
		element("serachfortextbox").sendKeys(empId);
		element("searchButton").click();
		element("searchresultlink").click();
		switchToOtherWindow(parent);
	}

	public void enterValueForSearch(String value) {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("searchfortextbox").sendKeys(value);
		msg.log("Search Value: " + value);
	}

	public void clickSearchButton() {
		element("actionlink", "Search").click();
		msg.log("Click on search");
	}

	public void clickOnSearchResult(String username) {
		wait.hardWait(2);
		element("essSearchResultsLink", username).click();
	}

	public void nevigateToGivenTab(String tabName) {
		isElementDisplayed("link_selectTab", tabName);
		element("link_selectTab", tabName).click();
		msg.log("tab " + tabName + " is clicked !!");
	}

	public void verifyTabIsPresent(String tabName)
	{
		isElementDisplayed("link_selectTab",tabName);
		logMessage("Step: verified tab "+tabName+" is present !!\n");
		
	}
	
	
	public void verifyFieldIsPresent(String tabName)
	{
		isElementDisplayed("txt_fields",tabName);
		logMessage("Step: verified tab "+tabName+" is present !!\n");
		
	}
	
	public void searchUserName(String username, String empId) {

		selectDropDownValueByValue(element("searchbydrpdwn"), "Name");
		element("serachfortextbox").sendKeys(username);
		clickSearchButton();
		System.out.println("*****"
				+ element("searchResult").getText().contains(
						"No Records to Display!"));
		boolean d = element("searchResult").getText().contains(
				"No Records to Display!");
		if (d == false) {
			wait.hardWait(2);
			element("link_selectTab", username).click();
		} else {
			wait.hardWait(2);
			switchToDefaultContent();
			switchToFrame(element("iframe"));
			executeJavascript("document.getElementsByTagName('img')[9].click()");
			enterESSUserDetails(username, empId);
			clickSaveBtnForAdd();
			selectDropDownValueByValue(element("searchbydrpdwn"), "Name");
			element("serachfortextbox").sendKeys(username);
			clickSearchButton();
			wait.hardWait(2);
			element("link_selectTab", username).click();
		}

	}

	public void AssignRoster(String date, String empId) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("txt_rostDate").sendKeys(date);
		msg.log("Assign rost for date " + date);
		wait.hardWait(3);
		element("drpdwn_rostEmp").click();
		element("txt_empToAssignRoster", empId).click();
		msg.log("Assign rost for employee " + date);
		wait.hardWait(2);
		element("btn_saveRoster").click();
		msg.log("Click on save button");
		msg.log("Step: Assign Roster Successfully !!");
	}

	public int DeleteRosterIfExist(String date, String empName,String empId,String timeValueForGivenDate) {
		String EmpNameForReplacingRoster;
		int flag=0;
		if (!timeValueForGivenDate.contains("OFF")) {
			int i=1;
			msg.log("start deleting roster !!");
			switchToDefaultContent();
			switchToFrame(element("iframe"));
			wait.hardWait(1);
			selectDropDownValueByValue(element("drpdwn_month"),DateUtil.dateConversion("yyyy-MM-dd","MMM",date));
			msg.log("Expand drp down of month");
			wait.hardWait(1);
			element("btn_go").click();
			msg.log("Click on go button !!");
			wait.hardWait(1);
			List<WebElement> list=elements("list_empNamenotHavingRosterForDate",date);
			System.out.println("size = "+list.size());
			while(flag==0 & i<=list.size())
			{
			
			EmpNameForReplacingRoster = list.get(i).getText();
			i++;
			element("btn_edit",empName,date).click();
			wait.hardWait(1);
			selectDropDownValueByValue(element("drpdwn_rostEmp"), EmpNameForReplacingRoster);
			wait.hardWait(1);
			element("btn_saveRoster").click();
			wait.hardWait(1);
			if(element("txt_submitMsg").getText().contains("Successfully Updated"))
			{
				flag=1;
			}
			}
			msg.log("Replacing Roster Successfully !!");
			return 1;
		}
		return 0;
	}

}
