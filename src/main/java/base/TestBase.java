package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.grid.selenium.GridLauncherV3;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class TestBase {
    public WebDriver driver;
    public Runtime runtime;

    @BeforeSuite
    public void setServer(){
        try {
            runtime = Runtime.getRuntime();
            runtime.exec("cmd /c cd C:\\Users\\Maria\\Desktop\\blopz && start server1.bat");
            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public void configDriver(){
        driver =  setRemoteChrome();
        //driver.setFileDetector(new LocalFileDetector());// allows to transfer files from local to remote machine
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(BaseFinals.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(BaseFinals.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public static WebDriver setChrome(){ //regular standalone run
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    public WebDriver setRemoteChrome() { //remote driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        //
        //options.addExtensions(new File("X://extension_3_40_1_0.crx")); //optional - add extension such as adblocker
        //capabilities.setCapability("browserName", "chrome"); //optional browser set up - for different browser versions
        //capabilities.setCapability("version", "77.0");
        //
        DesiredCapabilities capabilities = new DesiredCapabilities().chrome(); //.firefox()
        //capabilities.setCapability(ChromeOptions.CAPABILITY, options); // same as line above - use this if extra options were added
        options.merge(capabilities);


        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);//url for local docker/hub - aka server
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @AfterClass
    public void closeDriver(){
        driver.quit();
    }


    @AfterSuite
    public void shutDown(){ //shutdown batch files
        try {
            runtime = Runtime.getRuntime();
            runtime.exec("cmd /c cd C:\\Users\\Maria\\Desktop\\blopz && start shutdown.bat");
            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getScreenshotPath(String testName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
       // String des = BaseFinals.RESOURCES_PATH+"/iamges/"+testName+".png";
       // File file = new File(des);
        //FileUtils.copyFile(source,file);

        String currentDir = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(source, new File(currentDir));
        return currentDir;
    }
}





/**
 * download selenium-server-standalone
 * run java -jar selenium-server-standalone-3.141.59.jar -role hub
 * download chromedriver.exe
 * run java -Dwebdriver.chrome.driver=chromedriver.exe -jar selenium-server-standalone-3.141.59.jar -port 5557 -role node -hub http://localhost:4444/grid/register -browser "browserName=chrome"
 * each session of browser should run on different port
 * http://localhost:4444/grid/console to view registered nodes
 * **/




















