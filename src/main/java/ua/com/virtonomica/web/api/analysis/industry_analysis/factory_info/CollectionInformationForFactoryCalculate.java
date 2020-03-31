package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

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
public class CollectionInformationForFactoryCalculate extends DomNodeWorking {
    private String unitName;
    private List<UnitSpecialization> unitSpecializations;
    private final static String regexIntOrDecimal = "(\\d+(?:\\.\\d+)?)";// find int or decimal

    private CollectionInformationForFactoryCalculate(String unitName, List<UnitSpecialization> unitSpecializations) {
        this.unitSpecializations = unitSpecializations;
        this.unitName = unitName;
    }

    public CollectionInformationForFactoryCalculate() {
    }

    public List<CollectionInformationForFactoryCalculate> start (List<UnitType> unitTypes){
        HtmlPageData pageData = new HtmlPageData();

        List<CollectionInformationForFactoryCalculate> factoryInfoForCalculates = new ArrayList<>();
        UnitSpecialization unitSpecialization = new UnitSpecialization();

        String product_Id = "";
        String qualityModifier;
        double productionsByPerson;


        for (UnitType unitType : unitTypes) {
            long id = unitType.getId();
            unitName = unitType.getName();
//            if (!unitName.equalsIgnoreCase("Хлебозавод"))continue;
            System.out.println(unitName);

            HtmlPage page = pageData.getHtmlPage("https://virtonomica.ru/lien/main/industry/unit_type/info/", id);
            HtmlTable table = page.getFirstByXPath("//table[@class='grid']");

            DomNodeList<DomNode> children = tableNavigation(table);
            List<DomNode> collect = getNotEmptyChilds(children);
            collect.remove(0);

            List<UnitSpecialization> unitSpecializations = new ArrayList<>();
            for (DomNode domNode : collect) {
                DomNodeList<DomNode> childNodes = domNode.getChildNodes();
                List<DomNode> specializationsTable = getSpecializationsTable(childNodes);

                if (specializationsTable.size()>0){   //get specialization
                    DomNode domNode1 = specializationsTable.get(4);
                    product_Id = getIdProduct(domNode1);

                    List<IngredientsOfSpecialization> ingredients= new ArrayList<>(); //value of specializationMaterials

                    qualityModifier = getQualityModifier(childNodes);
                    String specializationName = getSpecializationName(specializationsTable);
                    productionsByPerson = getProductionCharacteristics(page,specializationName);

                    List<DomNode> tdWithIngredients = getIngredientsDomList(specializationsTable);
                    for (DomNode ingredientBox : tdWithIngredients) {   /*  working with
                                                                     ingredients table(td align='center'>table)*/
                        List<DomNode> notEmptyChilds = getNotEmptyChilds(ingredientBox.getChildNodes());
                        for (DomNode child : notEmptyChilds) {

                            DomNodeList<DomNode> domNodes = tableNavigation((HtmlTable) child);
                            List<DomNode> notEmptyChilds1 = getNotEmptyChilds(domNodes);

                            for (DomNode node : notEmptyChilds1) {
                                String ingredientCount = node.asText().replaceAll("\n","");
                                DomNode sibling = node.getPreviousSibling().getPreviousSibling();
                                for (DomNode node1 : sibling.getChildNodes()) {

                                    IngredientsOfSpecialization ingredientsOfSpecialization = getIngredientsOfSpecialization(node1, ingredientCount);
                                    ingredients.add(ingredientsOfSpecialization);
                                }
                            }
                        }
                        unitSpecialization = new UnitSpecialization(specializationName,product_Id, qualityModifier, productionsByPerson, ingredients);
                    }
                    unitSpecializations.add(unitSpecialization);
                }
            }
            CollectionInformationForFactoryCalculate factoryInfoForCalculate = new CollectionInformationForFactoryCalculate(unitName, unitSpecializations);
            factoryInfoForCalculates.add(factoryInfoForCalculate);
        }
        return factoryInfoForCalculates;
    }

    private String getSpecializationName(List<DomNode> specializationsTable) {
        DomNode specializationDom = specializationsTable.get(0);
        return specializationDom.getFirstChild().asText();
    }

    private List<DomNode> getSpecializationsTable(DomNodeList<DomNode> list) {
        return list.stream()
                .filter(d -> d.getNodeName().equals("td"))
                .collect(Collectors.toList());
    }

    private IngredientsOfSpecialization getIngredientsOfSpecialization(DomNode domNode, String ingredientCount) {
        String regex = "\\W+[.]";

        String ingredientName = getIngredientName(domNode);
        String ingredientQuant = ingredientCount.replaceAll(regex, "");
        String minProductQuality = "1";

        String s = "Требуется минимальное качество сырья: ";
        if (ingredientQuant.contains(s)){
            int start = ingredientQuant.indexOf(s);
            int end = ingredientQuant.length();
            String substring = ingredientQuant.substring(start, end);
            Pattern p = Pattern.compile(regexIntOrDecimal);
            Matcher m = p.matcher(substring);
            while(m.find()) {
                minProductQuality = m.group();
                ingredientQuant = ingredientQuant.replaceAll(substring,"")
                        .replaceAll("\r\n", "");
            }
        }
        return new IngredientsOfSpecialization(ingredientName,ingredientQuant,minProductQuality);
    }

    private String getIngredientName(DomNode node) {
        return node.getFirstChild().getAttributes().getNamedItem("title").getNodeValue().replaceAll("\"","");
    }

    private String getIdProduct(DomNode domNode) {
        DomNodeList<DomNode> childNodes1 = domNode.getChildNodes();
        List<DomNode> notEmptyChilds2 = getNotEmptyChilds(childNodes1);
        for (DomNode node : notEmptyChilds2) {
            HtmlTable node1 = (HtmlTable) node;
            DomNode domNode2 = tableNavigation(node1).get(0);
            DomNodeList<DomNode> childNodes2 = domNode2.getChildNodes();
            List<DomNode> notEmptyChilds = getNotEmptyChilds(childNodes2);
            for (DomNode notEmptyChild : notEmptyChilds) {
                HtmlTable firstChild = (HtmlTable)notEmptyChild.getChildNodes().get(1);
                DomNode domNode3 = tableNavigation(firstChild).get(0);
                DomNode idProd = domNode3.getFirstChild().getFirstChild();
                Node href = idProd.getAttributes().getNamedItem("href");
                if (href !=null) {
                    String nodeValue = href.getNodeValue();
//                    System.out.println(nodeValue);
                    return getRegexResult(nodeValue);
                }
                System.out.println("ERROR: get Id Product ->null");
            }
        }
        return "";
    }

    private String getQualityModifier(List<DomNode> list) {
        String qualityModifier = "0";
        List<DomNode> notEmptyChilds = getNotEmptyChilds(list);
        for (DomNode node : notEmptyChilds) {
            if (node.asText().contains("%")){
                String x = node.asText();
//               System.out.println(x);
               String replaceAllWhite = x.replaceAll(" ", "");
               String plusOrMinus = replaceAllWhite.substring(0,1);
               String qltNumber = replaceAllWhite.substring(1, replaceAllWhite.length()-1);
               double v = Double.parseDouble(qltNumber);
//                Pattern p = Pattern.compile(regexIntOrDecimal);
//                Matcher m = p.matcher(x);
//                while (m.find()){
//                    String s = m.group();
//                    double aDouble = Double.parseDouble(s);
                    qualityModifier = plusOrMinus + v / 100;
//                }
            }
        }
        return qualityModifier;
    }

    private List<DomNode> getIngredientsDomList(List<DomNode> specializationsTable) {
        DomNode ingredientsDom = specializationsTable.get(2);
        DomNodeList<DomNode> ingredientsList = ingredientsDom.getChildNodes();
        HtmlTable tableContainsIngredients = (HtmlTable) getNotEmptyChilds(ingredientsList).get(0);

        DomNodeList<DomNode> childNodes1 = tableNavigation(tableContainsIngredients);
        DomNode tbody = getNotEmptyChilds(childNodes1).get(0);

        return getNotEmptyChilds(tbody.getChildNodes());
    }

    /*
    * get next value:
    * -count of persons;
    * -volume production by count of persons;
    * and calculate this values by one person*/
    private double getProductionCharacteristics(HtmlPage page, String specializationName) {
        HtmlTable tableCharacteristics = (HtmlTable) page.getByXPath("//table[@class='grid']").get(1);
        DomNodeList<DomNode> childNodes4 = tableCharacteristics.getBodies().get(0).getChildNodes();
        List<DomNode> tableCharacteristicsChilds = getNotEmptyChilds(childNodes4);
        int size = tableCharacteristicsChilds.size();
        DomNode lineWithValue = tableCharacteristicsChilds.get(size - 1);
        DomNode lineWithSpecializationName = tableCharacteristicsChilds.get(size-2);
        List<DomNode> domListSpecializationName = getNotEmptyChilds(lineWithSpecializationName.getChildNodes());

        List<String> listSpecializationNames = domListSpecializationName.stream()
                .map(l -> l.getFirstChild().asText())
                .collect(Collectors.toList());
        int listSpecializationNamesSize = listSpecializationNames.size();
        int indexOfListSpecializationName = listSpecializationNames.indexOf(specializationName);

        DomNodeList<DomNode> lineWithValueChilds = lineWithValue.getChildNodes();

        List<DomNode> values = getNotEmptyChilds(lineWithValueChilds);
        List<DomNode> collect = values.stream()
                .skip(values.size() - listSpecializationNamesSize)
                .collect(Collectors.toList());
        DomNode node = collect.get(indexOfListSpecializationName);
        String volumeProductionStr = node.asText().replaceAll(" ","");

        String countPersonsStr = values.get(0).asText();

        String regexResult = getRegexResult(countPersonsStr);
        int countPersons = Integer.parseInt(regexResult);
        double volumeProduction;
        if (volumeProductionStr.contains("/")){
            String[] split = volumeProductionStr.split("/");
            int s = Integer.valueOf(split[0]);
            int e = Integer.valueOf(split[1]);
            volumeProduction = (double) s/e;
        } else volumeProduction = Integer.parseInt(volumeProductionStr);
        return volumeProduction / countPersons;
    }

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
        if (!(o instanceof CollectionInformationForFactoryCalculate)) return false;
        CollectionInformationForFactoryCalculate that = (CollectionInformationForFactoryCalculate) o;
        return Objects.equals(unitName, that.unitName) &&
                Objects.equals(unitSpecializations, that.unitSpecializations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(unitName, unitSpecializations);
    }

    @Override
    public String toString() {
        return "CollectionInformationForFactoryCalculate{" +
                "unitName='" + unitName + '\'' +
                ", unitSpecialization=" + unitSpecializations +
                '}';
    }
}
