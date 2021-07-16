package utils;

import base.BaseFinals;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtendReportInit {
    static ExtentReports extentReports;
    static ExtentSparkReporter sparkReporter;

    public static ExtentReports extentReportGenerator(){
        String si = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" Test Name";
        String path = BaseFinals.RESOURCES_PATH+"/reports/test.html";
        sparkReporter = new ExtentSparkReporter(path);
        sparkReporter.config().setDocumentTitle("Ree");
        sparkReporter.config().setReportName(si);
        sparkReporter.config().setDocumentTitle("Test Results");
        sparkReporter.config().setTheme(Theme.DARK);

         extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Tester","Maria Ost");
        extentReports.setSystemInfo("OS", "Windows");
        extentReports.setSystemInfo("Host Name", "CI");
        extentReports.setSystemInfo("Environment", "QA");

        return extentReports;
    }
}
