package ua.com.virtonomica.web.myUnitsControl.plant;

//@Service
public class PlantEquipmentVerification {

//    public String buyFull(WebClient webClient, HtmlPage htmlPage) {
//        equipmentVerification.getListWearOutEquipment(webClient, htmlPage, "i-machine");
//
//        return null;
//    }
//
//    public String replacement(WebClient webClient, HtmlPage htmlPage) {
//        equipmentVerification.getListWearOutEquipment(webClient, htmlPage, "i-machine");
//
//        return null;
//    }
//
//    public String writeOff(WebClient webClient, HtmlPage htmlPage) {
//
//        return null;

//    private ISalary isalary;
//
//    public PlantVerification(ISalary iSalary) {
//        this.isalary = iSalary;
//    }
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private String unitName=null;
//
//    List<String> plantVerification(WebClient webClient, List<HtmlAnchor> lowProductiveUnits) {
//
//        List<String> answer = new ArrayList<>();
//
//        for (HtmlAnchor lowProductiveUnit : lowProductiveUnits) {
//            HtmlPage unitPage = null;
//            try {
//                unitPage = lowProductiveUnit.click();
//            } catch (IOException e) {
//                e.printStackTrace();
//                answer.add(lowProductiveUnit.asText() + " it is not possible to go through this link");
//            }
//            List<HtmlTable> infoBlock = Objects.requireNonNull(unitPage).getByXPath("//table[@class='unit_table']");
//
//            unitName = lowProductiveUnit.asText().replaceAll("_","");
//
//            if (booleanUnitSale(unitPage)){
//                continue;
//            }
//            /*================================*/
//
//            /*matching wages -відповідність зарплат*/
//            Double currentLevel = Double.valueOf(infoBlock.get(2).getRow(3).asText().replaceAll("\\D+", ""));
//            Double necessaryLevel = Double.valueOf(infoBlock.get(2).getRow(4).asText().replaceAll("\\D+", ""));
//            if (!currentLevel.equals(necessaryLevel)) {
//                answer.add(unitSalary(webClient, unitPage));
//            }
//
//            /*if reason in equipment*/
////        do else ....
//            answer.add(unitName + " -> need other operation!!!");
//        }
//        return answer;
//    }
//
//    private boolean booleanUnitSale(HtmlPage unitPage) {
//        HtmlDivision title = unitPage.getFirstByXPath("//div[@class='title']");
//        String titleAsText = title.asText();
//        return titleAsText.contains("Предприятие выставлено на продажу");
//    }
//
//    @Override
//    public String unitBuyFull(WebClient webClient, HtmlPage htmlPage) {
//        return null;
//    }
//
//    @Override
//    public String unitReplacement(WebClient webClient, HtmlPage htmlPage) {
//        return null;
//    }
//
//    @Override
//    public String unitSalary( WebClient webClient, HtmlPage unitPage ) {
//        HtmlAnchor refLink = null;
//        List<HtmlAnchor> xPath = unitPage.getByXPath("//a[@class='unit_button btn-virtonomics-unit']");
//        for (HtmlAnchor htmlAnchor : xPath) {
//            if (htmlAnchor.asText().trim().equals("Сотрудники и зарплата")){
//                refLink = htmlAnchor;
//            }
//        }
//        HtmlAnchor upSalaryLink = unitPage.getAnchorByHref(Objects.requireNonNull(refLink).getHrefAttribute());
//        System.out.println(unitName);
//        try {
//            HtmlTable table = isalary.fixSalary(upSalaryLink, unitPage);
//            if (table==null){
//                return unitName + " error in " + getClass().getName() + "/salaryCheck";
//            }
//
//            HtmlTableCell isSalary = Objects.requireNonNull(table).getRow(3).getCell(1);
//            HtmlTableCell needSalary = table.getRow(4).getCell(1);
//
//            if (Double.valueOf(isSalary.asText()) < Double.valueOf(needSalary.asText())){
//                HtmlTable fixSalary = isalary.fixSalary(upSalaryLink, unitPage);
//                if (fixSalary==null)
//                    return unitName + " error in "+getClass().getName()+"/salaryUp";
//            }
//        } catch (NullPointerException e){
//            e.printStackTrace();
//            return unitName + " some error "+e;
//        }
//        return unitName+" ->salary checked";
//    }

}
