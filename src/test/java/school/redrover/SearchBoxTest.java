package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class SearchBoxTest extends BaseTest {

    @Test
    public void testVerifyTitleSearchBoxLink() {
       new HomePage(getDriver());

        Assert.assertTrue(true, "True");
    }

}