import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;


public class ApoTests  {


    @BeforeTest
    public void beforeTestClass() {
        System.out.println("Why are you running?");
        open("https://apomeds.com/");
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        Test test = method.getAnnotation(Test.class);
        if (test == null) {
            return;
        }
        System.out.println("Description of the current test: " + test.description());
    }


    @Description("Choose the store on main page")
    @Test(priority = 1) //description = "Choose the store on main page"
    public void mainPageTest() {
        $(By.xpath("//*[@id=\"de\"]")).click();
        url("https://apomeds.com/de");
        $("#header-menu").shouldBe(visible);
    }

    @Description("Close cookie banner")
    @Test(priority = 2)
    public void acceptCookiesTest() throws InterruptedException {
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"accept_cookie\"]")).click();
        $(By.className("/cookie-content")).shouldBe(disappear);

    }

    @Test(priority = 3, invocationCount = 3, description = "Open the login popup")
    public void openLoginPopupTest() throws InterruptedException {
        $("#login-on-header").click();
        $(By.xpath("//*[@id=\"content-wrapper\"]/login-sidebar/sidebar-right/div[2]/div[1]")).click();
        $(By.className("popup-login-main")).shouldBe(disappear);
    }

    @Test(priority = 4, successPercentage = 73, description = "Check the discount popup and it's closing")
    public void closeDiscountPopupTest() throws InterruptedException {
        // discount popup appears after 6-8 seconds on the page
        Thread.sleep(7000);
        $(By.className("close-btn")).click();
        $(By.xpath("//*[@id=\"content-wrapper\"]//welcome-info-modal")).shouldBe(disappear);
    }

    @Test(priority = 5, invocationCount = 3, description = "Check the select treatment sidebar")
    public void clickOnSelectTreatmentButtonTest() {
        $("#select-treatment-on-banner").shouldHave(text("Wählen Sie eine Behandlung")).click();
        $(By.xpath("//*[@id=\"content-wrapper\"]/treatment-sidebar/sidebar-right/div[2]/div[1]")).click();
        $(By.className("popup-scroll")).shouldBe(disappear);

    }

    @Test(priority = 6, description = "Redirect to man category")
    public void clickOnManCategoryMainMenuTest() {
        $(By.xpath("//*[@id=\"header-menu\"]/li[1]")).shouldHave(text("Männergesundheit")).click();
        url("https://apomeds.com/de/produkt-kategorie/maennergesundheit");
        $(By.xpath("//*[@id=\"content-wrapper\"]/section/h2")).shouldHave(text("Männergesundheit – was beinhaltet sie?"));
    }

    @Test(priority = 7, description = "Choose popular category")
    public void clickOnPopularTreatmentCategoryTest() throws InterruptedException {
        open("https://apomeds.com/de");
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"content-wrapper\"]//section")).scrollIntoView(true);
        $(By.xpath("//*[@id=\"content-wrapper\"]//section/ul/li[6]")).shouldHave(text("Akne")).click();
        url("https://apomeds.com/de/produkt-kategorie/wohlbefinden/akne");
        Thread.sleep(1000);
        $(By.className("category-subtitle")).shouldHave(text("Erhalten Sie Ihre Behandlung gegen Akne direkt per Post, diskret verpackt"));
    }

    @Test(priority = 8, description = "Search for items in catalog", dataProvider = "search-data-provider")
    public void searchProductTest(String searchQuery, String expectedTitle) throws InterruptedException {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"content-wrapper\"]//section")).scrollIntoView(true);
        $(By.xpath("//*[@id=\"main-header\"]//input")).click();
        $(By.xpath("//*[@id=\"main-header\"]//input")).sendKeys(searchQuery);
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"main-header\"]//button")).click();
        $(By.xpath("//*[@id=\"main-header\"]/div/div/quick-product-search/quick-search-results/div[2]/qs-hit-products/div/qs-hit-product/a")).click();
        $(By.className("product-ts-title")).shouldHave(text(expectedTitle));
    }

    @DataProvider(name = "search-data-provider")
    public Object[][] getSearchData() {
        return new Object[][]{{"cobraplus", "CobraPlus Gummis"}, {"last longer", "LastLonger"}};
    }

    @AfterTest
    public void afterClass() {
        System.out.println("I don't have the motivation and energy to do all this anymore, I have tried everything and now I am exhausted.");
        clearBrowserCookies();
    }
}

