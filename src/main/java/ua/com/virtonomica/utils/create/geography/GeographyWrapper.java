package ua.com.virtonomica.utils.create.geography;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.com.virtonomica.deserializer.GeographyDeserializer;
import ua.com.virtonomica.entity.geography.City;
import ua.com.virtonomica.entity.geography.Country;
import ua.com.virtonomica.entity.geography.Region;
import ua.com.virtonomica.utils.create.IUnitWrapper;

@JsonDeserialize(using = GeographyDeserializer.class)
public class GeographyWrapper implements IUnitWrapper {

    private City city;
    private Country country;
    private Region region;

    public GeographyWrapper() {
    }

    public GeographyWrapper(long city_id, String city_name, long country_id, String country_name, long region_id, String region_name, double city_salary, double city_education) {
        this.city = new City(city_id, city_name, city_salary, city_education);
        this.country = new Country(country_id, country_name);
        this.region = new Region(region_id, region_name);
    }

    public GeographyWrapper(long city_id, String city_name, long country_id, String country_name, long region_id, String region_name) {
        this.city = new City(city_id, city_name);
        this.country = new Country(country_id, country_name);
        this.region = new Region(region_id, region_name);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "GeographyWrapper{" +
                "city=" + city.getName() +
                " country=" + country.getName() +
                " region=" + region.getName() +
                '}';
    }

}
