package ua.com.virtonomica.web.connection;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import com.gargoylesoftware.htmlunit.util.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Configuration
@PropertySource("classpath:virtonomicaConfig.properties")
public class Virtonomica implements EnvironmentAware {
    private WebClient webClient;
    private static Environment environment;

    @Autowired
    public Virtonomica() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.waitForBackgroundJavaScript(3 * 1000);
        webClient.getOptions().setCssEnabled(false);
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public final WebClient getWebClient(String cookie){
        String s = cookie.replaceAll("\"", "");
        webClient = new WebClient(BrowserVersion.CHROME);
        Cookie cookie1 = new Cookie(
                ".virtonomica.ru","PHPSESSID",s,"/",1000,false);
        webClient.getCookieManager().addCookie(cookie1);
        return webClient;
    }

    @Override
    public void setEnvironment(Environment environment) {
        Virtonomica.environment = environment;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public HtmlPage getConnection() {
        WebClient client = getWebClient("asd");
        HtmlPage registerPage = null;
        try {
            registerPage = client.getPage(Objects.requireNonNull(getSite()));
            LOGGER.info("Connection to virtonomica success");
        } catch (FailingHttpStatusCodeException e){
            LOGGER.error("FailingHttpStatusCodeException ", e, " Идет пересчет игровой ситуации");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("Connection error ", e);
        }
        return registerPage;
    }

    public HtmlPage signIn() {
        HtmlPage loginPage = getConnection();
        HtmlForm loginForm = loginPage.getForms().get(0);
        HtmlInput usernameInput = loginForm.getInputByName("userData[login]");
        HtmlInput passwordInput = loginForm.getInputByName("userData[password]");

        usernameInput.setValueAttribute(Objects.requireNonNull(getUser()));
        passwordInput.setValueAttribute(Objects.requireNonNull(getPassword()));

        /*request login*/
        HtmlButton button = (HtmlButton) loginForm.getByXPath("//button[@class='submit pull-right']").get(0);

        HtmlPage page = null;
        try {
            /*success login*/
            page = button.click();
            LOGGER.info("Login success");
        } catch (IOException e) {
            LOGGER.error("Login error ", e);
            e.printStackTrace();
        }
        return page;
    }

    private String getUser() {
        return environment.getProperty("user");
    }

    private String getPassword() {
        return environment.getProperty("password");
    }

    private String getSite() {
        return environment.getProperty("site");
    }
}


