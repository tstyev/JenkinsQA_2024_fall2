package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

public class SearchBoxTest extends BaseTest {

    @Test
    public void testVerifyTitleSearchBoxLink() {
        String title = new HomePage(getDriver())
                .gotoSearchBox()
                .getTitle();

        Assert.assertEquals(title, "Search Box1");
    }

    @Test
    public void testSearch() {
        String result = new HomePage(getDriver())
                .enterSearch("built")
                .enter()
                .getResult();

        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    public void testSuggestionList() {
        String result = new HomePage(getDriver())
                .enterSearch("bu")
                .getSuggestion()
                .enter()
                .getResult();

        Assert.assertEquals(result, "Built-In Node");
    }

}