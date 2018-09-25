package com.qait.keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PIM_Page extends GetPage {

	WebDriver driver;
	String pagename = "PIM_Page";

	public PIM_Page(WebDriver driver) {
		super(driver, "PIM_Page");
		this.driver = driver;
	}

	public void clickAddButton() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("actionlink", "Add").click();
		msg.log("Click on add button for add a new employee !!");
	}

	public void searchForRecordWithEmpId() {
		switchToFrame(element("iframe"));
		selectDropDownValueByValue(element("searchbydrpdwn"), "Emp. ID");
	}

	public void enterValueForSearch(String value) {
		element("searchfortextbox").sendKeys(value);
		msg.log("Search Value: " + value);
	}

	public void clickSearchButton() {
		element("actionlink", "Search").click();
		msg.log("click on search button");
	}

	public void clickOnSearchResult() {
		element("searchresultlink").click();
		msg.log("search result");
		//acceptAlert();
	}

	public void clickOnJobTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("jobsubtag").click();
		msg.log("Navigated to Job Tab");
	}

	public void clickOnReportToTab() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		executeJavascript("document.getElementById('report-toLink').click()");
		msg.log("Navigated to Report To Tab");
	}

	public void enterReportingDetails() {
		wait.hardWait(2);
		selectProvidedTextFromDropDown(element("slctSprvsrSbrdnte"),
				"Supervisor");
		selectEmployee("1045");
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		selectProvidedTextFromDropDown(element("slctreportingmthd"), "Direct");
		element("saveBtn").click();
	}

	public void selectEmployee(String empId) {
		String parent = driver.getWindowHandle();
		element("slctemployeebtn").click();
		switchWindow();
		msg.log("Title of page = " + driver.getTitle());
		selectDropDownValueByValue(element("searchdrpdwn"), "ID");
		element("serachtextbox").sendKeys(empId);
		element("searchButton").click();
		element("searchrsltlink").click();
		switchToOtherWindow(parent);
	}

	public void clickOnPersonalTab() {
		wait.hardWait(2);
		acceptAlert();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("personalsubtag").click();
		msg.log("Navigated to Personal Tab");
	}

	public void clickOnGenderRadioBtn(String gender) {
		if (gender.equalsIgnoreCase("male")) {
			element("genderradiobtn", "1").click();
		} else {
			element("genderradiobtn", "2").click();
		}
		msg.log("Gender: " + gender);
	}

	public void clickOnMigrateLink() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("migrateLink").click();
	}

	public String getEmploymentDate() {
		String employmentDate = element("employmentDate").getAttribute("value");
		msg.log("Employment Date: " + employmentDate);
		return employmentDate;
	}

	public void enterEmployeeMigrationDetailsAndStartMigration(String newEmpId) {
		element("newEmpId").sendKeys(newEmpId);
		msg.log("New Employee Id: " + newEmpId);
		element("newJoinedDate").clear();
		element("newJoinedDate").sendKeys(getEmploymentDate());
		msg.log("New Joined Date: " + getEmploymentDate());
		element("saveMigrtion").click();
	}

	public void navigateToMigratedEmployee() {
		element("migratedLink").click();
	}

	public void clickEditSaveButton() {
		wait.hardWait(2);
		acceptAlert();
		acceptAlert();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("editbutton").click();
		wait.hardWait(3);
		
	}

	public String updateJoiningDateNMonthsBefore(int noOfMonths)
			 {
		String date;
		int dayOfWeek;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		date = date_format.format(calendar.getTime());
		Date convertedDate = null;
		try {
			convertedDate = date_format.parse(date);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		calendar.setTime(convertedDate);
		calendar.add(Calendar.MONTH, -(noOfMonths));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.DATE, +2);
		logMessage("Date Of Joining is : "
				+ date_format.format(calendar.getTime()));
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		logMessage("Day Of Week: " + dayOfWeek);
		if (dayOfWeek == 1 && dayOfWeek == 7) {
			calendar.add(Calendar.DATE, +2);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			logMessage("Day Of Week: " + dayOfWeek);
			date = date_format.format(calendar.getTime());

		}
		date = date_format.format(calendar.getTime());
		return date;
	}

	public void enterJoiningDate(String date) {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("joiningdate").clear();
		element("joiningdate").sendKeys(date);
		msg.log("Joining Date: " + date);
	}
	
	public void entertrainingMonth(String month) {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("trainingMonthTxt").clear();
		element("trainingMonthTxt").sendKeys(month);
		msg.log("Training Period: " + month + " Months");
		}
	

	public void enterFromDateForLocation(String date) {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("fromdate").clear();
		element("fromdate").sendKeys(date);
		msg.log("Location From Date: " + date);
		element("assignButton").click();
	}

	public void enterNewEmployeeID(String empID) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("newEmpIDTxt").sendKeys(empID);
		msg.log("Employee Id: " + empID);
	}

	public void enterNewEmployeeLastName(String lastName) {
		element("newEmpLastNameTxt").sendKeys(lastName);
		msg.log("Employee Last Name: " + lastName);
	}

	public void enterNewEmployeeFirstName(String firstName) {
		element("newEmpFirstNameTxt").sendKeys(firstName);
		msg.log("Employee First Name: " + firstName);
	}

	public void addEmployeeCustomDetails(String isTestGurukul,
			String noticePeriod, String trainingMonth) {
		element("noticePeriodTxt").sendKeys(noticePeriod);
		msg.log("Notice Period: " + noticePeriod + " Months");
		element("trainingMonthTxt").sendKeys(trainingMonth);
		msg.log("Training Period: " + trainingMonth + " Months");
		if (isTestGurukul.equalsIgnoreCase("yes")) {
			selectProvidedTextFromDropDown(element("selectTestGurukul"), "Yes");
			msg.log("Test Gurukul: " + "Yes");
		}
	}

	public void addEmployeeJobDetails(String jobTitle, String employmentStatus) {
		selectProvidedTextFromDropDown(element("selectJobTitle"), jobTitle);
		msg.log("Job Title: " + jobTitle);
		selectProvidedTextFromDropDown(element("selectEmploymentStatus"),
				employmentStatus);
		msg.log("Employment Status: " + employmentStatus);
	}

	public void selectSubDivision(String subDivision) {
		String parent = driver.getWindowHandle();
		element("divisionPopBtn").click();
		switchWindow();
		msg.log("Title of page = " + driver.getTitle());
		element("subDivision", subDivision).click();
		msg.log("Sub Division: " + subDivision);
		switchToOtherWindow(parent);
	}

	public void addLocationForEmployee(String location, String date) {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("addlocation").click();
		selectProvidedTextFromDropDown(element("selectlocation"), location);
		msg.log("Location: " + location);
		enterFromDateForLocation(date);
	}

	public void logOut() {
		switchToDefaultContent();
		element("logout").click();
		msg.log("Click on logout");
	}

	public void reportToGivenManager(String empId) {
		msg.log("report to given manager!!!!");
		//element("link_reportTo").click();
		wait.hardWait(2);
		clickUsingXpathInJavaScriptExecutor(element("link_reportTo"));
		msg.log("clicked on link report_to!!!!");
		selectDropDownValueByValue(element("drpdwn_superwiser"), "Supervisor");
		msg.log("selected drop down value !!!!");
		(new Time_Page(driver)).selectEmployInReportTo(empId);
		wait.hardWait(2);
		switchToFrame("rightMenu");
		selectDropDownValueByValue(element("drpdwn_reportingMethod"), "Direct");
		element("saveBtn").click();
		
	}

	void enterChildName(String name) {
		isElementDisplayed("txt_childName");
		element("txt_childName").sendKeys(name);
		msg.log("Enter child name as " + name);
	}

	void enterChildDob(String dob) {
		isElementDisplayed("txt_childDob");
		element("txt_childDob").sendKeys(dob);
		msg.log("Enter child date of birth as " + dob);
	}

	public void clickOnSaveButton() {
		// isElementDisplayed("saveBtn");
		// element("saveBtn").click();
		executeJavascript("document.getElementById('addPaneChildren').getElementsByTagName('img')[0].click()");
		msg.log("Click on save button");
		
	}

	public void clickOnGivenTab(String tabName) {
		wait.hardWait(3);
		wait.waitForElementToBeClickable(element("btn_tab", tabName));
		isElementDisplayed("btn_tab", tabName);
		element("btn_tab", tabName).click();
		msg.log("Click on tab " + tabName);
	}
	 public boolean checkDisplayButton()
	 {
		 try{
		 return isElementDisplayed("actionlink","Delete");
		 }
		 catch(org.openqa.selenium.NoSuchElementException exc)
		 {
			return false; 
		 }
	 }


	public void enterChildInfo(String childName, String ChildDob) {
		acceptAlert();
		clickOnGivenTab("Dependents");
		switchToDefaultContent();
		switchToFrame("rightMenu");
		if (!(checkDisplayButton())) {
			
			wait.hardWait(2);
			enterChildName(childName);
			enterChildDob(ChildDob);	
			wait.hardWait(2);
		clickOnSaveButton();
		acceptAlert();
		msg.log("Successfully updated child info !!");
		}
		msg.log("Already having child info ");
	}
	
	public void enterMarriageDetails(String marriageDate)
	{
		clickOnGivenTab("Personal");
		clickEditSaveButton();
		selectDropDownValueByValue(element("drpdwn_maritalStatus"), "Married");
		wait.hardWait(2);
		wait.waitForElementToBeVisible(element("txt_marriageDate"));
		element("txt_marriageDate").clear();
		element("txt_marriageDate").sendKeys(marriageDate);
		msg.log("send marriage date as "+marriageDate);
		clickEditSaveButton();
		acceptAlert();
	}
	

	public void fillDependentsDetail()
	 {
		 clickOnGivenTab("Dependents");
		 switchToDefaultContent();
		 switchToFrame("rightMenu");
		 if (!(checkDisplayButton())) {
				wait.hardWait(2);
				enterFatherName("test");
				selectDropDownForRelationship("Father");
				enterFatherDob("2016-12-12");	
				wait.hardWait(2);
				clickOnDependentsSaveButton();
			acceptAlert();
			msg.log("Successfully updated child info !!");
			}
			msg.log("Already having child info ");
	 }
	void enterFatherName(String name) {
		isElementDisplayed("txt_name");
		element("txt_name").sendKeys(name);
		msg.log("Enter name as " + name);
	}

	void enterFatherDob(String dob) {
		element("txt_FatherDob").clear();
		element("txt_FatherDob").sendKeys(dob);
		msg.log("Enter father date of birth as " + dob);
	}
	void selectDropDownForRelationship(String relationship) {
		isElementDisplayed("drpDwn_relationship");
		selectDropDownValueByValue(element("drpDwn_relationship"),relationship);
		msg.log("selected the relationship drp dwn"+relationship);
	}
	
	public void clickOnDependentsSaveButton() {
		// isElementDisplayed("saveBtn");
		// element("saveBtn").click();
		executeJavascript("document.getElementById('parentPaneDependents').getElementsByTagName('img')[0].click()");
		msg.log("Click on save button");
	}
	
	
	

}
