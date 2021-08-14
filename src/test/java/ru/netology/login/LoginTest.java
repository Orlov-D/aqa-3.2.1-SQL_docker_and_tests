package ru.netology.login;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.netology.data.DataHelper;
import ru.netology.db.SqlGetters;
import ru.netology.page.LoginPage;

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
        loginPage.notValidLogin(authInfo);
        loginPage.wrongUserMessage();
        loginPage.notValidLogin(authInfo);
        loginPage.wrongUserMessage();
        loginPage.notValidLogin(authInfo);
        loginPage.wrongUserMessage();
        loginPage.notValidLogin(authInfo);
        loginPage.blockMessage();
    }
}

