package ua.com.virtonomica.web.api;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import ua.com.virtonomica.web.connection.UserWebClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlPageData {

    public String getContent(String url){
        WebClient webClient = getWebClient();
        String content = "";
        if (webClient!=null){
            try {
                Page p = webClient.getPage(url);
                content = p.getWebResponse().getContentAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (FailingHttpStatusCodeException e){
                System.out.println("Идет пересчет игровой ситуации");
            }
        }else System.out.println("Спочатку потрібно зайти на головну сторінку ігри, щоб отримати куки");
        return content;
    }

    public HtmlPage getHtmlPage(String pageUrl, long unitTypeId){
        URL url = createUrl(pageUrl);
        HtmlPage page = null;
        try {
            WebClient webClient = UserWebClient.webClient;
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            page = webClient.getPage(url+""+unitTypeId);
        } catch (IOException e) {
            System.out.println("Error IndustryUnitPage with unit_class_id="+unitTypeId);
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("Потрібна авторизація в ігрі");
        }
        return  page;
    }

    public HtmlPage getHtmlPage(String pageUrl){
        URL url = createUrl(pageUrl);
        HtmlPage page = null;
        try {
            WebClient webClient = UserWebClient.webClient;
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            page = webClient.getPage(url);
        } catch (IOException e) {
            System.out.println("Error page = "+pageUrl);
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("Потрібна авторизація в ігрі");
        }
        return  page;
    }

    private URL createUrl (String pageUrl){
        try {
            return new URL(pageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private WebClient getWebClient(){
        return UserWebClient.webClient;
    }

}
