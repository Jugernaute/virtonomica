package ua.com.virtonomica.web.myUnitsControl.salary.officeAndShop;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import ua.com.virtonomica.web.myUnitsControl.salary.ISalary;
import ua.com.virtonomica.web.myUnitsControl.Verification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Service
public class OfficeShopSalary implements Verification {

    private ISalary iSalary;
    private String unitName=null;

    public OfficeShopSalary (ISalary salary) {
        this.iSalary = salary;
    }

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    List<String> officeSopSalary (WebClient webClient, List<HtmlAnchor> lowProductiveUnits){

        List<String> answer = new ArrayList<>();

        for (HtmlAnchor lowProductiveUnit : lowProductiveUnits) {
            HtmlPage officePage = null;
            try {
                officePage = lowProductiveUnit.click();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<HtmlTable> infoBlock = Objects.requireNonNull(officePage).getByXPath("//table[@class='unit_table']");

            unitName = lowProductiveUnit.asText().replaceAll("_","");
            /*work efficiency*/
            String infoAboutEmployees = infoBlock.get(2).getRow(3).asText();

            String[] levelEmployee = infoAboutEmployees.split("\\(");
            Double currentLevel = Double.valueOf(levelEmployee[0].replaceAll("[^\\d.]| \\s", ""));
            Double necessaryLevel = Double.valueOf(levelEmployee[1].replaceAll("[^\\d.]| \\s", ""));

            /*if reason in salary*/
            if (currentLevel < necessaryLevel) {
                answer.add(unitSalary(webClient, officePage));
            }
            /*if reason in equipment*/
            HtmlTableRow infoAboutEquipment = infoBlock.get(1).getRow(4);
            DomNode domNode = infoAboutEquipment.getChildNodes().get(3);    // in percent
            String serviceability = domNode.asText().replaceAll("[^\\d.]", "");
            Double percentServiceabilityEquipment = Double.valueOf(serviceability);
            if (percentServiceabilityEquipment < 100){
                unitReplacement(webClient, officePage);
            }

            answer.add(unitName + " -> need other operation!!!");
        }
        return answer;
    }

    @Override
    public String unitBuyFull(WebClient webClient, HtmlPage htmlPage) {
        return null;
    }

    @Override
    public String unitReplacement(WebClient webClient, HtmlPage officePage) {

        HtmlPage repairPage = null;
        List<HtmlAnchor> anchors = officePage.getByXPath("//a[@class='unit_button btn-virtonomics-unit']");
        for (HtmlAnchor anchor : anchors) {
            String repair = anchor.asText().trim();
            if (repair.equals("Ремонт оборудования")){
                try {
                    repairPage = anchor.click();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DomElement btnRepair = Objects.requireNonNull(repairPage).getElementById("tabRepair");
                try {
                    HtmlPage click = btnRepair.click();
//                    workWithTable(webClient, )
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    @Override
    public String unitSalary( WebClient webClient, HtmlPage unitPage ) {
////        HtmlAnchor refLink = (HtmlAnchor) unitPage.getByXPath("//a[@class='unit_button btn-virtonomics-unit']").get(3);
//        HtmlAnchor refLink = null;
//        List<HtmlAnchor> xPath = unitPage.getByXPath("//a[@class='unit_button btn-virtonomics-unit']");
//        for (HtmlAnchor htmlAnchor : xPath) {
//            if (htmlAnchor.asText().equals("  Сотрудники и зарплата")){
//                refLink = htmlAnchor;
//            }
//        }
//        HtmlAnchor upSalaryLink = unitPage.getAnchorByHref(Objects.requireNonNull(refLink).getHrefAttribute());
//
//        try {
//            HtmlPage upSalaryPage = upSalaryLink.click();
//            HtmlInput salary = (HtmlInput) upSalaryPage.getElementById("salary");
//            double mySalary = Double.valueOf(salary.asText());
//
//            DomElement firstByXPath1 = upSalaryPage.getFirstByXPath("//td[@class='text_to_left']");
//            String prizeStr = firstByXPath1.asText().replaceAll("[^\\d.]| \\.|\\.$\\s", "");
//            double prize = Double.valueOf(prizeStr);
//
//            HtmlSpan apprisedEmployeeLevel = (HtmlSpan) upSalaryPage.getElementById("apprisedEmployeeLevel");
//            String current_kv_Str = apprisedEmployeeLevel.asText();
//            double current_kv = Double.valueOf(current_kv_Str);
//
//
//            String splitStr = apprisedEmployeeLevel.getNextElementSibling().asText();
//
//            String[] split = splitStr.split(",");
//            String regex = "[^\\d.]| \\s";
//            double find_kv = Double.valueOf(split[1].replaceAll(regex,""));
//    //                double avg_kv = Double.valueOf(split[0].replaceAll(regex,""));
//            double val = 0;
//            double k = mySalary/prize;
//            double k1 = k*k;
//
//            if (k<1){
//                double base = current_kv/k1;
//                val = Math.sqrt(find_kv/base)*prize;
//            }
//            String format = new DecimalFormat("#.00").format(val);
//            salary.setValueAttribute(format);
//
//            HtmlSubmitInput btnSave = upSalaryPage.getFirstByXPath("//input[@class='button160']");
//            btnSave.click();
//        } catch (IOException e) {
////            logger.info("=======================================");
//            e.printStackTrace();
////            logger.info("========================================");
//            e.printStackTrace();
//        } catch (NullPointerException e){
////            logger.info("=======================================");
//            e.printStackTrace();
////            logger.info("========================================");
//            return unitName + "\r\n missing link -> "+e;
//        }
//        return unitName+" salary up";
        HtmlAnchor refLink = null;
        List<HtmlAnchor> xPath = unitPage.getByXPath("//a[@class='unit_button btn-virtonomics-unit']");
        for (HtmlAnchor htmlAnchor : xPath) {
            if (htmlAnchor.asText().equals("  Сотрудники и зарплата")){
                refLink = htmlAnchor;
            }
        }
        HtmlAnchor upSalaryLink = unitPage.getAnchorByHref(Objects.requireNonNull(refLink).getHrefAttribute());
        try {
            HtmlTable table = iSalary.fixSalary(upSalaryLink, unitPage);
            if (table==null){
                return unitName + " error in " + getClass().getName() + "/salaryCheck";
            }
            /*
            * Here do not need to re-check salary
            * */
//            HtmlTableCell isSalary = Objects.requireNonNull(table).getRow(3).getCell(1);
//            HtmlTableCell needSalary = table.getRow(4).getCell(1);

//            System.out.println(unitName);
//            HtmlTableCell qualification = Objects.requireNonNull(table).getRow(3).getCell(1);
//            String[] split = qualification.asText().replaceAll("[^\\d.\\s]", "").split(" {3}");
//
//            String isSalary = split[0];
//            String needSalary = split[1];
//
//            if (Double.valueOf(isSalary) < Double.valueOf(needSalary)){
//                HtmlTable fixSalary = abstractSalary.fixSalary(upSalaryLink, unitPage);
//                if (fixSalary==null)
//                    return unitName + " error in "+getClass().getName()+"/salaryUp";
//            }
        } catch (NullPointerException e){
            e.printStackTrace();
            return unitName + " some error "+e;
        }
        return unitName+" ->salary checked";
    }
}
