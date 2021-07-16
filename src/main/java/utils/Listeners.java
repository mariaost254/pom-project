package utils;


import base.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends TestBase implements ITestListener {

    ExtentReports extentReports = ExtendReportInit.extentReportGenerator();
    ExtentTest test;
    static Logger logger = LoggerFactory.getLogger(Listeners.class);

    private static ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<>(); //to prevent tests collisions/deadlock(overriding)
    @Override
    public void onTestStart(ITestResult result) {
        test = extentReports.createTest(result.getTestClass().getName()+" "+result.getMethod().getDescription());
        threadLocal.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info(result.getMethod().getMethodName() + "- test passed");
        threadLocal.get().log(Status.PASS,result.getMethod().getMethodName()+ " - test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null ;
        threadLocal.get().log(Status.FAIL,result.getMethod().getMethodName()+ " - test failed");
        threadLocal.get().fail(result.getThrowable());
        Object testObject = result.getInstance();
        Class cla= result.getTestClass().getRealClass();//get access to the actual class
        try {
            driver = (WebDriver) cla.getSuperclass().getDeclaredField("driver").get(testObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try{
            String b = getScreenshotPath(result.getMethod().getMethodName(),driver);
            threadLocal.get().addScreenCaptureFromPath(b,result.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
