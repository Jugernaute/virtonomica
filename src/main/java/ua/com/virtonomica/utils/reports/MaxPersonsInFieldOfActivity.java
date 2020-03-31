package ua.com.virtonomica.utils.reports;

import java.util.Objects;

//область деятельности
public class MaxPersonsInFieldOfActivity {
    private int Management;
    private int Factory;
    private int Energy;
    private int Mining;
    private int Trade;
    private int Science;
    private int Restaurant;
    private int FishingSector;
    private int Medical;
    private int ServiceSector;
    private int AnimalsSector;
    private int AgrarianSector;
    private int GasStation;
    private int AutoRepair;

    public MaxPersonsInFieldOfActivity() {
    }

    public MaxPersonsInFieldOfActivity(int management, int factory, int energy, int mining, int trade, int science, int restaurant, int fishingSector, int medical, int serviceSector, int animalsSector, int agrarianSector, int gasStation, int autoRepair) {

        Management = management;
        Factory = factory;
        Energy = energy;
        Mining = mining;
        Trade = trade;
        Science = science;
        Restaurant = restaurant;
        FishingSector = fishingSector;
        Medical = medical;
        ServiceSector = serviceSector;
        AnimalsSector = animalsSector;
        AgrarianSector = agrarianSector;
        GasStation = gasStation;
        AutoRepair = autoRepair;
    }

    public int getManagement() {
        return Management;
    }

    public void setManagement(int management) {
        Management = management;
    }

    public int getFactory() {
        return Factory;
    }

    public void setFactory(int factory) {
        Factory = factory;
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }

    public int getMining() {
        return Mining;
    }

    public void setMining(int mining) {
        Mining = mining;
    }

    public int getTrade() {
        return Trade;
    }

    public void setTrade(int trade) {
        Trade = trade;
    }

    public int getScience() {
        return Science;
    }

    public void setScience(int science) {
        Science = science;
    }

    public int getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(int restaurant) {
        Restaurant = restaurant;
    }

    public int getFishingSector() {
        return FishingSector;
    }

    public void setFishingSector(int fishingSector) {
        FishingSector = fishingSector;
    }

    public int getMedical() {
        return Medical;
    }

    public void setMedical(int medical) {
        Medical = medical;
    }

    public int getServiceSector() {
        return ServiceSector;
    }

    public void setServiceSector(int serviceSector) {
        ServiceSector = serviceSector;
    }

    public int getAnimalsSector() {
        return AnimalsSector;
    }

    public void setAnimalsSector(int animalsSector) {
        AnimalsSector = animalsSector;
    }

    public int getAgrarianSector() {
        return AgrarianSector;
    }

    public void setAgrarianSector(int agrarianSector) {
        AgrarianSector = agrarianSector;
    }

    public int getGasStation() {
        return GasStation;
    }

    public void setGasStation(int gasStation) {
        GasStation = gasStation;
    }

    public int getAutoRepair() {
        return AutoRepair;
    }

    public void setAutoRepair(int autoRepair) {
        AutoRepair = autoRepair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaxPersonsInFieldOfActivity)) return false;
        MaxPersonsInFieldOfActivity that = (MaxPersonsInFieldOfActivity) o;
        return Management == that.Management &&
                Factory == that.Factory &&
                Energy == that.Energy &&
                Mining == that.Mining &&
                Trade == that.Trade &&
                Science == that.Science &&
                Restaurant == that.Restaurant &&
                FishingSector == that.FishingSector &&
                Medical == that.Medical &&
                ServiceSector == that.ServiceSector &&
                AnimalsSector == that.AnimalsSector &&
                AgrarianSector == that.AgrarianSector &&
                GasStation == that.GasStation &&
                AutoRepair == that.AutoRepair;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Management, Factory, Energy, Mining, Trade, Science, Restaurant, FishingSector, Medical, ServiceSector, AnimalsSector, AgrarianSector, GasStation, AutoRepair);
    }

    @Override
    public String toString() {
        return "MaxPersonsInFieldOfActivity{" +
                "Management=" + Management +
                ", Factory=" + Factory +
                ", Energy=" + Energy +
                ", Mining=" + Mining +
                ", Trade=" + Trade +
                ", Science=" + Science +
                ", Restaurant=" + Restaurant +
                ", FishingSector=" + FishingSector +
                ", Medical=" + Medical +
                ", ServiceSector=" + ServiceSector +
                ", AnimalsSector=" + AnimalsSector +
                ", AgrarianSector=" + AgrarianSector +
                ", GasStation=" + GasStation +
                ", AutoRepair=" + AutoRepair +
                '}';
    }
}
