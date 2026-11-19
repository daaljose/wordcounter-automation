package com.josedaal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private final By emailInput = By.id("loginform-email");
    private final By passwordInput = By.id("loginform-password");
    private final By loginButton = By.xpath("//button[@name='login-button']");

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    public void ingresarMail(String mail) {
        driver.findElement(emailInput).sendKeys(mail);
    }

    public void ingresarPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void esperarFormularioVisible(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
    }
}
