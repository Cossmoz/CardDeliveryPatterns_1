package ru.netology;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;

public class CardDeliveryTest {
    private SelenideElement form;
    private final String city = getRandomCity();
    private final String date = getDate(3);
    private final String dateReplan = getDate(10);
    private final String dateInvalid = getDate(1);
    private final String name = getName();
    private final String phone = getPhone();

    @BeforeEach
    void BeforeAll() {
        open("http://localhost:9999");
        form = $("[action='/']");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void testPositiveAllFieldsWithValidData() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000)
                .shouldHave(text(date));

    }
    @Test
    void testPositiveAllFieldsWithValidDataReplan() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000)
                .shouldHave(text(date));
        open("http://localhost:9999");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(dateReplan);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='replan-notification']").waitUntil(visible, 15000)
                .shouldHave(text("Перепланировать"));
        $("[data-test-id='replan-notification'] .button__content").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 15000)
                .shouldHave(text(dateReplan));
    }

    @Test
    void testNegativeCityFieldEmpty() {
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void testNegativeDateFieldEmpty() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='date'] .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    void testNegativeDateLess3day() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(dateInvalid);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='date'] .input__sub").shouldBe(visible)
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void testNegativeNameFieldEmpty() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='name'] .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void testNegativePhoneFieldEmpty() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        form.$("[data-test-id='phone'] .input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void testNegativeAgreementFieldEmpty() {
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$(".button__content").click();
        form.$("[data-test-id='agreement'].input_invalid").shouldBe(visible)
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}