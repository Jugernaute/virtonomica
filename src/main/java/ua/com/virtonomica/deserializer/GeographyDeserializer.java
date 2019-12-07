package ua.com.virtonomica.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ua.com.virtonomica.utils.create.geography.GeographyWrapper;

import java.io.IOException;

public class GeographyDeserializer extends JsonDeserializer<GeographyWrapper> {
    @Override
    public GeographyWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
//        System.out.println(jsonNode);
        final long city_id = jsonNode.get("city_id").asLong();
        final String city_name = jsonNode.get("city_name").asText();
        final long country_id = jsonNode.get("country_id").asLong();
        final String country_name = jsonNode.get("country_name").asText();
        final String region_name = jsonNode.get("region_name").asText();
        final double city_salary = jsonNode.get("salary").asDouble();
        final double city_education = jsonNode.get("education").asDouble();
        final long region_id = jsonNode.get("region_id").asLong();

        return new GeographyWrapper(
                city_id, city_name, country_id, country_name, region_id, region_name, city_salary, city_education
        );
    }
}
