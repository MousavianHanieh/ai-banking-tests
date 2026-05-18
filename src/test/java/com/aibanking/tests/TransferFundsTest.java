package com.aibanking.tests;

import com.aibanking.ai.TestDataGenerator;
import com.aibanking.pages.LoginPage;
import com.aibanking.pages.TransferFundsPage;
import com.aibanking.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TransferFundsTest {

    private TransferFundsPage transferPage;
    private static List<String> aiAmounts;

    @BeforeClass
    public void generateTestData() {
        System.out.println("🤖 Asking Claude to generate transfer amounts...");
        TestDataGenerator generator = new TestDataGenerator();
        aiAmounts = generator.generateTransferAmounts();
        System.out.println("✅ Claude generated " + aiAmounts.size() + " amounts: " + aiAmounts);
    }

    @BeforeMethod
    public void setUp() {
        new LoginPage().open().login("john", "demo");
        transferPage = new TransferFundsPage();
        transferPage.open();
    }

    @Test
    public void testTransferPageLoads() {
        Assert.assertTrue(
                transferPage.getCurrentUrl().contains("transfer"),
                "Transfer page should be open"
        );
        System.out.println("✅ Transfer page loaded");
    }

    @Test
    public void testValidTransfer() {
        String fromAccount = transferPage.getFirstAccountId();
        String toAccount   = transferPage.getSecondAccountId();

        transferPage.selectFromAccount(fromAccount)
                .selectToAccount(toAccount)
                .enterAmount("100")
                .clickTransfer();

        Assert.assertTrue(
                transferPage.isTransferSuccessful(),
                "Transfer of $100 should succeed"
        );
        System.out.println("✅ Valid transfer passed");
    }

    @Test
    public void testAIGeneratedAmounts() {
        for (String amount : aiAmounts) {
            System.out.println("Testing transfer amount: " + amount);

            DriverManager.quitDriver();
            new LoginPage().open().login("john", "demo");
            transferPage = new TransferFundsPage();
            transferPage.open();

            String fromAccount = transferPage.getFirstAccountId();
            String toAccount   = transferPage.getSecondAccountId();

            transferPage.selectFromAccount(fromAccount)
                    .selectToAccount(toAccount)
                    .enterAmount(amount)
                    .clickTransfer();

            boolean success = transferPage.isTransferSuccessful();
            System.out.println("Amount: " + amount + " → " + (success ? "✅ success" : "❌ failed"));
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
