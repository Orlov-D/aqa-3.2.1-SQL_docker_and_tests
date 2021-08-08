package ru.netology.login;

import com.codeborne.selenide.Condition;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.*;


public class LoginTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    @SneakyThrows
    public void verifyLogin() {
        $("[name=login]").setValue("vasya");
        $("[name=password]").setValue("qwerty123");
        $("[data-test-id=action-login]").click();
        var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
        var dataStmt = conn.createStatement().executeQuery("SELECT * FROM auth_codes");
        String code = "";
        try (var rs = dataStmt) {
            while (rs.next()) {
                code = rs.getString("code");
                var user_id = rs.getString("user_id");
                if (user_id.equalsIgnoreCase("7d3feb80-fa58-4395-9c5c-850b592adde5")) {
                    break;
                }
            }
            $("[class=input__control]").setValue(code);
            $("[data-test-id=action-verify]").click();
            $(".heading").shouldHave(Condition.exactText("  Личный кабинет"));
        }
    }


    @Test
    public void tripleLogin() {
        $("[name=login]").setValue("vasya");
        $("[name=password]").setValue("3");
        $("[data-test-id=action-login]").click();
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        $("[data-test-id=action-login]").click();
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        $("[data-test-id=action-login]").click();
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        $("[data-test-id=action-login]").click();
        $("[class=notification__content]").shouldBe(Condition.visible).shouldHave(Condition.exactText("Система заблокирована! Попробуйте через минуту"));
    }
}
