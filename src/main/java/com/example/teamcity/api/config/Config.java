package com.example.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// класс для распарсинга проперти из файла config.properties
public final class Config {
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private Properties properties;

    private Config() {
        properties = new Properties();
//        loadProperties(CONFIG_PROPERTIES);
        loadProperties();
    }

    private static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    // чтобы прочитать проперти из файла config.properties:
    // вариант ex-elias:
    private void loadProperties() {
        try (var inputStream = Config.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            properties.load(inputStream);
            // Убираем проверку на null (если файла не существует), это будет отлавливаться в блоке catch
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Cannot load properties file", e);
        }
    }

    //вариант AlexPshe:
//    public void loadProperties(String fileName) {
//        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
//            if (stream == null) {
//                System.err.println("File not found " + fileName);
//            }
//            properties.load(stream);
//        } catch (IOException e) {
//            System.err.println("Error during file reading " + fileName);
//            throw new RuntimeException(e);
//        }
//    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
