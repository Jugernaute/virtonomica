package ua.com.virtonomica.controllers;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.PropertySource;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;import org.telegram.telegrambots.ApiContextInitializer;import org.telegram.telegrambots.TelegramBotsApi;import org.telegram.telegrambots.exceptions.TelegramApiRequestException;import ua.com.virtonomica.Bot;import ua.com.virtonomica.web.api.VirtonomicaAPI;import ua.com.virtonomica.web.factory_analytics.IndustryUnits;@Controller@PropertySource("classpath:virtonomicaConfig.properties")public class MainController {    private final Logger logger = LoggerFactory.getLogger(this.getClass());    @Autowired    VirtonomicaAPI virtonomicaAPI;    @Autowired    IndustryUnits industryUnits;    @GetMapping("/")    private String Start (){        ApiContextInitializer.init();        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();        try {            industryUnits.getUnits();            telegramBotsApi.registerBot(new Bot());            logger.info("Start Bot");        } catch (TelegramApiRequestException | NullPointerException e) {            e.printStackTrace();        }        return "index";    }}