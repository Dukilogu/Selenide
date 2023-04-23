import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideNegativeTest {

    public static String setLocalDate(int day) {
        String date = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestForDay2() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(2));
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestForDay1() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(-1));
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=date] .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestForEmptyCity() {
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(3));
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=city] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestForEmptyDate() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=date] .input__sub").shouldHave(Condition.text("Неверно введена дата"));
    }

    @Test
    void shouldTestForEmptyName() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(3));
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestForLatinaName() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(3));
        $("[data-test-id=name] .input__control").setValue("Antonov Dmitriy");
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestForEmptyPhone() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(3));
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestForWrongPhone() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(3));
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=phone] .input__control").setValue("+7987872362443");
        $("[data-test-id=agreement]").click();
        $(".button__content").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestForEmptyCheckBox() {
        $("[data-test-id=city] .input__control").setValue("Воронеж");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(setLocalDate(3));
        $("[data-test-id=name] .input__control").setValue("Антонов Дмитрий");
        $("[data-test-id=phone] .input__control").setValue("+79878723624");
        $(".button__content").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
}
