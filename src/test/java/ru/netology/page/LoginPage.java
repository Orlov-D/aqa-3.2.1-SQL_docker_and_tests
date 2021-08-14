package ru.netology.page;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[name=login]").setValue(info.getLogin());
        $("[name=password]").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void notValidLogin(DataHelper.AuthInfo info) {
        $("[name=login]").doubleClick();
        $("[name=login]").sendKeys(Keys.DELETE);
        $("[name=login]").setValue(info.getLogin());
        $("[name=password]").doubleClick();
        $("[name=password]").sendKeys(Keys.DELETE);
        $("[name=password]").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
    }

    public void wrongUserMessage(){
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    public void blockMessage(){
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Система заблокирована! Попробуйте через минуту"));
    }
}