package ua.com.virtonomica.web.api.analysis.industry_analysis.write_json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.html.*;
import ua.com.virtonomica.utils.reports.DependencyDataFromQualification;
import ua.com.virtonomica.utils.reports.MaxPersonsInFieldOfActivity;
import ua.com.virtonomica.web.api.HtmlPageData;
import ua.com.virtonomica.web.api.analysis.industry_analysis.DomNodeWorking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WriteDataIntoJson extends DomNodeWorking {
    private HtmlPageData htmlPageData = new HtmlPageData();

    public void write (String url, File file){
        List<DependencyDataFromQualification> list = getObjects(url);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file,list);
            System.out.println("Write json success into file '"+file+"'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<DependencyDataFromQualification> getObjects(String pageUrl){
        List<DependencyDataFromQualification>dataFromQualification = new ArrayList<>();

        HtmlPageData pageData = new HtmlPageData();
        HtmlPage htmlPage = pageData.getHtmlPage(pageUrl);
        HtmlTable table = (HtmlTable)htmlPage.getByXPath("//table[@class='grid']").get(1);

        DomNodeList<DomNode> nodes = tableNavigation(table);
        List<DomNode> notEmptyChilds = getNotEmptyChilds(nodes);

        List<DomNode> collect = notEmptyChilds.stream()
                .limit(notEmptyChilds.size() - 2)
                .collect(Collectors.toList());

        for (DomNode node : collect) {
            DependencyDataFromQualification data = new DependencyDataFromQualification();
            MaxPersonsInFieldOfActivity persons = new MaxPersonsInFieldOfActivity();
            DomNodeList<DomNode> childNodes = node.getChildNodes();
            List<DomNode> domNodes = getNotEmptyChilds(childNodes);

            data.setQualVal(toInt(domNodes.get(0).asText()));
            data.setTechnology(toInt(domNodes.get(2).asText()));


            persons.setManagement(toInt(domNodes.get(3).asText()));
            persons.setFactory(toInt(domNodes.get(4).asText()));
            persons.setEnergy(toInt(domNodes.get(5).asText()));
            persons.setMining(toInt(domNodes.get(6).asText()));
            persons.setTrade(toInt(domNodes.get(7).asText()));
            persons.setScience(toInt(domNodes.get(7).asText()));
            persons.setRestaurant(toInt(domNodes.get(7).asText()));
            persons.setFishingSector(toInt(domNodes.get(8).asText()));
            persons.setMedical(toInt(domNodes.get(8).asText()));
            persons.setServiceSector(toInt(domNodes.get(9).asText()));
            persons.setAnimalsSector(toInt(domNodes.get(10).asText()));
            persons.setAgrarianSector(toInt(domNodes.get(11).asText()));
            persons.setGasStation(toInt(domNodes.get(12).asText()));
            persons.setAutoRepair(toInt(domNodes.get(12).asText()));

            data.setPersons(persons);
            data.setMaxAdvertisingFinances(toInt(domNodes.get(13).asText()));

            dataFromQualification.add(data);
        }

        dataFromQualification.forEach(System.out::println);

        return dataFromQualification;
    }

    private String getCalculateDataByKvala(String url){
        return htmlPageData.getContent(url);
    }

    private int toInt(String s){
        String str = s.replaceAll(" ", "");
        return Integer.parseInt(str);
    }

}
