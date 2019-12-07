package ua.com.virtonomica.web.myUnitsControl.animal;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import ua.com.virtonomica.web.myUnitsControl.AbstractClickOnEquipmentControl;
import ua.com.virtonomica.web.myUnitsControl.Verification;

import java.io.IOException;
import java.util.Objects;


public class ChickenVerification extends AnimalTable implements Verification {

    /*get info with chickens and chooseAllCheckbox*/
    private HtmlPage chickenClick(WebClient webClient, HtmlPage htmlPage){
        /*Моя компания - управление*/
        HtmlPage control = AbstractClickOnEquipmentControl.getControl(htmlPage);

        /*choose animals*/
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlAnchor animal = Objects.requireNonNull(control).getAnchorByHref("https://virtonomica.ru/lien/main/company/view/3247145/unit_list/animals");
        HtmlPage animalChoose = null;
        try {
            animalChoose = animal.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(animalChoose.asText());

        /*click chickens*/

        HtmlAnchor chickenRef = Objects.requireNonNull(animalChoose).getFirstByXPath("//a[@class='i-chicken']");
        HtmlPage chicken = null;
        try {
            chicken = chickenRef.click();
        } catch (NullPointerException e) {
            HtmlAnchor chickenRef2 = Objects.requireNonNull(animalChoose).getFirstByXPath("//a[@class='i-chicken u-s']");
            try {
                chicken = chickenRef2.click();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }

        /*checkbox all click*/
        DomElement checkboxAll = Objects.requireNonNull(chicken).getElementById("units[selectedaAll]");
        try {
            checkboxAll.click();
        } catch (IOException e) {
            System.out.println("checkboxAll of chicken error" + "\r\n"+ e.getMessage());
            e.printStackTrace();
        }

        return chicken;
    }

    @Override
    public String unitBuyFull(WebClient webClient, HtmlPage htmlPage){

        HtmlPage chicken = chickenClick(webClient, htmlPage);

        DomElement buy = chicken.getElementByName("buy");
        /*Якщо загони заповнені на максимум, то кнопка закупити буде не доступна,
        * тому і робимо провірку*/
        String buyAttribute = buy.getAttribute("disabled");
        if (buyAttribute.equals("disabled")){
            return "Загони з курами - заповнені на максимум";
        }

        /*Over on page where choose necessary chikens*/
        HtmlPage fillFerm = null;
        try {
            fillFerm = buy.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workWithAnimalTable(webClient, fillFerm);
    }

    @Override
    public String unitReplacement (WebClient webClient, HtmlPage htmlPage) {
        HtmlPage chickens = chickenClick(webClient, htmlPage);

        DomElement repair = chickens.getElementById("repair");

        /*Якщо всі кури здорові, то кнопка вилікувати буде не доступна,
         * тому і робимо провірку*/
        String buyAttribute = repair.getAttribute("disabled");
        if (buyAttribute.equals("disabled")){
            return "Кури - всі здорові";
        }
        /*Over on page where choose necessary chikens*/
        HtmlPage healthyChick = null;
        try {
            healthyChick = repair.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workWithAnimalTable(webClient, healthyChick);
    }

    @Override
    public String unitSalary(WebClient webClient, HtmlPage htmlPage) {
        return null;
    }
}
