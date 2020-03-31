package ua.com.virtonomica.web.myUnitsControl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class MixerQualityEquipment {

    public TreeMap<Double, Integer> mixer (final HashMap<Double, Integer> map, final double needQuality, final int needCount) {

        TreeMap<Double, Integer> response = new TreeMap<>();
        double average = 0;

        do {
            /*key ->  quality
             * value -> count of equipments*/
            TreeMap<Double, Integer> treeMap = searchRequiredQuality (map, needQuality, needCount);
            double qualityFirst ;
            double qualitySecond;
            double qualityEquals;

            if (treeMap.size()==1){
                qualityEquals = treeMap.firstKey();
                response.put(qualityEquals, needCount);
                return response;
            } else {
                Iterator<Double> iterator = treeMap.keySet().iterator();
                qualityFirst = iterator.next();
                qualitySecond = iterator.next();
            }

            Integer countQualityFirst = map.get(qualityFirst);
            Integer countQualitySecond = map.get(qualitySecond);
            for (int i = 1; i <= needCount; i++) {
                if (i<=countQualityFirst && i<=countQualitySecond) {
                    average = ((qualityFirst * i) + qualitySecond * (needCount - i)) / needCount;
                    if (average > needQuality - 0.05 && average < needQuality + 0.05) {
                        response.put(qualityFirst, i);
                        response.put(qualitySecond, needCount - i);
                        return response;
                    }
                } else if (i>countQualityFirst){
                    map.remove(qualityFirst);
                    break;
                } else {
                    map.remove(qualitySecond);
                }
            }
        } while (Math.abs(average - needQuality) >= 0.1);

        return response;
    }

/*
* метод шукає  обладнання з двома різними якостями
 * які є найближчою нижчою і найближчою вищою,
 * і передає для змішування в інший метод
 * */
    private TreeMap<Double, Integer> searchRequiredQuality (HashMap<Double, Integer> mapQualities, double needQuality, int needCount) {

        TreeMap<Double, Integer> response = new TreeMap<>();

        double qualityLess = 0;
        double qualityMore = 100;

        for (Double mapQuality : mapQualities.keySet()) {
            if (mapQuality == needQuality) {
                int value = mapQualities.get(mapQuality);
                if (value >=needCount){
                    response.put(mapQuality, value);
                    return response;
                }
            }else if (mapQuality > qualityLess && mapQuality < needQuality) {
                qualityLess = mapQuality;
            }else if (mapQuality < qualityMore && mapQuality > needQuality) {
                qualityMore = mapQuality;
            }
        }

        int countQualityLess = mapQualities.get(qualityLess);
        int countQualityMore = mapQualities.get(qualityMore);

        response.put(qualityLess, countQualityLess);
        response.put(qualityMore, countQualityMore);
        return response;
    }
}
