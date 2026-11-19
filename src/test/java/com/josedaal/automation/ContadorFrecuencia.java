package com.josedaal.automation;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContadorFrecuencia {

    @Test
    void contarFrecuenciaPalabras() {
        String texto = "farmatodo farmatodo farmatodo farmatodo farmatodo cuida cuida tu salud\n"
                        + "farmatodo cuida toda tu salud";
        //String texto = "farmatodo farmatodo farmatodo farmatodo farmatodo cuida cuida tu salud farmatodo cuida toda tu salud";
        String[] palabras = texto.trim().split("\\s+");
        int cantidadPalabras = palabras.length;
        String textoSinSaltos = texto.replace("\n", "");
        int cantidadCaracteres = textoSinSaltos.length();
        Map<String, Integer> frecuencias = new HashMap<>();
        for (String palabra : palabras) {
            String palabraNormalizada = palabra.toLowerCase();
            frecuencias.put(palabraNormalizada, frecuencias.getOrDefault(
                            palabraNormalizada,
                            0) + 1);
        }
        List<Map.Entry<String, Integer>> resultado =
                frecuencias
                        .entrySet()
                        .stream()
                        .sorted(
                                Map.Entry
                                        .<String, Integer>
                                                comparingByValue()
                                        .reversed()
                                        .thenComparing(
                                                Map.Entry
                                                        .comparingByKey()
                                        )
                        )
                        .toList();
        System.out.println(cantidadPalabras + " Palabras");
        System.out.println(cantidadCaracteres  + " Caracteres");
        for (Map.Entry<String, Integer> entrada
                : resultado) {
            System.out.println(entrada.getKey()
                            + ": "
                            + entrada.getValue());
        }
        assertEquals(14, cantidadPalabras,
                "La cantidad de palabras es incorrecta"
        );
        assertEquals(99, cantidadCaracteres,
                "La cantidad de caracteres es incorrecta"
        );
        assertEquals(6,
                frecuencias.get("farmatodo")
        );
        assertEquals(3,
                frecuencias.get("cuida")
        );
        assertEquals(2, frecuencias.get("salud")
        );
        assertEquals(2, frecuencias.get("tu")
        );
        assertEquals(1,
                frecuencias.get("toda")
        );
    }
}
