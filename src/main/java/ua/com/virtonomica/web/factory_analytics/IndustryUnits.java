package ua.com.virtonomica.web.factory_analytics;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.service.industrial.unit_type.UnitTypeService;
import ua.com.virtonomica.web.connection.UserWebClient;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class IndustryUnits {
    private String unitName;
    private String specialization;
    private final int employeeCount = 5000;
    private final int technology = 22;
    private List<String> materials=new ArrayList<>();
    private String product;
    private String productCount;
    private String productQuality;
    private String minProductQuality;
    private double productCostPrice;
    private int productSellPrice;
    private String profit;

    private HashMap<String,HashMap<String,List<String>>>map = new HashMap<>();



    private final UnitTypeService unitTypeService;

    public IndustryUnits(UnitTypeService unitTypeService) {
        this.unitTypeService = unitTypeService;
    }

    public List<UnitType> getUnits(){
        List<UnitType> unitTypeByUnitClassName = unitTypeService.getUnitTypeByUnitClassName(UnitClassEnum.Завод);
        industryCalculation(unitTypeByUnitClassName);
        return unitTypeByUnitClassName;
    }

    private void industryCalculation(List<UnitType> unitTypes){
        map.clear();
        Set<String> unitNameKeys = map.keySet();
        List<String> innerList= new ArrayList<>();


        for (UnitType unitType : unitTypes) {
//            innerMap.clear();
            long id = unitType.getId();
            unitName = unitType.getName();
//            System.out.println(id +" " + unitName);

            HtmlPage page = getIndustryUnitPage(id);
            HtmlTable table = page.getFirstByXPath("//table[@class='grid']");
            DomNodeList<DomNode> children = table.getBodies().get(0).getChildNodes();
            List<DomNode> collect = getNotEmptyChilds(children);

            for (DomNode domNode : collect) {
                HashMap<String,List<String>>innerMap = new HashMap<>();
                for (String s : unitNameKeys) {
                    innerMap = map.get(s);
                }
                innerList.clear();
                DomNodeList<DomNode> childNodes = domNode.getChildNodes();
                List<DomNode> td = childNodes.stream()
                        .filter(d -> d.getNodeName().equals("td"))
                        .collect(Collectors.toList());

                if (td.size()>0){
                    DomNode specializationDom = td.get(0);
                    DomNode materialsDom = td.get(2);
//
                    specialization = specializationDom.getFirstChild().asText();
//                    System.out.println(specialization);

                    DomNodeList<DomNode> nodes = materialsDom.getChildNodes();
//                    HtmlTable tableContainsMaterials = (HtmlTable)nodes.stream()
//                            .filter(n -> !n.toString().equals(""))
//                            .collect(Collectors.toList()).get(0);
                    HtmlTable tableContainsMaterials = (HtmlTable) getNotEmptyChilds(nodes).get(0);

                    HtmlTableBody body = tableContainsMaterials.getBodies().get(0);
                    DomNodeList<DomNode> childNodes1 = body.getChildNodes();
//                    childNodes1.forEach(System.out::println);

                    DomNode tbody = getNotEmptyChilds(childNodes1).get(0);
                    List<DomNode> tdWithMaterials = getNotEmptyChilds(tbody.getChildNodes());
//                    tdWithMaterials.forEach(System.out::println);
                    //working with each materials
                    for (DomNode materialBox : tdWithMaterials) {
//                        materials.clear();
                        List<DomNode> notEmptyChilds = getNotEmptyChilds(materialBox.getChildNodes());
                        for (DomNode child : notEmptyChilds) {
                            DomNodeList<DomNode> domNodes = ((HtmlTable) child).getBodies().get(0).getChildNodes();
                            List<DomNode> notEmptyChilds1 = getNotEmptyChilds(domNodes);
                            for (DomNode node : notEmptyChilds1) {
                                String material = node.asText();
                                materials.add(material);
                                DomNode previousSibling = node.getPreviousSibling().getPreviousSibling();
                                for (DomNode node1 : previousSibling.getChildNodes()) {
                                    product = node1.getFirstChild().getAttributes().getNamedItem("title").getNodeValue();
                                    String regex = "\\W+[.]";
                                    productCount = material.replaceAll(regex, "");
                                    String s = "Требуется минимальное качество сырья: ";
                                    if (productCount.contains(s)){
                                        int start = productCount.indexOf(s);
                                        int end = productCount.length();
                                        String substring = productCount.substring(start, end);
                                        String regex2 = "(\\d+(?:\\.\\d+)?)";// find int or decimal
                                        Pattern p = Pattern.compile(regex2);
                                        Matcher m = p.matcher(substring);
                                        while(m.find()) {
                                            minProductQuality = m.group();
                                            productCount = productCount.replaceAll(substring,"");
                                            productCount = productCount.replaceAll("\r\n", "");
                                        }
                                    } else {
                                        minProductQuality = "0";
                                    }
                                    innerList.add(product+": "+ productCount);
//                                    System.out.println(product+": "+productCount+" ,minQuality: "+minProductQuality);
                                }
                            }
                        }
                        innerMap.put(specialization,innerList);
                    }
                 }
                map.put(unitName,innerMap);
            }
//            inner?`Map.put(specialization,productCount);
        }
        for (String s : map.keySet()) {
            HashMap<String, List<String>> mapValue = map.get(s);
            Set<String> keySet = mapValue.keySet();
            System.out.println(s+"=>\r\n");
            for (String s1 : keySet) {
                List<String> strings = mapValue.get(s1);
                System.out.println(s1);
                for (String string : strings) {
                    System.out.println(" : "+string);
                }
            }
            System.out.println("==============================");
        }
    }

    private List<DomNode> getNotEmptyChilds(Collection<DomNode> elements) {
        return elements.stream()
                .filter(e -> !e.asText().equals(""))
                .collect(Collectors.toList());
    }

    private HtmlPage getIndustryUnitPage(long id){
//        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
        WebClient webClient = UserWebClient.webClient;
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = null;
        try {
            //https:virtonomica.ru/lien/main/industry/unit_type/info/370100
            page = webClient.getPage("https://virtonomica.ru/lien/main/industry/unit_type/info/" + id);


        } catch (IOException e) {
            System.out.println("Error IndustryUnitPage with unit_class_id="+id);
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("Потрібна авторизація в ігрі");
        }
        return  page;
    }
}
