package com.josedaal.automation.test;

import com.josedaal.automation.configuracion.ConfigReader;
import com.josedaal.automation.driver.BrowserManager;
import com.josedaal.automation.pages.LoginPage;
import com.josedaal.automation.pages.WordCounterPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordCounterTest {

    private BrowserManager browserManager;
    private WebDriver driver;
    private WordCounterPage wordCounterPage;
    private LoginPage loginPage;


    @BeforeEach
    void prepararSesion(){
        browserManager = new BrowserManager();
        browserManager.iniciarNavegador();
        driver = browserManager.getDriver();
        ConfigReader configReader = new ConfigReader();
        String url = configReader.getProperty("base.url");
        wordCounterPage = new WordCounterPage(driver);
        loginPage = new LoginPage(driver);
        driver.get(url);
        String titulo = driver.getTitle();
        System.out.println("Título obtenido: " + titulo);
        assertTrue(titulo.contains("WordCounter"),
                "El título debería contener 'WordCounter'. Título obtenido: " + titulo);
        wordCounterPage.clickSignIn();
        loginPage.esperarFormularioVisible();
        loginPage.ingresarMail("hoiilad.store@gmail.com");
        loginPage.ingresarPassword("123456789");
        loginPage.clickLogin();
        assertTrue(wordCounterPage.esSignOutVisible(),
                "El botón 'Sign Out' no esta visible después de iniciar sesión."
        );
    }

    @AfterEach
    void finalizarPrueba() {
        browserManager.cerrarNavegador();
        if (browserManager != null) {
            browserManager.cerrarNavegador();
        }
    }

    @Test
    void validarConteoPalabrasYCaracteres() {
        wordCounterPage.limpiarEditor();
        wordCounterPage.ingresarTexto("Hola mundo");
        wordCounterPage.esperarCantidadPalabras(3);
        wordCounterPage.esperarCantidadCaracteres(10);
        int palabras = wordCounterPage.obtenerCantidadPalabras();
        int caracteres = wordCounterPage.obtenerCantidadCaracteres();
        assertEquals(2, palabras,
                "La cantidad de palabras es incorrecta");
        assertEquals(10, caracteres,
                "La cantidad de caracteres es incorrecta");
    }

    @Test
    void validarUnaPalabra () {
        wordCounterPage.ingresarTexto("Hola");
        wordCounterPage.esperarCantidadPalabras(1);
        wordCounterPage.esperarCantidadCaracteres(4);
        int palabras = wordCounterPage.obtenerCantidadPalabras();
        int caracteres = wordCounterPage.obtenerCantidadCaracteres();
        assertEquals(1, palabras,
                "La cantidad de palabras debería ser 1");
        assertEquals(4, caracteres, "La cantidad de caracteres debería ser 4");
    }

    @Test
    void validarDosPalabras () {
        wordCounterPage.ingresarTexto("Buenos Aires");
        wordCounterPage.esperarCantidadPalabras(2);
        wordCounterPage.esperarCantidadCaracteres(12);
        int palabras = wordCounterPage.obtenerCantidadPalabras();
        int caracteres = wordCounterPage.obtenerCantidadCaracteres();
        assertEquals(2, palabras,
                "La cantidad de palabras debería ser 2");
        assertEquals(12, caracteres, "La cantidad de caracteres debería ser 12");
    }

    @Test
    void validarTresPalabras() {
        wordCounterPage.ingresarTexto("Las tres estrellas");
        wordCounterPage.esperarCantidadPalabras(3);
        wordCounterPage.esperarCantidadCaracteres(18);
        int palabras = wordCounterPage.obtenerCantidadPalabras();
        int caracteres = wordCounterPage.obtenerCantidadCaracteres();
        assertEquals(3, palabras,
                "La cantidad de palabras debería ser 3");
        assertEquals(18, caracteres, "La cantidad de caracteres debería ser 18"
        );
    }

    @Test
    void validarTextoConSignosPuntuacion() {
        wordCounterPage.ingresarTexto("Caracteres Especiales #¿?$+-*/");
        wordCounterPage.esperarCantidadPalabras(3);
        wordCounterPage.esperarCantidadCaracteres(30);
        int palabras = wordCounterPage.obtenerCantidadPalabras();
        int caracteres = wordCounterPage.obtenerCantidadCaracteres();
        assertEquals(3, palabras,
                "Los signos no deberían modificar la cantidad de palabras"
        );
        assertEquals(30,
                caracteres, "La cantidad de caracteres debería ser 30"
        );
    }

    @Test
    void validarTextoConNumeros() {
        wordCounterPage.ingresarTexto("Numero Documento 123456789");
        wordCounterPage.esperarCantidadPalabras(3);
        wordCounterPage.esperarCantidadCaracteres(26);
        int palabras = wordCounterPage.obtenerCantidadPalabras();
        int caracteres = wordCounterPage.obtenerCantidadCaracteres();
        assertEquals(3, palabras,
                "La cantidad de palabras/numeros debería ser 3"
        );
        assertEquals(26,
                caracteres, "Los signos deberían contabilizarse como caracteres, deberian ser 26"
        );
    }

    @Test
    void validarContadoresEnCeroAlLimpiarTexto() {
        wordCounterPage.ingresarTexto("Hola de nuevo");
        wordCounterPage.esperarCantidadPalabras(3);
        wordCounterPage.esperarCantidadCaracteres(13);
        assertEquals(
                3, wordCounterPage.obtenerCantidadPalabras(),
                "Antes de limpiar deberían existir 2 palabras");
        assertEquals(13, wordCounterPage.obtenerCantidadCaracteres(),
                "Antes de limpiar deberían existir 10 caracteres");
        wordCounterPage.limpiarEditor();
        wordCounterPage.esperarCantidadPalabras(0);
        wordCounterPage.esperarCantidadCaracteres(0);
        assertEquals(
                0, wordCounterPage.obtenerCantidadPalabras(),
                "Después de limpiar deberían existir 0 palabras");
        assertEquals(
                0, wordCounterPage.obtenerCantidadCaracteres(),
                "Después de limpiar deberían existir 0 caracteres");
    }

    @Test
    void validarTresPalabrasMasRepetidas() {
        String texto = "automatizacion selenium pruebas automatizacion java selenium "
                        + "automatizacion pruebas automatizacion selenium";
        wordCounterPage.ingresarTexto(texto);
        wordCounterPage.esperarKeywordDensity();
        assertEquals("automatizacion",
                wordCounterPage.obtenerPalabraPorPosicion(1),
                "La palabra más repetida debería ser automatizacion");
        assertEquals(4,
                wordCounterPage.obtenerRepeticionesPorPosicion(1),
                "automatizacion debería aparecer 4 veces");
        assertEquals("selenium",
                wordCounterPage.obtenerPalabraPorPosicion(2),
                "La segunda palabra más repetida debería ser 'selenium");
        assertEquals(3,
                wordCounterPage.obtenerRepeticionesPorPosicion(2),
                "'selenium' debería aparecer 3 veces");

        assertEquals("pruebas",
                wordCounterPage.obtenerPalabraPorPosicion(3),
                "La tercera palabra más repetida debería ser 'pruebas");
        assertEquals(2,
                wordCounterPage.obtenerRepeticionesPorPosicion(3),
                "'pruebas' debería aparecer 2 veces");
    }


    /*@Test
    void validarConteoPalabras() {
        wordCounterPage.ingresarTexto(
                "Hola mundo"
        );
    }*/


    //VALIDAR TEXTO CON ESPACIOS MULTIPLES

}
