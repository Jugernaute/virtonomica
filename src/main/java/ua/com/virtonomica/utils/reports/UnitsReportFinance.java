package ua.com.virtonomica.utils.reports;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitsReportFinance implements Comparable<UnitsReportFinance> {
    private long id;
    private long unit_type_id;
    private String name;
    private String country_name;
    private String region_name;
    private String city_name;
    @JsonProperty(value = "cashflow")
    private String cashFlow;

    public long getId() {
        return id;
    }

    public long getUnit_type_id() {
        return unit_type_id;
    }

    public String getName() {
        return name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public String getCity_name() {
        return city_name;
    }

//    public String setCashFlow(String cashFlow){
//        return cashFlow;
//    }

    public String getCashflow() {
//        if (cashFlow == null)cashFlow="0";
       return new DecimalFormat("###,###.##").format(BigDecimal.valueOf(Double.parseDouble(cashFlow)));
    }

    @Override
    public int compareTo(UnitsReportFinance o) {
        BigDecimal decimal_1 = BigDecimal.valueOf(Double.parseDouble(this.cashFlow));
        BigDecimal decimal_2 = BigDecimal.valueOf(Double.parseDouble(o.cashFlow));
        return decimal_2.compareTo(decimal_1);
    }

    @Override
    public String toString() {
        return "UnitsReportFinance{" +
                "id=" + id +
                ", unit_type_id=" + unit_type_id +
                ", name='" + name + '\'' +
                ", country_name='" + country_name + '\'' +
                ", region_name='" + region_name + '\'' +
                ", city_name='" + city_name + '\'' +
                ", cashflow='" + cashFlow + '\'' +
                '}';
    }
}
