package ua.com.virtonomica.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ua.com.virtonomica.utils.create.product.retail_product.RetailProductWrapper;

import java.io.IOException;

public class RetailProductDeserializer extends JsonDeserializer<RetailProductWrapper> {

    @Override
    public RetailProductWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        long product_id = jsonNode.get("product_id").asLong();
        String product_name = jsonNode.get("product_name").asText();
        long category_id = jsonNode.get("category_id").asLong();
        String product_category_name = jsonNode.get("product_category_name").asText();
        String symbol = jsonNode.get("symbol").asText();

        return new RetailProductWrapper(product_id, product_name, category_id, product_category_name,symbol);
    }
}
