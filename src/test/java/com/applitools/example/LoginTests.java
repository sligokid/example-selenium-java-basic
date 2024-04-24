package com.applitools.example;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginTests {
    static void testLoginFail(Eyes eyes, WebDriver driver, String testName) {
        eyes.open(
                driver,
                AITestDriver.APP_NAME,
                testName,
                new RectangleSize(1600, 800));

        // Load the login page.
        driver.get(AITestDriver.SLICK_PLUS_URL);

        // Login Fails
        driver.findElement(By.xpath("//button[@type = 'submit']")).click();
        eyes.checkWindow("Unauthorized - User not found.");
        eyes.closeAsync();
    }

    static void testLoginFailInvalidPassword(Eyes eyes, WebDriver driver, String testName) {
        eyes.open(
                driver,
                AITestDriver.APP_NAME,
                testName,
                new RectangleSize(1600, 800));

        // Load the login page.
        driver.get(AITestDriver.SLICK_PLUS_URL);

        // Login
        driver.findElement(By.xpath("//input[@type = 'email']")).clear();
        driver.findElement(By.xpath("//input[@type = 'email']")).sendKeys("test@slick.com");

        driver.findElement(By.xpath("//input[@type = 'password']")).clear();

        // Logout   
        driver.findElement(By.xpath("//button[@type = 'submit']")).click();
        eyes.checkWindow("Bye!");
        eyes.closeAsync();
    }

    static void testLoginLogout(Eyes eyes, WebDriver driver, String testName) {
        eyes.open(

                // WebDriver object to "watch".
                driver,

                // The name of the application under test.
                // All tests for the same app should share the same app name.
                // Set this name wisely: Applitools features rely on a shared app name across tests.
                AITestDriver.APP_NAME,

                // The name of the test case for the given application.
                // Additional unique characteristics of the test may also be specified as part of the test name,
                // such as localization information ("Home Page - EN") or different user permissions ("Login by admin").
                testName,

                // The viewport size for the local browser.
                // Eyes will resize the web browser to match the requested viewport size.
                // This parameter is optional but encouraged in order to produce consistent results.
                new RectangleSize(1600, 800));

        // Load the login page.
        driver.get(AITestDriver.SLICK_PLUS_URL);

        // Verify the full login page loaded correctly.
        eyes.check(Target.window().fully().withName("SlickUi"));

        // Inspect -> R-click -> Copy full Xcode
        // Login
        driver.findElement(By.xpath("//input[@type = 'email']")).clear();
        driver.findElement(By.xpath("//input[@type = 'email']")).sendKeys("test@slick.com");

        driver.findElement(By.xpath("//input[@type = 'password']")).clear();
        driver.findElement(By.xpath("//input[@type = 'password']")).sendKeys("123123");

        driver.findElement(By.xpath("//button[@type = 'submit']")).click();

        // Verify the full main page loaded correctly.
        // This snapshot uses LAYOUT match level to avoid differences in closing time text.
        eyes.check(Target.window().fully().withName("SlickUi").layout());

        // Logout
        driver.findElement(By.xpath("//a[@mattooltip=\"Logout\"]")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/mat-dialog-container/div/div/app-logout-dialog/div/div[2]/button[2]")).click();

        // Close Eyes to tell the server it should display the results.
        eyes.closeAsync();
    }
}
