package ua.com.virtonomica.web.myUnitsControl;

import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.TreeMap;

public class SupplyForRepairEquipment implements Supply {
    private TreeMap<Double, Integer> allAvailableSupplies = new TreeMap<>();

    @Override
    public TreeMap<Double, Integer> concludeSupplyContract(HtmlPage repairPage) {


//        HtmlTable table = getTable(repairPage);
//        int countOfRowsSupply = getCountOfRowsSupply(table);
        int countOfTab = getCountOfTab(repairPage);
        for (int i = 0; i < countOfTab; i++) {
            System.out.println("for i> " + i);
            HtmlTable tabPage = setTab(repairPage, i);
            System.out.println("====================================");
            System.out.println("====================================");
            System.out.println("====================================");
            System.out.println(tabPage.asText());
            allAvailableSupplies.putAll(availableSuppliesFromTab(tabPage));
        }

        return allAvailableSupplies;
    }

//    private HtmlTable getTable(HtmlPage repairPage) {
//        return (HtmlTable) Objects
//                .requireNonNull(repairPage)
//                .getByXPath("//table[@class='list']").get(1);
//    }
//
//    private int getCountOfRowsSupply(HtmlTable table) {
//        return table.getRowCount();
//    }

    private int getCountOfTab(HtmlPage htmlPage) {
        HtmlElement element = htmlPage.getFirstByXPath("//ul[@class='pager_list pull-right']");
        DomNodeList<HtmlElement> li = element.getElementsByTagName("li");
        int size = li.size();
        String maxPage = li.get(size - 1).asText();
        System.out.println("maxPage> "+ maxPage);
        return Integer.valueOf(maxPage);
    }

    private HtmlTable setTab (HtmlPage repairPage, int numTab){
        System.out.println("numTab> " + numTab);
        repairPage.getAnchors().iterator()
                .forEachRemaining(anchor -> System.out.println(anchor.getHrefAttribute()));
        if (numTab != 0) {
            HtmlAnchor tab = repairPage.getAnchorByHref
                    (
                            "https://virtonomica.ru/lien/window/common/util/setpaging/dbunitsman/equipmentSupplierListByProductAndCompany/50/"+numTab
                    );
            try {
                repairPage = tab.click();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (HtmlTable)repairPage.getByXPath("//table[@class='list']").get(1);
    }




    private TreeMap<Double, Integer> availableSuppliesFromTab(HtmlTable table) {
        double quality;
        int totalEquipment;
        TreeMap<Double, Integer> map = new TreeMap<>();

//        System.out.println(table.asText());
        System.out.println("count of rows>> " + table.getRowCount());
        for (int i = 1; i < table.getRowCount()-1; i++) {
            quality = Double.valueOf(table.getCellAt(i, 5).asText());
            totalEquipment = Integer.valueOf(table
                    .getCellAt(i,3).asText()
                    .replaceAll("\\D+",""));
            map.put(quality,totalEquipment);
        }
        System.out.println(map);

        return map;
    }

}
