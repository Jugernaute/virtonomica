package ua.com.virtonomica.web.myUnitsControl.equipment;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import ua.com.virtonomica.web.myUnitsControl.AbstractClickOnEquipmentControl;
import ua.com.virtonomica.web.myUnitsControl.Supply;

import java.io.IOException;
import java.util.*;

/*Tab MyComputer -> Control -> hardware in xPath
* hardware -> offices computer, plants machine tool, sawmill equipment and other*/
public class RepairEquipment extends AbstractClickOnEquipmentControl implements IEquipmentFix {
    private Supply supply;

    public RepairEquipment(Supply supply) {
        this.supply = supply;
    }

    public void checkAndRepair(WebClient webClient, HtmlPage htmlPage, String hardware) {

        createWebClient(webClient);
        HtmlPage pageControl = getPageControl(htmlPage);
        HtmlPage hardwarePage = chooseHardware(pageControl, hardware);
        int countOfTab = getCountOfTab(Objects.requireNonNull(hardwarePage));
        for (int i = 0; i < countOfTab; i++) {
            System.out.println("сторінка з зношеним обладнанням " + i);
            HtmlPage refreshPage = refreshPageWithSetNewTab(hardwarePage, i);
            HtmlTable table = getTab(refreshPage);
            HashMap<Double, List<Integer>> wearEquipment = getWearEquipments (table);
            checkboxUnits(table,wearEquipment);
//            System.out.println(table.asText());
//            HtmlTableRow row = table.getRow(table.getRowCount()-1);
//            System.out.println("=============================");
//            System.out.println(row);
//            System.out.println("=============================");
//            HtmlTableRow.CellIterator cellIterator = row.getCellIterator();
//            HtmlElement byXPath = table.getFirstByXPath("//ul[@class='pager_list pull-right']");
//            DomNodeList<HtmlElement> li = byXPath.getElementsByTagName("li");
//            HtmlElement element = li.get(li.size() - 1);
//            String nodeValue = element.getFirstChild().getAttributes().item(0).getNodeValue();
//
//            table.
//            System.out.println(nodeValue);
//            System.out.println(element.getFirstChild());

            HtmlPage repairPage = clickOnRepair(refreshPage);
//            System.out.println(Objects.requireNonNull(repairPage,"repairPage must not be null").asText());
            repair(webClient, repairPage);

        }

//        HtmlPage control = AbstractClickOnEquipmentControl.getControl(htmlPage);
//        HtmlAnchor hardwareIcon = control.getFirstByXPath(hardware);
        //        String title = hardwareIcon.getAttribute("title" );
//        System.out.println(title);
//        try {
//            /*клік на іконку з необхідним юнітом*/
//            HtmlPage click = hardwareIcon.click();
//            /*шукаємо скільки вкладок є з нашим обладнанням*/
//            HtmlElement element = click.getFirstByXPath("//ul[@class='pager_list pull-right']");
//            DomNodeList<HtmlElement> li = element.getElementsByTagName("li");
//            int sizePage = li.size();
//            String maxPage = li.get(sizePage - 1).asText();
//            int pages = Integer.valueOf(maxPage);
//
//            for (int j = 0; j < pages; j++) {
//                if (j!=0){
//                    HtmlAnchor numPage = click.getAnchorByHref
//                            (
//                                    "https://virtonomica.ru/lien/main/common/util/setpaging/dbunit/unitListWithEquipment/50/" + j
//                            );
//                    click = numPage.click();
//                }
//                HtmlTable table = click.getFirstByXPath("//table[@class='list']");
//
//                /*
//                all quality are 26.00 (must be)
//                 * */
//
//                checkUnits(table);
//                repair(webClient, click);
//
//                String s = workWithOfficeTable(webClient, repair);


//            ============================================

//            HtmlAnchor anchorByHref = click.getAnchorByHref("https://virtonomica.ru/lien/main/unit/view/3253051");
//            HtmlPage click9 = anchorByHref.click();

//            anchorList.add(anchorByHref);
//            return salary(webClient, click9);

//                for (int i = 3; i < table.getRowCount() - 1; i += 2) {
//                    /*efficiency of units*/
//                    String cellAt = table.getCellAt(i, 8).asText().replaceAll("\\D+", "");
//                    /*if efficiency is < ...%*/
//                    if (Integer.valueOf(cellAt) < 9800) {
//                        if (Integer.valueOf(cellAt)==0){
//                            continue;
//                        }
//                        DomNode firstChild = table.getCellAt(i, 2);
////
//                        HtmlAnchor unitLink = (HtmlAnchor) firstChild.querySelectorAll("a").get(0);
////
////            System.out.println(unitLink.asText());
//                        anchorList.add(unitLink);
//                    }
//                }
//            }
//        } catch (IOException | ElementNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void createWebClient(WebClient webClient) {
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.waitForBackgroundJavaScript(3 * 1000);
    }

    private HtmlPage getPageControl(HtmlPage htmlPage) {
        return AbstractClickOnEquipmentControl.getControl(htmlPage);
    }

    private HtmlPage chooseHardware(HtmlPage htmlPage, String hardware) {
        HtmlPage click;
        HtmlAnchor hardwareIcon = getPageControl(htmlPage).getFirstByXPath(hardware);
        try {
            click = hardwareIcon.click();
            return click;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getCountOfTab(HtmlPage htmlPage) {
        HtmlElement element = htmlPage.getFirstByXPath("//ul[@class='pager_list pull-right']");
        DomNodeList<HtmlElement> li = element.getElementsByTagName("li");
        int size = li.size();
        String maxPage = li.get(size - 1).asText();
        return Integer.valueOf(maxPage);
    }

    private HtmlPage refreshPageWithSetNewTab(HtmlPage refreshPage, int numTab) {
        if (numTab != 0) {
            HtmlAnchor tab = refreshPage.getAnchorByHref
                    (
                            "https://virtonomica.ru/lien/main/common/util/setpaging/dbunit/unitListWithEquipment/50/" + numTab
                    );
            try {
                refreshPage = tab.click();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return refreshPage/*.getFirstByXPath("//table[@class='list']")*/;
    }

    private HtmlTable getTab (HtmlPage refreshPage){
        return refreshPage.getFirstByXPath("//table[@class='list']");
    }


    private HashMap<Double, List<Integer>> getWearEquipments (HtmlTable table){
        HashMap<Double,List<Integer>> needQuantity = new HashMap<>();
        List<Integer> line = new ArrayList<>();
        for (int i = 3; i < table.getRowCount() - 1; i += 2) {
            /*deprecation of equipment*/
            String wearEquipment = table.getCellAt(i, 7).asText()
                    .substring(0, 4);
            if (Double.valueOf(wearEquipment) == 1.68) {
                String needQuantityEquipment = table.getCellAt(i, 6).asText();
                Double value = Double.valueOf(needQuantityEquipment);
                line.add(i);
                needQuantity.put(value, line);
            }
        }
        return needQuantity;
    }

    private void checkboxUnits(HtmlTable table, HashMap<Double, List<Integer>> wearEquipment) {

        wearEquipment.forEach((aDouble, integers) -> integers.forEach(i -> {
            try {
                table.getCellAt(i,0).getFirstElementChild().click();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }


    private HtmlPage clickOnRepair(HtmlPage hardwarePage) {
        try {
            DomElement btnRepair = hardwarePage.getElementById("repair");
            if (isButtonRepairDisabled(btnRepair)){
                return btnRepair.click();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isButtonRepairDisabled(DomElement btnRepair){
        String disabled = btnRepair.getAttribute("disabled");
        return !disabled.equals("disabled");
    }

    @Override
    public String repair(WebClient webClient, HtmlPage repairPage) {
        CookieManager cookieManager = webClient.getCookieManager();
        Set<Cookie> cookies = cookieManager.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(">>>>>>>>" +cookie);
        }
        return "cookie";
//        TreeMap<Double, Integer> treeMap = supply.concludeSupplyContract(repairPage);
//        System.out.println(treeMap);
//        return "ok";
//
////        System.out.println(repairPage.asText());
////        +++++++++++++++++++++++++++++++++
////        webClient.getOptions().setJavaScriptEnabled(false);
//
//        HtmlTable htmlTable = (HtmlTable) Objects
//                .requireNonNull(repairPage)
//                .getByXPath("//table[@class='list']").get(1);
//        System.out.println("row count " + htmlTable.getRowCount());
//        int countOfTab = getCountOfTab(repairPage);
//
//
//
//        DomElement quantityEquipment = repairPage.getElementByName("quantity[from]");
//        String value = quantityEquipment.getAttribute("value");
//        int needEquipment = Integer.valueOf(value);
//
//        for (int i = 0; i < 20; i++) {
//
//            String qualityCell = htmlTable.getCellAt(i, 5).asText();
//
//            /*how much chickens are on storage*/
//            String totalEquipmentOnStorage = htmlTable.getCellAt(i, 3).asText();
//
//            int totalEquipment;
//            float qualityEquipment;
//            try {
//                totalEquipment = Integer.valueOf(totalEquipmentOnStorage.replaceAll("\\D+", ""));
//                qualityEquipment = Float.valueOf(qualityCell);
//            } catch (NumberFormatException e) {
//                continue;
//            }
//            if (needEquipment <= totalEquipment && qualityEquipment > 9.0f) {
//                /*find this input type='radio' and check it*/
//                HtmlRadioButtonInput radio = (HtmlRadioButtonInput) repairPage.getElementsByName("supplyData[offer]").get(i - 1);
//                radio.setChecked(true);
//
//                DomElement buyButton = repairPage.getElementByName("submitRepair");
//                String totalPrice = repairPage.getElementById("totalPrice").asText();
//                Long price = Long.valueOf(totalPrice.replaceAll("\\D+", ""));
////                webClient.getOptions().setJavaScriptEnabled(true);
//                /*add return attention on telegram*/
//                if (price > 1000000000) {
//                    return "price buy chickens is too large " + price;
//                }
////                try {
////                    buyButton.click();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                return "Закуплено курей на суму " + price;
//            }
//        }
//        return "some error";
//
////        +++++++++++++++++++++++++++++++++
////        System.out.println(repairPage.asText());
////        return null;
    }

    private String getNameRepairingUnit (HtmlPage repairPage){
        HtmlTable titleTable = (HtmlTable) repairPage.getByXPath("//table[@class='local_header']").get(0);
        //        System.out.println(titleName);
        return titleTable.getRow(1).asText();
    }
}
