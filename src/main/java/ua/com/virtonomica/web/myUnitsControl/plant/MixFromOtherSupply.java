package ua.com.virtonomica.web.myUnitsControl.plant;


import java.util.TreeMap;

public class MixFromOtherSupply {
    public static void main(String[] args) {

        double[] quality = {12, 16, 14.7, 9, 18.4, 7, 20, 18.2, 19.5, 18.1};
//        double[] quality = {11, 16, 18};
        int[] count = {30, 20, 300, 50, 10, 330, 300, 122, 100, 40};
//        int[] count = {200,1000, 100};

        TreeMap<Double, Integer> map = new TreeMap<>();
        for (int i = 0; i < quality.length; i++) {
            map.put(quality[i], count[i]);
        }
      qwe qwe = new qwe();
        double minQual = qwe.minQualityForRepair(15.0, 12.0, 100, 50);
        System.out.println(minQual);
        double v = qwe.qualityForRepair(map, minQual);
        System.out.println(v);

//        MixerQualityEquipment calculationEquipment = new MixerQualityEquipment();
//        TreeMap<Double, Integer> calculation = calculationEquipment.mixer(map, 14, 50);
//        for (Double aDouble : calculation.keySet()) {
//            System.out.println(aDouble + " " + calculation.get(aDouble));
//        }
    }

    static class qwe{
        double minQualityForRepair (double needQuality, double installQuality, int installCount, int repairCount){
          double findQuality;
          findQuality = (needQuality*installCount-installQuality*(installCount-repairCount))/repairCount;
          return findQuality;
        }

        double qualityForRepair (TreeMap<Double, Integer> map, double miQuality){

            for (Double aDouble : map.keySet()) {
                if (aDouble>=miQuality){
                    return aDouble;
                }
            }
           return 0;
        }

    }
}