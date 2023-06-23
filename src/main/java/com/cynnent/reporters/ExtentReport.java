package com.cynnent.reporters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
    private static ExtentReports extent;
    private static Logger log = LogManager.getLogger(ExtentReport.class);

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);

        String reportPath = System.getProperty("user.dir") + "/Reports/ExecutionReport_" + actualDate + ".html";
        log.info("Creating ExtentReports instance with report path: {}", reportPath);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Numeracle");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Numeracle Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        log.info("ExtentReports instance created successfully");
        return extent;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            log.info("ExtentReports flushed successfully");
        }
    }
}
