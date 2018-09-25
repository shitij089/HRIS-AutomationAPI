package com.qait.keywords;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class Leave_Page extends GetPage {

	WebDriver driver;
	String pagename = "Leave_Page";
	Home_Page homepage;
	Time_Page timepage;

	public Leave_Page(WebDriver driver) {
		super(driver, "Leave_Page");
		this.driver = driver;
		homepage = new Home_Page(driver);
		timepage = new Time_Page(driver);

	}

	public void navigateToApplyTab() {
		switchToDefaultContent();
		clickUsingXpathInJavaScriptExecutor(element("lefttab", "Apply"));
		msg.log("Nevigated to apply tab");
		wait.hardWait(2);
	}

	public void navigateToAssignLeaveTab() {
		wait.hardWait(2);
		element("lefttab", "Assign Leave").click();
		msg.log("Nevigated to assign leave tab");
	}

	public void cancelLeaveOfGivenEmpWithCategory(String date, String empName,
			String leaveType, String empId, int n) {

		navigateToLeaveList();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		selectPeriod(date, date);
		clickOnAllCheckOnLeaveList();
		clickOnSearchButton();
		msg.log("cancelling leave");
		cancelLeave(empName, leaveType);
		new Home_Page(driver).navigateToTimeTab();
		homepage.navigateToTimeTab();
		timepage.enterEmpId(empId);
		timepage.expandMonthDropDown();
		timepage.clickOnMonthThatIsNMonthBeforeCurrentMonth(n);

	}

	public void cancelLeaveForGivenDate(String date, String empName,
			String empId, int n) {
		msg.log("####### Start cancelling leaves in given week if exists #######");
		String infoValue;
		homepage.switchToDefaultContent();
		homepage.navigateToTimeTab();
		timepage.enterEmpId(empId);
		timepage.expandMonthDropDown();
		timepage.clickOnMonthThatIsNMonthBeforeCurrentMonth(n);
		infoValue = timepage.getInfoAtGivenDate(date, empId);

		if (infoValue.contains("Approved SL Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "Sick Leave",
					empId, n);
		}
		if (infoValue.contains("Approved RH Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName,
					"Restricted Leave", empId, n);
		}
		if (infoValue.contains("Approved CO Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName,
					"Compensatory Off", empId, n);
		}
		if (infoValue.contains("Approved BOOTO Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "Business OOTO",
					empId, n);
		}
		if (infoValue.contains("Approved PTO Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "PTO", empId, n);
		}

		if (infoValue.contains("Approved PL Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "Paternity Leave",
					empId, n);
		}

		if (infoValue.contains("Approved PLWP Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "Planned LWP",
					empId, n);
		}

		if (infoValue.contains("Approved POOTO Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "Personal OOTO",
					empId, n);
		}
		if (infoValue.contains("Approved CCL Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName,
					"Child Care Leave", empId, n);
		}
		if (infoValue.contains("Approved WFH Request")
				|| infoValue.contains("Pending WFH Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "WFH", empId, n);
		}

		if (infoValue.contains("Pending PTO Request")) {
			new Home_Page(driver).navigateToLeaveTab();
			cancelLeaveOfGivenEmpWithCategory(date, empName, "PTO", empId, n);
		}

	}

	public void cancelAllLeavesInWeekHavingWeekStartDateAsGiven(String date,
			String empName, String empId, int n) {
		msg.log("####### Start cancelling leaves in given week if exists #######");
		String[] infoValue = new String[8];
		homepage.switchToDefaultContent();
		homepage.navigateToTimeTab();
		timepage.enterEmpId(empId);
		timepage.expandMonthDropDown();
		timepage.clickOnMonthThatIsNMonthBeforeCurrentMonth(n);
		date = DateUtil.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd",
				"day", date, -1);
		for (int i = 1; i <= 7; i++) {
			date = DateUtil.getNextDateFromGivenDateFoGivenDateModule(
					"yyyy-MM-dd", "day", date, 1);
			infoValue[i] = timepage.getInfoAtGivenDate(date, empId);

			if (infoValue[i].contains("Approved SL Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName, "Sick Leave",
						empId, n);
			}
			if (infoValue[i].contains("Approved RH Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName,
						"Restricted Leave", empId, n);
			}
			if (infoValue[i].contains("Approved CO Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName,
						"Compensatory Off", empId, n);
			}
			if (infoValue[i].contains("Approved BOOTO Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName,
						"Business OOTO", empId, n);
			}
			if (infoValue[i].contains("Approved PTO Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName, "PTO", empId,
						n);
			}

			if (infoValue[i].contains("Approved PL Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName,
						"Paternity Leave", empId, n);
			}

			if (infoValue[i].contains("Approved PLWP Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName, "Planned LWP",
						empId, n);
			}

			if (infoValue[i].contains("Approved POOTO Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName,
						"Personal OOTO", empId, n);
			}
			if (infoValue[i].contains("Approved CCL Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName,
						"Child Care Leave", empId, n);
			}
			if (infoValue[i].contains("Approved WFH Request")
					|| infoValue[i].contains("Pending WFH Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName, "WFH", empId,
						n);
			}

			if (infoValue[i].contains("Pending PTO Request")) {
				new Home_Page(driver).navigateToLeaveTab();
				cancelLeaveOfGivenEmpWithCategory(date, empName, "PTO", empId,
						n);
			}
		}
	}

	public void selectLeaveType(String leaveType) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		hardWait(2);
		isElementDisplayed("sltLeaveType", leaveType);
		selectDropDownValueByValue(element("sltLeaveType"), leaveType);
		msg.log("selected the leave type" + leaveType);

	}

	public void selectGivenDate(String date) {
		wait.hardWait(4);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("radio_date", date);
		element("radio_date", date).click();
		msg.log("Select date " + date + " in restricted leave table list");
	}

	public void selectDuration(String duration) {
		isElementDisplayed("drpdwn_duration");
		selectProvidedTextFromDropDown(element("drpdwn_duration"), duration);
		msg.log("Select duration as " + duration);
	}

	public void selectFromDate(String date) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(1);
		element("txtLeaveFromDate").click();
		element("txtLeaveFromDate").clear();
		wait.hardWait(2);
		element("txtLeaveFromDate").sendKeys(date);
		msg.log("Leave From: " + date);
		element("txtLeaveFromDate").sendKeys(Keys.ENTER);
		wait.hardWait(2);
	}

	public void selectEndDate(String date) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		msg.log("Leave To: " + date);
		executeJavascript("document.getElementById('txtLeaveToDate').value='"
				+ date + "'");
		wait.hardWait(2);
	}

	public void sendTotalHours(int n) {
		element("txt_hoursForBooto").sendKeys(String.valueOf(n));
		msg.log("total hours " + n + " is applied for BOOTO");
	}

	public void sendComment(String comment) {
		element("txt_cmntForBooto").sendKeys(comment);
		msg.log("Send comment ");
	}

	public void clickApplyButton() {
		isElementDisplayed("applyButton");
		wait.waitForElementToBeClickable(element("applyButton"));
		// element("applyButton").click();
		executeJavascript("document.getElementById('frmLeaveApp').getElementsByTagName('img')[1].click()");
		msg.log("Click on apply button");
		wait.hardWait(2);
		acceptAlert();
		confirmationLeavestatus();
	}

	public void clickApplyButton1() {
		isElementDisplayed("applyButton");
		wait.waitForElementToBeClickable(element("applyButton"));
		// element("applyButton").click();
		executeJavascript("document.getElementById('frmLeaveApp').getElementsByTagName('img')[1].click()");
		msg.log("Click on apply button");
		wait.hardWait(2);
		acceptAlert();
		confirmationLeavestatus1();

	}

	public void confirmationLeavestatus1() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		try {
			wait.waitForElementToBeVisible(element("leaveStatusText",
					"Apply succeeded"));

			if (element("leaveStatusText", "Apply succeeded").getText().equals(
					"Apply succeeded"))
				msg.log("leave assigned successfully !!!!!");
		} catch (NoSuchElementException e) {
			Assert.fail("leave not assigned successfully !!!!!");
		}
	}

	public void selectDropDownForFromTimeToToTime(String fromTime, String toTime) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		selectDropDownValueByValue(
				element("drpDwnFromTimeToToTime", "sltLeaveFromTime"), fromTime);
		selectDropDownValueByValue(
				element("drpDwnFromTimeToToTime", "sltLeaveToTime"), toTime);
	}

	public void setCommentMessage(String comment) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("drpDwnFromTimeToToTime", "txtComments").sendKeys(comment);
	}

	public void selectProject(String projectName) {
		wait.hardWait(2);
		isElementDisplayed("drpdwn_selectProject");
		selectDropDownValueByValue(element("drpdwn_selectProject"), projectName);
	}

	public void verifyApplySucceededMessage() {
		isElementDisplayed("applySucceededMessage");
		String applySucceededMessage = "Apply succeeded";
		String assignedSucceededMessage = "Leave Assigned";
		assert (element("applySucceededMessage").getText().equalsIgnoreCase(
				applySucceededMessage) || element("applySucceededMessage")
				.getText().equalsIgnoreCase(assignedSucceededMessage));
	}

	public void verifyMLpplyFailureMessage() {
		isElementDisplayed("applyFailureMessage");
		String applyFailureMessage = "As per the Maternity leave policy, Employee can not take maternity leave for more than 90 days. If employee wants extension, they can apply PLWP to inform manager/HR for the same.";
		assert element("applyFailureMessage").getText().equalsIgnoreCase(
				applyFailureMessage);
	}

	public void verifyApplyFailureMessage() {
		isElementDisplayed("applyFailureMessage");
		String applyFailureMessage = "Selected leave type falls under planned leaves, thus this leave can only be applied for future dates.(at least one day prior)";
		assert element("applyFailureMessage").getText().equalsIgnoreCase(
				applyFailureMessage);
	}

	public void verifyMoreThan5LeaveApplyFailureMessage() {
		isElementDisplayed("applyFailureMessage");
		String applyFailureMessage = "Employee can not take this leave type more than 5 in a year.";
		assert element("applyFailureMessage").getText().equalsIgnoreCase(
				applyFailureMessage);
	}

	public void verifyRHApplyFailureMessage() {
		assert getAlertText().contains(
				"RH Date and Leave Taken Date Should be same");
		acceptAlert();
	}

	public void verifyRHApplyBackDateFailureMessage() {
		assert getAlertText().contains(
				"Please Select a Valid Restricted Holiday Date");
		acceptAlert();
	}

	public void selectEmploy(String empId) {
		String parent = driver.getWindowHandle();
		element("selectemplink").click();
		switchToOtherWindow(parent);
		selectDropDownValueByValue(element("searchbydrpdwn"), "ID");
		msg.log("Select ID from Dropbox");
		element("serachfortextbox").sendKeys(empId);
		msg.log("Send " + empId + " as employee id to search");
		element("searchButton").click();
		element("searchresultlink").click();
		switchToOtherWindow(parent);
	}

	public void supervisorSelectEmployee(String empId) {
		String employeeFullNameWithID = element("empFullNameOption", empId)
				.getText();
		selectProvidedTextFromDropDown(element("sprvsrselectemployee"),
				employeeFullNameWithID);
	}

	public void navigateToLeaveList() {
		switchToDefaultContent();
		wait.hardWait(2);
		// switchToFrame(element("iframe"));
		element("lefttab", "Leave List").click();

	}

	public void clickOnAllCheckOnLeaveList() {
		isElementDisplayed("allLeaveCheckBox");
		element("allLeaveCheckBox").click();
		msg.log("Click on check of all list");
		wait.hardWait(2);
	}

	public void selectPeriod(String froDate, String toDate) {
		element("txtFromDate").clear();
		wait.hardWait(2);
		element("txtFromDate").sendKeys(froDate);
		element("txtToDate").clear();
		wait.hardWait(2);
		element("txtToDate").sendKeys(toDate);
		wait.hardWait(2);
	}

	public void clickOnSearchButton() {
		isElementDisplayed("searchLink");
		element("searchLink").click();
		msg.log("Click on search button");
		wait.hardWait(4);
	}

	public void selectLeaveStatus() {
		isElementDisplayed("takenLeaveCheckBox");
		wait.waitForElementToBeClickable(element("takenLeaveCheckBox"));
		element("takenLeaveCheckBox").click();
		wait.hardWait(3);
	}

	public void cancelLeave(String empName, String leaveType) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		selectDropDownValueByValue(
				element("drpDwn_cancelLeave", empName, leaveType), "Cancelled");
		wait.hardWait(2);
		element("btn_save", empName).click();
		wait.hardWait(2);
	}

	public void cancelPTO(String date) {
		int i = getListOfTheElements(elements("dateList"));
		for (int j = 1; j <= i; j++) {
			String s = Integer.toString(j);
			if (element("dateOfLeave", s).getText().equals(date)) {
				selectDropDownValueByValue(element("actionOnLeave", s),
						"Cancelled");
				element("saveButton", s).click();
				wait.hardWait(2);
				break;
			}
		}
	}

	public String getNextMondayDateFromCurrentDate() throws ParseException {
		String date;
		int dayOfWeek;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		date = date_format.format(calendar.getTime());
		msg.log("Today's Date : " + date);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		msg.log("Current Day Of Week: " + dayOfWeek);
		if (dayOfWeek != 2) {
			for (; dayOfWeek != 2;) {
				calendar.add(Calendar.DATE, +1);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			}
		}
		date = date_format.format(calendar.getTime());
		msg.log("Next Week's First Monday Date: " + date);
		return date;
	}

	public String getNDaysAheadDate(String date, int noOfDays)
			throws ParseException {
		String newDate;
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateObj = date_format.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		calendar.add(Calendar.DATE, noOfDays);
		newDate = date_format.format(calendar.getTime());
		return newDate;
	}

	public void verifyTotalRestrictedLeavesInAYear() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		String restrictedLeaves = element("leaveRemaining", "Restricted Leave")
				.getText();
		assert restrictedLeaves.equalsIgnoreCase("2");
	}

	public String getFirstRHDateAvailable() {
		element("firstRHInputRadio").click();
		String RHDate = element("firstRHInputRadio").getAttribute("value")
				.split(" ")[0];
		return RHDate;
	}

	public String getTodaysDate() throws ParseException {
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		return date_format.format(calendar.getTime());
	}

	public void verifyMsgAfterApplyLeave(String msgName) {
		isElementDisplayed("applyButton");
		wait.waitForElementToBeClickable(element("applyButton"));
		executeJavascript("document.getElementById('frmLeaveApp').getElementsByTagName('img')[1].click()");
		msg.log("Click on apply button");
		acceptAlert();
		wait.hardWait(2);
		if (elements("txt_InvalidDatePtoMsg").size() >= 1) {
			if (element("txt_InvalidDatePtoMsg").getText().contains(
					YamlReader.getData("msg." + msgName))) {
				logMessage("ASSERT PASSED: Verified Invalid Date Pto Msg !!");
			} else
				Assert.fail("Expected was "
						+ YamlReader.getData("msg." + msgName + " but found "
								+ element("txt_InvalidDatePtoMsg").getText()));
		} else {
			Assert.fail("Invalid Msg not shown");
		}
	}

	public void verifyMsgIfWFHAppliedForMoreThenLimit() {
		if (elements("txt_InvalidDatePtoMsg").size() >= 1) {
			Assert.assertEquals(element("txt_InvalidDatePtoMsg").getText(),
					YamlReader.getData("msg.invalidWFHMsg"));
			logMessage("ASSERT PASSED: Verified Msg for exceeded number of wfh applied !!");
		} else {
			logMessage("Step: Invalid Msg not shown");
			Assert.fail();
		}
	}

	public void assignSickForGivenNumberOfDays(String noOfSickLeaves,
			String empId, String startDate) {
		DateUtil dateUtil = new DateUtil();
		Home_Page homePage = new Home_Page(driver);
		Leave_Page leavePage = new Leave_Page(driver);
		int i = 0;
		String day;
		msg.log("Number of sich leaves to apply are = " + noOfSickLeaves);

		for (i = 0; i < (int) (Float.parseFloat(noOfSickLeaves) * 2); i++) {
			if (i != 0) {
				startDate = dateUtil.getNextDateFromGivenDate(startDate);
			}
			day = DateUtil.dateConversionForGivenPattern(startDate, "EEEE");
			if (day.equals("Saturday") || day.equals("Sunday")) {
				i--;
			} else {
				homePage.navigateToLeaveTab();
				leavePage.navigateToAssignLeaveTab();
				leavePage.selectLeaveType("Sick Leave");
				leavePage.selectEmploy(empId);
				leavePage.selectFromDate(startDate);
				leavePage.selectEndDate(startDate);
				leavePage.selectDuration("Half Day");
				wait.waitForElementToBeClickable(element("applyButton"));
				executeJavascript("document.getElementById('frmLeaveApp').getElementsByTagName('img')[1].click()");
				msg.log("Click on apply button");
				wait.hardWait(2);
				switchToDefaultContent();
				switchToFrame(element("iframe"));
				try {
					wait.waitForElementToBeVisible(element("leaveStatusText"));
					msg.log("confirmation msg found as "
							+ element("leaveStatusText", "Leave Assigned")
									.getText());
					if (element("leaveStatusText", "Leave Assigned").getText()
							.equals("Leave Assigned")) {
						msg.log("leave assigned successfully !!!!!");
					} else {
						System.out.println("value = "
								+ element("leaveStatusText").getText());
						if (element("txt_confirmBox")
								.getText()
								.contains(
										"You can not apply a leave for sunday or a specific holiday")) {
							msg.log("Leave can not assighed due to holiday");
							i--;
						}
					}

				} catch (NoSuchElementException n) {
					System.out.println("exception = " + n);
					Assert.fail("Leave not assigned successfully !!");
				}
				homePage.switchToDefaultContent();
				msg.log("Sick leave no. " + i + " is applied ");
			}
		}
	}

	public void selectEmployeeByManagerToAssignLeave(String empId) {
		System.out.println("size = "
				+ elements("list_text_assignEmpOptions").size());
		System.out.println("empId");
		for (WebElement data : elements("list_text_assignEmpOptions")) {
			if (data.getText().contains(empId)) {
				String empData = data.getText();
				selectProvidedTextFromDropDown(element("drpdwn_employeeName"),
						empData);
				break;
			}
		}
	}

	public void clickOnApprovalLeave(String month) {
		switchToDefaultContent();
		wait.hardWait(2);
		element("lefttab", "Approve Leave").click();
		switchToFrame(element("iframe"));
		wait.hardWait(3);
		selectDropDownValueByValue(element("drpdwn_monthYr"), month);
		wait.hardWait(2);
	}

	public void searchEmpIdForCancelLeaveByManager(String name, String date) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("searchName", name, date).click();
		element("drpdwnstatus1").click();
		element("button_save").click();

	}

	public String verifyThereShouldNoLeaveInWeekHavingStartDateAsGiven1(
			String startDate, String empName, String empId, int n) {
		msg.log("####### Start cancelling leaves in given week if exists #######");
		return verifyThereShouldNotAnyLeaveInTheGivenWeekHavingDate2(startDate,
				empName, empId, n);
	}

	public String verifyThereShouldNotAnyLeaveInTheGivenWeekHavingDate2(
			String date, String empName, String empId, int n) {
		String[] infoValue = new String[89];
		homepage.switchToDefaultContent();
		homepage.navigateToTimeTab();
		timepage.enterEmpId(empId);
		timepage.expandMonthDropDown();
		timepage.clickOnMonthThatIsNMonthBeforeCurrentMonth(n);
		wait.hardWait(6);
		date = DateUtil.getNextDateFromGivenDateFoGivenDateModule("yyyy-MM-dd",
				"day", date, -1);
		System.out.println("********************* date = " + date);
		try {
			for (int i = 1; i <= 89; i++) {

				date = DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", date, 1);
				System.out.println("********************* date = " + date);

				infoValue[i] = timepage.getInfoAtGivenDate(date, empId);

				if (infoValue[i].contains("Approved SL Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Sick Leave", empId, n);
				}

				if (infoValue[i].contains("Approved BOOTO Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Business OOTO", empId, n);
				}
				if (infoValue[i].contains("Approved PTO Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName, "PTO",
							empId, n);
				}

				if (infoValue[i].contains("Approved PL Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Paternity Leave", empId, n);
				}

				if (infoValue[i].contains("Approved PLWP Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Planned LWP", empId, n);
				}

				if (infoValue[i].contains("Approved POOTO Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Personal OOTO", empId, n);
				}
				if (infoValue[i].contains("Approved CCL Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Child Care Leave", empId, n);
				}
				if (infoValue[i].contains("Approved ML Request")) {
					new Home_Page(driver).navigateToLeaveTab();
					cancelLeaveOfGivenEmpWithCategory(date, empName,
							"Maternity leave", empId, n);
				}
			}
			return " ";
		} catch (Exception exc) {
			return date;
		}

	}

	public void confirmationLeavestatus() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		try {
			wait.waitForElementToBeVisible(element("leaveStatusText",
					"Leave Assigned"));
			if (element("leaveStatusText", "Leave Assigned").getText().equals(
					"Leave Assigned"))
				msg.log("leave assigned successfully !!!!!");
			else
				Assert.fail("leave assigned msg not displays !!!!!");
		} catch (NoSuchElementException ne) {

			Assert.fail("Leave not assigned successfully !!");
		}
	}

	public void selectDateForApplyingCompOffDateAtParticularDate(String date) {
		wait.hardWait(10);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("chk_confirmDate", date).click();
	}

	public String getPopUpTextAfterClickingOnApplyButtonOfLeave() {
		isElementDisplayed("applyButton");
		wait.waitForElementToBeClickable(element("applyButton"));
		executeJavascript("document.getElementById('frmLeaveApp').getElementsByTagName('img')[1].click()");
		msg.log("Click on apply button");
		wait.hardWait(2);
		return getAlertText();
	}

}
