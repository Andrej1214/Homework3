package org.pavlov.property_reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final String FILE_NAME = "/dbURL.properties";
    private static final Properties INSTANCE_PROPERTIES = new Properties();

    static {
        initProperties(INSTANCE_PROPERTIES, FILE_NAME);
    }

    private static synchronized void initProperties(Properties properties, String fileName) {
        try (InputStream inputStream = PropertiesReader.class.getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable load properties from file" + fileName);
        }
    }

    public static String getValueFromProperty(String property) {
        return INSTANCE_PROPERTIES.getProperty(property);
    }

}
