package ua.com.virtonomica.web.myUnitsControl.equipment;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface IEquipmentFix {

    String repair (WebClient webClient, HtmlPage htmlPage);
    /*other methods no need now*/
//    String buyFull(WebClient webClient, HtmlPage htmlPage);
//    String writeOff (WebClient webClient, HtmlPage htmlPage);
}
