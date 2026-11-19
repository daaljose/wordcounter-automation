package com.josedaal.automation.configuracion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;

    public ConfigReader() {

        properties = new Properties();

        try (InputStream input =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream(
                                     "configuracion/test.properties"
                             )) {

            if (input == null) {
                throw new RuntimeException(
                        "No se encontró configuracion/test.properties"
                );
            }

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error al cargar test.properties",
                    e
            );
        }
    }

    public String getProperty(String key) {

        return properties.getProperty(key);

    }
}
