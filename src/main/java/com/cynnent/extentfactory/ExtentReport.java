package com.cynnent.extentfactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentReport {
    private static final Logger log = LogManager.getLogger(ExtentReport.class);
    private static ExtentReports extent;

    public static ExtentReports setupExtentReport() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);

        String reportPath = System.getProperty("user.dir") +
                "/Reports/ExecutionReport_" + actualDate + ".html";

        ExtentSparkReporter sparkReport = new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();
        extent.attachReporter(sparkReport);

        sparkReport.config().setDocumentTitle("Numeracle");
        sparkReport.config().setTheme(Theme.DARK);
        sparkReport.config().setReportName("Numeracle Execution Report");

        log.info("Extent report setup completed.");

        return extent;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            log.info("Extent report flushed successfully.");
        }
    }
}
