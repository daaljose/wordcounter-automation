package com.josedaal.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WordCounterPage {

    private WebDriver driver;
    private final By signIn =By.xpath("//span[text()='Sign In']");
    private final By signOut = By.xpath("//span[text()='Sign Out']");
    private final By editorTextArea = By.id("box");
    private final By contadorPalabras = By.id("word_count");
    private final By contadorCaracteres = By.id("character_count");
    private final By botonLimpiar = By.id("clear-btn");
    private final By keywordWords = By.xpath("//span[@class='word']");
    private final By keywordBadges = By.xpath("//div[@id='kwd-accordion-data']//span[@class='badge']");

    public WordCounterPage(WebDriver driver){
        this.driver = driver;
    }

    public void clickSignIn(){
        driver.findElement(signIn).click();
    }

    public void ingresarTexto(String texto) {
        WebElement editor = driver.findElement(editorTextArea);
        editor.sendKeys(texto);
    }

    public void reemplazarTexto(String texto) {
        limpiarEditor();
        if (!texto.isEmpty()) {
            ingresarTexto(texto);
        }
    }

    public void esperarCantidadPalabras(int cantidadEsperada) {
        WebDriverWait wait = new WebDriverWait(driver,
                        Duration.ofSeconds(10));
        wait.until(
                ExpectedConditions.textToBe(contadorPalabras,
                        String.valueOf(cantidadEsperada)));
    }

    public void esperarCantidadCaracteres(int cantidadEsperada) {
        WebDriverWait wait =
                new WebDriverWait(driver,
                        Duration.ofSeconds(10));
        wait.until(
                ExpectedConditions.textToBe(
                        contadorCaracteres,
                        String.valueOf(cantidadEsperada)));
    }

    public int obtenerCantidadPalabras() {
        String cantidad = driver.findElement(contadorPalabras).getText();
        return Integer.parseInt(cantidad);
    }

    public int obtenerCantidadCaracteres() {
        String cantidad = driver.findElement(contadorCaracteres).getText();
        return Integer.parseInt(cantidad);
    }

    public void limpiarEditor() {
        driver.findElement(botonLimpiar).click();
    }

    public boolean esSignOutVisible() {
        WebDriverWait wait = new WebDriverWait(
                        driver, Duration.ofSeconds(10));
        WebElement elementoSignOut =
                wait.until(ExpectedConditions.visibilityOfElementLocated(signOut)
                );
        return elementoSignOut.isDisplayed();
    }

    public String obtenerPalabraPorPosicion(int posicion){
        List<WebElement> palabras = driver.findElements(keywordWords);
        return palabras
                .get(posicion - 1)
                .getText();
    }

    public int obtenerRepeticionesPorPosicion(int posicion) {
        List<WebElement> badges = driver.findElements(keywordBadges);
        String resultado = badges
                        .get(posicion - 1)
                        .getText();
        String repeticiones =
                resultado.split(" ")[0];
        return Integer.parseInt(repeticiones);
    }

    public void esperarKeywordDensity() {
        WebDriverWait wait = new WebDriverWait(driver,
                        Duration.ofSeconds(10));
        wait.until(
                driver ->
                        driver.findElements(keywordWords)
                                .size() >= 3
        );
    }


}
