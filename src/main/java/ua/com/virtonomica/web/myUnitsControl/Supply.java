package ua.com.virtonomica.web.myUnitsControl;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.TreeMap;

public interface Supply {

    TreeMap<Double, Integer> concludeSupplyContract (HtmlPage repairPage);
//    HtmlTable getTable (HtmlPage page);
//    int getCountOfRowsSupply(HtmlTable table);
//    int getCountOfTab(HtmlPage page);
//    HashMap<Double,Integer> allAvailableSupplies (HtmlTable table, int countOfRows);




}
