package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.utils.LayoutValidation;

public class GetPage extends BaseUi {

    private static final long THREAD_SLEEP = 0;
    protected WebDriver webdriver;
    String pageName;
    LayoutValidation layouttest;

    public GetPage(WebDriver driver, String pageName) {
        super(driver, pageName);
        this.webdriver = driver;
        this.pageName = pageName;
        layouttest = new LayoutValidation(driver, pageName);
    }

    public void testPageLayout(List<String> tagsToBeTested) {
        layouttest.checklayout(tagsToBeTested);
    }

    public void testPageLayout(String tagToBeTested) {
        testPageLayout(Arrays.asList(tagToBeTested));
    }

    public void testPageLayout() {
        testPageLayout(Arrays.asList(getProperty("./Config.properties",
                "browser")));
    }

    // TODO: put this in right place, create dedicated class for frame and
    // window handlers
    protected void switchToNestedFrames(String frameNames) {
        switchToDefaultContent();
        String[] frameIdentifiers = frameNames.split(":");
        for (String frameId : frameIdentifiers) {
            wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
                    .trim()));
        }
    }

    protected WebElement element(String elementToken)
            throws NoSuchElementException {
        waitjsForPageLoad(wait.timeout);
        return element(elementToken, "");
    }

    public Object executeJavascript1(String js, Object... args) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(js, args);
    }

    public void waitjsForPageLoad(long milliseconds) {
        boolean waitJS = false;
        long endTime;
        long startTime = System.currentTimeMillis();

        do {
            try {
                waitJS = executeJavascript1("return document.readyState").equals("complete");
                Thread.sleep(THREAD_SLEEP);
            } catch (InterruptedException e) {

            }

            endTime = System.currentTimeMillis();
        } while (!waitJS && endTime - startTime <= milliseconds);
        if (!waitJS) {

        }
//			System.out.println(" JS readyState Value : "+waitJS+" and time : "+(endTime - startTime));

    }

    protected WebElement element(String elementToken, String replacement)
            throws NoSuchElementException {
        WebElement elem = null;
        By locator = getLocator(elementToken, replacement);
        try {
            elem = wait.waitForElementToBeVisible(webdriver
                    .findElement(locator));
            scrollDown(elem);
        } catch (TimeoutException excp) {
            throw new NoSuchElementException("Element " + elementToken
                    + " with locator " + locator.toString().substring(2)
                    + " not found on the " + this.pageName + " !!!");
        }
        return elem;
    }
    
    
    
    protected WebElement element(String elementToken, String replacement1,
    		   String replacement2) throws NoSuchElementException {
    		  WebElement elem = null;
    		  By locator = getLocator(elementToken, replacement1, replacement2);
    		  try {
    		   elem = wait.waitForElementToBeVisible(webdriver
    		     .findElement(locator));
    		  } catch (TimeoutException excp) {
    		   throw new NoSuchElementException("Element " + elementToken
    		     + " with locator " + locator.toString().substring(2)
    		     + " not found on the " + this.pageName + " !!!");
    		  }
    		  return elem;
    		 }
    
    

    protected List<WebElement> elements(String elementToken, String replacement) {
        return (webdriver
                .findElements(getLocator(elementToken, replacement)));
    }

    protected List<WebElement> elements(String elementToken) {
        return elements(elementToken, "");
    }

    protected void isStringMatching(String actual, String expected) {
        Assert.assertEquals(actual, expected);
        logMessage("ACTUAL STRING : " + actual);
        logMessage("EXPECTED STRING :" + expected);
        logMessage("String compare Assertion passed.");
    }

    protected boolean isElementDisplayed(String elementName,
            String elementTextReplace) {
        wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
        boolean result = element(elementName, elementTextReplace).isDisplayed();
        assertTrue(result, "ASSERT FAILED: element '" + elementName
                + "with text " + elementTextReplace + "' is not displayed.");
        logMessage("ASSERT PASSED: element " + elementName + " with text '"
                + elementTextReplace + "' is displayed.");
        return result;
    }

    protected void verifyElementText(String elementName, String expectedText) {
        wait.waitForElementToBeVisible(element(elementName));
        assertEquals(element(elementName).getText().trim(), expectedText,
                "ASSERT FAILED: element '" + elementName
                + "' Text is not as expected: ");
        logMessage("ASSERT PASSED: element " + elementName
                + " is visible and Text is " + expectedText);
    }

    protected void verifyElementText(String elementName, String replacement, String expectedText) {
        wait.waitForElementToBeVisible(element(elementName, replacement));
        assertEquals(element(elementName, replacement).getText().trim(), expectedText,
                "ASSERT FAILED: element '" + elementName
                + "' Text is not as expected: ");
        logMessage("ASSERT PASSED: element " + elementName
                + " is visible and Text is " + expectedText);
    }

    protected void verifyElementTextContains(String elementName,
            String expectedText) {
        wait.waitForElementToBeVisible(element(elementName));
        assertThat("ASSERT FAILED: element '" + elementName
                + "' Text is not as expected: ", element(elementName).getText()
                .trim(), containsString(expectedText));
        logMessage("ASSERT PASSED: element " + elementName
                + " is visible and Text is " + expectedText);
    }

    protected boolean isElementDisplayed(String elementName)
            throws NoSuchElementException {
        boolean result = wait.waitForElementToBeVisible(element(elementName))
                .isDisplayed();
        assertTrue(result, "ASSERT FAILED: element '" + elementName
                + "' is not displayed.");
        logMessage("ASSERT PASSED: element " + elementName + " is displayed.");
        return result;
    }

    protected boolean isElementEnabled(String elementName, boolean expected) {
        wait.waitForElementToBeVisible(element(elementName));
        boolean result = expected && element(elementName).isEnabled();
        assertTrue(result, "ASSERT FAILED: element '" + elementName
                + "' is  ENABLED :- " + !expected);
        logMessage("ASSERT PASSED: element " + elementName + " is enabled :- "
                + expected);
        return result;
    }

    protected By getLocator(String elementToken) {
        return getLocator(elementToken, "");
    }

    protected By getLocator(String elementToken, String replacement) {
        String[] locator = getELementFromFile(this.pageName, elementToken);
        locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
        return getBy(locator[1].trim(), locator[2].trim());
    }
    
    protected By getLocator(String elementToken, String replacement1,
    		   String replacement2) {
    		  String[] locator = getELementFromFile(this.pageName, elementToken);
    		  locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
    		  locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
    		  return getBy(locator[1].trim(), locator[2].trim());
    		 }

    private By getBy(String locatorType, String locatorValue) {
        switch (Locators.valueOf(locatorType)) {
            case id:
                return By.id(locatorValue);
            case xpath:
                return By.xpath(locatorValue);
            case css:
                return By.cssSelector(locatorValue);
            case name:
                return By.name(locatorValue);
            case classname:
                return By.className(locatorValue);
            case linktext:
                return By.linkText(locatorValue);
            default:
                return By.id(locatorValue);
        }
    }

    public void acceptAlert() {
    	wait.hardWait(3);
    	try{
    	Alert alert = driver.switchTo().alert();
    	alert.accept();
    	msg.log("Alert handled");
    	}
    	catch(Exception e)
    	{
    		msg.log("No alert  window appeared");
    	}
       // handleAlert();
    }
    
    public String getAlertText()
    {
    	String alertText="";
    	wait.hardWait(3);
    	try{
    	Alert alert = driver.switchTo().alert();
    	alertText=alert.getText();
    	alert.accept();
    	msg.log("Alert handled");
    	}
    	catch(Exception e)
    	{
    		Assert.fail("No alert  window appeared");
    	}
    	return alertText;
    }
    
}
