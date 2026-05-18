package com.aibanking.pages;

import com.aibanking.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By USERNAME_FIELD = By.name("username");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By LOGIN_BUTTON   = By.cssSelector("input[value='Log In']");
    private static final By ERROR_MESSAGE  = By.cssSelector(".error");

    public LoginPage open() {
        driver.get(Config.BASE_URL + "/index.htm");
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(USERNAME_FIELD, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD_FIELD, password);
        return this;
    }

    public LoginPage clickLogin() {
        click(LOGIN_BUTTON);
        return this;
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("overview");
    }

    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void login(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}