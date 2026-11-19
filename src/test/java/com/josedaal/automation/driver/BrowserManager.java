package com.josedaal.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserManager {

    private WebDriver driver;

    public void iniciarNavegador(){
        driver = new ChromeDriver();
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void cerrarNavegador(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }




}
