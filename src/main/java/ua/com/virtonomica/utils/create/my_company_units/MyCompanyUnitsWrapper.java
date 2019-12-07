package ua.com.virtonomica.utils.create.my_company_units;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.com.virtonomica.entity.company.CompanyProducts;
import ua.com.virtonomica.entity.geography.City;
import ua.com.virtonomica.entity.geography.Country;
import ua.com.virtonomica.entity.geography.Region;
import ua.com.virtonomica.entity.industrial.UnitClass;
import ua.com.virtonomica.entity.industrial.UnitType;
import ua.com.virtonomica.utils.create.IUnitWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyCompanyUnitsWrapper implements IUnitWrapper {

    private long id;
    private String name;
    private long unit_type_id;
    private String city_name;
    private long city_id;
    private String unit_class_name;
    private String country_name;
    private String region_name;
    private long region_id;
    private long unit_class_id;
    private String unit_type_name;
    private long country_id;
//    private String unit_type_produce_name;
    private List<CompanyProducts> products;

    public City getCity(){
        return new City(city_id,city_name);
    }

    public Country getCountry(){
        return new Country(country_id,country_name);
    }
     public UnitClass getUnitClass(){
        return new UnitClass(unit_class_id,unit_class_name);
     }

     public UnitType getUnitType(){
        return new UnitType(unit_type_id,unit_type_name);
     }

     public Region getRegion(){
        return new Region(region_id,region_name);
     }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CompanyProducts>getProducts(){
        return products;
     }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit_type_id(long unit_type_id) {
        this.unit_type_id = unit_type_id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setUnit_class_name(String unit_class_name) {
        this.unit_class_name = unit_class_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setRegion_id(long region_id) {
        this.region_id = region_id;
    }

    public void setUnit_class_id(long unit_class_id) {
        this.unit_class_id = unit_class_id;
    }

    public void setUnit_type_name(String unit_type_name) {
        this.unit_type_name = unit_type_name;
    }

    public void setCountry_id(long country_id) {
        this.country_id = country_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

//    public void setUnit_type_produce_name(String unit_type_produce_name) {
//        this.unit_type_produce_name = unit_type_produce_name;
//    }

    public void setProducts(List<CompanyProducts> products) {
        this.products = products;
    }
}
