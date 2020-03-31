package ua.com.virtonomica.utils.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyReportFinance {

    private final static int companyId = 3247145;
    private String item_name;
    private String category_name;
    private String value;
    private String prev_turn_value;
    private String month_value;
    private String year_value;

    public static int getCompanyId() {
        return companyId;
    }

    public String getItem_name() {

        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getValue() {
        return new DecimalFormat("###,###.##").format(BigDecimal.valueOf(Double.valueOf(value)));
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrev_turn_value() {
        return new DecimalFormat("###,###.##").format(BigDecimal.valueOf(Double.valueOf(prev_turn_value)));
    }

    public void setPrev_turn_value(String prev_turn_value) {
        this.prev_turn_value = prev_turn_value;
    }

    public String getMonth_value() {

        return new DecimalFormat("###,###.##").format(BigDecimal.valueOf(Double.valueOf(month_value)));
    }

    public void setMonth_value(String month_value) {
        this.month_value = month_value;
    }

    public String getYear_value() {

        return new DecimalFormat("###,###.##").format(BigDecimal.valueOf(Double.valueOf(year_value)));
    }

    public void setYear_value(String year_value) {
        this.year_value = year_value;
    }

    @Override
    public String toString() {
        return "CompanyReportFinance{" +
                ", item_name='" + item_name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", value=" + value +
                ", prev_turn_value=" + prev_turn_value +
                ", month_value=" + month_value +
                ", year_value=" + year_value +
                '}';
    }
}
