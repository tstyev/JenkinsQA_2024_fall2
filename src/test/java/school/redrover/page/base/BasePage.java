package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.HomePage;
import school.redrover.page.SearchBoxPage;

public abstract class BasePage extends BaseModel {

    @FindBy(id = "jenkins-home-link")
    private WebElement logo;

    @FindBy(xpath = "//a[contains(@class,'main-search')]")
    private WebElement iconQuestion;


    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        logo.click();

        return new HomePage(getDriver());
    }

    public SearchBoxPage gotoSearchBox() {
        iconQuestion.click();

        return new SearchBoxPage(getDriver());
    }
}
