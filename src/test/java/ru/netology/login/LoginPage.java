package ru.netology.login;

import org.openqa.selenium.Keys;

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
}