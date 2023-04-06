package ru.netology.selentest;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;

public class CardDeliveryFormTest {
    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("span[data-test-id=city] input.input__control").setValue("Майкоп");
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 7);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        form.$("span[data-test-id=date] input.input__control").setValue(formatter.format(date.getTime()));
        form.$("span[data-test-id=name] input.input__control").setValue("Василий");
        form.$("span[data-test-id=phone] input.input__control").setValue("+79270000000");
        form.$("label[data-test-id=agreement]").click();
        form.$$("button").find(exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(1));
    }
}
