package testCases;

import base.BaseFinals;
import base.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import pageObjects.AmazonPage;

import static org.testng.Assert.assertTrue;

public class ExampleTest2 extends TestBase {

    private AmazonPage amazonPage;
    public static Logger logger = LoggerFactory.getLogger(ExampleTest2.class);


    @BeforeClass
    public void setUpTest(){
        driver.navigate().to(BaseFinals.ROOT_URL2);
        amazonPage = new AmazonPage(driver);
    }

    @Test(priority = 1,testName = "Validate ebay page", description = "Verify page loaded successfully ")
    public void validateMainPageIsAccessible(){
        assertTrue(driver.getTitle().contains("eBay"),"Page is unreachable");
    }


//    @Parameters({ "validSearch" })
//    @Test(priority = 2, description = "Validate page displays valid results ")
//    public void validateResults(String param){
//        amazonPage.searchForItem(param);
//        String serachRes =amazonPage.searchRes();
//        String res = amazonPage.getResultsNumber(param,serachRes);
//        assertNotNull(res);
//        logger.info("Number of search results: "+res);
//    }


//    @Parameters({ "validSearch" })
//    @Test(priority = 3, description = "Validate result titles ")
//    public void validateResultTitles(String param){
//        amazonPage.searchForItem(param);
//        String res = amazonPage.getResultsTitleLongest();
//        assertNotNull(res);
//        logger.info("Longest search result: "+res);
//    }
//
//    /**The test is running without a user, so i had many filters missing
//     * therefore im running the test with the first filter avaliable**/
//    @Parameters({ "validSearch" })
//    @Test(priority = 4, description = "Validate page displays valid results when filter is applied")
//    public void validateResultsWithFilter(String param){
//        amazonPage.searchForItem(param);
//        amazonPage.applyFilter();
//        String serachRes =amazonPage.searchRes();
//        String res = amazonPage.getResultsNumber(param,serachRes);
//        assertNotNull(res);
//        logger.info("Number of search results with filter: "+res);
//        }
//
//    @Parameters({ "invalidSearch" })
//    @Test(priority = 5, description = "Validate no results is displayed in a case of no results ")
//    public void validateNoResultsFound(String param){
//        amazonPage.searchForItem(param);
//        assertTrue(amazonPage.getNoResults(),"No search results found message in not displayed");
//    }


}
