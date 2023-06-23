package com.cynnent.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.cynnent.emailReport.EmailSender;
import com.cynnent.extentfactory.ExtentFactory;
import com.cynnent.framework.TestContextManager;
import com.cynnent.reporters.ExtentReport;
import com.cynnent.utils.TestNGUtils;

public class CustomITestListener implements ITestListener {
	private static final Logger log = LogManager.getLogger(CustomITestListener.class);

	private ExtentReports report;
	// private ExtentTest test;
	// ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();

	@Override
	public void onStart(ITestContext context) {
		log.info("*** Test Suite {} started ***", context.getName());
		try {
			log.info("Initializing ExtentReports instance...");
			report = ExtentReport.getInstance();
			TestContextManager.setTestContext(context);
			log.info("TestContext set for thread: " + Thread.currentThread().getId());
		} catch (Exception e) {
			log.error("Failed to get the ExtentReports instance: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// @Override
	/*
	 * public void onTestStart(ITestResult result) {
	 * log.info("*** Running Test Case - {} ...", result.getTestClass().getName());
	 * log.info("*** Running Test - {}...", result.getMethod().getMethodName());
	 * 
	 * 
	 * String browsername =
	 * result.getTestContext().getCurrentXmlTest().getParameter("browser"); String
	 * testdescription = result.getMethod().getDescription(); test =
	 * report.createTest(result.getMethod().getMethodName() + "_" + browsername,
	 * testdescription); test.assignCategory(result.getMethod().getMethodName());
	 * ExtentFactory.getInstance().setExtent(test); TestNGUtils.setExtentTest(test);
	 * // System.out.println("CustomItestlistener : "+ExtentFactory.getInstance().
	 * getExtent()); }
	 */
	@Override
	public void onTestStart(ITestResult result) {
		log.info("*** Running Test Case - {} ...", result.getTestClass().getName());
		log.info("*** Running Test - {}...", result.getMethod().getMethodName());

		String browserName = result.getTestContext().getCurrentXmlTest().getParameter("browser");
		String testDescription = result.getMethod().getDescription();
		String testName = result.getMethod().getMethodName() + "_" + browserName;

		ExtentTest test = report.createTest(testName, testDescription);
		// extent.set(test);
		test.assignCategory(result.getMethod().getMethodName());
		ExtentFactory.getInstance().setExtent(test);
		TestNGUtils.setExtentTest(test);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("*** Executed Test Case - " + result.getTestClass().getName() + " ...");
		log.info("*** Executed Test - " + result.getMethod().getMethodName() + " Passed...");
		ExtentTest test = ExtentFactory.getInstance().getExtent();
		test.pass(result.getMethod().getDescription() + " - Passed");
		// TestNGUtils.getExtentTest();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.error("Test Case Failed: " + result.getMethod().getDescription(), result.getThrowable());
		ExtentTest test = ExtentFactory.getInstance().getExtent();
		test.fail(result.getMethod().getDescription());
		// TestNGUtils.getExtentTest();
		// TestContextManager.getTestContext()
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.warn("Test Skipped: " + result.getMethod().getDescription());
		ExtentTest test = ExtentFactory.getInstance().getExtent();
		test.skip(result.getMethod().getDescription());
		// TestNGUtils.getExtentTest();
	}

	@AfterMethod
	public void cleanupTestMethod() {
		TestNGUtils.removeExtentTest();
	}

	
	  @Override public void onFinish(ITestContext context) { 
		  report.flush();
		  log.info("*** Test Suite " + context.getName() + " finished ***");
	  
			/*
			 * SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); Date
			 * date = new Date(); String actualDate = format.format(date); String
			 * reportFilePath = System.getProperty("user.dir") + "/Reports/ExecutionReport_"
			 * + actualDate + ".html";
			 */
	  //String reportFilePath ="C:\\Users\\Sai Krishna M\\Downloads\\OneDrive_2023-06-21\\WebTesting_Framework - Copy\\test-output\\emailable-report.html"
	  ; //String reportFilePath = System.getProperty("user.dir") + File.separator "emailable-report.html"; 
	  //String reportFilePath = System.getProperty("user.dir")+ "/test-output/emailable-report.html"; 
	 // String reportFilePath = System.getProperty("user.dir") +"/Reports/ExecutionReport.html"; 
	  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
      Date date = new Date();
      String actualDate = format.format(date);
	   String reportPath = System.getProperty("user.dir") +
               "/Reports/ExecutionReport_" + actualDate + ".html";
	  EmailSender.sendEmailReport(reportPath);
	  }
	 

	// Other methods are left as it is
	/*
	 * @Override public void onFinish(ITestContext context) { report.flush();
	 * log.info("*** Test Suite " + context.getName() + " finished ***");
	 * 
	 * SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); Date
	 * date = new Date(); String actualDate = format.format(date); String
	 * reportFilePath = System.getProperty("user.dir") + "/Reports/ExecutionReport_"
	 * + actualDate + ".html";
	 * 
	 * // Rename the report file to include the current date File oldFile = new
	 * File(System.getProperty("user.dir") + "/Reports/ExecutionReport.html"); File
	 * newFile = new File(reportFilePath); boolean fileRenamed =
	 * oldFile.renameTo(newFile);
	 * 
	 * if (fileRenamed) { log.info("Extent report file renamed successfully."); }
	 * else { log.warn("Failed to rename extent report file."); }
	 * 
	 * EmailSender.sendEmailReport(reportFilePath); }
	 */

}
