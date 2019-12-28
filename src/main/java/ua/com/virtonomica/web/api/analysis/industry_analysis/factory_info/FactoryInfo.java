package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gargoylesoftware.htmlunit.html.*;
import org.w3c.dom.Node;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.web.api.HtmlPageData;
import ua.com.virtonomica.web.api.analysis.industry_analysis.DomNodeWorking;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class FactoryInfo extends DomNodeWorking {
    private String unitName;
    private List<UnitSpecialization> unitSpecializations;
    private final static String regexIntOrDecimal = "(\\d+(?:\\.\\d+)?)";// find int or decimal

    private FactoryInfo(String unitName, List<UnitSpecialization> unitSpecializations) {
        this.unitSpecializations = unitSpecializations;
        this.unitName = unitName;
    }

    public FactoryInfo() {
    }

    public List<FactoryInfo> industryCalculation(List<UnitType> unitTypes){
        HtmlPageData pageData = new HtmlPageData();

        List<FactoryInfo> factoryInfos = new ArrayList<>();
        UnitSpecialization unitSpecialization = new UnitSpecialization();

        String product;
        String product_Id = "";
        String productCount;
        String minProductQuality="1";
        String qualityModifier="0";


        for (UnitType unitType : unitTypes) {
            long id = unitType.getId();
            unitName = unitType.getName();

            HtmlPage page = pageData.getHtmlPage("https://virtonomica.ru/lien/main/industry/unit_type/info/", id);
//            HtmlPage page = getIndustryUnitPage(id);
            HtmlTable table = page.getFirstByXPath("//table[@class='grid']");

            DomNodeList<DomNode> children = tableNavigation(table);
            List<DomNode> collect = getNotEmptyChilds(children);

            List<UnitSpecialization> unitSpecializations = new ArrayList<>();
            for (DomNode domNode : collect) {
                DomNodeList<DomNode> childNodes = domNode.getChildNodes();

                List<DomNode> td = childNodes.stream()
                        .filter(d -> d.getNodeName().equals("td"))
                        .collect(Collectors.toList());

                if (td.size()>0){   //get specialization
                    DomNode specializationDom = td.get(0);
                    DomNode materialsDom = td.get(2);
                    String specialization = specializationDom.getFirstChild().asText(); //key of specializationMaterials
                    List<SpecializationMaterials> materials= new ArrayList<>(); //value of specializationMaterials

                    List<DomNode> notEmptyChilds2 = getNotEmptyChilds(childNodes);
                    for (DomNode node : notEmptyChilds2) {
                        if (node.asText().contains("%")){
                            String x = node.asText();
                            Pattern p = Pattern.compile(regexIntOrDecimal);
                            Matcher m = p.matcher(x);
                            while (m.find()){
                                String s = m.group();
                                Double aDouble = Double.valueOf(s);
                                qualityModifier = String.valueOf(aDouble/100);
                            }
                        } else qualityModifier="0";
                    }
                    DomNodeList<DomNode> nodes = materialsDom.getChildNodes();
                    HtmlTable tableContainsMaterials = (HtmlTable) getNotEmptyChilds(nodes).get(0);

                    DomNodeList<DomNode> childNodes1 = tableNavigation(tableContainsMaterials);
                    DomNode tbody = getNotEmptyChilds(childNodes1).get(0);

                    List<DomNode> tdWithMaterials = getNotEmptyChilds(tbody.getChildNodes());

                    for (DomNode materialBox : tdWithMaterials) {   /*  working with
                                                                     materials table(td align='center'>table)*/
                        List<DomNode> notEmptyChilds = getNotEmptyChilds(materialBox.getChildNodes());
                        for (DomNode child : notEmptyChilds) {

                            DomNodeList<DomNode> domNodes = tableNavigation((HtmlTable) child);
                            List<DomNode> notEmptyChilds1 = getNotEmptyChilds(domNodes);

                            for (DomNode node : notEmptyChilds1) {
                                String material = node.asText();
                                DomNode previousSibling = node.getPreviousSibling().getPreviousSibling();
                                DomNode idProduct1 = previousSibling.getFirstByXPath("/html/body/div/div[2]/table[3]/tbody/tr[2]/td[5]/table/tbody/tr/td/table/tbody/tr/td/a");
                                Node href1 = idProduct1.getAttributes().getNamedItem("href");
                                if (href1 !=null) {
                                    String nodeValue = href1.getNodeValue();
                                    product_Id = getRegexResult(nodeValue);
                                }

                                for (DomNode node1 : previousSibling.getChildNodes()) {
                                    product = node1.getFirstChild().getAttributes().getNamedItem("title").getNodeValue();
                                    String regex = "\\W+[.]";
                                    productCount = material.replaceAll(regex, "");
                                    String s = "Требуется минимальное качество сырья: ";
                                    if (productCount.contains(s)){
                                        int start = productCount.indexOf(s);
                                        int end = productCount.length();
                                        String substring = productCount.substring(start, end);
                                        Pattern p = Pattern.compile(regexIntOrDecimal);
                                        Matcher m = p.matcher(substring);
                                        while(m.find()) {
                                            minProductQuality = m.group();
                                            productCount = productCount.replaceAll(substring,"")
                                                    .replaceAll("\r\n", "");
                                        }
                                    } else {
                                        minProductQuality = "1";
                                    }
                                    SpecializationMaterials specializationMaterials1 = new SpecializationMaterials(product,productCount,minProductQuality);
                                    materials.add(specializationMaterials1);
                                }
                            }
                        }
                        unitSpecialization = new UnitSpecialization(specialization,product_Id, qualityModifier, materials);
                    }
                    unitSpecializations.add(unitSpecialization);
                }
            }
            FactoryInfo factoryInfo = new FactoryInfo(unitName, unitSpecializations);
            factoryInfos.add(factoryInfo);
        }
        return factoryInfos;
    }


//    private HtmlPage getIndustryUnitPage(long id){
//        HtmlPage page = null;
//        try {
//            WebClient webClient = UserWebClient.webClient;
//            webClient.getOptions().setJavaScriptEnabled(false);
//            webClient.getOptions().setCssEnabled(false);
//            page = webClient.getPage("https://virtonomica.ru/lien/main/industry/unit_type/info/" + id);
//        } catch (IOException e) {
//            System.out.println("Error IndustryUnitPage with unit_class_id="+id);
//            e.printStackTrace();
//        }catch (NullPointerException e){
//            System.out.println("Потрібна авторизація в ігрі");
//        }
//        return  page;
//    }



    private String getRegexResult(String s){
        List<String>list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexIntOrDecimal);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()){
            list.add(matcher.group());
        }
        return list.get(0);

    }


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<UnitSpecialization> getUnitSpecializations() {
        return unitSpecializations;
    }

    public void setUnitSpecializations(List<UnitSpecialization> unitSpecialization) {
        this.unitSpecializations = unitSpecialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactoryInfo)) return false;
        FactoryInfo that = (FactoryInfo) o;
        return Objects.equals(unitName, that.unitName) &&
                Objects.equals(unitSpecializations, that.unitSpecializations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(unitName, unitSpecializations);
    }

    @Override
    public String toString() {
        return "FactoryInfo{" +
                "unitName='" + unitName + '\'' +
                ", unitSpecialization=" + unitSpecializations +
                '}';
    }
}
