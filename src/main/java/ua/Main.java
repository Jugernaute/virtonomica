package ua;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {



//       LinkedHashMap<Double,Double>map = new LinkedHashMap<>();
//        map.put(1.0,180000.0);
//        map.put(1.1,110000.0);
//        map.put(1.14, 500000.0);
//        map.put(1.15 , 180000.0);
//        map.put(1.18 , 200000.0);
//        map.put(1.24 ,85656.62);
//        map.put(1.26 ,195000.0);
//        map.put(1.44 ,105000.0);
//        map.put(1.53 ,720000.0);
//        map.put(1.62 ,110000.0);
//        map.put(2.35 ,300000.0);
//        map.put(2.53 ,275768.96);
//        map.put(5.0 ,300000.0);
//
//        Double less=Double.MAX_VALUE;
//        for (Double aDouble : map.keySet()) {
//            Double value = map.get(aDouble);
//            less = (less<value)?less:value;
//
//        }
//        System.out.println(less);

//        boolean matches = s.matches(regex);
//        String matcher = Matcher.quoteReplacement(regex);
//        System.out.println(matcher);
//        System.out.println(s.replaceAll(regex, ""));

        String s = "{adad}345";
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        System.out.println(s.replaceAll(regex, ""));

//        List<String>strings = new ArrayList<>();
//        strings.add("1/2 ед.");
//        strings.add("1/2 ед.");
//        strings.add("1 ед.");
//        for (String string : strings) {
//            String regex = "\\W+[.]";
//            System.out.println(string.replace("/", ".").replaceAll(regex,""));
//        }
    }
}
