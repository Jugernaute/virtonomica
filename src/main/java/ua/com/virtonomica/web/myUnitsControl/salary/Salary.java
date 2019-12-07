package ua.com.virtonomica.web.myUnitsControl.salary;

import com.gargoylesoftware.htmlunit.html.*;

import java.text.DecimalFormat;
import java.util.List;

/*
* fix salary and return new refresh table with
* info-block -"Сотрудники и Зарплата".
* Refresh necessary, because sometimes needed to check again
* salary
* */
public class Salary implements ISalary {

//    public String checkSalary(HtmlPage unitPage, String unitName){
//        HtmlAnchor refLink = null;
//        List<HtmlAnchor> xPath = unitPage.getByXPath("//a[@class='unit_button btn-virtonomics-unit']");
//            for (HtmlAnchor htmlAnchor : xPath) {
//                if (htmlAnchor.asText().equals("  Сотрудники и зарплата")){
//                    refLink = htmlAnchor;
//                }
//            }
//            HtmlAnchor upSalaryLink = unitPage.getAnchorByHref(Objects.requireNonNull(refLink).getHrefAttribute());
//            try {
//                HtmlTable table = fixSalary(upSalaryLink, unitPage);
//                if (table==null){
//                    return unitName + " error in "+getClass().getName()+"/salaryCheck";
//                }
//                HtmlTableCell isSalary = Objects.requireNonNull(table).getRow(3).getCell(1);
//                HtmlTableCell needSalary = table.getRow(4).getCell(1);
//
//                if (Double.valueOf(isSalary.asText()) < Double.valueOf(needSalary.asText())){
//                    HtmlTable fixSalary = fixSalary(upSalaryLink, unitPage);
//                    if (fixSalary==null)
//                        return unitName + " error in "+getClass().getName()+"/salaryUp";;
//                }
//            } catch (NullPointerException e){
//                e.printStackTrace();
//                return unitName + " some error "+e;
//            }
//            return unitName+" ->salary checked";
//    }

    @Override
    public HtmlTable fixSalary (HtmlAnchor upSalaryLink, HtmlPage officePage){
        try{
            HtmlPage salaryTab = upSalaryLink.click();
            HtmlInput salary = (HtmlInput) salaryTab.getElementById("salary");
            double current_kv;
            double find_kv;

            double mySalary = Double.valueOf(salary.getValueAttribute().replace(",","."));

            DomElement firstByXPath1 = salaryTab.getFirstByXPath("//td[@class='text_to_left']");
            String prizeStr = firstByXPath1.asText().replaceAll("[^\\d.]| \\.|\\.$\\s", "");
            double prize = Double.valueOf(prizeStr);

            HtmlSpan apprisedEmployeeLevel = (HtmlSpan) salaryTab.getElementById("apprisedEmployeeLevel");
            String current_kv_Str = apprisedEmployeeLevel.asText();
            current_kv = Double.valueOf(current_kv_Str);

            String splitStr = apprisedEmployeeLevel.getNextElementSibling().asText();

            String[] split = splitStr.split(",");
            String regex = "[^\\d.]| \\s";
            find_kv = Double.valueOf(split[1].replaceAll(regex, ""));
            double avg_kv = Double.valueOf(split[0].replaceAll(regex, ""));
            double val;
            double k = mySalary / prize;
            double k1 = k * k;
            double base;
            double limit;
            if (k < 1) {
                base = current_kv / k1;
                val = Math.sqrt(find_kv / base) * prize;
                // если зарплата превысила среднею
                limit = val / prize;
                if (limit > 1) {
//                    System.out.println("fix 1");
                    base = base / avg_kv;
                    base = 2 * Math.pow(0.5, base);
                    val = (Math.pow(2, find_kv / avg_kv) * base - 1) * prize;
                }
            } else {
//                System.out.println("k 2");
                base = (k + 1) / Math.pow(2, current_kv / avg_kv);
                val = (Math.pow(2, find_kv / avg_kv) * base - 1) * prize;
                // если зарплата стала меньше средней
                limit = val / prize;
                if (limit < 1) {
//                    System.out.println("fix 2");
                    base = avg_kv * Math.log(base / 2) / Math.log(0.5);
                    val = Math.sqrt(find_kv / base) * prize;
                }
            }
            // блокировка от потери обученности
            limit = val / prize;
            if (limit <= 0.80) {
                val = Math.floor(0.80 * prize) + 1;
            }
            String format = new DecimalFormat("#.00").format(val);
            salary.setValueAttribute(format);

            HtmlSubmitInput btnSave = salaryTab.getFirstByXPath("//input[@class='button160']");
            btnSave.click();

            HtmlPage refresh = (HtmlPage) officePage.refresh();
            List<HtmlTable> byXPath = refresh.getByXPath("//table[@class='unit_table']");
            return byXPath.get(2);
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }
}
