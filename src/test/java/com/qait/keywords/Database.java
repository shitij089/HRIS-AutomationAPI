package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class Database extends GetPage {

	WebDriver driver;

	public Database(WebDriver driver) {
		super(driver, "database");
		this.driver = driver;
	}

	public void login() {
		isElementDisplayed("txt_userName");
		element("txt_userName")
				.sendKeys(YamlReader.getData("databaseUserName"));
		isElementDisplayed("txt_password");
		element("txt_password")
				.sendKeys(YamlReader.getData("databasePassword"));
		isElementDisplayed("btn_go");
		element("btn_go").click();
		msg.log("Successfully logged in !!");
	}

	public void gotoTable() {
		switchToDefaultContent();
		switchToFrame(element("frame_navigation"));
		element("btn_tableTab", "_hris").click();
		switchToDefaultContent();
		switchToFrame(element("frame_navigation"));
		element("txt_searchTable").click();
		wait.hardWait(3);
		element("txt_searchTable").clear();
		element("txt_searchTable").sendKeys("raw");
		wait.hardWait(4);
		clickUsingXpathInJavaScriptExecutor(element("link_goTable"));
		msg.log("Go to table !!");
	}

	public void insertPunches(String empId, String date, String rawPunches) {
		switchToDefaultContent();
		switchToFrame(element("frame_table"));
		element("btn_tableTab", "Insert").click();
		msg.log("Go to table to insert");
		wait.hardWait(2);
		element("txt_tableData", "date", "2").sendKeys(date);
		wait.hardWait(1);
		msg.log("send date as " + date);
		wait.hardWait(2);
		element("txt_tableData", "emp_other_id", "2").sendKeys(empId);
		wait.hardWait(1);
		msg.log("send empId as " + empId);
		element("txt_tableData", "raw_punches", "2").sendKeys(rawPunches);
		wait.hardWait(1);
		msg.log("send raw_punches as " + rawPunches);
		element("btn_submitTableData").click();
		msg.log("Sublit details !!");
		wait.hardWait(2);
	}

	public void updatePunches(String empId, String date, String rawPunches) {
		switchToDefaultContent();
		switchToFrame(element("frame_table"));
		isElementDisplayed("btn_tableTab", "Search");
		element("btn_tableTab", "Search").click();
		msg.log("Go to table to Search");
		wait.hardWait(1);
		isElementDisplayed("txt_tableSearchData", "date");
		element("txt_tableSearchData", "date").sendKeys(date);
		wait.hardWait(1);
		msg.log("send date to search " + date);
		isElementDisplayed("txt_tableSearchData", "emp_other_id");
		element("txt_tableSearchData", "emp_other_id").sendKeys(empId);
		wait.hardWait(1);
		msg.log("send empId to search " + empId);
		isElementDisplayed("btn_submitTableData");
		element("btn_submitTableData").click();
		msg.log("Sublit details to search!!");
		wait.hardWait(2);
		isElementDisplayed("txt_searchResult");
		if (element("txt_searchResult").getText().contains("Showing rows")) {
			element("btn_edit").click();
			wait.hardWait(1);
			element("txt_tableData", "raw_punches", "3").clear();
			element("txt_tableData", "raw_punches", "3").click();
			wait.hardWait(1);

			switchToDefaultContent();
			executeJavascript("document.getElementById('frame_content').contentDocument.getElementById('field_3_3').value='"
					+ rawPunches + "';");
			wait.hardWait(1);
			executeJavascript("document.getElementById('frame_content').contentDocument.getElementById('field_3_3').innerHTML='"
					+ rawPunches + "';");
			switchToFrame(element("frame_table"));
			element("updated_time").clear();
			wait.hardWait(1);
			msg.log("send raw_punches as " + rawPunches);
			wait.hardWait(1);
			isElementDisplayed("btn_submitTableData");
			clickUsingXpathInJavaScriptExecutor(element("btn_submitTableData"));
			msg.log("Sublit details !!");
			wait.hardWait(1);
		} else
			insertPunches(empId, date, rawPunches);
	}

}
