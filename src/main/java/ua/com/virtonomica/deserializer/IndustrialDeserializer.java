package ua.com.virtonomica.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ua.com.virtonomica.utils.create.industrial.IndustrialWrapper;

import java.io.IOException;

public class IndustrialDeserializer extends JsonDeserializer<IndustrialWrapper> {

    @Override
    public IndustrialWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
        long unitType_id = tree.get("id").asLong();
        String unitType_name = tree.get("name").asText();
        long unitClass_id = tree.get("class_id").asLong();
        String unitClass_name = tree.get("class_name").asText();
        long industry_id = tree.get("industry_id").asLong();
        String industry_name = tree.get("industry_name").asText();
        return new IndustrialWrapper(unitType_id,industry_id,unitClass_id,unitType_name,industry_name,unitClass_name);
    }
}
