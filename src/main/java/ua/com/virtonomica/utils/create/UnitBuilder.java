package ua.com.virtonomica.utils.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.virtonomica.service.unit_wrapper.UnitWrapperService;
import ua.com.virtonomica.utils.create.my_company_units.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class UnitBuilder implements JsonParser {
private final UnitWrapperService unitWrapperService;

    @Autowired
    public UnitBuilder(UnitWrapperService unitWrapperService) {
        this.unitWrapperService = unitWrapperService;
    }

    @Override
    public List<IUnitWrapper> getWrapper(String json,IUnitWrapper iUnitWrapper) {
        List<IUnitWrapper> iUnitWrappers = new ArrayList<>();
        JSONObject object = new JSONObject(json);
        Set<String> keySet = object.keySet();
        for (String s : keySet) {
            JSONObject jsonObject = object.getJSONObject(s);
            String value = jsonObject.toString();
            ObjectMapper mapper = new ObjectMapper();
            IUnitWrapper unitWrapper = getUnitCategory(value, mapper, iUnitWrapper).get();
            iUnitWrappers.add(unitWrapper);
        }
        return iUnitWrappers;
    }

    public void build(String json, IUnitWrapper iUnitWrapper){
        List<IUnitWrapper> wrapper = getWrapper(json,iUnitWrapper);
        for (IUnitWrapper unitWrapper : wrapper) {
            saver(unitWrapper);
        }

    }

    private Optional<IUnitWrapper> getUnitCategory(String value, ObjectMapper mapper, IUnitWrapper iUnitWrapper) {
        IUnitWrapper unitWrapper =null;
        try {
            unitWrapper = mapper.readValue(value, iUnitWrapper.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(unitWrapper);
    }


    private void saver(IUnitWrapper iUnitWrapper){
        unitWrapperService.save(iUnitWrapper);
    }
}
