package ua.com.virtonomica.web.myUnitsControl;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FindLowProductiveUnits {

    protected List<HtmlAnchor> findLowProductiveUnits (WebClient webClient, HtmlPage htmlPage, String hardware){
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlPage control = AbstractClickOnEquipmentControl.getControl(htmlPage);
        webClient.waitForBackgroundJavaScript(3 * 1000);
        HtmlAnchor hardwareIcon = control.getFirstByXPath(hardware);
//        String title = hardwareIcon.getAttribute("title" );
//        System.out.println(title);
        /*===========================================*/
        List<HtmlAnchor> anchorList = new ArrayList<>();
        /*=============================================*/
        try {
            /*клік на іконку з необхідним юнітом*/
            HtmlPage click = hardwareIcon.click();
            /*шукаємо скільки вкладок є з нашим обладнанням*/
            HtmlElement element = click.getFirstByXPath("//ul[@class='pager_list pull-right']");
            DomNodeList<HtmlElement> li = element.getElementsByTagName("li");
            int sizePage = li.size();
            String maxPage = li.get(sizePage - 1).asText();
            int pages = Integer.valueOf(maxPage);

            for (int j = 0; j < pages; j++) {
                if (j!=0){
                    HtmlAnchor numPage = click.getAnchorByHref
                            (
                            "https://virtonomica.ru/lien/main/common/util/setpaging/dbunit/unitListWithEquipment/50/" + j
                            );
                    click = numPage.click();
                }
                HtmlTable table = click.getFirstByXPath("//table[@class='list']");

//            Iterable<DomNode> children = click.getChildren();


//            ============================================

                /*create map with quality category and link them*/
                HashMap<Integer, String> map = new HashMap<>();
                /*
                * [13-16,5] -> 1 category
                * [16,6-18,5] -> 2 category
                * [18,6-20] -> 3 cat
                * [20,1-26] -> 4 cat
                * [26,1-30] -> 5 cat
                * [30,1-37] -> 6 cat
                *
                * can be more!!!!!!
                * */

                for (int i = 3; i < table.getRowCount() - 1; i += 2) {
                    /*wear out of equipment*/
                    String cellAt = table.getCellAt(i, 7).asText()
                            .substring(0, 4);
                    if (Double.valueOf(cellAt) > 3){
                        String s = table.getCellAt(i, 5).asText();
                        Double value = Double.valueOf(s);
//                        /*click checkbox*/
//                        table.getCellAt(i, 0).getFirstElementChild().click();
                    }

//                    System.out.println(click.getElementById("repair"));
                }
                HtmlPage repair = click.getElementById("repair").click();

                /*!!!!!!!!!!!
                * поміняти під обладнання
                * !!!!!!!!!! працює лише з тваринами*/
                String s = workWithOfficeTable(webClient, repair);




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
            }
        } catch (IOException | ElementNotFoundException e) {
            e.printStackTrace();
        }
        return anchorList;
    }

    private String workWithOfficeTable(WebClient webClient, HtmlPage repair) {

        webClient.getOptions().setJavaScriptEnabled(false);

        HtmlTable htmlTable = (HtmlTable) Objects
                .requireNonNull(repair)
                .getByXPath("//table[@class='list']").get(1);

        /*how much need chickens for replacement*/
        DomElement needChickensNumber = repair.getElementByName("quantity[from]");
        String value = needChickensNumber.getAttribute("value");
        System.out.println(">> "+value);
        return value;
    }
}
