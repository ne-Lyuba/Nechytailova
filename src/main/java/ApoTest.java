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
        $("#header-menu").shouldBe(visible);
    }

    @Test(priority = 2)
    public void acceptCookiesTest() throws InterruptedException {
        open("https://apomeds.com/de");
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"accept_cookie\"]")).click();
        $(By.className("/cookie-content")).shouldBe(disappear);

    }

    @Test(priority = 3)
    public void openLoginPopupTest() throws InterruptedException {
        open("https://apomeds.com/de");
        Thread.sleep(2000);
        $("#login-on-header").click();
        $(By.className("popup-login-main")).shouldBe(visible);
    }

    @Test(priority = 4)
    public void closeDiscountPopupTest() throws InterruptedException {
        open("https://apomeds.com/de");
        // discount popup appears after 6-8 seconds on the page
        Thread.sleep(7000);
        $(By.className("close-btn")).click();
        $(By.xpath("//*[@id=\"content-wrapper\"]//welcome-info-modal")).shouldBe(disappear);
    }

    @Test(priority = 5)
    public void clickOnSelectTreatmentButtonTest() {
        open("https://apomeds.com/de");
        $("#select-treatment-on-banner").shouldHave(text("Wählen Sie eine Behandlung")).click();
        $(By.className("popup-scroll")).shouldBe(visible);

    }

    @Test(priority = 6)
    public void clickOnManCategoryMainMenuTest() {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"header-menu\"]/li[1]")).shouldHave(text("Männergesundheit")).click();
        url("https://apomeds.com/de/produkt-kategorie/maennergesundheit");
        $(By.xpath("//*[@id=\"content-wrapper\"]/section/h2")).shouldHave(text("Männergesundheit – was beinhaltet sie?"));
    }

    @Test(priority = 7)
    public void clickOnPopularTreatmentCategoryTest() throws InterruptedException {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"content-wrapper\"]//section")).scrollIntoView(true);
        $(By.xpath("//*[@id=\"content-wrapper\"]//section/ul/li[6]")).shouldHave(text("Akne")).click();
        url("https://apomeds.com/de/produkt-kategorie/wohlbefinden/akne");
        Thread.sleep(1000);
        $(By.className("category-subtitle")).shouldHave(text("Erhalten Sie Ihre Behandlung gegen Akne direkt per Post, diskret verpackt"));
    }

    @Test(priority = 8)
    public void searchProductTest() throws InterruptedException {
        open("https://apomeds.com/de");
        $(By.xpath("//*[@id=\"content-wrapper\"]//section")).scrollIntoView(true);
        $(By.xpath("//*[@id=\"main-header\"]//input")).click();
        $(By.xpath("//*[@id=\"main-header\"]//input")).sendKeys("cobraplus");
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"main-header\"]//button")).click();
        $(By.xpath("//*[@id=\"main-header\"]/div/div/quick-product-search/quick-search-results/div[2]/qs-hit-products/div/qs-hit-product/a")).click();
        $(By.className("product-ts-title")).shouldHave(text("CobraPlus Gummis"));
    }

}

