package ua.com.virtonomica.web.api.analysis.industry_analysis;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DomNodeWorking {
    public List<DomNode> getNotEmptyChilds(Collection<DomNode> elements) {
        return elements.stream()
                .filter(e -> !e.asText().equals(""))
                .collect(Collectors.toList());
    }

    public DomNodeList<DomNode> tableNavigation(HtmlTable table) {
        return table.getBodies().get(0).getChildNodes();
    }

}
