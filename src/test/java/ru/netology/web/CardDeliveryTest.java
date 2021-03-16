package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    DataGenerator generator = new DataGenerator();

    @BeforeEach
    public void clean() {
        open("http://localhost:9999");
        clearBrowserCookies();
        refresh();
    }

    @Test
    public void shouldGetCardForm (){
        open("http://localhost:9999");
        $("h2.heading").shouldHave(Condition.text("Карта с доставкой"));
        $("[data-test-id=city] .input__control").shouldHave(Condition.attribute("placeholder", "Город"));
        $("[data-test-id=phone] .input__top").shouldHave(Condition.exactText("Мобильный телефон"));
    }

    @Test
    public void shouldGetSuccessForm() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $("[data-test-id='name'] .input__control").setValue(generator.getName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(generator.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification'].notification_visible .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + generator.getDate(3)));
    }

    @Test public void shouldGetChangeDateAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $("[data-test-id='name'] .input__control").setValue(generator.getName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(generator.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification'].notification_visible .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + generator.getDate(3)));
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(4));
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldHave(Condition.cssClass("notification_visible"));
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='replan-notification']").shouldNotHave(Condition.cssClass("notification_visible"));
        $("[data-test-id='success-notification'].notification_visible .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + generator.getDate(4)));
    }

    @Test
    public void shouldHaveEmptyCityAlert() {
        $(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldHaveWrongCityAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("us"));
        $(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldHaveEmptyDateAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    public void shouldHaveWrongDateAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(0));
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldHaveEmptyNameAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldHaveWrongNameAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $("[data-test-id='name'] .input__control").setValue(generator.getName("us"));
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldHaveEmptyPhoneAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $("[data-test-id='name'] .input__control").setValue(generator.getName("ru"));
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldHaveWrongPhoneAlert() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $("[data-test-id='name'] .input__control").setValue(generator.getName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(generator.getRandomNumber());
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldHaveUncheckedCheckboxText() {
        $("[data-test-id=city] .input__control").setValue(generator.getCity("ru"));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(generator.getDate(3));
        $("[data-test-id='name'] .input__control").setValue(generator.getName("ru"));
        $("[data-test-id='phone'] .input__control").setValue(generator.getPhone());
        $(".button").click();
        $("[data-test-id='agreement']").shouldHave(Condition.cssClass("input_invalid"));
    }
}


