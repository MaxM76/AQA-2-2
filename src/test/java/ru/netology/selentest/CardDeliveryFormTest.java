package ru.netology.selentest;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class CardDeliveryFormTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        String planningDate = generateDate(7);
        SelenideElement form = $("form");
        form.$("span[data-test-id=city] input.input__control").setValue("Майкоп");
        form.$("span[data-test-id='date'] input.input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("span[data-test-id='date'] input.input__control").setValue(planningDate);
        form.$("span[data-test-id=name] input.input__control").setValue("Василий");
        form.$("span[data-test-id=phone] input.input__control").setValue("+79270000000");
        form.$("label[data-test-id=agreement]").click();
        form.$$("button").find(exactText("Забронировать")).click();
        $("div[data-test-id=notification] div.notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}