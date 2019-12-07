package ua;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String s = "Требуется минимальное качество сырья: 409";
//        String regex2 = "(\\d[0-9]*\\.[0-9]*)";
        String regex2 = "(\\d+(?:\\.\\d+)?)";
        Pattern p = Pattern.compile(regex2);
        Matcher m = p.matcher(s);
        while (m.find()){
            System.out.println(m.group());
        }
//        System.out.println(s.replaceAll("\n",""));



//        boolean matches = s.matches(regex);
//        String matcher = Matcher.quoteReplacement(regex);
//        System.out.println(matcher);
//        System.out.println(s.replaceAll(regex, ""));


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
