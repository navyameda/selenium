package com.cynnent.framework;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.cynnent.driverfactory.ChromeDriverFactory;
import com.cynnent.driverfactory.EdgeDriverFactory;
import com.cynnent.driverfactory.FirefoxDriverFactory;
import com.cynnent.driverfactory.WebDriverFactory;
import com.cynnent.exceptions.NoSuitableDriverException;
import com.cynnent.utils.TestNGUtils;

public class TestBase {
	private static final Logger log = LogManager.getLogger(TestBase.class);

	protected static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	protected static Actions actions;
	protected static WebDriverWait wait;
	
	// Add the ExtentTest instance
	protected static ExtentTest extentTest;

	@BeforeSuite
	public void beforeSuite(ITestContext context) {
		TestContextManager.setTestContext(context);
//        Log4jUtils.setLogLevelToError();
	}

	@BeforeMethod
	public void setUp(ITestContext context) {
		initializeDriver(context);
		initializeActions();
		initializeWait();

		String driverName = tdriver.get().getClass().getSimpleName();
		ThreadContext.put("driverName", driverName);

		// Set the ExtentTest instance in TestNGUtils
//		extentTest = ExtentFactory.getInstance().getExtent();
//		 System.out.println("Testbase : "+extentTest); 
//		TestNGUtils.setExtentTest(extentTest);
	}

	private void initializeDriver(ITestContext context) {
		String browser = TestNGUtils.getTestParameter(context, "browser");
		WebDriverFactory factory;

		switch (browser.trim().toLowerCase()) {
		case "chrome":
			factory = new ChromeDriverFactory();
			break;
		case "firefox":
			factory = new FirefoxDriverFactory();
			break;
		case "edge":
			factory = new EdgeDriverFactory();
			// Add cases for other supported browsers
			break;
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		try {
			WebDriver driver = factory.createWebDriver();
			driver.manage().window().maximize();
			tdriver.set(driver);
			log.info("WebDriver initialized for browser: " + driver);
		} catch (NoSuitableDriverException e) {
			log.error("Failed to initialize WebDriver: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void initializeActions() {
		WebDriver driver = tdriver.get();
		actions = new Actions(driver);
	}

	private void initializeWait() {
		WebDriver driver = tdriver.get();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	@AfterMethod
	public void tearDown() {
		WebDriver driver = tdriver.get();
		if (driver != null) {
			driver.quit();
			tdriver.remove();
			log.info("WebDriver instance quit.");
		}
		ThreadContext.remove("driverName");
		TestNGUtils.removeExtentTest(); // setextentest is in customitestlistener
	}

	@AfterSuite
	public void afterSuite() {
		// Remove TestContext from ThreadLocal
		TestContextManager.removeTestContext();

		// Log test suite completion
		Logger log = LogManager.getLogger(TestBase.class);
		log.info("Test suite execution completed.");
	}

	// Getter method to access the WebDriver instance
	public static WebDriver getDriver() {
		return tdriver.get();
	}

	// Getter method to access the Actions instance
	public static Actions getActions() {
		return actions;
	}

	// Getter method to access the WebDriverWait instance
	public static WebDriverWait getWait() {
		return wait;
	}
	
	// Getter method to access the ExtentTest instance
	public static ExtentTest getExtentTest() {
		return extentTest;
	}
}


//package com.cynnent.framework;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.ITestContext;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
//import com.cynnent.driverfactory.ChromeDriverFactory;
//import com.cynnent.driverfactory.EdgeDriverFactory;
//import com.cynnent.driverfactory.FirefoxDriverFactory;
//import com.cynnent.driverfactory.WebDriverFactory;
//import com.cynnent.exceptions.NoSuitableDriverException;
//import com.cynnent.pages.BasePage;
//import com.cynnent.utils.TestNGUtils;
//
//public class TestBase extends BasePage {
//	
//	protected ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
//	protected WebDriver driver;
//
//	@BeforeMethod
//	public void setUp(ITestContext context) {
//		String browser = TestNGUtils.getTestParameter(context, "browser");
////		String browser = context.getCurrentXmlTest().getParameter("browser");
//		WebDriverFactory factory;
//
//		switch (browser.trim().toLowerCase()) {
//		case "chrome":
//			factory = new ChromeDriverFactory();
//			break;
//		case "firefox":
//			factory = new FirefoxDriverFactory();
//			break;
//		case "edge":
//			factory = new EdgeDriverFactory();
//			// Add cases for other supported browsers
//		default:
//			throw new IllegalArgumentException("Unsupported browser: " + browser);
//		}
//
//		try {
//			driver = factory.createWebDriver();
//			driver.manage().window().maximize();
//			tdriver.set(driver); // Set the driver in the ThreadLocal variable
//			actions = new Actions(driver);
//            wait = new WebDriverWait(driver, 10);
//		} catch (NoSuitableDriverException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@AfterMethod
//	public void tearDown() {
//		driver=tdriver.get();
//		if (driver != null) {
//			driver.quit();
//			tdriver.remove();
//		}
//	}
//
//	// Getter method to access the WebDriver instance
//	public WebDriver driver() {
//		return driver;
//	}
//}
