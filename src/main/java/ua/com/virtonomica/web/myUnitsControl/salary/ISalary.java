package ua.com.virtonomica.web.myUnitsControl.salary;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public interface ISalary {

    HtmlTable fixSalary (HtmlAnchor upSalaryLink, HtmlPage officePage);
}
