package com.qait.automation.report;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.internal.runners.TestMethod;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.qait.automation.utils.YamlReader;

public class ResultsIT extends ReformatTestFile {

	String today = new Date().toString();
	String resultOfRun = null;
	String host = "smtp.gmail.com";
	String from = "automation.resultsqait@gmail.com";
	String password = "QaitAutomation";
	int port = 25;
	String failureResults = "";
	String skippedResults = "";
	String passedResult = "",className;
	List<String> FailureDescription = new ArrayList<String>();
	List<String> FailureClassName = new ArrayList<String>();
	List<String> FailureMethodName = new ArrayList<String>();
	List<String> DeveloperFailureClass = new ArrayList<String>();
	List<String> DeveloperFailureMethod = new ArrayList<String>();
	List<String> ScriptFailureClass = new ArrayList<String>();
	List<String> ScriptFailureMethod = new ArrayList<String>();
	List<String> FailureReason = new ArrayList<String>();
	
	
	boolean sendResults = false;
	YamlReader util = new YamlReader();
	final String projectName = "HRIS";
	@SuppressWarnings("unused")
	private String totaltest;
	@SuppressWarnings("unused")
	private String passedResults;
	public static int count = 0;

	@BeforeClass
	void setupMailConfig() {
	}

	@Test
	public void changeTimeStamp() throws IOException {
		String html = readLargerTextFile("target/surefire-reports/emailable-report.html");

		html = replacealltimestamp(html);
		writeLargerTextFile("target/surefire-reports/emailable-report.html",
				html);
	}

	@Test(dependsOnMethods = "changeTimeStamp")
	public void sendResultsMail() throws MessagingException, IOException {

		if (true) { // send email is true *************************
			Message message = new MimeMessage(getSession());
			message.addFrom(new InternetAddress[] { (new InternetAddress(from)) });
			setMailRecipient(message);
			message.setContent(setAttachment());
			message.setSubject(setMailSubject());
			Session session = getSession();
			 Transport transport = session.getTransport("smtps");
			 transport.connect(host, from, password);
			 transport.sendMessage(message, message.getAllRecipients());
			 transport.close();
		}
		System.out.println("Reports emailed");

	}

	private Session getSession() {
		Authenticator authenticator = new Authenticator(from, password);
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.auth", "true");
		properties.setProperty("mail.smtp.submitter", authenticator
				.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", String.valueOf(port));
		return Session.getInstance(properties, authenticator);
	}

	private String setBodyText() throws IOException {
		List<String> failedResultsList = printFailedTestInformation();
		String[] failedResultArray = new String[failedResultsList.size()];
		for (int i = 0; i < failedResultArray.length; i++) {
			failedResultArray[i] = failedResultsList.get(i);
		}
		String mailtext = "";

		mailtext = "Hi All,<br>";
		mailtext = mailtext + "</br><b>" + projectName
				+ " Test Automation Result:: </b></br><br>";
		mailtext = mailtext
				+ "<br><b><font style = Courier, color = green>Test Name: </font></b>"
				+ getTestName();

		mailtext = mailtext + "<br><b><font color = green>Browser: </font></b>"
				+ getProperty("./Config.properties", "browser").toUpperCase();
		mailtext = mailtext
				+ "<br><b><font color = green>Test Case Executed By: </font></b>"
				+ projectName + " Automation Team";
		mailtext = mailtext
				+ "<br><b><font color = green>Test Date: </font></b>" + today;
		mailtext = mailtext + "<b>" + testSetResult() + "</b>";

		mailtext = mailtext + "<br><br>";
		mailtext = mailtext
				+ "<br><br>Note: This is a system generated mail. Please do not reply."
				+ "</br></br>";
		mailtext = mailtext
				+ "<br>If you have any queries mail to <a href=mailto:" + from
				+ "?subject=Reply-of-Automation-Status"
				+ today.replaceAll(" ", "_") + ">" + projectName
				+ " AUTOMATION </a></br>";
		mailtext = mailtext
				+ "<br><br>The detailed test results are given in the attached <i>emailable-report.html</i> </br></br>";
		mailtext = mailtext + "<br><br>Best Regards" + "</br></br>";
		mailtext = mailtext + "<br>" + projectName + " Automation Team"
				+ "</br>";
         
		return mailtext;
	}

	private String setMailSubject() {

		return (projectName + " Automated Test Results: " + failureResults
				+ " Failures | " + today);
	}

	private void setMailRecipient(Message message) throws AddressException,
			MessagingException, IOException {

		Map<String, Object> emailMap = YamlReader
				.getYamlValues("email.recepients");
		for (Object val : emailMap.values()) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					val.toString()));
		}
		/*message.addRecipient(Message.RecipientType.BCC, new InternetAddress(
				"deepanshugarg@qainfotech.net"));*/

	}

	private Multipart setAttachment() throws MessagingException, IOException {
		// Create the message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setContent(setBodyText(), "text/html");

		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		addAttachment(multipart, messageBodyPart,
				"./target/surefire-reports/emailable-report.html");

		return multipart;
	}

	private static void addAttachment(Multipart multipart,
			MimeBodyPart messageBodyPart, String filename)
			throws MessagingException {
		messageBodyPart = new MimeBodyPart();
		File f = new File(filename);
		DataSource source = new FileDataSource(f);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(f.getName());
		multipart.addBodyPart(messageBodyPart);
	}

	private String getTestName() {
		String test = System.getProperty("test", "null");
		String testsuite = System.getProperty("testsuite", "null");
		String testName;
		if (test != "null") {
			testName = test + " was executed";
			return testName;
		} else if (testsuite != "null") {
			testName = testsuite + "were executed";
			return testName;
		} else {
			testName = "Complete Automated SMOKE Test Suite Executed";
			return testName;
		}
	}

	@SuppressWarnings("unused")
	private String getTextFile() {
		String textFile = "";
		File folder = new File("./target/surefire-reports/");
		String[] fileNames = folder.list();
		int total = 0;
		for(int i = 0; i < fileNames.length; i++){
			if (fileNames[i].contains(".txt")) {
				total++;
				assert total == 1;
				textFile = fileNames[i];
				System.out.println("Text File Path: " + textFile);
				return textFile;
			}
		}
		return textFile;
	}

	private String testSetResult() throws IOException {
		String messageToBeSent = "";
		String overallRes = "";

		String filepath = "./target/surefire-reports/testng-results.xml";
		overallRes = parseTestNgXmlFile(filepath);
		messageToBeSent = "<br>" + overallRes;
		return messageToBeSent;
	}

	private String parseTestNgXmlFile(String filepath) throws IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document dom = null;
		String msg = null;
		String value;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.parse(filepath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		NodeList testNodes = dom.getElementsByTagName("test");
		int totalTest = 0,totalPass=0,totalFail=0,totalSkip=0;
		int test=0;
		int skipTest = 0, passTest = 0, failTest = 0;
		
		msg="<br><table border=1>";
		msg=msg+"<tr bgcolor=#99ccff><b>";
		msg=msg+"</b><th>Department Name</th><th>Total Tests</th><th>Passed Tests</th><th>Failed Tests</th><th>Skip Tests</th></tr>";
		
		for (int i = 0; i < testNodes.getLength(); i++) {
			Element ele = (Element) testNodes.item(i);
			NodeList listClassNode = ele.getElementsByTagName("class");
			for (int j = 0; j < listClassNode.getLength(); j++) {
				Element testMethod = (Element) listClassNode.item(j);
				className=testMethod.getAttribute("name").substring(15);
				NodeList listMethodNode = testMethod
						.getElementsByTagName("test-method");
				for (int k = 0; k < listMethodNode.getLength(); k++) {
					Element methodNode = (Element) listMethodNode.item(k);
					if (!(methodNode.getAttribute("name").equals(
							"take_screenshot_on_failure") || methodNode
							.getAttribute("name").equals("Close_Test_Session") || methodNode
							.getAttribute("name").equals("Step00_OpenBrowserWindow") || methodNode
							.getAttribute("name").equals("methodName")  || methodNode
							.getAttribute("name").equals("step_BeforeMethod"))) {
						if (methodNode.getAttribute("status").equals("PASS")) {
							passTest++;
						}
						if (methodNode.getAttribute("status").equals("FAIL")) {
							FailureClassName.add(className);
							FailureMethodName.add(methodNode.getAttribute("name"));
							NodeList failureNode = testMethod
									.getElementsByTagName("exception");
							Element ee=(Element)failureNode.item(0);
							FailureDescription.add(ee.getAttribute("class"));
							
							String fff=ee.getChildNodes().item(1).getTextContent().trim();
							if(fff.split("\n").length==1)
							{
								FailureReason.add(fff);
							}
							
							failTest++;
						}
						if (methodNode.getAttribute("status").equals("SKIP")) {
							skipTest++;
						}
						totalTest++;
						test++;
					}
				}
			}			
			msg=msg+"<tr><td><b>"+ele.getAttribute("name")+"</b></td><td>"+test+"</td><td>"+passTest+"</td><td>"+failTest+"</td><td>"+skipTest+"</td>";
			msg=msg+"</tr>";
			totalPass+=passTest;
			totalFail+=failTest;
			totalSkip+=skipTest;
			passTest = 0;
			test=0;
			// totalTest=0;
			skipTest = 0;
			failTest = 0;
			
			
		}
		msg=msg+"<tr bgcolor=#99ccff"+"><td><b>"+"Total"+"</b></td><td>"+totalTest+"</td><td>"+totalPass+"</td><td>"+totalFail+"</td><td>"+totalSkip+"</td>";
		msg=msg+"</span></tr>";
		msg=msg+"</table>";
		failureResults=String.valueOf(totalFail);
		msg+="<br><p style=\"font-size:115%;\"><u>Failures Category Table :</u></p>";
		int developerIssues=0,scriptIssues=0;
		for(int i=0;i<FailureDescription.size();i++)
		{
			if(FailureDescription.get(i).contains("AssertionError"))
			{	developerIssues++;
				DeveloperFailureClass.add(FailureClassName.get(i));
				DeveloperFailureMethod.add(FailureMethodName.get(i));
			}
			else
				{
				scriptIssues++;
				ScriptFailureClass.add(FailureClassName.get(i));
				ScriptFailureMethod.add(FailureMethodName.get(i));
				}

		}
		
		msg+="<table border=1>";
		msg=msg+"<tr bgcolor=#99ccff><b>";
		msg=msg+"</b><th>Total Issues</th><th>Assertion Failures</th><th>Failures to be investigate</th></tr>";
		msg=msg+"<tr><td><b>"+FailureDescription.size()+"</b></td><td>"+developerIssues+"</td><td>"+scriptIssues+"</td>";
		msg+="</table>";
		msg+="</b><br>";
		//msg+="<b><br><p style=\"font-size:115%;\">Failures Description</p></b>";
		if(developerIssues>0)
		{
		msg+="<br><br><p style=\"font-size:105%;\"><u><b>Failures Description Which occur due to assertion failures</b></u></p><br>";}
		for(int i=0;i<developerIssues;i++)
		{
			msg+="<p style=\"font-size:100%;\">("+(i+1)+".) Class Name : "+DeveloperFailureClass.get(i)+"</p>";	
			msg+="<p style=\"font-size:100%;\">Method Name : "+DeveloperFailureMethod.get(i)+"</p>";
			msg+="<p style=\"font-size:100%;\"><b>Reason</b> : "+FailureReason.get(i)+"</p>";
		}
		
		/*msg+="<br><p style=\"font-size:100%;\"><u><b>Failures To Be Investigate</b></u></p>";
		for(int i=0;i<scriptIssues;i++)
		{
			msg+="<p style=\"font-size:100%;\">("+(i+1)+".) Class Name : "+ScriptFailureClass.get(i)+"</p>";	
			msg+="<p style=\"font-size:100%;\">Method Name : "+ScriptFailureMethod.get(i)+"</p>";
		}*/
		
		return msg;
	}


	@SuppressWarnings("unused")
	private String checkFilePresent() {
		File folder = new File("./target/surefire-reports");
		String[] fileNames = folder.list();
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].contains("TEST-TestSuite")) {
				return "./target/surefire-reports/" + fileNames[i];
			} else if (fileNames[i].contains("TEST-com")) {
				return "./target/surefire-reports/" + fileNames[i];
			}
		}
		return "";
	}

	@SuppressWarnings("unused")
	private void parseTestNgXmlFile1(String filepath) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document dom = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.parse(filepath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NodeList nodes = dom.getElementsByTagName("testng-results");
		Element ele = (Element) nodes.item(0);
		totaltest = ele.getAttribute("total");
		passedResults = ele.getAttribute("passed");
		failureResults = ele.getAttribute("failed");
		skippedResults = ele.getAttribute("skipped");
	}

	private List<String> printFailedTestInformation() {
		String filepath = "./target/surefire-reports/testng-results.xml";
		File file = new File(filepath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document dom = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> list = identifyTagsAndTraverseThroguhElements(dom);
		System.out.println("Number of Failed Test Cases:- " + count);
		return list;
	}

	private List<String> identifyTagsAndTraverseThroguhElements(Document dom) {

		List<String> list = new ArrayList<String>();

		NodeList nodes = dom.getElementsByTagName("test-method");
		try {
			NodeList nodesMessage = dom.getElementsByTagName("full-stacktrace");
			for (int i = 0, j = 0; i < nodes.getLength()
					&& j < nodesMessage.getLength(); i++) {

				Element ele1 = (Element) nodes.item(i);
				Element ele2 = (Element) nodesMessage.item(j);

				if (ele1.getAttribute("status").equalsIgnoreCase("FAIL")) {
					count++;
					String[] testMethodResonOfFailure = getNameTestReason(ele1,
							ele2);
					list.add(testMethodResonOfFailure[0]);
					list.add(testMethodResonOfFailure[1]);
					list.add(testMethodResonOfFailure[2]);

					j++;
				}
			}
		} catch (Exception e) {
			System.out.println("No Failures");
			Reporter.log("No Failures!!");
		}
		return list;

	}

	private String[] getNameTestReason(Element el1, Element el2) {
		String[] returnNameTestReason = new String[3];
		NamedNodeMap name = el1.getParentNode().getParentNode().getAttributes();

		returnNameTestReason[0] = name.getNamedItem("name").toString()
				.replaceAll("name=", "");
		returnNameTestReason[1] = el1.getAttribute("name");
		returnNameTestReason[2] = el2.getTextContent();
		return returnNameTestReason;

	}

	@SuppressWarnings("unused")
	private String giveTable(String[] failedResults) {
		String table = "";
		table = table
				+ "<table border='3'><tbody><tr style='background:red'><th><b>Test Case</b></th>"
				+ "<th><b>Test Method</b></th></tr>";

		for (int k = 0; k < failedResults.length; k += 3) {
			table = table + "<tr valign='top'><b>" + failedResults[k] + "</b>"
					+ "<b><td>" + failedResults[k + 1] + "</b></tr>";

		}

		table = table + "</tbody></table>";
		return table;
	}
}
