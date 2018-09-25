package com.qait.automation;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.qait.automation.utils.Msg;
import com.qait.automation.utils.TakeScreenshot;
import com.qait.keywords.Admin_Page;
import com.qait.keywords.Database;
import com.qait.keywords.Home_Page;
import com.qait.keywords.Leave_Page;
import com.qait.keywords.Login_Page;
import com.qait.keywords.PIM_Page;
import com.qait.keywords.ResouceManagement_Page;
import com.qait.keywords.Time_Page;


public class TestSessionInitiator {

	protected WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */
	public Login_Page loginPage;
	public Home_Page homePage;
	public PIM_Page pimPage;
	public Time_Page timePage;
	public Leave_Page leavePage;
        public Admin_Page adminPage;
        public ResouceManagement_Page resourceManagementpage;
      public Msg msg;


	public TakeScreenshot takescreenshot;
	public Database database_page;

	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPage() {
		loginPage = new Login_Page(driver);	
		homePage = new Home_Page(driver);
		pimPage = new PIM_Page(driver);
		timePage = new Time_Page(driver);
		leavePage = new Leave_Page(driver);
                adminPage = new Admin_Page((driver));
                resourceManagementpage =new ResouceManagement_Page((driver));
              database_page=new Database(driver);
              msg=new Msg();
	}

	/**
	 * Page object Initiation done
	 */

	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
	}

	private void testInitiator(String testname) {
		Reporter.log(setYamlFilePath(),true);
		_configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage()
				.timeouts()
				.implicitlyWait(Integer.parseInt(getProperty("timeout")),
						TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver",
				"seleniumserverhost", "timeout", "driverpathchrome", "driverpathie" };
		Map<String, String> config = new HashMap<>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("baseurl"));
	}

	public void launchApplication(String baseurl) {
		Reporter.log("\nThe application url is :- " + baseurl, true);
		Reporter.log(
				"The test browser is :- " + _getSessionConfig().get("browser")
						+ "\n", true);
		driver.manage().deleteAllCookies();
		driver.get(baseurl);
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void closeBrowserSession() {
		driver.quit();
	}

	public void closeBrowserWindow() {
		driver.close();
	}
	
	public void stepStartMessage(String testStepName) {
		  Reporter.log(" ", true);
		  Reporter.log(" STARTING TEST STEP:- " + testStepName + " ", true);
		  Reporter.log(" ", true);
		 }
	
}