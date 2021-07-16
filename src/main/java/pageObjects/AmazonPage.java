package pageObjects;

import base.PageObjectBase;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AmazonPage extends PageObjectBase {

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @FindBy(id = "nav-search-submit-text")
    private WebElement submitSearch;

    @FindBy(xpath = "//span[contains(text(),'No results for')]")
    private WebElement noResults;

    @FindBys(@FindBy(xpath = "//i[@class='a-icon a-icon-checkbox']"))
    private List<WebElement> englishFilter;

    @FindBy(xpath = "//*[@class='a-section a-spacing-small a-spacing-top-small']")
    private WebElement searchResults;

    @FindBys(@FindBy(xpath = "//span[@class='a-size-medium a-color-base a-text-normal']"))
    private List<WebElement> resultTitles;

    private Actions actions;
    public AmazonPage(WebDriver driver) {
        super(driver);
        actions = new Actions(driver);
    }

    public void searchForItem(String item){
        searchBox.clear();
        searchBox.sendKeys(item);
        actions.moveToElement(submitSearch).click().build().perform();
    }

    public Boolean getNoResults() {
        if (noResults.isDisplayed())
            return true;
        return false;
    }

    public String getResultsTitleLongest(){
        List<String> names = new ArrayList<>();
        for(int i=0;i<resultTitles.size();i++){
           names.add(resultTitles.get(i).getText());
        }
        String[] titles = new String[names.size()];
        names.toArray(titles);
        String longest = Arrays.stream(titles).max(Comparator.comparingInt(String::length)).get();
        return longest;
    }

    public void applyFilter(){
    //    actions.moveToElement(englishFilter.get(0)).click().build().perform();
        englishFilter.get(0).click();
        checkThatPageIsCompletelyLoaded();
    }

    public String searchRes(){
        return searchResults.getText();
    }

    public String getResultsNumber(String item,String res){
        if (!searchResults.isDisplayed()){return null;}
        String [] results;
        String test = res;
        test = test.replace("\"", "");
        int t =test.indexOf("results");
        test= test.substring( 0, t);
        results = test.split(" ");
        return this.getNumber(results);
    }

    public String getNumber(String [] results){
        switch (results.length){
            case 4:
                return results[3];
            case 3:
                return results[2];
            case 2:
                return results[0];
            default:
                return null;

        }
    }



}

