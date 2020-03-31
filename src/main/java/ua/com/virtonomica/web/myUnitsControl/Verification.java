package ua.com.virtonomica.web.myUnitsControl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface  Verification {

    String unitBuyFull(WebClient webClient, HtmlPage htmlPage);
    String unitReplacement (WebClient webClient, HtmlPage htmlPage);
    String unitSalary (WebClient webClient, HtmlPage htmlPage);
}
