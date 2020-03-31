package ua.com.virtonomica.utils.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class DependencyDataFromQualification {
    private int qualVal;
    private int technology;
    private int maxAdvertisingFinances;
    private MaxPersonsInFieldOfActivity persons;

    public DependencyDataFromQualification(int qualVal, int technology, int maxAdvertisingFinances, MaxPersonsInFieldOfActivity persons) {
        this.qualVal = qualVal;
        this.technology = technology;
        this.maxAdvertisingFinances = maxAdvertisingFinances;
        this.persons = persons;
    }

    public DependencyDataFromQualification() {
    }

    public int getQualVal() {
        return qualVal;
    }

    public void setQualVal(int qualVal) {
        this.qualVal = qualVal;
    }

    public int getTechnology() {
        return technology;
    }

    public void setTechnology(int technology) {
        this.technology = technology;
    }

    public int getMaxAdvertisingFinances() {
        return maxAdvertisingFinances;
    }

    public void setMaxAdvertisingFinances(int maxAdvertisingFinances) {
        this.maxAdvertisingFinances = maxAdvertisingFinances;
    }

    public MaxPersonsInFieldOfActivity getPersons() {
        return persons;
    }

    public void setPersons(MaxPersonsInFieldOfActivity persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DependencyDataFromQualification)) return false;
        DependencyDataFromQualification that = (DependencyDataFromQualification) o;
        return qualVal == that.qualVal &&
                technology == that.technology &&
                maxAdvertisingFinances == that.maxAdvertisingFinances &&
                Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {

        return Objects.hash(qualVal, technology, maxAdvertisingFinances, persons);
    }

    @Override
    public String toString() {
        return "DependencyDataFromQualification{" +
                "qualVal=" + qualVal +
                ", technology=" + technology +
                ", maxAdvertisingFinances=" + maxAdvertisingFinances +
                ", persons=" + persons +
                '}';
    }
}
