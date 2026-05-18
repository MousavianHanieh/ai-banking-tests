package com.aibanking.pages;

import com.aibanking.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class TransferFundsPage extends BasePage {

    private static final By AMOUNT_FIELD    = By.id("amount");
    private static final By FROM_ACCOUNT    = By.id("fromAccountId");
    private static final By TO_ACCOUNT      = By.id("toAccountId");
    private static final By TRANSFER_BUTTON = By.cssSelector("input[value='Transfer']");
    private static final By SUCCESS_TITLE   = By.cssSelector("#showResult h1");

    public TransferFundsPage open() {
        driver.get(Config.BASE_URL + "/transfer.htm");
        return this;
    }

    public TransferFundsPage enterAmount(String amount) {
        type(AMOUNT_FIELD, amount);
        return this;
    }

    public TransferFundsPage selectFromAccount(String accountId) {
        Select select = new Select(driver.findElement(FROM_ACCOUNT));
        select.selectByValue(accountId);
        return this;
    }

    public TransferFundsPage selectToAccount(String accountId) {
        Select select = new Select(driver.findElement(TO_ACCOUNT));
        select.selectByValue(accountId);
        return this;
    }

    public TransferFundsPage clickTransfer() {
        click(TRANSFER_BUTTON);
        return this;
    }

    public boolean isTransferSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TITLE));
            String title = getText(SUCCESS_TITLE);
            System.out.println("Transfer result title: " + title);
            return title.contains("Transfer Complete");
        } catch (Exception e) {
            System.out.println("Transfer title not found: " + e.getMessage());
            return false;
        }
    }

    public String getFirstAccountId() {
        Select select = new Select(driver.findElement(FROM_ACCOUNT));
        return select.getOptions().get(0).getAttribute("value");
    }

    public String getSecondAccountId() {
        Select select = new Select(driver.findElement(FROM_ACCOUNT));
        return select.getOptions().get(1).getAttribute("value");
    }
}
