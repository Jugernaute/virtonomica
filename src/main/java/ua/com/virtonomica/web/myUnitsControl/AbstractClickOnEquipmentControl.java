package ua.com.virtonomica.web.myUnitsControl;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/*Click on MyCompany -> equipment control*/
public abstract class AbstractClickOnEquipmentControl {
    public static HtmlPage getControl (HtmlPage htmlPage){
        String httpManagement = "https://virtonomica.ru/lien/main/management_action/3247145";
        HtmlAnchor management = htmlPage.getAnchorByHref(httpManagement);
        HtmlPage control = null;
        try {
            control = management.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return control;
    }
}
