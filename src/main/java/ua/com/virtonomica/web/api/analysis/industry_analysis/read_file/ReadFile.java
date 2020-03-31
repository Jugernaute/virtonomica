package ua.com.virtonomica.web.api.analysis.industry_analysis.read_file;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile <T>{
    private T targetClass;

    public ReadFile() {
    }

    public List<T> read(Class aClass, ObjectMapper objectMapper, String jsonFile){

        List<T> list = new ArrayList<>();
        JavaType type = objectMapper.getTypeFactory().
                constructCollectionType(List.class, aClass);
        try {
            list = objectMapper.readValue(new File(jsonFile),  type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
