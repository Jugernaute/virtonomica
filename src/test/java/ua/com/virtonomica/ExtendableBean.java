package ua.com.virtonomica;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;

public class ExtendableBean {
    String name;
    Map<String, String> properties;

    public ExtendableBean() {
    }

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
