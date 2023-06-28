package jp.wapio.calc.config;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    private Properties properties = new Properties();

    private String configName = "config.properties";

    public Config() {
        try {
            URI configUri = Config.class.getClassLoader().getResource(configName).toURI();
            Path configPath = Path.of(configUri);
            properties.load(newInputStream(configPath, READ));
        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public Integer getAsInteger(String key) {
        String value = properties.getProperty(key);
        return value == null ? null : Integer.valueOf(value);
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
