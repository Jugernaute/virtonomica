package ua.com.virtonomica.utils.create.industrial;

import org.springframework.stereotype.Component;
import ua.com.virtonomica.utils.create.UnitBuilder;
import ua.com.virtonomica.utils.create.IUnitWrapper;
import ua.com.virtonomica.service.unit_wrapper.UnitWrapperService;


@Component
public class IndustrialUnitBuilder extends UnitBuilder {
    public IndustrialUnitBuilder(UnitWrapperService unitWrapperService) {
        super(unitWrapperService);
    }

    @Override
    public void build(String json, IUnitWrapper IUnitWrapper) {
        super.build(json, IUnitWrapper);
    }

    //    private final IndustrialWrapperService industrialService;
//
//    public IndustrialBuilder(IndustrialWrapperService industrialService) {
//        this.industrialService = industrialService;
//    }
//
//    public void build(String json){
//        JSONObject object = new JSONObject(json);
//        Set<String> keySet = object.keySet();
//        for (String s : keySet) {
//            JSONObject jsonObject = object.getJSONObject(s);
//            String value = jsonObject.toString();
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                IndustrialWrapper industrial = mapper.readValue(value, IndustrialWrapper.class);
//                industrialService.save(industrial);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
