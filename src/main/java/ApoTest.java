import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverConditions.url;



public class ApoTest {


    @Test(priority = 1)
    public void mainPageTest() {
        open("https://apomeds.com/");
        $(By.xpath("//*[@id=\"de\"]")).click();
        url("https://apomeds.com/de");
    }

    @Test(priority = 2)
    public void acceptCookiesTest() throws InterruptedException {
        open("https://apomeds.com/de");
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"accept_cookie\"]")).click();
        $(By.xpath("/html/body/cookie-policy/div/div[2]/div")).shouldBe(disappear);
    }

    @Test(priority = 3)
    public void openLoginPopupTest() throws InterruptedException {
        open("https://apomeds.com/de");
        Thread.sleep(2000);
        $("#login-on-header").click();
        $(By.xpath("//*[@id=\"content-wrapper\"]/login-sidebar/sidebar-right/div[2]/div[2]/div[1]/sidebar-form/sidebar-form-login/h3")).shouldHave(text("Melden Sie sich in Ihrem Apomeds Konto an"));
    }

    @Test(priority = 4)
    public void closeDiscountPopupTest() throws InterruptedException {
        open("https://apomeds.com/de");
        // discount popup appears after 6-8 seconds on the page
        Thread.sleep(7000);
        $(By.xpath("//*[@id=\"content-wrapper\"]/welcome-modal/welcome-info-modal"));
        $(By.xpath("//*[@id=\"content-wrapper\"]/welcome-modal/welcome-info-modal/button")).click();
        $(By.xpath("//*[@id=\"content-wrapper\"]/welcome-modal/welcome-info-modal")).shouldBe(disappear);
    }

    @Test(priority = 5)
    public void clickOnSelectTreatmentButtonTest() {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"select-treatment-on-banner\"]")).shouldHave(text("Wählen Sie eine Behandlung")).click();
        $(By.xpath("//*[@id=\"content-wrapper\"]/treatment-sidebar/sidebar-right/div[2]")).shouldBe(visible);

    }

    @Test(priority = 6)
    public void clickOnManCategoryMainMenuTest() {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"header-menu\"]/li[1]")).click();
        url("https://apomeds.com/de/produkt-kategorie/maennergesundheit");
    }

    @Test(priority = 7)
    public void clickOnPopularTreatmentCategoryTest() {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"content-wrapper\"]/main/div[1]/section")).scrollIntoView(true);
        $(By.xpath("//*[@id=\"content-wrapper\"]/main/div[1]/section/ul/li[6]")).shouldHave(text("Akne")).click();
        url("https://apomeds.com/de/produkt-kategorie/wohlbefinden/akne");
    }

    @Test(priority = 8)
    public void searchProductTest() throws InterruptedException {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"content-wrapper\"]/main/div[1]/section")).scrollIntoView(true);
        $(By.xpath("//*[@id=\"main-header\"]/div/div/quick-product-search/form/input")).click();
        $(By.xpath("//*[@id=\"main-header\"]/div/div/quick-product-search/form/input")).sendKeys("cobraplus");
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"main-header\"]/div/div/quick-product-search/form/button")).click();
        $(By.xpath("//*[@id=\"main-header\"]/div/div/quick-product-search/quick-search-results/div[2]/qs-hit-products/div/qs-hit-product/a")).click();
        url("https://apomeds.com/de/produkt/cobra-plus-gummies?qty=1&type=subscription&sp=3_month");
    }

}

