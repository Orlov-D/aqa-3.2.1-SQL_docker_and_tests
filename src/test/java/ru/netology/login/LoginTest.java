package ru.netology.login;

import com.codeborne.selenide.Condition;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
    }

    @AfterAll
    public void cleanUp() {
        SqlGetters sqlGetters = new SqlGetters();
        sqlGetters.cleanDatabase();
    }

    @Test
    @SneakyThrows
    public void verifyLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        SqlGetters sqlGetters = new SqlGetters();
        var verificationCode = sqlGetters.getCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void tripleLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getWrongAuthInfo();
        for (int i = 0; i < 4; i++) {
            loginPage.notValidLogin(authInfo);
            if (i != 3) {
                $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
            } else {
                $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Система заблокирована! Попробуйте через минуту"));
            }
        }
    }
}
