package ua.com.virtonomica.web.myUnitsControl.animal;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

import java.io.IOException;
import java.util.Objects;

/*Class fill or cures all animals*/
public abstract class AnimalTable {

    public String workWithAnimalTable(WebClient webClient, HtmlPage htmlPage) {
        /*off js*/
        webClient.getOptions().setJavaScriptEnabled(false);

        HtmlTable htmlTable = (HtmlTable) Objects
                .requireNonNull(htmlPage)
                .getByXPath("//table[@class='list']").get(1);

        /*how much need chickens for replacement*/
        DomElement needChickensNumber = htmlPage.getElementByName("quantity[from]");
        String value = needChickensNumber.getAttribute("value");
        int needChickens = Integer.valueOf(value);

        /**/

        for (int i = 0; i < 20; i++) {

            /*quality of chickens*/
            String qual = htmlTable.getCellAt(i, 5).asText();

            /*how much chickens are on storage*/
            String areChickensNumber = htmlTable.getCellAt(i, 3).asText();

            int areChickens;
            float quality;
            try {
                areChickens = Integer.valueOf(areChickensNumber.replaceAll("\\D+", ""));
                quality = Float.valueOf(qual);
            } catch (NumberFormatException e) {
                continue;
            }
            if (needChickens <= areChickens && quality > 9.0f) {
                /*find this input type='radio' and check it*/
                HtmlRadioButtonInput radio = (HtmlRadioButtonInput) htmlPage.getElementsByName("supplyData[offer]").get(i - 1);
                radio.setChecked(true);

                DomElement buyButton = htmlPage.getElementByName("submitRepair");
                String totalPrice = htmlPage.getElementById("totalPrice").asText();
                Long price = Long.valueOf(totalPrice.replaceAll("\\D+", ""));
                /*add return attention on telegram*/
                if (price > 1000000000) {
                    return "price buy chickens is too large " + price;
                }
                try {
                    buyButton.click();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Закуплено курей на суму " + price;
            }
        }
        return "some error";
    }
}
