package com.qait.tests.CreateEmployee;

import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class CreateEmployee {

	TestSessionInitiator test;
	String date, empId,endDate1,endDate2,previousDate[];

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		test.loginPage.login(YamlReader.getData("username"),
				YamlReader.getData("password"));
	}

	//@Test
	public void Step01_createEmployeeOfAccountDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("Account");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.fillDependentsDetail();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("AccountsExecutive.jobTitle"),
				YamlReader.getData("AccountsExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("AccountsExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("AccountsExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("AccountDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("AccountDepartmentEmployeeFirstName", "Account");
		DataReadWrite.writeDataToFile("AccountDepartmentEmployeeLastName",empId);
		DataReadWrite.writeDataToFile("AccountDepartmentEmployeeFullName", "Account "+empId);
	}

	//@Test
	public void Step02_UpdateOnsitePunchesForCreatedAccountEmployee() throws ParseException{
		System.setProperty("department", "account");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2,"account");
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2,"account");
	}
	
	/*@Test
	public void Step03_createEmployeeOfAdminDepartment() {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("Admin");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("AdminExecutive.jobTitle"),
				YamlReader.getData("AdminExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("AdminExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("AdminExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("AdminDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("AdminDepartmentEmployeeFirstName", "Admin");
		DataReadWrite.writeDataToFile("AdminDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("AdminDepartmentEmployeeFullName", "Admin "+empId);
	}

	@Test
	public void Step04_UpdateOnsitePunchesForCreatedAdminEmployee() throws ParseException{
		System.setProperty("department", "admin");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2);
	}
	
	@Test
	public void Step05_createEmployeeOfItDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("It");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("ITExecutive.jobTitle"),
				YamlReader.getData("ITExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("ITExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("ITExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("ItDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("ItDepartmentEmployeeFirstName", "It");
		DataReadWrite.writeDataToFile("ItDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("ItDepartmentEmployeeFullName", "It "+empId);
	}

	@Test
	public void Step06_UpdateOnsitePunchesForCreatedItEmployee() throws ParseException{
		System.setProperty("department", "it");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2);
	}
	
	@Test
	public void Step07_createEmployeeOfSocDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("Soc");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("SOCExecutive.jobTitle"),
				YamlReader.getData("SOCExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("SOCExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("SOCExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("SocDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("SocDepartmentEmployeeFirstName", "Soc");
		DataReadWrite.writeDataToFile("SocDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("SocDepartmentEmployeeFullName", "Soc "+empId);
	}

	@Test
	public void Step08_UpdateOnsitePunchesForCreatedSocEmployee() throws ParseException{
		System.setProperty("department", "soc");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2);
	}
	*/
	@Test
	public void Step09_createEmployeeOfSttDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("Stt");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.fillDependentsDetail();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "0", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("STTExecutive.jobTitle"),
				YamlReader.getData("STTExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("STTExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("STTExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("SttDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("SttDepartmentEmployeeFirstName", "Stt");
		DataReadWrite.writeDataToFile("SttDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("SttDepartmentEmployeeFullName", "Stt "+empId);
	}

	@Test
	public void Step10_UpdateOnsitePunchesForCreatedSttEmployee() throws ParseException{
		System.setProperty("department", "stt");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2,"stt");
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2,"stt");
	}

	/*
	@Test
	public void Step11_createEmployeeOfMaleSEDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("FullTime");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("SEExecutive.jobTitle"),
				YamlReader.getData("SEExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("SEExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("SEExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("SEDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("SEDepartmentEmployeeFirstName", "FullTime");
		DataReadWrite.writeDataToFile("SEDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("SEDepartmentEmployeeFullName", "FullTime "+empId);
	}

	@Test
	public void Step12_UpdateOnsitePunchesForCreatedMaleSEEmployee() throws ParseException{
		System.setProperty("department", "se");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2);
	}
	/*@Test
	public void Step13_createEmployeeOfCDLDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("CDL");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("CDLExecutive.jobTitle"),
				YamlReader.getData("CDLExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("CDLExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("CDLExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("CdlDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("CdlDepartmentEmployeeFirstName", "CDL");
		DataReadWrite.writeDataToFile("CdlDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("CdlDepartmentEmployeeFullName", "CDL "+empId);
	}

	@Test
	public void Step14_UpdateOnsitePunchesForCreatedCDLEmployee() throws ParseException{
		System.setProperty("department", "cdl");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2);
	}
	@Test
	public void Step15_createEmployeeOfFemaleSEDepartment() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("FullTime");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "0");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("SEExecutive.jobTitle"),
				YamlReader.getData("SEExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("SEExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(6);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("SEExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.femaleGender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("FemaleSEDepartmentEmployeeId", empId);
		DataReadWrite.writeDataToFile("FemaleSEDepartmentEmployeeFirstName", "FullTime");
		DataReadWrite.writeDataToFile("FemaleSEDepartmentEmployeeLastName", empId);
		DataReadWrite.writeDataToFile("FemaleSEDepartmentEmployeeFullName", "FullTime "+empId);
	}

	@Test
	public void Step16_UpdateOnsitePunchesForCreatedFemaleSEEmployee() throws ParseException{
		System.setProperty("department", "se");
		previousDate=DateUtil.getPreviousDate("month", 6);
		endDate1=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		previousDate=DateUtil.getPreviousDate("month", 3);
		endDate2=previousDate[0]+"-"+previousDate[1]+"-"+previousDate[2];
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate1,endDate2);
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(endDate2);
	}
	@Test
	public void Step01_createEmployeeOfFullInternshipTrainee() throws InterruptedException {
		empId = String.valueOf(System.currentTimeMillis());
		empId = empId.substring(empId.length() - 6, empId.length());
		test.homePage.navigateToPIMTab();
		test.pimPage.clickAddButton();
		test.pimPage.enterNewEmployeeID(empId);
		test.pimPage.enterNewEmployeeLastName(empId);
		test.pimPage.enterNewEmployeeFirstName("TestingTrainee");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeCustomDetails("No", "3", "3");
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.acceptAlert();
		test.pimPage.clickEditSaveButton();
		test.pimPage.addEmployeeJobDetails(
				YamlReader.getData("InternshipExecutive.jobTitle"),
				YamlReader.getData("InternshipExecutive.employmentStatus"));
		test.pimPage.selectSubDivision(YamlReader
				.getData("InternshipExecutive.subDivision"));
		date = test.pimPage.updateJoiningDateNMonthsBefore(1);
		test.pimPage.enterJoiningDate(date);
		test.pimPage.addLocationForEmployee(
				YamlReader.getData("InternshipExecutive.location"), date);
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnPersonalTab();
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		test.pimPage.clickOnGenderRadioBtn(YamlReader
				.getData("fullTimePermanentUser.gender"));
		test.pimPage.clickEditSaveButton();
		test.pimPage.acceptAlert();
		DataReadWrite.writeDataToFile("InternEmployeeId", empId);
		DataReadWrite.writeDataToFile("InternEmployeeFirstName", "Intern");
		DataReadWrite.writeDataToFile("InternEmployeeLastName",empId);
		DataReadWrite.writeDataToFile("InternEmployeeFullName", "Intern "+empId);
	}

	
	@Test
	public void Step02_UpdateOnsitePunchesForCreatedAccountEmployee() throws ParseException{
		System.setProperty("department", "Intern");
		test.homePage.switchToDefaultContent();
		test.homePage.navigateToTimeTab();
		test.timePage.clickOnsitePunchesTab();
		test.timePage.clickSingleRadioButton();
		test.timePage.selectEmploy(empId);
		test.timePage.updatePunches(date); 
	}*/
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}	
	
}
