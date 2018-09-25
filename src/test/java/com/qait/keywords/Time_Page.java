package com.qait.keywords;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;
import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Time_Page extends GetPage {

	WebDriver driver;
	String pagename = "Time_Page";
	public static boolean isWeekday;
	public static boolean isFriday;
	public static int dayOfWeek;
	Document doc;

	public Time_Page(WebDriver driver) {
		super(driver, "Time_Page");
		this.driver = driver;
	}

	public void clickOnsitePunchesTab() {
		switchToDefaultContent();
		element("lefttab", "Onsite Punches").click();
		msg.log("Click on onsite punches tab");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
	}

	public void sendKeysAfterQaitPunches(String empID, String fromDate,
			String toDate, String outTime) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		element("inputBoxAfterQaitPunches1", "1").clear();
		element("inputBoxAfterQaitPunches1", "1").sendKeys(empID);
		msg.log("empID has entered ");
		wait.hardWait(2);
		element("inputBoxAfterQaitPunches1", "2").clear();
		element("inputBoxAfterQaitPunches1", "2").sendKeys(fromDate);
		msg.log("fromDate has entered");
		wait.hardWait(2);
		element("inputBoxAfterQaitPunches1", "3").clear();
		element("inputBoxAfterQaitPunches1", "3").sendKeys(toDate);
		msg.log("toDate has entered");
		wait.hardWait(2);
		element("inputBoxAfterQaitPunches2", "4").click();
		msg.log("Clicked on search");
		wait.hardWait(2);
		if (element("user_info").getText()
				.equals("Showing 0 to 0 of 0 entries")) {
			msg.log("no record found for that particular ID within date");
		} else {
			element("outTime_InputBox").clear();
			element("outTime_InputBox").sendKeys(outTime);
			wait.hardWait(2);
			driver.findElement(By.cssSelector("#users_length>label>select"))
					.click();
			wait.hardWait(10);
			msg.log("out punch has entered");
		}

	}

	public void clickQaitPunchesTab() {
		switchToDefaultContent();
		element("lefttab", "QAIT Punches").click();
		msg.log("Click on onsite punches tab");
		wait.hardWait(2);
	}

	public void clickMyTimeSheetTab() {
		element("lefttab", "My Timesheet").click();
		msg.log("Click on timesheet tab");
		wait.hardWait(3);
	}

	public void clickSingleRadioButton() {
		wait.hardWait(2);
		acceptAlert();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		acceptAlert();
		clickUsingXpathInJavaScriptExecutor(element("singleradiobutton"));
		wait.hardWait(1);
	}

	public void selectEmploy(String empId) {
		String parent = driver.getWindowHandle();
		element("selectemplink").click();
		switchWindow();
		selectDropDownValueByValue(element("searchbydrpdwn"), "ID");
		msg.log("Select id in drop down");
		element("serachfortextbox").sendKeys(empId);
		msg.log("Send employee id " + empId + " to search");
		element("searchButton").click();
		element("searchresultlink").click();
		switchToOtherWindow(parent);
		wait.hardWait(2);
	}

	public void selectEmployInReportTo(String empId) {
		String parent = driver.getWindowHandle();
		driver.findElement(
				By.xpath(".//div[@id='addPaneReportTo']//input[@class='button']"))
				.click();
		switchWindow();
		selectDropDownValueByValue(element("searchbydrpdwn"), "ID");
		element("serachfortextbox").sendKeys(empId);
		element("searchButton").click();
		element("searchresultlink").click();
		switchWindow();
	}

	public void clickAssignHolidayTab() {
		element("lefttab", " Assign Holiday ").click();
		wait.hardWait(2);
		msg.log("Nevigate to assign holiday tab");

	}

	public void clickOnAdd() {
		switchToFrame(element("frame"));
		element("btn_add").click();
		wait.hardWait(2);
	}

	public void selectHolidayDate(String date) {
		isElementDisplayed("txt_date");
		element("txt_date").sendKeys(date);
		wait.hardWait(1);
	}

	public void selectHolidayName(String holidayName) {
		isElementDisplayed("txt_holiday");
		element("txt_holiday").sendKeys(holidayName);
		wait.hardWait(1);
	}

	public void selectHolidayDivision(String divisionName) {
		isElementDisplayed("scr_division", divisionName);
		element("scr_division", divisionName).click();
		wait.hardWait(1);
	}

	public void assighEmployee(String empId) {
		isElementDisplayed("scr_assignemp", empId);
		element("scr_assignemp", empId).click();
		wait.hardWait(1);
		element("btn_moveRight").click();
		wait.hardWait(2);
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		wait.hardWait(2);
	}

	public void updatePunches(String joinDate, String department) {
		int lastDay = Calendar.SATURDAY;
		switchToFrame(element("iframe"));
		element("startDate").clear();
		element("startDate").sendKeys(joinDate);
		msg.log("Send start date as " + joinDate);
		element("endDate").clear();
		element("endDate").sendKeys(DateUtil.getDateWithNDaysBefore(1));
		msg.log("Send end date as one day before today ie "
				+ DateUtil.getDateWithNDaysBefore(1));
		element("searchButtonTwo").click();
		msg.log("Click on search button");
		wait.hardWait(4);
		element("editButton").click();
		msg.log("Click on edit button for update punches");
		wait.hardWait(4);

		if (department.equals("stt") || department.equals("it")
				|| department.equals("se") || department.equals("cdl")
				|| department.equals("Intern")) {
			lastDay = Calendar.FRIDAY;
		}
		int i = getListOfTheElements(elements("dateList"));
		msg.log("Start filling punches");
		for (int j = 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= lastDay));
				if (department.equalsIgnoreCase("admin")) {
					isWeekday = true;
				}
				if (isWeekday) {
					element("dateCheckBox", s).click();
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
					if (department.equalsIgnoreCase("admin")) {
						element("inTime", s).sendKeys("08:00:00");
						element("outTime", s).sendKeys("16:00:00");
						element("elapsedTime", s).sendKeys("00:00:00");
					} else {
						element("inTime", s).sendKeys("08:00:00");
						element("outTime", s).sendKeys("18:00:00");
						element("elapsedTime", s).sendKeys("00:00:00");
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		element("editButton").click();
	}

	public void updatePunches(String joinDate, String endDate, String department) {
		int lastDay = Calendar.SATURDAY;
		switchToFrame(element("iframe"));
		element("startDate").clear();
		element("startDate").sendKeys(joinDate);
		msg.log("Send start date as " + joinDate);
		element("endDate").clear();
		element("endDate").sendKeys(DateUtil.getDateWithNDaysBefore(1));
		msg.log("Send end date as one day before today ie "
				+ DateUtil.getDateWithNDaysBefore(1));
		element("searchButtonTwo").click();
		msg.log("Click on search button");
		wait.hardWait(4);
		element("editButton").click();
		msg.log("Click on edit button for update punches");
		wait.hardWait(4);

		if (department.equals("stt") || department.equals("it")
				|| department.equals("se") || department.equals("cdl")
				|| department.equals("Intern")) {
			lastDay = Calendar.FRIDAY;
		}
		int i = getListOfTheElements(elements("dateList"));
		msg.log("Start filling punches");
		for (int j = 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= lastDay));
				if (department.equalsIgnoreCase("admin")) {
					isWeekday = true;
				}
				if (isWeekday) {
					element("dateCheckBox", s).click();
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
					if (department.equalsIgnoreCase("admin")) {
						element("inTime", s).sendKeys("08:00:00");
						element("outTime", s).sendKeys("16:00:00");
						element("elapsedTime", s).sendKeys("00:00:00");
					} else {
						element("inTime", s).sendKeys("08:00:00");
						element("outTime", s).sendKeys("18:00:00");
						element("elapsedTime", s).sendKeys("00:00:00");
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		element("editButton").click();
	}

	public void updateTimeForFirday(String fridayDate, String mondayDate) {
		element("editButton").click();
		int i = getListOfTheElements(elements("dateList"));
		for (int j = 1; j <= i; j++) {
			String s = Integer.toString(j);
			String dateDayFriday = element("dateDay", s).getText();
			if (fridayDate.equals(dateDayFriday)) {
				element("dateCheckBox", s).click();
				element("inTime", s).clear();
				element("outTime", s).clear();
				element("elapsedTime", s).clear();
				j = j + 2;
			}
			String dateDayMonday = element("dateDay", s).getText();
			if (mondayDate.equals(dateDayMonday)) {
				element("dateCheckBox", s).click();
				element("inTime", s).clear();
				element("outTime", s).clear();
				element("elapsedTime", s).clear();
				break;
			}
		}
		element("editButton").click();
	}

	// Method to Update Punches not for the whole list (i.e. from joining date
	// to current date)
	public void updatePunches(String joinDate, int skippedDays)
			throws ParseException {
		driver.switchTo().frame(element("iframe"));
		element("startDate").clear();
		element("startDate").sendKeys(joinDate);
		element("searchButtonTwo").click();
		element("editButton").click();
		int i = getListOfTheElements(elements("dateList"));
		for (int j = 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));
				if (isWeekday) {
					element("dateCheckBox", s).click();
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int j = skippedDays + 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));
				if (isWeekday) {
					System.out.println("value of j is: " + j);
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
					element("inTime", s).sendKeys("08:00:00");
					element("outTime", s).sendKeys("18:00:00");
					element("elapsedTime", s).sendKeys("00:00:00");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		isElementDisplayed("editButton");
		element("editButton").click();
	}

	// Method to Update Punches not for the whole list (i.e. from joining date
	// to current date)
	public void updatePunchesOfNNumberOfDays(String joinDate, int punchCount)
			throws ParseException {
		driver.switchTo().frame(element("iframe"));
		element("startDate").clear();
		element("startDate").sendKeys(joinDate);
		element("searchButtonTwo").click();
		element("editButton").click();
		int i = getListOfTheElements(elements("dateList"));
		for (int j = 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));
				if (isWeekday) {
					element("dateCheckBox", s).click();
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int j = 1; j <= punchCount; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));
				if (isWeekday) {
					System.out.println("value of j is: " + j);
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
					element("inTime", s).sendKeys("08:00:00");
					element("outTime", s).sendKeys("18:00:00");
					element("elapsedTime", s).sendKeys("00:00:00");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		isElementDisplayed("editButton");
		element("editButton").click();
	}

	public void enterEmpId(String empId) {
		empId = empId.trim();
		wait.hardWait(3);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		// isElementDisplayed("empidtextfield");

		element("empidtextfield").click();
		element("empidtextfield").clear();

		element("empidtextfield").sendKeys(empId);
		msg.log("Send emp id as " + empId);

		wait.hardWait(2);
		element("empidtextfield").sendKeys(Keys.ENTER);
		wait.hardWait(2);
	}

	public void clickOnPTOSummaryLink() {
		wait.hardWait(2);
		element("ptosumlink").click();
		wait.hardWait(2);
	}

	public void verifySickLeaveBalance(String actualBalance) {
		verifyElementText("sickleavebalance", actualBalance);
	}

	public void verifyPTOLeaveBalance(String actualBalance) {
		verifyElementText("ptoleavebalance", actualBalance);
	}

	public void selectSandwichMonth(int index) {
		selectDropDownValue(element("selectmonth"), index);
		wait.hardWait(2);
	}

	public void verifyStatusOfTheDay(String sundayDate, String saturdayDate,
			String status) {
		verifyElementText("statusOfDayOff", saturdayDate, status);
		verifyElementText("statusOfDayOff", sundayDate, status);
	}

	public void verifySW(String sundayDate, String saturdayDate, String status) {
		verifyElementText("statusOfDay", saturdayDate, status);
		verifyElementText("statusOfDay", sundayDate, status);
	}

	public void updatePunchesForWeek(String joinDate, List<String> punches,
			String department) {
		int lastDay = Calendar.SATURDAY;
		if (department.equalsIgnoreCase("stt")) {
			lastDay = Calendar.FRIDAY;
		}
		switchToFrame(element("iframe"));
		element("startDate").click();
		element("startDate").clear();
		wait.hardWait(2);
		element("startDate").sendKeys(joinDate);
		msg.log("Send start date as " + joinDate);
		wait.hardWait(1);
		element("endDate").clear();
		wait.hardWait(2);
		element("endDate").sendKeys(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", joinDate, punches.size() - 1));
		msg.log("Send end date as "
				+ DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", joinDate, punches.size() - 1));
		wait.hardWait(1);
		element("searchButtonTwo").click();
		wait.hardWait(1);
		wait.waitForElementToBeClickable(element("editButton"));
		element("editButton").click();
		wait.hardWait(3);
		int i = getListOfTheElements(elements("dateList"));
		for (int j = 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= lastDay));
				if (department.equalsIgnoreCase("admin")) {
					isWeekday = true;
				}

				isWeekday = true;
				if (isWeekday) {
					element("dateCheckBox", s).click();
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
					element("inTime", s).sendKeys(
							YamlReader.getData("Punches." + punches.get(j - 1)
									+ ".inTime"));
					element("outTime", s).sendKeys(
							YamlReader.getData("Punches." + punches.get(j - 1)
									+ ".outTime"));
				}
			} catch (NoSuchElementException | ParseException e) {
			}
		}
		element("editButton").click();
	}

	public void selectNextMonthInTimesheet() {
		int value = getSelectedValueOfDropDown(element("selectMonth"));
		if (value == 12) {
			value = 1;
		} else {
			value = value + 1;
		}
		selectDropDownValue(element("selectMonth"), value);
	}

	public void clickOnApplyAHLink() {
		switchToDefaultContent();
		element("applyAHlink").click();
		wait.hardWait(2);
		driver.switchTo().frame(element("iframe"));
		element("closeBtn").click();
	}

	public void clickOnApplyAHBtn() {
		wait.hardWait(2);
		switchToDefaultContent();
		driver.switchTo().frame(element("iframe"));
		element("applyAHBtn").click();
	}

	public void enterAHDateNDaysBefore(String days) {
		element("AHDate").click();
		wait.hardWait(2);
		element("selectPreviousNDate", days).click();
	}

	public void enterAHDateNDaysAhead(String days) {
		element("AHDate").click();
		wait.hardWait(2);
		element("selectFutureNDate", days).click();
	}

	public void enterAHDateAsToday() {
		element("AHDate").click();
		wait.hardWait(2);
		element("selectTodaysDate").click();
	}

	public void enterAHTimeAndSubmit(String time) {
		wait.hardWait(2);
		element("AHTime").click();
		element("AHTime").clear();
		element("AHTime").sendKeys(time);
		wait.hardWait(2);
		element("applyAHTimeBtn").click();
	}

	public void verifyAHFailureMessage() {
		isElementDisplayed("applyAHFailureMessage");
		String applyAHFailureMessage = "You can apply a maximum of 2 additional hours per day in the past week";
		assert element("applyAHFailureMessage").getText().equalsIgnoreCase(
				applyAHFailureMessage);
	}

	public void verifySameDayAHFailureMessage() {
		isElementDisplayed("applyAHFailureMessage");
		String applyAHFailureMessage = "You can apply additional hours only for the past week (starting yesterday)";
		assert element("applyAHFailureMessage").getText().equalsIgnoreCase(
				applyAHFailureMessage);
	}

	public void verifyAHSuccessMessage() {
		isElementDisplayed("applyAHFailureMessage");
		String applyAHSuccessMessage = "Successfully Applied";
		assert element("applyAHFailureMessage").getText().equalsIgnoreCase(
				applyAHSuccessMessage);
	}

	public void expandMonthDropDown() {
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.waitForElementToBeClickable(element("drpdwn_month"));
		isElementDisplayed("drpdwn_month");
		element("drpdwn_month").click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step: Expand drop Down of Month!!\n");
	}

	public void clickOnGivenMonth(String monthName) {
		isElementDisplayed("txt_monthName", monthName);
		element("txt_monthName", monthName).click();
		logMessage("Step: click on given month !!\n");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
	}

	public void clickOnMonthThatIsNMonthBeforeCurrentMonth(int n) {
		clickOnGivenMonth(DateUtil.dateConversion("yyyy-MM-dd", "MMMM",
				DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
						"yyyy-MM-dd", "month", (n))));
		if (n > 0) {
			wait.hardWait(2);
			selectDropDownValueByValue(element("yearSelection"),
					DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
							"yyyy", "month", n));
			msg.log("Click on year "
					+ DateUtil.getPreviousDateForGivenDateModuleInGivenFormat(
							"yyyy", "month", n));
		}
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
	}

	public void verifyLWPForWeeksHavingHourGreaterThan50() {
		for (WebElement element : elements("list_weekHours")) {
			if (Integer.parseInt(element.getText().substring(0,
					element.getText().length() - 3)) > 50) {
				for (WebElement element2 : elements(
						"list_hourOfDay",
						"Week "
								+ element.getAttribute("id")
										.substring(
												element.getAttribute("id")
														.length() - 1))) {
					System.out.println("dayValue = " + element2.getText());
					if (element2.equals("LWP")) {
						System.out.println(element2.getAttribute("class"));
						Assert.fail();
					}
				}
			}
		}
		logMessage("ASSERT PASSED: There is no any LWP !!\n");
	}

	public String getWeekHourValueWhichHaveGivenDate(String date) {
		isElementDisplayed("txt_weekHours", date);
		return element("txt_weekHours", date).getText().trim();
	}

	void makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(String date,
			String empID) {
		for (int i = 0; i < 7; i++) {
			String day = date.substring(date.length() - 2, date.length());
			if ((Integer.parseInt(day) + i) < 10) {
				day = "0" + (Integer.parseInt(day) + i);
			} else
				day = Integer.toString((Integer.parseInt(day) + i));

			executeJavascript("document.getElementById('idTitle"
					+ date.substring(0, date.length() - 2) + day + "_" + empID
					+ "').type='visible'");
		}
	}

	void makeVisibleLeaveInfoForGivenDate(String date, String empId) {

		executeJavascript("document.getElementById('idTitle" + date + "_"
				+ empId + "').type='visible'");
	}

	public void VerifyNoLWPIfTotalWeekHourIsGreaterThanRequiredHour(
			String empID, String startDate) {
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empID);
		for (WebElement element1 : elements("list_valueForDay", startDate)) {
			if (element1.getAttribute("value").contains("LWP")) {
				Assert.fail("Step: It Shows A LWP !!\n");
			}
		}
		logMessage("Step: Verified There is no LWP in given week!!\n");
	}

	public void verifyHLWPForWeekHavingDayHourGreaterOrEqual8AndWeekHourLess50(
			String empID, String startDate) {
		int count = 0;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empID);
		for (WebElement element1 : elements("list_valueForDay", startDate)) {
			if (element1.getAttribute("value").contains(
					"System marked half LWP")) {
				logMessage("Step: It Shows A HLWP !!\n");
				count++;
			}
		}
		if (count != 1) {
			Assert.fail("Step: System marked " + count + " HLWP's \n");
		}
		logMessage("Step: Verified System marked a single hlwp\n");
	}

	public void verifyHLWPForWeekWithHourLessThan50AndWeekDayHourAreBetween5And8(
			String empID, String startDate, String department) {
		String newDate;
		int count = 0, noOfDays = 6;
		if (department.equalsIgnoreCase("it")
				|| department.equalsIgnoreCase("cdl")) {
			noOfDays = 5;
		}
		String date = startDate;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empID);
		for (int i = 0; i < noOfDays; i++) {
			String day = date.substring(date.length() - 2, date.length());
			if ((Integer.parseInt(day) + i) < 10) {
				day = "0" + (Integer.parseInt(day) + i);
			} else
				day = Integer.toString((Integer.parseInt(day) + i));
			newDate = date.substring(0, date.length() - 2) + day;
			if (Integer.parseInt(element("txt_hourForGivenDay", newDate)
					.getText().split(":")[0]) < 8) {
				if (!element("txt_valueForDay", newDate).getAttribute("value")
						.contains("System marked half LWP")) {
					Assert.fail("day having day hour less then 8 but not having HLWP\n");
				}
			}
			if (Integer.parseInt(element("txt_hourForGivenDay", newDate)
					.getText().split(":")[0]) == 8) {
				count++;
				if (count == 3) {
					if (!element("txt_valueForDay", newDate).getAttribute(
							"value").contains("System marked half LWP")) {
						Assert.fail("Step: There are more then 3 8 hours but not cut any leave\n");
					}

				}
			}
		}
		logMessage("Step: Verified HLWP's for day having hour less then 8!!\n");
	}

	public void verifyFullDayLWPIfWorkingHourIsLessThan5(String empID,
			String startDate, String department) {
		String newDate;
		int workingDay = 6;

		if (department.equalsIgnoreCase("stt")
				|| department.equalsIgnoreCase("it")
				|| department.equalsIgnoreCase("cdl")) {
			workingDay = 5;
		}

		System.out.println("***************************** department = "
				+ department);

		String date = startDate;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empID);
		for (int i = 0; i < workingDay; i++) {
			String day = date.substring(date.length() - 2, date.length());
			if ((Integer.parseInt(day) + i) < 10) {
				day = "0" + (Integer.parseInt(day) + i);
			} else
				day = Integer.toString((Integer.parseInt(day) + i));
			newDate = date.substring(0, date.length() - 2) + day;

			if (Integer.parseInt(element("txt_hourForGivenDay", newDate)
					.getText().split(":")[0]) < 5) {
				if (!element("txt_valueForDay", newDate).getAttribute("value")
						.contains(YamlReader.getData("msg.fullDayLWP"))) {
					Assert.fail("day haiving day hour less then 5 but not having Full day LWP\n");
				}
			}
		}
		logMessage("Step: Verified Full Day LWP for day having hour less then 5!!\n");
	}

	public void verifyPTOAppliedSuccessfully(String empID, String startDate) {
		int count = 0;
		String totalTime = "00:00";
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empID);
		for (WebElement element : elements("list_hourForDay", startDate)) {
			if (!element.getText().equals("PTO")) {
				count++;
				if (count > 5) {
					break;
				}
				totalTime = DateUtil.addHour(totalTime, element.getText()
						.substring(0, 4));
			}
		}
		if (Integer.parseInt(totalTime.split(":")[0]) >= 42) {
			for (WebElement element1 : elements("list_valueForDay", startDate)) {
				if (element1.getAttribute("value").contains("LWP")) {
					Assert.fail(" It Shows A LWP !!");
				}
			}
		}
		logMessage("Step: Verified PTO Applied Successfully !!\n");
	}

	public void verifySingleLwpInAGivenWeek(String empId, String startDate) {
		int count = 0;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empId);

		for (WebElement element1 : elements("list_valueForDay", startDate)) {
			if (element1.getAttribute("value").contains("LWP")) {
				logMessage("Step: It Shows A LWP !!\n");
				count++;
			}
		}

		if (count != 1) {
			Assert.fail("Expected Number of HLWP was 1 but found " + count);
		} else
			logMessage("Step: Verified single LWP Successfully !!\n");
	}

	/*
	 * public void verifyNoFullDayLWPInAGivenWeek(String empId, String caseNo) {
	 * String date = YamlReader.getData(caseNo + ".startDate"); int count = 0;
	 * makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empId);
	 * 
	 * for (WebElement element1 : elements("list_valueForDay", date)) { if
	 * (element1.getAttribute("value").contains("Full Day LWP")) {
	 * logMessage("Step: It Shows A LWP !!\n"); count++; } }
	 * 
	 * if (count > 1) { Assert.fail("It shows Full day LWP!!\n"); } else
	 * logMessage("Step: Verified No full day LWP Successfully !!\n"); }
	 */

	public void verifySingleLWPIfInRemainingDaysTotalHoursIs45(String empID,
			String startDate) {
		int count = 0;
		String totalTime = "00:00";
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empID);
		for (WebElement element : elements("list_hourForDay", startDate)) {
			if (!(element.getText().equals("LWP") || element.getText().equals(
					"PTO"))) {
				count++;
				if (count > 5) {
					break;
				}
				System.out.println(element.getText());
				totalTime = DateUtil.addHour(totalTime, element.getText()
						.substring(0, 4));
			}
		}
		count = 0;
		if (Integer.parseInt(totalTime.split(":")[0]) >= 45) {
			for (WebElement element1 : elements("list_valueForDay", startDate)) {
				if (element1.getAttribute("value").contains("LWP")) {
					logMessage("Step: It Shows A LWP !!\n");
					count++;
				}
			}
		}
		if (count != 1) {
			Assert.fail("Number of LWP's are not as expected!!");
		} else
			logMessage("Step: Verified single LWP Successfully !!\n");

	}

	public void verifyHSLAppliedSuccessfully(String empID, String startDate) {
		int count = 0;
		String totalTime = "00:00";

		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empID);
		for (WebElement element : elements("list_hourForDay", startDate)) {
			count++;
			if (count > 6)
				break;
			// if (!element.getText().contains("HSL")) {
			totalTime = DateUtil.addHour(totalTime, element.getText()
					.substring(0, 4));
			// }
		}
		if (Integer.parseInt(totalTime.split(":")[0]) >= 45) {
			for (WebElement element1 : elements("list_valueForDay", startDate)) {
				if (element1.getAttribute("value").contains("LWP")) {
					Assert.fail("It Shows A LWP !!");
				}
			}
		}
		logMessage("Step: Verified Sick Leave Applied Successfully !!\n");
	}

	public void verifyFullDayLWPMessage(String empID, String startDate) {
		String newDate;
		String date = startDate;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empID);
		for (int i = 0; i < 5; i++) {
			String day = date.substring(date.length() - 2, date.length());
			if ((Integer.parseInt(day) + i) < 10) {
				day = "0" + (Integer.parseInt(day) + i);
			} else
				day = Integer.toString((Integer.parseInt(day) + i));
			newDate = date.substring(0, date.length() - 2) + day;

			if (element("txt_hourForGivenDay", newDate).getText().equals("LWP")) {
				if (!element("txt_valueForDay", newDate).getAttribute("value")
						.contains(YamlReader.getData("msg.noPunch"))) {
					Assert.fail("Day haiving LWP but not showing proper message.\n");
				}
			}
		}
		logMessage("Step: Verified Full Day LWP message !!\n");
	}

	public void verifyHLWPMessage(String empID, String startDate) {
		String newDate;
		String date = startDate;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empID);
		for (int i = 0; i < 5; i++) {
			String day = date.substring(date.length() - 2, date.length());
			if ((Integer.parseInt(day) + i) < 10) {
				day = "0" + (Integer.parseInt(day) + i);
			} else
				day = Integer.toString((Integer.parseInt(day) + i));
			newDate = date.substring(0, date.length() - 2) + day;

			if (Integer.parseInt(element("txt_hourForGivenDay", newDate)
					.getText().split(":")[0]) == 5) {
				if (!element("txt_valueForDay", newDate).getAttribute("value")
						.contains(YamlReader.getData("msg.halfDay"))) {
					Assert.fail("Day haiving LWP but not showing proper message.\n");
				}
			}
		}
		logMessage("Step: Verified Half Day LWP message !!\n");
	}

	public void VerifyMsgWhichOccurWhenTotalWeeklyHoursBecomesLessThen40(
			String empID, String startDate) {

		String date = startDate;
		int count = 0;
		String totalTime = "00:00";
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empID);
		for (WebElement element : elements("list_hourForDay", date)) {
			count++;
			if (count > 5) {
				break;
			}
			totalTime = DateUtil.addHour(totalTime, element.getText()
					.substring(0, 4));
		}
		count = 0;
		if (Integer.parseInt(totalTime.split(":")[0]) < 40) {
			for (WebElement element1 : elements("list_valueForDay", date)) {
				if (element1.getAttribute("value").contains(
						YamlReader.getData("msg.lessWeekHours"))) {
					logMessage("Step: It Shows A Half LWP !!");
					count++;
				}
			}
		}
		if (count == 0) {
			Assert.fail("It does not shows any half LWP!!\n");
		} else
			logMessage("Step: Verified Half LWP Successfully !!\n");
	}

	public String getNoOfLeaveOFGivenType(String leaveType) {
		switchToDefaultContent();
		switchToFrame("rightMenu");
		element("img_leaveInfo").click();
		return element("txt_leaveOfGivenCategory", leaveType).getText();
	}

	public boolean getInfoThatGivenDayIsOff(String date, String empID) {
		makeVisibleLeaveInfoForGivenDate(date, empID);
		if (element("timeOfGivenDate", date, empID).getText().equals("OFF"))
			return true;
		else
			return false;
	}

	public String getInfoAtGivenDate(String date, String empID) {
		makeVisibleLeaveInfoForGivenDate(date, empID);
		return element("txt_dayInformation", date, empID).getAttribute("value");
	}

	public String getStartDateForApplyingSickLeaves(String date, String empID) {
		String id;
		id = element("idPreviousWeek", date, empID).getAttribute("id");
		id = id.substring(6, 16);
		return id;
	}

	public void verifyPTOMessageForGivenDate(String date, String empId) {
		makeVisibleLeaveInfoForGivenDate(date, empId);
		msg.log(element("txt_dayInformation", date, empId)
				.getAttribute("value"));
		if (!element("txt_dayInformation", date, empId).getAttribute("value")
				.contains("Approved PTO Request")) {
			Assert.fail("Pto not applied successfully !!");
		}
		logMessage("Step: Verify that PTO Applied Successfully !!");
	}

	public void verifyHLWPMessageForGivenDate(String date, String empId) {
		makeVisibleLeaveInfoForGivenDate(date, empId);

		if (!element("txt_dayInformation", date, empId).getAttribute("value")
				.contains("half LWP")) {
			Assert.fail("HALF LWP Not applied !!!");
		} else
			logMessage("Step: Verify that HALF LWP leave at particular date !!");
	}

	public void verifyHPTOMessageForGivenDate(String date, String empId,
			String message) {
		makeVisibleLeaveInfoForGivenDate(date, empId);
		System.out.println(element("txt_dayInformation", date, empId)
				.getAttribute("value"));
		if (!element("txt_dayInformation", date, empId).getAttribute("value")
				.contains(message)) {
			Assert.fail("HALF PTO Not applied !!!");
		} else
			logMessage("Step: Verify that HALF PTO" + message
					+ " leave at particular date !!");
	}

	public void verifyCompOffMessageForGivenDate(String date, String empId,
			String message) {
		makeVisibleLeaveInfoForGivenDate(date, empId);
		System.out.println(element("txt_dayInformation", date, empId)
				.getAttribute("value"));
		if (!element("txt_dayInformation", date, empId).getAttribute("value")
				.contains(message)) {
			Assert.fail("CO leave not applied");
		} else
			logMessage("Step: Verified that Comp Off leave have applied successfully!!!");
	}

	public void verifyPLWPMessageForGivenDate(String date, String empId) {
		makeVisibleLeaveInfoForGivenDate(date, empId);
		if (!element("txt_dayInformation", date, empId).getAttribute("value")
				.contains("counted as LWP")) {
			Assert.fail("PLWP not applied successfully");
		}
		logMessage("Step: Verify that Full Day LWP ocuur at given date !!");
	}

	public String timeValueForGivenDate(String date, String empId) {

		element("txt_timeValueForGivenDate", date, empId).getText();
		msg.log("time value for the given date found is "
				+ element("txt_timeValueForGivenDate", date, empId).getText());
		return element("txt_timeValueForGivenDate", date, empId).getText();
	}

	public void verifyTimeValueForTheGivenDateIsEqualToTheGivenValue(
			String date, String empId, String expectedValue) {

		Assert.assertEquals(timeValueForGivenDate(date, empId), expectedValue);
		msg.pass("verified expected value " + expectedValue
				+ " and actual value " + timeValueForGivenDate(date, empId)
				+ " are equal !!");
	}

	public void verifyTharThereIsNoSandwitchBetweenMAternityLeaveApplied(
			String startDate, String endDate) {
		String date;
		switchToDefaultContent();
		switchToFrame("rightMenu");
		if (elements("list_allSandwitchInGivenPage").size() > 0) {
			for (WebElement element : elements("list_allSandwitchInGivenPage")) {
				date = element.getAttribute("id").substring(6, 16);
				if (!(dateUtil.isFirstDateIsBeforeSecondDate("yyyy-MM-dd",
						date, startDate) || dateUtil
						.isFirstDateIsAfterSecondDate("yyyy-MM-dd", date,
								endDate))) {
					Assert.fail("It shows a sandwitch between maternity leave applied");
				}
			}
		}
		msg.pass("Verified that there is no any sandwitch sat-sun between maternity leave");
	}

	public String getStartDateOfGivenWeek(int weekNumber) {
		switchToDefaultContent();
		switchToFrame("rightMenu");
		return element("txt_startDateOfGivenWeek", String.valueOf(weekNumber))
				.getAttribute("id").substring(6, 16);
	}

	public void updatePunchesForWeek1(String joinDate, List<String> punches,
			String department) {
		int lastDay = Calendar.SATURDAY;
		if (department.equals("stt")) {
			lastDay = Calendar.FRIDAY;
		}
		switchToFrame(element("iframe"));
		element("startDate").clear();
		element("startDate").sendKeys(joinDate);
		element("endDate").clear();
		element("endDate").sendKeys(
				DateUtil.getNextDateFromGivenDateFoGivenDateModule(
						"yyyy-MM-dd", "day", joinDate, punches.size() - 1));
		element("searchButtonTwo").click();
		wait.waitForElementToBeClickable(element("editButton"));
		element("editButton").click();
		wait.hardWait(3);
		int i = getListOfTheElements(elements("dateList"));
		for (int j = 1; j <= i; j++) {
			try {
				String s = Integer.toString(j);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat date_format = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date convertedDate = date_format.parse(element("dateDay", s)
						.getText());
				calendar.setTime(convertedDate);
				dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= lastDay));
				if (department.equalsIgnoreCase("admin")) {
					isWeekday = true;
				}

				isWeekday = true;
				if (isWeekday) {
					element("dateCheckBox", s).click();
					element("inTime", s).clear();
					element("outTime", s).clear();
					element("elapsedTime", s).clear();
					element("inTime", s).sendKeys(
							YamlReader.getData("Punches." + punches.get(j - 1)
									+ ".inTime"));
					element("outTime", s).sendKeys(
							YamlReader.getData("Punches." + punches.get(j - 1)
									+ ".outTime"));
				}
			} catch (NoSuchElementException | ParseException e) {
			}
		}
		element("editButton").click();
	}

	public List<String> savePunches(String lastDate, String curTo1DayPreDate) {
		List<String> punches = new ArrayList<String>();
		while (!lastDate.equals(curTo1DayPreDate)) {
			punches.add("zeroHour");
			lastDate = DateUtil.getNextDateFromGivenDateFoGivenDateModule(
					"yyyy-MM-dd", "day", lastDate, 1);
			System.out.println("*****" + lastDate);
		}
		punches.add("eightHour");
		return punches;
	}

	public void verifyfirstValueContainsSecond(String value1, String value2) {
		if (value1.contains(value2)) {
			msg.pass("Verified !!");
		} else {
			Assert.fail("Expected value was " + value2 + " but not get this");
		}
	}

	public int getDayHavingFirstLWPInWeek(String empId, String startDate) {
		int noOfDay = 0;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(startDate, empId);

		for (WebElement element1 : elements("list_valueForDay", startDate)) {
			noOfDay++;
			if (element1.getAttribute("value").contains("LWP")) {
				logMessage("Step: It Shows A LWP !!\n");
				msg.log("Get first Leave at " + noOfDay + " day");
				return noOfDay;
			}
		}
		Assert.fail("Number of HLWP are not as expected !!");
		return 0;
	}

	public void verifyfirstValueNotContainsSecond(String value1, String value2) {
		if (!value1.contains(value2)) {
			msg.pass("Verified !!");
		} else {
			Assert.fail(value2 + " occur !!");
		}
	}

	public void verifyHLWPMessageForAdmin(String empID, String startDate) {
		String newDate;
		String date = startDate;
		makeVisibleInfoAboutLeaveInWeekHavingStartDateAsGiven(date, empID);
		for (int i = 0; i < 7; i++) {
			String day = date.substring(date.length() - 2, date.length());
			if ((Integer.parseInt(day) + i) < 10) {
				day = "0" + (Integer.parseInt(day) + i);
			} else
				day = Integer.toString((Integer.parseInt(day) + i));
			newDate = date.substring(0, date.length() - 2) + day;
			if (!element("txt_valueForDay", newDate).getAttribute("value")
					.contains(YamlReader.getData("msg.halfDayForAdmin"))) {
				Assert.fail("Day haiving LWP but not showing proper message.\n");
			}

		}
		logMessage("Step: Verified Half Day LWP message For entire week!!\n");
	}

}