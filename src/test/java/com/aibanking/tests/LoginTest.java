package com.aibanking.tests;

import com.aibanking.ai.TestDataGenerator;
import com.aibanking.pages.LoginPage;
import com.aibanking.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest {

    private LoginPage loginPage;
    private static List<String[]> aiTestData;

    @BeforeClass
    public void generateTestData() {
        System.out.println("🤖 Asking Claude to generate test data...");
        TestDataGenerator generator = new TestDataGenerator();
        aiTestData = generator.generateLoginTestData();
        System.out.println("✅ Claude generated " + aiTestData.size() + " test cases");
    }

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage();
        loginPage.open();
    }

    @Test
    public void testValidLogin() {
        loginPage.enterUsername("john")
                .enterPassword("demo")
                .clickLogin();

        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "Login should succeed with valid credentials");
    }

    @Test
    public void testAIGeneratedLoginCases() {
        for (String[] testCase : aiTestData) {
            String username = testCase[0];
            String password = testCase[1];
            String expected = testCase[2];

            System.out.println("Testing: " + username + " / " + password
                    + " → expected: " + expected);

            DriverManager.quitDriver();
            loginPage = new LoginPage();
            loginPage.open();

            loginPage.enterUsername(username)
                    .enterPassword(password)
                    .clickLogin();

            if ("success".equals(expected)) {
                Assert.assertTrue(loginPage.isLoginSuccessful(),
                        "Expected success for: " + username);
            } else {
                Assert.assertTrue(loginPage.isErrorDisplayed(),
                        "Expected failure for: " + username);
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}