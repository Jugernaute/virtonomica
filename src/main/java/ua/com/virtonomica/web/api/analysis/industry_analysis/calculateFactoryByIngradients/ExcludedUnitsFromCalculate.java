package ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients;

import java.util.ArrayList;
import java.util.List;

class ExcludedUnitsFromCalculate {
    private final static long[] excludeId = new long[]{380060, 1870, 2382, 101, 122, 380058, 380052};
    private final static String [] excludeName = new String[]{
            "Яхтостроительный завод",
            "Авиасборочное предприятие"
    };

    boolean checkExclude(long id){
        for (long l : excludeId) {
            if (l==id) return true;
        }
        return false;
    }

    boolean checkExclude(String name){
        for (String s : excludeName) {
            if (name.equalsIgnoreCase(s)) return true;
        }
        return false;
    }
}
