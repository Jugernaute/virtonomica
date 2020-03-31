package ua.delTest;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    static int x;
    public static void main(String[] args) {

        String i = "23 90";
        System.out.println(Integer.valueOf(i.replaceAll("\\D+","")));
        TreeMap<Double,Integer> treeMap2 = new TreeMap<>();
        TreeMap<Double, Integer>treeMap = new TreeMap<>();
        treeMap.put(3.1,1);
        treeMap.put(3.2,11);
        treeMap.put(3.3,10);

        TreeMap<Double, Integer> treeMap1 = new TreeMap<>();
        treeMap1.put(2.1, 2);
        treeMap1.put(2.2, 24);
        treeMap1.put(2.3, 23);
        treeMap1.put(3.3, 23);
        treeMap2.putAll(treeMap);
        treeMap2.putAll(treeMap1);
        System.out.println(treeMap2);

//        MethodViewer methodViewer = new MethodViewer(new MethodManager());
//        MethodViewer methodViewer2 = new MethodViewer(new MethodManager2());
//        methodViewer.renderMethod();
//        methodViewer2.renderMethod();
//        methodViewer2.renderMethod();

        int [] arr = {1,1,1,3,1,3,4,4,1,3,2,5,4,2,8,8};
        HashMap<Integer, Integer> map = new HashMap<>();
        int c = 0;
        for (int anArr : arr) {
            // если уже есть ключ, то прибавляем единицу
            if (map.keySet().contains(anArr)) {
                map.put(anArr, map.get(anArr) + 1);
                // Если нет, то кладем ключ и присваиваем значение 1
            } else {
                map.put(anArr, 1);
            }
        }
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            System.out.printf("key %d value %d", integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
            System.out.println();
        }

//        int[][] multiplyTab  = new int[10][10];
//
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                multiplyTab[i][j] = (i+1)*(j+1);
//                вывод ряда чисел разделенных знаком табуляции
//                System.out.print(multiplyTab[i][j] + "\t");
//            }
//            System.out.println();
//        }
    }
}
