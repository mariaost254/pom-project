package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectBase {
    private static final int TIMEOUT = 30;

    protected WebDriver driver;
    private WebDriverWait wait;


    public PageObjectBase(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public void waitForElementToAppear(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToDisAppear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementToBeInteractable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void checkThatPageIsCompletelyLoaded() {

        wait.until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}
