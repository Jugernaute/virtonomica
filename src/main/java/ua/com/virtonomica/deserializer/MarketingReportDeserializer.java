package ua.com.virtonomica.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.com.virtonomica.utils.create.IUnitWrapper;
import ua.com.virtonomica.utils.create.geography.GeographyWrapper;
import ua.com.virtonomica.utils.reports.MarketingReport;

import java.io.IOException;

public class MarketingReportDeserializer extends JsonDeserializer<MarketingReport> {
    @Override
    public MarketingReport deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        long unit_id = treeNode.get("unit_id").asLong();
        String unit_name = treeNode.get("unit_name").asText();
        long company_id = treeNode.get("company_id").asLong();
        String company_name = treeNode.get("company_name").asText();
        int shop_size = treeNode.get("shop_size").asInt();
        String district_name = treeNode.get("district_name").asText();
        long qty = treeNode.get("qty").asLong();
        double price = treeNode.get("price").asDouble();
        double quality = treeNode.get("quality").asDouble();
        double brand = treeNode.get("brand").asDouble();
        ObjectMapper mapper = new ObjectMapper();
        GeographyWrapper geographyWrapper = mapper.treeToValue(treeNode, GeographyWrapper.class);
        return new MarketingReport(geographyWrapper,unit_id,unit_name,company_id,company_name,shop_size,
                district_name,qty,price,quality,brand);
    }
}
