package ua.com.virtonomica.utils.create.my_company_units;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import ua.com.virtonomica.service.unit_wrapper.UnitWrapperService;
import ua.com.virtonomica.utils.create.IUnitWrapper;
import ua.com.virtonomica.utils.create.UnitBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MyCompanyUnitsBuilder extends UnitBuilder implements JsonParser {
    public MyCompanyUnitsBuilder(UnitWrapperService unitWrapperService) {
        super(unitWrapperService);
    }

    @Override
    public List<IUnitWrapper> getWrapper(String json, IUnitWrapper iUnitWrapper) {
        List<IUnitWrapper>wrappers = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject object = new JSONObject(json);
        Set<String> keySet = object.keySet();
        for (String s : keySet) {
            if (s.equalsIgnoreCase("data")){
                JSONObject dataObject = object.getJSONObject(s);
                Set<String> keySet1 = dataObject.keySet();
                for (String s1 : keySet1) {
                    String o = dataObject.get(s1).toString();
                    try {
                        MyCompanyUnitsWrapper myCompanyUnitsWrapper = mapper.readValue(o, MyCompanyUnitsWrapper.class);
                        wrappers.add(myCompanyUnitsWrapper);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return wrappers;
    }

    @Override
    public void build(String json, IUnitWrapper IUnitWrapper) {
        super.build(json, new MyCompanyUnitsWrapper());
    }
}
