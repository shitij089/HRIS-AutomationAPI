package com.qait.keywords;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.qait.automation.utils.DateUtil;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author vinaygupta
 */
public class ResouceManagement_Page extends GetPage {

	WebDriver driver;
	String pagename = "ResouceManagement_Page";
	String permitted_division;

	public ResouceManagement_Page(WebDriver driver) {
		super(driver, "ResouceManagement_Page");
		this.driver = driver;
	}

	// Actions action=new Actions(driver);

	public void closeModalDialog() {
		wait.hardWait(2);
		switchToDefaultContent();
		element("closeModalDialog").click();
	}

	public void navigateAndVerifyMyProjectsTab(){
		switchToDefaultContent();
		wait.hardWait(2);
		element("genericSearchById", "hamburger").click();
		element("myProjectstab").click();
		msg.log("Navigate to my projects tab");
		Assert.assertEquals(getCurrentURL(),
				YamlReader.getData("myProjectUnderRMSUrl"),
				"My Project subtab under RMS is not loaded successfully!!!!");
		msg.pass("My Project subtab under RMS is loaded successfully!!!!");
	}

	public void navigateToMyProjectsTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		element("myProjectstab").click();
		msg.log("Navigate to my projects tab");
		Assert.assertEquals(getCurrentURL(),
				YamlReader.getData("myProjectUnderRMSUrl"),
				"My Project subtab under RMS is not loaded successfully!!!!");
		msg.pass("My Project subtab under RMS is loaded successfully!!!!");
	}

	public void navigateToSrManagerTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		element("genericSearchById", "hamburger").click();
		element("genericSearchById", "addsm").click();
		msg.log("Navigate to Sr. Manager subTab");
		Assert.assertEquals(getCurrentURL(),
				YamlReader.getData("srManagerUnderRMSUrl"),
				"Sr. Manager subTab under RMS is not loaded successfully!!!!");
		msg.pass("Sr. Manager subTab under RMS is loaded successfully!!!!");
	}

	public void clickUpdateBillingButtonForVerifyOpenPopup() {
		switchToDefaultContent();
		element("button_updateBilling", "2", "7").click();
		executeJavascript("document.getElementsByClassName('close')[2].click()");
		msg.log("verified that pop up window closed successfully!!!!");
	}

	public void searchForProject(String projectID) {
		wait.hardWait(2);
		switchToDefaultContent();
		element("searchProject").clear();
		element("searchProject").sendKeys(projectID);
		msg.log("Send " + projectID + " as project id to search");
		element("projectLink").click();
		msg.log("Click for search");
		hardWait(4);
	}

	public void addedProject(String projectName, String date) {
		wait.hardWait(2);
		switchToDefaultContent();
		element("genericSearchById", "empProjectSearch").sendKeys("SAI");
		element("select_dropDown", projectName).click();
		// executeJavascript("document.getElementById('empProjectSearch').value='"+projectName+"'");
		msg.log("added project name");
		element("genericSearchById", "startDt").sendKeys(date);
		hardWait(3);
		msg.log("sent start date");
		element("genericSearchById", "empType").click();
		msg.log("clicked at checkbox");
		element("genericSearchById", "submitBtn").click();
		wait.hardWait(4);
		msg.log("all process have completed for adding project");
		// element("txt_resultInfo").isDisplayed();
	}

	public void searchAndAddResourceToTeam(String shortEmpName, String empID) {
		wait.hardWait(2);
		switchToDefaultContent();
		element("genericSearchById", "empNameSearchEmp").sendKeys(shortEmpName);
		element("select_dropDown", empID).click();
		// executeJavascript("document.getElementById('ui-id-1').style.display='block'");
		element("submitBtn").click();
	}

	public void searchAndAddResourceToTeam(String shortEmpName, String empID,
			String empType) {
		wait.hardWait(2);
		switchToDefaultContent();
		element("genericSearchById", "empNameSearchEmp").sendKeys(shortEmpName);
		element("select_dropDown", empID).click();
		hardWait(3);
		//executeJavascript("document.getElementById('ui-id-1').style.display='block'");
		selectDropDownValueByValue(element("genericSearchById", "empTypeEmp"),
				empType);
		msg.log("select as" + empType);
		element("submitBtn").click();
		wait.hardWait(4);
		msg.log("successfully added the empId with respective emp name" + empID);
	}

	public void verifyNewResourceAddedSuccessfully(String empName) {
		wait.hardWait(4);
		boolean flag = false;
		long lead = (long) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByClassName('teamLeadCls').length");
		long member = (long) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByClassName('teamMemberClr').length");
		System.out.println("*****" + lead);
		System.out.println("*****" + member);
		for (int i = 0; i < lead; i++) {
			String s1 = (String) ((JavascriptExecutor) driver)
					.executeScript("return document.getElementsByClassName('teamLeadCls')["
							+ i + "].children[2].innerText");
			System.out.println("####" + s1.contains(empName));
			if (s1.contains(empName)) {
				flag = true;
				break;
			}

		}
		if (flag) {
			msg.pass("successfully added as team lead with emp name!!!"
					+ empName);
		} else {
			for (int i = 0; i < member; i++) {
				String s2 = (String) ((JavascriptExecutor) driver)
						.executeScript("return document.getElementsByClassName('teamMemberClr')["
								+ i + "].children[2].innerText");
				System.out.println("****" + s2.contains(empName));
				if (s2.contains(empName)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				msg.pass("successfully added as team member with emp name!!!"
						+ empName);
			} else {
				Assert.fail();
			}
		}
	}

	public void searchAndDeleteResourceToTeam(String empName) {

		boolean flag = false;
		long count = (long) ((JavascriptExecutor) driver)
				.executeScript("return document.querySelectorAll('img.imgDelCls').length");

		System.out.println("*****" + count);

		for (int i = 0; i < count; i++) {
			String s1 = (String) ((JavascriptExecutor) driver)
					.executeScript("return document.querySelectorAll('img.imgDelCls')["
							+ i + "].getAttribute('onclick')");
			System.out.println("####" + s1.contains(empName));
			if (s1.contains(empName)) {
				((JavascriptExecutor) driver)
						.executeScript("return document.querySelectorAll('img.imgDelCls')["
								+ i + "].click()");
				executeJavascript("document.querySelector('button#deleteBtn').click()");
				flag = true;
				break;
			}

		}
		if (flag) {
			msg.pass("successfully deleted as team lead with emp name!!!"
					+ empName);
		} else {
			Assert.fail();
		}
	}

	public void selectEmployeeToCreditAdditonalHours() {
		wait.hardWait(1);
		switchToDefaultContent();
		element("updateBilling").click();
		wait.hardWait(1);
		element("toggleCheckBox").click();
		int size = elements("allEmployeesCheckBox").size();
		element("latestEmployeeCheckBox", Integer.toString(size)).click();
	}

	public void searchUserInSrMAnagerSubtab(String empId) {
		wait.hardWait(2);
		switchToDefaultContent();
		element("search_text_field").clear();
		element("search_text_field").sendKeys(empId);
		wait.hardWait(4);
		element("search_text_field").sendKeys(Keys.ENTER);
		wait.hardWait(4);
		msg.log("Send " + empId + " for search");
	}

	public void updateAHForEmployee(String date) {
		wait.hardWait(1);
		switchToDefaultContent();
		element("selectDate", date).click();
		wait.hardWait(1);
		element("additionalHourTxt").sendKeys("8");
		element("comment").sendKeys("AH given");
		element("saveAHBtn").click();
		wait.hardWait(2);
	}

	public void verifyRMSPageLoadsSuccessfully() {
		Assert.assertEquals(getResponseCode(YamlReader.getData("rmsUrl")), true);
		logMessage("ASSERT PASSED: Verified page load successfully !!");
	}

	public boolean getResponseCode(String url) {
		boolean isValid = false;
		try {
			URL u = new URL(url);
			HttpURLConnection h = (HttpURLConnection) u.openConnection();
			h.setRequestMethod("GET");
			h.connect();
			if (h.getResponseCode() == 200) {
				isValid = true;
			} else {
				logMessage("link : " + url + " is broken.\n");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return isValid;
	}

	public void veriyfFooterText() {
		isElementDisplayed("txt_footer");
		if (!element("txt_footer").getText().contains("App version")) {
			Assert.fail("Not Displaying Footer Text !!");
		}
		msg.pass("Successfully Displaying Footer Text !!");
	}

	public void clickOnHRISLogo() {
		isElementDisplayed("img_hrisLogo");
		clickUsingXpathInJavaScriptExecutor(element("img_hrisLogo"));
		msg.log("Click on HRIS logo");
	}

	public void verifyReportingPageLoadsCorrectly() {
		String reporting_heading = element("reporting_heading").getText();
		Assert.assertTrue(reporting_heading.contains("Reporting"),
				"[ASSERT FAILED]: Reporting page did not open correctly");
		logMessage("[ASSERT PASSED]: Reporting tab opened correctly");
	}

	public void verifyManageRankButtonISPresent() {
		Assert.assertTrue(
				element("manage_rank_btn").getText().trim()
						.equals("Manage Rank"),
				"[ASSERT FAILED]: Manage Rank button is not present on reporting page");
		logMessage("[ASSERT PASSED]: Manage Rank button is present on reporting page");
	}

	public void verifySearchInputTextIsPresent() {
		Assert.assertTrue(
				element("search_text").getText().contains("Search")
						&& element("search_text_field").getAttribute("type")
								.equals("text"),
				"[ASSERT FAILED]: Search input box is not present in reporting page");
		logMessage("[ASSERT FAILED]: Search input box is present in reporting page");

	}

	public void verifyListOfEmpIsPresentWithReportingDetails() {
		List<WebElement> employees_name = elements("emp_name_list");
		List<WebElement> emp_report_to = elements("report_to_list");
		int count1 = 0;
		int count = 0;
		if (employees_name.size() > 0) {
			for (WebElement e : employees_name) {
				if (e.getText() != null) {
					count++;
				}
			}
			for (WebElement e : emp_report_to) {
				if (e.getAttribute("value").equals("")) {
					System.out.println("Empty reporting details");
				} else {
					++count1;
				}

			}

			Assert.assertTrue(count == employees_name.size() && count1 != 0,
					"[ASSERT FAILED]: Employees list and details not present in table");
			logMessage("[ASSERT PASSED]: Employees list and detail is present in table");
		}

	}

	public void verifyUserIsAbleToCLickManageRankButton()
			throws InterruptedException {

		Thread.sleep(2000);
		// ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",element("manage_rank_btn"));

		// ((JavascriptExecutor)
		// driver).executeScript("window.scrollBy(0, -250)", "");
		clickUsingXpathInJavaScriptExecutor(element("manage_rank_btn"));

	}

	public void verifyRankWeightageCannotBeNegative() {

		clickUsingXpathInJavaScriptExecutor(elements("rank_weightage").get(0));

		elements("rank_weightage").get(0).clear();
		elements("rank_weightage").get(0).sendKeys("-1");
		clickUsingXpathInJavaScriptExecutor(elements("save_btn").get(0));
		String alert_text = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertTrue(
				alert_text.trim().contains(
						"Rank weightage must be greater than zero"),
				"[ASSERT FAILED]: User is able to enter negative value");
		logMessage("[ASSERT PASSED]: User is not able to enter negative value");

	}

	public void verifyRankWeghtageCannotBeZero() {
		clickUsingXpathInJavaScriptExecutor(element("new_rank_weightage"));
		element("new_rank_weightage").sendKeys("0");
		clickUsingXpathInJavaScriptExecutor(element("rank_name_field"));
		element("rank_name_field").sendKeys("Sample");
		clickUsingXpathInJavaScriptExecutor(element("save_btn_for_zero"));
		String alert_txt = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertTrue(
				alert_txt.trim().contains(
						"Rank weightage must be greater than zero"),
				"[ASSERT FAILED]: User is able to enter zero as rank weightage");
		logMessage("[ASSERT PASSED]: User is not able to enter zero as rank weightage");

	}

	public void verifyRankWeghtageCannotBeRepeated() {
		clickUsingXpathInJavaScriptExecutor(element("new_rank_weightage"));
		element("new_rank_weightage").clear();
		element("new_rank_weightage").sendKeys("1");
		clickUsingXpathInJavaScriptExecutor(element("rank_name_field"));
		element("rank_name_field").clear();
		element("rank_name_field").sendKeys("Sample");
		clickUsingXpathInJavaScriptExecutor(element("save_btn_for_zero"));
		String alert_txt = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertTrue(
				alert_txt.trim().contains(
						"Rank with assigned weightage already existed"),
				"[ASSERT FAILED]: User is able to enter repeated rank weightage");
		logMessage("[ASSERT PASSED]: User is not able to enter repeated rank weightage");

	}

	public void verifyNewRankCanBeAdded() throws InterruptedException {
		boolean flag1 = false;
		boolean flag2 = false;
		String new_rank = "10";
		String new_rank_name = "Test Rank";
		clickUsingXpathInJavaScriptExecutor(element("new_rank_weightage"));
		element("new_rank_weightage").clear();
		element("new_rank_weightage").sendKeys(new_rank);
		clickUsingXpathInJavaScriptExecutor(element("rank_name_field"));
		element("rank_name_field").clear();
		element("rank_name_field").sendKeys(new_rank_name);
		clickUsingXpathInJavaScriptExecutor(element("save_btn_for_zero"));
		driver.navigate().refresh();
		clickUsingXpathInJavaScriptExecutor(element("manage_rank_btn"));

		Thread.sleep(4000);
		List<WebElement> ranks = elements("ranks_list");
		List<WebElement> ranks_name = elements("ranks_name_list");
		for (WebElement e : ranks) {
			if (e.getAttribute("value").contains(new_rank)) {
				flag1 = true;
				break;
			} else
				flag1 = false;
		}
		for (WebElement e : ranks_name) {
			if (e.getAttribute("value").contains(new_rank_name)) {
				flag2 = true;
				break;
			} else
				flag2 = false;
		}
		Assert.assertTrue(flag1 && flag2,
				"[ASSERT FAILED]: new rank name based on rank weightage not saved");
		logMessage("[ASSERT PASSED]: new rank name based on rank weightage saved successfully");
	}

	public void verifySaveButtonWorksProperly() {
		String edited_rank = "15";
		elements("rank_weightage").get(0).clear();
		elements("rank_weightage").get(0).sendKeys(edited_rank);
		clickUsingXpathInJavaScriptExecutor(element("save_btn"));
		Assert.assertTrue(element("rank_weightage").getAttribute("value")
				.equals(edited_rank),
				"[ASSERT FAILED]: Save functionality not working");
		logMessage("[ASSERT PASSED]: Save functionality working properly");
	}

	public void verifyDeleteButtonWorksProperly() {
		int count = 0;
		String rank = elements("rank_weightage").get(0).getAttribute("value");
		clickUsingXpathInJavaScriptExecutor(elements("delete_rank").get(0));
		driver.switchTo().alert().accept();
		clickUsingXpathInJavaScriptExecutor(element("manage_rank_btn"));

		List<WebElement> ranks = elements("ranks_list");

		for (WebElement e : ranks) {
			if (e.getAttribute("value").contains(rank)) {
				count++;
			}
		}
		Assert.assertFalse((count > 0),
				"[ASSERT FAILED]: Delete button is not working properly");
		logMessage("[ASSERT PASSED]: Delete button Working properly");
	}

	public void enterTextInSearchBar(String search_item) {
		element("search_text_field").sendKeys(search_item);

		hardWait(3);

	}

	public void verifySearchResults(String empId) {
		int count = 0;
		List<WebElement> search_results = elements("search_results");
		System.out.println("^^^^^^^^^^^^^^^^^^" + search_results.size());
		for (WebElement e : search_results) {

			System.out.println("+++" + e.getText());
			if (e.getText().contains(empId)) {

				count++;
			}
		}
		Assert.assertTrue(count == search_results.size(),
				"[ASSERT FAILED]: Search results are incorrect");
		logMessage("[ASSERT PASSED]: Search results are correct");
	}

	public void verifyEditInputValueSavedOrNot(String editableName) {
		switchToDefaultContent();
		element("input_editName").clear();
		element("input_editName").sendKeys("Minesh");
		element("select_dropDown", editableName).click();
		wait.hardWait(3);
		System.out
				.println("$$$$$$$$$$$$" + element("input_editName").getText());
		Assert.assertEquals(element("input_editName").getText(), editableName,
				"editable name not saved");
		msg.pass("editable value persist!!!!");
	}

	public void enterEmployeeValidEmployeeInSearchOnTop(String emp_name) {
		clickUsingXpathInJavaScriptExecutor(element("search_on_top"));
		element("search_on_top").sendKeys(emp_name);

	}

	public void selectEmployeeFromSearchResults() {
		Actions act = new Actions(driver);
		act.moveToElement(element("desired_search_result")).click().build()
				.perform();

	}

	public void verifyBillingCalenderOfSearchEmployeeIsDisplayed(
			String expected_emp_name) throws InterruptedException {
		boolean flag = false;
		Thread.sleep(3000);
		String actual_emp_name = element("emp_name_bill_cal").getText();
		System.out.println("actual name:**" + actual_emp_name);
		if (element("billing_calender").isDisplayed()) {
			if (actual_emp_name.contains(expected_emp_name)) {
				flag = true;
			}
		}
		Assert.assertTrue(flag,
				"[ASSERT FAILED]: Billing calender of correct employee is not displayed");
		logMessage("[ASSERT PASSED]: Billing calender of correct employee is displayed");
	}

	public void closeBillingCalender() {
		element("close_billing_cal").click();
	}

	public void verifyCorrectOptionsAreDisplayedOnExpandingSettings()
			throws InterruptedException {
		hardWait(3);
		Actions act = new Actions(driver);
		act.moveToElement(element("expand_settings")).click().build().perform();
		int count = 0;
		Thread.sleep(3000);
		List<WebElement> submenu = elements("settings_submenu");
		Thread.sleep(2000);
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list2.add("QUESTIONS");
		list2.add("QUESTIONNAIRE");
		list2.add("QUESTIONNAIRE REPORT");
		list2.add("REPORT");
		for (WebElement e : submenu) {
			System.out.println("***" + e.getText());
			list1.add(e.getText());
		}
		System.out.println("size" + list1.size());
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).contains(list2.get(i))) {
				count++;
			}

		}
		Assert.assertTrue(count == list1.size(),
				"[ASSERT FAILED]: Incorrect Submenu is displayed on hovering Settings and tags");
		logMessage("[ASSERT PASSED]: Correct submenu is displayed on hovering settings and tags");
	}

	public void verifyCorrectPAgeLoadedOnClickingQuestionsOption() {
		hoverToReportThenToDesiredOption("questions");

		verifyPageHeading("Questions");
		hardWait(3);

	}

	public void verifyCorrectPAgeLoadedOnClickingQuestionnaireOption() {
		hoverToReportThenToDesiredOption("feedback_set");
		verifyPageHeading("Questionnaire");
		hardWait(3);
	}

	public void verifyCorrectPAgeLoadedOnClickingQuestionnaireReportOption() {
		// hoverToReportThenToDesiredOption("report");
		Actions act = new Actions(driver);
		act.moveToElement(element("expand_settings")).click().build().perform();
		act.moveToElement(element("questionnaire_report_option")).click()
				.build().perform();

		verifyPageHeading("Feedback Report");
		hardWait(3);
	}

	public void verifyCorrectPageLoadedOnClickingReportOption() {
		hoverToReportThenToDesiredOption("reportchart");
		verifyPageHeading("Feedback Report");
		hardWait(3);
	}

	public void verifyPageHeading(String exp_heading) {
		String page_heading = element("submenu_item_page_heading").getText();
		System.out.println("***1"
				+ element("submenu_item_page_heading").getText());
		System.out.println("***2" + exp_heading);
		Assert.assertTrue(page_heading.contains(exp_heading),
				"[ASSERT FAILED]: Navigated to Incorrect Page");
		logMessage("[ASSERT PASSED]: Navigated to Correct Page");
	}

	public void hoverToReportThenToDesiredOption(String option) {
		// element("slideout").click();
		Actions act = new Actions(driver);
		act.moveToElement(element("expand_settings")).click().build().perform();
		act.moveToElement(element("settings_submenu_items", option)).click()
				.build().perform();
	}

	public void clickSettingsAndTags() {
		Actions act = new Actions(driver);
		act.moveToElement(element("settings_and_tags")).click().build()
				.perform();

	}

	public void verifyCorrectSubModulesAreDisplyed() {
		List<WebElement> sub_modules = elements("settings_submodules");
		int count = 0;
		for (WebElement e : sub_modules) {
			if (e.getText().contains("Tags") || e.getText().contains("Freeze")
					|| e.getText().contains("Division/Category Permission")) {
				count++;
			}
		}
		Assert.assertTrue(count == sub_modules.size(),
				"[ASSERT FAILED]: Incorrect sub-modules are displayed");
		logMessage("[ASSERT PASSED]: Correct sub-modules are displayed on clicking settings and tags");
	}

	public void clickOnSlideout() {
		isElementDisplayed("slideout");
		element("slideout").click();
		msg.log("Click on slideout");
	}

	public void navigateToReportingTab() {
		wait.hardWait(2);
		switchToDefaultContent();

		element("reporting_tab").click();
		msg.log("Navigated to repoting tab");
		wait.hardWait(3);

	}

	public void closeSlideout() {

		element("slideout_prev").click();
	}

	public void verifyDefaultDateInRMS() {
		String date;
		isElementDisplayed("txt_defaultDate");
		date = DateUtil.getCurrentdateInStringWithGivenFormate("dd")
				+ dateUtil.getDayOfMonthSuffix(0)
				+ DateUtil
						.getCurrentdateInStringWithGivenFormate(", MMMM yyyy");
		if (!element("txt_defaultDate").getText().contains(date)) {
			Assert.fail("Expected that date contains " + date
					+ " but date was " + element("txt_defaultDate").getText());
		}
		msg.pass("Verified default date displays correctly !!");

	}

	public void clickOnGivenCategoryEmployeeLink(String category) {
		clickUsingXpathInJavaScriptExecutor(element("link_employeesCategory",
				category));
		wait.hardWait(2);
		msg.log("Click on " + category + " link !!");
	}

	public void verifyTitleForGivenCategoryEmployee(String category) {
		isElementDisplayed("txt_categoryName", category);
		msg.pass("Verified title for given category employee");
	}

	public String getEmployeeInfo(String infoType) {
		if (infoType.toLowerCase().equals("id"))
			return element("txt_employeeInfoName", "1").getText();
		else if (infoType.toLowerCase().equals("name"))
			return element("txt_employeeInfoName", "2").getText();
		else if (infoType.toLowerCase().equals("yoe"))
			return element("txt_employeeInfoName", "3").getText();
		else if (infoType.toLowerCase().equals("division"))
			return element("txt_employeeInfoName", "4").getText();
		else
			Assert.fail("invalid info type !!");
		return "";
	}

	public void clickOnGivenEmployee(String empName) {
		isElementDisplayed("link_employeesCategory", empName);
		element("link_employeesCategory", empName).click();
		msg.log("Click on given employee name to verify information !!");
	}

	public void clickOnCloseButtonOfPopUp() {
		isElementDisplayed("btn_close");
		element("btn_close").click();
		msg.log("Click on close button !!");
	}

	public void clickingOnLeftOrRightButtonOFCalender(String direction) {
		isElementDisplayed("link_calender", direction);
		executeJavascript("document.getElementsByClassName('dp-nav dp-nav-"
				+ direction + "')[0].click();");
		// element("link_calender",direction).click();
		msg.log("clicking on " + direction + " button of direction tab !!");
	}

	public void verifyClickingOnLeftOrRightButtonCalenderShowsCorrectDate(
			String direction) {
		String date;
		if (direction.equals("left")) {
			date = DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
					"dd", "day", 1)
					+ dateUtil.getDayOfMonthSuffix(-1)
					+ DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
							", MMMM yyyy", "day", 1);
			if (!element("txt_defaultDate").getText().contains(date)) {
				Assert.fail("Expected that date contains " + date
						+ " but date was "
						+ element("txt_defaultDate").getText());
			}
			msg.pass("Verified that date displays correctly !!");
		} else if (direction.equals("right")) {
			date = DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
					"dd", "day", -1)
					+ dateUtil.getDayOfMonthSuffix(1)
					+ DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
							", MMMM yyyy", "day", -1);
			if (!element("txt_defaultDate").getText().contains(date)) {
				Assert.fail("Expected that date contains " + date
						+ " but date was "
						+ element("txt_defaultDate").getText());
			}
			msg.pass("Verified that date displays correctly !!");
		} else
			Assert.fail("Given option is " + direction
					+ " that is not valid !!");
	}

	public void VerifyUserIsAbleToEnterESSUser(String ESS_username) {
		element("btn_category_permission").click();
		permitted_division = element("division_name").getText();
		element("input_division_software").sendKeys(ESS_username);
		hardWait(2);
		boolean flag = false;
		System.out.println("sfsaf" + ESS_username);
		Actions act = new Actions(driver);
		act.moveToElement(element("dropdwon_ESS_option")).click().build()
				.perform();
		List<WebElement> list_emp_given_permission = elements("list_user_given_permission");
		for (WebElement e : list_emp_given_permission) {
			System.out.println("user:::" + e.getText());
			if (e.getText().contains(ESS_username)) {

				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag,
				"[ASSERT FAILED]: ESS user did not get permission");
		logMessage("[ASSERT PASSED]: ESS user is successfully given permission");

	}

	public void LogoutCurrentUser() {
		hardWait(3);
		element("btn_profile").click();
		Actions action = new Actions(driver);
		action.moveToElement(element("profile_back_btn")).click().build()
				.perform();
		hardWait(2);
		element("logout_btn").click();
	}

	public void verifyGivenPermissionReflectedForESSUser() {

		boolean flag = false;
		Actions action = new Actions(driver);
		hardWait(2);
		System.out.println("----" + element("btn_dashboard").getText());
		action.moveToElement(element("btn_dashboard")).click().build()
				.perform();
		element("close_billing_cal").click();
		List<WebElement> divisions = elements("division_name_list");
		for (WebElement e : divisions) {
			if (e.getText().contains(permitted_division)) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag,
				"[ASSERT FAILED]: Given Permission not reflected for ESS User");
		logMessage("[ASSERT PASSED]: Permission is reflected for ESS User");
	}

	public void VerifyPermittedESSUserGetsDeleted(String username) {
		element("btn_category_permission").click();
		hardWait(3);
		clickUsingXpathInJavaScriptExecutor(element("x_last_user"));
		hardWait(2);
		boolean flag = false;
		driver.switchTo().alert().accept();

	}

	public void clickOnDashBoard() {
		wait.hardWait(3);
		executeJavascript("document.getElementById('dashboard').click();");
		wait.hardWait(3);
		msg.log("Click on dashboard !!");
	}

	public void verifyUserIsAbleToClickSupportLink() {
		String support_title;
		clickUsingXpathInJavaScriptExecutor(element("link_support"));
		support_title = element("support_page_title").getText();
		Assert.assertTrue(support_title.contains("Support Employee"),
				"[ASSERT FAILED]: Support link did not open correct page");
		logMessage("[ASSERT PASSED]: Support link opened correct page");
	}

	public void verifyCorrectDetailsAreShownOnClickingSupportLink() {
		List<WebElement> columns = elements("support_table_columns");
		int count = 0;
		for (WebElement e : columns) {
			if (e.getText().contains("Emp Name")
					|| e.getText().contains("Actual Billable")) {
				count++;
			}
		}
		Assert.assertTrue(
				count == 2,
				"[ASSERT FAILED]: Employee Name or Actual Billable employee list is not present on support page");
		logMessage("[ASSERT PASSED]: Employee Name or Actual Billable employee list is present on support page");
	}

	public void verifyNamesPresentInEmpNameListNavigatesToEmpBillingCalender() {
		Calendar cal = Calendar.getInstance();
		String year;
		String current_month;
		String current_month_year;
		String actual_billing_month;
		String emp_name = element("first_emp_name_support").getText();
		element("first_emp_name_support").click();
		hardWait(3);
		boolean flag = false;

		if (element("first_emp_name_billing_cal").getText().contains(emp_name)) {
			flag = true;
		}

		current_month = new SimpleDateFormat("MMM").format(cal.getTime());
		year = Integer.toString(cal.get(Calendar.YEAR));
		current_month_year = current_month + " " + year;
		actual_billing_month = element("billing_month").getText();
		Assert.assertTrue(
				flag
						&& element("billing_calender").isDisplayed()
						&& actual_billing_month
								.equalsIgnoreCase(current_month_year),
				"[ASSERT FAILED]: Billing calender did not open on clicking name on support page");
		logMessage("[ASSERT PASSED]: Billing calender opened on clicking name on support page");
	}

	public void verifyBillableEmpPresentInEmpNameListNavigatesToEmpBillingCalender() {
		Calendar cal = Calendar.getInstance();
		String year;
		String current_month;
		String current_month_year;
		String actual_billing_month;
		String emp_name = element("first_actual_billable_emp").getText();
		element("first_actual_billable_emp").click();
		hardWait(3);
		boolean flag = false;

		if (element("first_emp_name_billing_cal").getText().contains(emp_name)) {
			flag = true;
		}
		current_month = new SimpleDateFormat("MMM").format(cal.getTime());
		year = Integer.toString(cal.get(Calendar.YEAR));
		current_month_year = current_month + " " + year;
		actual_billing_month = element("billing_month").getText();
		Assert.assertTrue(
				flag
						&& element("billing_calender").isDisplayed()
						&& actual_billing_month
								.equalsIgnoreCase(current_month_year),
				"[ASSERT FAILED]: Billing calender did not open on clicking name on support page");
		logMessage("[ASSERT FAILED]: Billing calender opened on clicking name on support page");
	}

	public void verifyOnlyOneEngineerMarkedAsShadow(String projectCountFromTop) {
		switchToDefaultContent();
		element("button_updateBilling", projectCountFromTop, "7").click();

	}

	public void verifyUserIsAbleToNavigateToMyProjects() {
		hardWait(2);
		Actions action = new Actions(driver);
		action.moveToElement(element("myprojects_tab")).click().build()
				.perform();
		hardWait(3);
	}

	public void verifyUserCannotMarkEmployeeShadowForMoreThanTenDays() {

		Calendar now = Calendar.getInstance();
		int current_month = now.get(Calendar.MONTH) + 2;
		// int current_date=now.get(Calendar.DATE);
		String current_year = Integer.toString(now.get(Calendar.YEAR));
		String date = "";
		/*
		 * System.out.println("Current Year is : " + now.get(Calendar.YEAR)); //
		 * month start from 0 to 11 System.out.println("Current Month is : " +
		 * (now.get(Calendar.MONTH) + 1));
		 * System.out.println("Current Date is : " + now.get(Calendar.DATE));
		 */
		// 2016-12-01

		String alert_title;
		String alert_body;
		clickUsingXpathInJavaScriptExecutor(element("myproject_first_dollar"));
		element("toggle_Uncheck_all").click();
		element("billing_next_month_btn").click();
		element("checkbox_third_emp").click();
		hardWait(2);
		/*
		 * SelectDate: for(int i=1;i<=11;i++) { switchToDefaultContent();
		 * 
		 * if(i<10){
		 * 
		 * date=current_year+"-"+Integer.toString(current_month)+"-0"+Integer.
		 * toString(i); System.out.println("date is:"+date);
		 * System.out.println(element("select_date_billing_cal",date));
		 * clickUsingXpathInJavaScriptExecutor
		 * (element("select_date_billing_cal",date));
		 * element("shadow_hours").click();
		 * element("shadow_hours").sendKeys("8"); hardWait(1);
		 * element("billing_comment").click();
		 * element("billing_comment").sendKeys("shadow");
		 * clickUsingXpathInJavaScriptExecutor(element("shadow_save_btn"));
		 * hardWait(3); } else if(i==10){
		 * date=current_year+"-"+Integer.toString(
		 * current_month)+"-"+Integer.toString(i);
		 * System.out.println("date is:"+date);
		 * System.out.println(element("select_date_billing_cal",date));
		 * clickUsingXpathInJavaScriptExecutor
		 * (element("select_date_billing_cal",date));
		 * element("shadow_hours").click();
		 * element("shadow_hours").sendKeys("8"); hardWait(1);
		 * element("billing_comment").click();
		 * element("billing_comment").sendKeys("shadow");
		 * clickUsingXpathInJavaScriptExecutor(element("shadow_save_btn")); }
		 * else {
		 * date=current_year+"-"+Integer.toString(current_month)+"-"+Integer
		 * .toString(i);
		 * clickUsingXpathInJavaScriptExecutor(element("select_date_billing_cal"
		 * ,date)); element("shadow_hours").click();
		 * element("shadow_hours").sendKeys("8"); hardWait(1);
		 * element("billing_comment").click();
		 * element("billing_comment").sendKeys("shadow");
		 * clickUsingXpathInJavaScriptExecutor(element("shadow_save_btn"));
		 * break SelectDate; }
		 * 
		 * }
		 */
		String from_date = current_year + "-" + Integer.toString(current_month)
				+ "-05";
		String to_date = current_year + "-" + Integer.toString(current_month)
				+ "-18";
		Actions builder = new Actions(driver);

		builder.clickAndHold(element("select_date_billing_cal", from_date))
				.moveToElement(element("select_date_billing_cal", to_date))
				.release(element("select_date_billing_cal", to_date)).build()
				.perform();
		;
		hardWait(2);
		element("shadow_hours_new").click();
		element("shadow_hours_new").sendKeys("8");
		hardWait(1);
		element("billing_comment").click();
		element("billing_comment").sendKeys("shadow");
		clickUsingXpathInJavaScriptExecutor(element("shadow_save_btn"));
		alert_title = element("alert_heading").getText();
		alert_body = element("alert_body").getText();
		Assert.assertTrue(
				alert_title
						.contains("Alert : You are trying to voilates below listed rules:")
						&& alert_body
								.contains("The engineer(s) has been exausted the shadow day limit on this project:"),
				"[ASSERT FAILED]: Error message did not appear on marking employee shadow for more than 10 days");
		logMessage("[ASSERT FAILED]: Error message appeared on marking employee shadow for more than 10 days");

	}

	public void deleteShadowMarked() {
		Calendar now = Calendar.getInstance();
		String current_year = Integer.toString(now.get(Calendar.YEAR));
		int current_month = now.get(Calendar.MONTH) + 2;
		element("close_error_btn").click();
		String from_date = current_year + "-" + Integer.toString(current_month)
				+ "-05";
		String to_date = current_year + "-" + Integer.toString(current_month)
				+ "-18";
		Actions builder = new Actions(driver);

		builder.clickAndHold(element("select_date_billing_cal", from_date))
				.moveToElement(element("select_date_billing_cal", to_date))
				.release(element("select_date_billing_cal", to_date)).build()
				.perform();
		;
		hardWait(2);
		clickUsingXpathInJavaScriptExecutor(element("delete_shadows"));

	}

	public void verifyLinksAreWorkingInsideEnggBox() {
		int count = 0;
		String link_heading;
		List<WebElement> list_links = elements("links_inside_engg_box_list");
		for (WebElement e : list_links) {
			switchToDefaultContent();

			link_heading = e.getText();
			System.out.println("before" + link_heading);
			clickUsingXpathInJavaScriptExecutor(e);
			System.out.println("after"
					+ element("links_heading_after_clicking").getText());
			if (element("links_heading_after_clicking").getText().contains(
					link_heading)) {
				count++;
			}
			System.out.println("count" + count);
			hardWait(2);
			clickUsingXpathInJavaScriptExecutor(element("btn_close_link"));
			/*
			 * JavascriptExecutor jse = (JavascriptExecutor) driver;
			 * jse.executeScript("window.scrollBy(0,-450)", "");
			 */
			// driver.navigate().refresh();

		}
		Assert.assertTrue(count == list_links.size(),
				"[ASSERT FAILED]: links inside engineers box are not working");
		logMessage("[ASSERT PASSED]: links inside engineers box are working");
	}

	public void verifyEmployeesHeadingLinkIsWorking() {
		String emp_link_heading;
		clickUsingXpathInJavaScriptExecutor(element("heading_engg_box"));
		System.out.println(element("links_heading_after_clicking").getText());
		emp_link_heading = element("links_heading_after_clicking").getText();
		hardWait(2);
		clickUsingXpathInJavaScriptExecutor(element("btn_close_link"));
		Assert.assertTrue(emp_link_heading.contains("All Employee List"),
				"[ASSERT FAILED]: Engineers link is not working");
		logMessage("[ASSERT PASSED]: Engineers link is working properly");
	}

	public void verifyDivisionsLinkNavigateToCorrespondingPage() {
		String title_before;
		String title_after;
		int count = 0;
		List<WebElement> heading_link_list = elements("all_division_heading");
		for (WebElement e : heading_link_list) {
			switchToDefaultContent();
			title_before = e.getText();
			System.out.println("before" + title_before);
			clickUsingXpathInJavaScriptExecutor(e);
			title_after = element("links_heading_after_clicking").getText();
			System.out.println("after*" + title_after);
			if (title_after.contains(title_before)) {
				count++;
			}
			verifyNamesPresentInEmpNameListNavigatesToEmpBillingCalender();
			hardWait(2);
			clickUsingXpathInJavaScriptExecutor(element("close_billing_cal"));

		}

	}

	public void clickOnMonthlyButton() {
		// element("monthly_btn").click();
		clickUsingXpathInJavaScriptExecutor(element("monthly_btn"));
		msg.log("Clicked on monthly button !!");
	}

	public void verifyCorrectSubModulesAreDisplyedInMonthly() {
		List<WebElement> submodules = elements("monthly_submodules");
		int count = 0;
		for (WebElement e : submodules) {
			if (e.getText().contains("Project Dashboard")
					|| e.getText().contains("Projects")
					|| e.getText().contains("Invoices Data")
					|| e.getText().contains("Outstanding Invoices")
					|| e.getText().contains("Shadow Engineers")
					|| e.getText().contains("Joined/Yet to Join")) {
				count++;
			}
		}
		Assert.assertTrue(count == submodules.size(),
				"[ASSERT FAILED]: Correct Submodules not displayed on clicking monthly tab");
		logMessage("[ASSERT PASSED]: Correct Submodules are displayed on clicking monthly tab");

	}

	public void verifyCorrectDetailsDisplayedOnClickingProjectBox() {
		String project_heading = element("monthly_first_project").getText();
		clickUsingXpathInJavaScriptExecutor(element("monthly_first_project"));
		String project_heading_modal = element("project_title_monthly")
				.getText();
		// verifyNamesPresentInEmpNameListNavigatesToEmpBillingCalender();
		String emp_name = element("first_emp_name_support").getText();
		System.out.println("565" + emp_name);
		element("first_emp_name_support").click();
		hardWait(3);
		boolean flag = false;

		Select sel = new Select(driver.findElement(By
				.xpath("//div[@id='projectModal']//select[@id='empOption']")));
		WebElement el = sel.getFirstSelectedOption();
		System.out.println("text is :" + el.getText());
		if (emp_name.contains(el.getText())) {
			flag = true;
		}
		System.out.println(flag);
		hardWait(2);
		clickUsingXpathInJavaScriptExecutor(element("close_billing_cal_monthly"));
		Assert.assertTrue(
				flag && project_heading.equalsIgnoreCase(project_heading_modal),
				"[ASSERT FAILED]: Incorrect details are diplayed on clicking project heading");
		logMessage("[ASSERT PASSED]: Correct details displayed on clicking project heading");

	}

	public void clickOnProjects() {
		// WebElement element = element("monthly_projects_submenu");
		/*
		 * ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].scrollIntoView(true);", element);
		 * ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].scrollIntoView(true);", element);
		 */

		// element("monthly_projects_submenu").click();
		clickUsingXpathInJavaScriptExecutor(element("monthly_projects_submenu"));
	}

	public void verifySearchButtonWorksFineInsideProjects(String search_project) {
		int count = 0;
		element("search_monthly_projects_submenu").sendKeys(search_project);
		List<WebElement> projects_list = elements("search_results_projects");
		for (WebElement e : projects_list) {
			if (e.getText().contains(search_project)) {
				count++;
			}
		}
		Assert.assertTrue(count == projects_list.size(),
				"[ASSERT FAILED]: Search functionality is not working as intended");
		logMessage("[ASSERT PASSED]: Search functionality is working as intended");
	}

	public void verifySearchWorksFineAccordingProjectId(String search_id) {
		boolean flag = false;
		element("search_monthly_projects_submenu").clear();
		element("search_monthly_projects_submenu").sendKeys(search_id);
		String search_result_id = element("search_results_id").getText();
		if (search_result_id.contains(search_id)) {
			flag = true;
		}
		Assert.assertTrue(flag,
				"[ASSERT FAILED]: Search functionality is not working as intended");
		logMessage("[ASSERT PASSED]: Search functionality is working as intended");
	}

	public void verifySearchWorksFineAccordingManagerName(String search_manager) {

		int count = 0;
		element("search_monthly_projects_submenu").clear();
		element("search_monthly_projects_submenu").sendKeys(search_manager);
		List<WebElement> projects_list = elements("search_results_manager");
		for (WebElement e : projects_list) {
			if (e.getText().contains(search_manager)) {
				count++;
			}
		}
		Assert.assertTrue(count == projects_list.size(),
				"[ASSERT FAILED]: Search functionality is not working as intended");
		logMessage("[ASSERT PASSED]: Search functionality is working as intended");
	}

	public void verifySearchTextInMonthlyTabOfProjectDashboard(String empName,String empId)
	{
		switchToDefaultContent();
		element("genericSearchById", "searchEmp").sendKeys(empName);
		element("select_dropDown", empId).click();
		element("lnk_firstProject").click();
		element("txt_searchEmployee").sendKeys(empName);
		wait.hardWait(2);
		Assert.assertEquals(element("txt_getIdOfEmployee",empName).getText(),empId);
		msg.pass("Verified search field !!");
		clickUsingXpathInJavaScriptExecutor(element("close_billing_cal_monthly"));
		wait.hardWait(3);
	}
	
	public void verifyProjectsLinkInMonthlyTabOfProjectDashboard()
	{
		String empName , empId;
		clickUsingXpathInJavaScriptExecutor(element("genericSearchById", "persFact"));
		empName=element("txt_projectDetails","3").getText();
		empId=element("txt_projectDetails","1").getText();
		clickUsingXpathInJavaScriptExecutor(element("txt_projectDetails","3"));
		element("txt_searchEmployee").sendKeys(empName);
		wait.hardWait(2);
		Assert.assertEquals(element("txt_getIdOfEmployee",empName).getText(),empId);
		msg.pass("Verified search field !!");
		clickUsingXpathInJavaScriptExecutor(element("close_billing_cal_monthly"));
		msg.log("closed open pop up !!");
	}
	
	public void clickOnLinkHavingText(String text)
	{
		clickUsingXpathInJavaScriptExecutor(element("lnk_dashboardMenu",text));
		msg.log("Click on "+text+" link");
		scrollToDefaultSize();
	}
	
	public void verifyInvoiceDataInfo(String index)
	{
		String invoiceDataValue=element("lnk_invoiceDataInfo",index).getText();
		int valueFromTable=0;
		System.out.println("invoice value = "+invoiceDataValue);
		if(!invoiceDataValue.equals("0"))
		{
		element("lnk_invoiceDataInfo",index).click();
		msg.log("Click on invoice data value");
		wait.hardWait(3);
		List<WebElement> list= elements("list_RMSAddlHours","7");
		
		for(WebElement ele: list)
		{
			msg.log("value = "+ele.getText());
			valueFromTable+=Integer.parseInt(ele.getText());
		}
		clickUsingXpathInJavaScriptExecutor(element("close_billing_cal_monthly"));
		if(valueFromTable!=Integer.parseInt(invoiceDataValue))
			Assert.fail("Expected value was "+invoiceDataValue+" but found "+valueFromTable);
		}
		msg.pass("Verified value !!");
	}
	
	public void verifyOutstandingInvoiceData(String index)
	{
		String data;
		int dataFromTable = 0;
		isElementDisplayed("lnk_outstandingInvoiceDataSummary",index);
		data=element("lnk_outstandingInvoiceDataSummary",index).getText();
		System.out.println("text = "+data);
		if(!data.equals("0"))
		{
			clickUsingXpathInJavaScriptExecutor(element("lnk_outstandingInvoiceDataSummary",index));
			msg.log("click on invoice data summary");
			wait.hardWait(2);
			List<WebElement> list= elements("list_RMSAddlHours","6");
			for(WebElement ele: list)
			{
				msg.log("value = "+ele.getText());
				dataFromTable+=Integer.parseInt(ele.getText());
			}
			clickUsingXpathInJavaScriptExecutor(element("close_billing_cal_monthly"));
			if(dataFromTable!=Integer.parseInt(data))
				Assert.fail("Expected value was "+data+" but found "+dataFromTable);
		}
			msg.pass("Verified value !!");
   }

	public void verifyJoined_YetToJoinData()
	{
		System.out.println("###########################");
		for(WebElement ele : elements("txt_joinDepartment","Dev"))
		{
			String value;
			int count;
			System.out.println(ele.getText());
			value=ele.getText().split(":")[0];
			count=Integer.parseInt(ele.getText().split(":")[1].substring(1));
			System.out.println("value = "+value);
			System.out.println("count = "+count);
		}
		
   }
	
}
