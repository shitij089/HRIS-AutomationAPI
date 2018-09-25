package com.qait.tests.ResourceManagement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataReadWrite;
import com.qait.automation.utils.YamlReader;

public class MyProjectSubTabTest {
	TestSessionInitiator test;
	String temp;
	String empID, empName,empName1,empName2;

	@BeforeClass
	public void Step00_OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication();
		empID = "307346";
		empName="FullTime 307346";
		empName1="Minesh Upadhyaya";
		empName2="Sugandha Mathur";
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
	public void Step03_VerifyMyProjectSubTabLoadSuccessfully() {
			test.homePage.navigateToResourceManagementTab();
			test.resourceManagementpage.navigateAndVerifyMyProjectsTab();
	}
	//@Test
	public void Step04_VerifyBillingCalenderWithUpdateBillingButtonForOpenPopUp() {
			test.resourceManagementpage.clickUpdateBillingButtonForVerifyOpenPopup();
	}
	//@Test
	public void Step06_AddedNewResourcInParticularProjectSuccessfully() {
			test.resourceManagementpage.searchForProject("QAIT and Devlabs Websites");
			test.resourceManagementpage.searchAndAddResourceToTeam("FullTime", empID,"Team Lead");
			test.resourceManagementpage.verifyNewResourceAddedSuccessfully(empName);
	}
   // @Test
	public void Step07_DeletedResourcFromParticularProjectSuccessfully() {
			test.resourceManagementpage.searchForProject("QAIT and Devlabs Websites");
			test.resourceManagementpage.searchAndDeleteResourceToTeam(empName);
	}
	//@Test
	public void Step08_LoginUserNameAddedInParticularProjectSuccessfully() {
		test.resourceManagementpage.addedProject("SAI Global Content QA (Kapil  Dev)","2016-11-02");
		test.resourceManagementpage.searchForProject("SAI Global Content QA");
		test.resourceManagementpage.verifyNewResourceAddedSuccessfully(empName1);
	}
	//@Test
	public void Step09_DeletedResourcFromParticularProjectSuccessfully() {
			test.resourceManagementpage.searchForProject("SAI Global Content QA");
			test.resourceManagementpage.searchAndDeleteResourceToTeam(empName1);
	}

	//@Test
	public void Step10_AddedNewResourcInTraineeAsTeamMemberNotLeadSuccessfully() {
			test.resourceManagementpage.searchForProject("Trainees");
			test.resourceManagementpage.searchAndAddResourceToTeam(YamlReader.getData("Trainee_shortName"),YamlReader.getData("Trainee_EmpId"),"Team Lead");
			test.resourceManagementpage.verifyNewResourceAddedSuccessfully(empName2);
	}
	//@Test
	public void Step11_DeletedResourcInTraineeAsTeamMemberNotLeadSuccessfully() {
			test.resourceManagementpage.searchForProject("Trainees");
			test.resourceManagementpage.searchAndDeleteResourceToTeam(empName2);
	}
	//@Test
	public void Step12_AddedFullTimeEmpInTraineeAsTeamLeadNotMemberSuccessfully() {
			test.resourceManagementpage.searchForProject("Trainees");
			test.resourceManagementpage.searchAndAddResourceToTeam("Vishal","1027","Team Lead");
			test.resourceManagementpage.verifyNewResourceAddedSuccessfully("Vishal Sethi");// we need some updation if select the Team member ,will do later
	}
	//@Test
	public void Step13_DeletedFullTimeEmpInTraineeAsTeamLeadNotMemberSuccessfully() {
			test.resourceManagementpage.searchForProject("Trainees");
			test.resourceManagementpage.searchAndDeleteResourceToTeam("Vishal Sethi");
	}
	@Test
	public void Step14_VerifyOnlyOneEngineerMarkedAsShadowInParticularProject() {
		    test.resourceManagementpage.navigateAndVerifyMyProjectsTab();
			test.resourceManagementpage.verifyOnlyOneEngineerMarkedAsShadow("2");
	}

	
	@Test
	public void Step10_VerifyManagerCannotAddShadowForMoreThanTenDays(){
		test.resourceManagementpage.clickOnSlideout();
		test.resourceManagementpage.verifyUserIsAbleToNavigateToMyProjects();
		test.resourceManagementpage.verifyUserCannotMarkEmployeeShadowForMoreThanTenDays();
		test.resourceManagementpage.deleteShadowMarked();
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
