package ua.com.virtonomica;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VirtonomicaApplicationTests {

    @Test
    public void contextLoads() throws IOException {
            String json
                    = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

            ExtendableBean bean = new ObjectMapper()
                    .readerFor(ExtendableBean.class)
                    .readValue(json);

            assertEquals("My bean", bean.name);
            assertEquals("val2", bean.getProperties().get("attr2"));
        }



}
