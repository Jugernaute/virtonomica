package ua.com.virtonomica.web.myUnitsControl.shops;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableHeaderCellElement;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.UserDataHandler;
import ua.com.virtonomica.utils.reports.UnitsSupplyContracts;
import ua.com.virtonomica.web.api.VirtonomicaAPI;
import ua.com.virtonomica.web.connection.UserWebClient;

import java.io.IOException;
import java.util.List;

@Component
public class ShopSettings {
    private static final Logger logger = LoggerFactory.getLogger(ShopSettings.class);

    private final VirtonomicaAPI virtonomicaAPI;

    @Autowired
    public ShopSettings(VirtonomicaAPI virtonomicaAPI) {
        this.virtonomicaAPI = virtonomicaAPI;
    }

    //Вкладка доставки
    public List<UnitsSupplyContracts> SupplyTab(long shopId/*, long contractId*/){
        logger.info("Запуск методу переходу на вкладку поставщиків в магазині...");
        WebClient webClient = UserWebClient.webClient;
        webClient.getOptions().setJavaScriptEnabled(false);
        logger.info("Отримуємо список контрактів...");
        List<UnitsSupplyContracts> supplyContractsByUnitId = virtonomicaAPI.getSupplyContractsByUnitId(shopId);
//        for (UnitsSupplyContracts unitsSupplyContracts : supplyContractsByUnitId) {
//            System.out.println(unitsSupplyContracts);
//            long product_id = unitsSupplyContracts.getProduct_id();
//            System.out.println("++++++++++++");
//            System.out.println(product_id);
            try {
//                logger.info("Клік на вкладку поставщиків...");
                HtmlPage page = webClient.getPage("https://virtonomica.ru/lien/main/unit/view/" + shopId + "/supply");
                HtmlTable table = page.getFirstByXPath("//table[@class='list']");
                HtmlTableBody body = table.getBodies().get(0);
                Iterable<DomNode> children = body.getChildren();
                for (DomNode child : children) {
                    if (child.getAttributes().getNamedItem("id")!=null){
//                        System.out.println(child);
                        Iterable<DomNode> domNodes = child.getChildren();/*.forEach(System.out::println);*/
                        for (DomNode domNode : domNodes) {
                            String nodeValue = domNode.getNodeName();
                            if (nodeValue.equals("th")) {
                                Iterable<DomNode> nodes = domNode.getChildren();
                                for (DomNode node : nodes) {
                                    if(node.getNodeName().equals("table")){
//                                        System.out.println(node);
                                        DomNode domTable = node.getChildNodes().get(1);
//                                        System.out.println(domTable);
//                                        domTable.getChildren().forEach(System.out::println);
                                        DomNode tBody = domTable.getChildNodes().get(0);
                                        DomNode tdRowSpan = tBody.getChildNodes().get(1);
//                                        System.out.println(tdRowSpan);
//                                        System.out.println("->");
                                        System.out.println(tdRowSpan.getFirstChild().getAttributes().getNamedItem("alt").getNodeValue());
                                    }
                                }
                                System.out.println(nodeValue + " -> "+domNode.getAttributes().getNamedItem("rowspan").getNodeValue());
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return supplyContractsByUnitId;
        }
    }


