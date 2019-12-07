package ua.com.virtonomica.utils.create.my_company_units;

import ua.com.virtonomica.utils.create.IUnitWrapper;

import java.util.List;

public interface JsonParser {
    List<IUnitWrapper> getWrapper(String json,IUnitWrapper iUnitWrapper);

//    static CompanyUnits MY_COMPANY_UNITS(){
//        JSONObject json = ApiTestJson.json;
//        ObjectMapper mapper = new ObjectMapper();
//        Set<String> keySet = json.keySet();
//        for (String s : keySet) {
//            if (s.equalsIgnoreCase("data")){
//                JSONObject dataObject = json.getJSONObject(s);
//                Set<String> keySet1 = dataObject.keySet();
//                for (String s1 : keySet1) {
//                    String o = dataObject.get(s1).toString();
//                    try {
//                        MyCompanyUnitsWrapper myCompanyUnitsWrapper = mapper.readValue(o, MyCompanyUnitsWrapper.class);
//                        return new CompanyUnits(myCompanyUnitsWrapper);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return new CompanyUnits();
//    }
}
