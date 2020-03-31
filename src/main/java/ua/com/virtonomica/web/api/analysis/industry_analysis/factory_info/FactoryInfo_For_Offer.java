package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

import java.util.Objects;

public class FactoryInfo_For_Offer {
    private long unit_id;
    private long product_id;
    private long unit_offer_id;
    private long qty; // продукции на складе
    private double quality;
    private double prime_cost; // себестоимость на складе
    // если сс неопределено = 0, инакше = 1;
    private byte prime_cost_is_null;
    private long produced_quantity; //выпуск продукции
    private double produced_quality;
    private double produced_prime_cost; // себестоимость производимой продукции
    private double old_price;
    private double price;
    // 1-продажи всем
    // 2 - ище не известно!!!
    // 3 - только своей компании
    private byte offer_constraint;
    private long volume_of_orders; //обьем заказов
    private byte contract_qty;
    private long free;
    private String product_name;
    private String product_symbol;
    private double brandname_id;
    private String company_consumer_ids;
    private byte product_is_equipment;
    private long max_qty;
    private double quality_bonus;

    public FactoryInfo_For_Offer(long unit_id, long product_id, long unit_offer_id, long qty, double quality, double prime_cost, byte prime_cost_is_null, long produced_quantity, double produced_quality, double produced_prime_cost, double old_price, double price, byte offer_constraint, long volume_of_orders, byte contract_qty, long free, String product_name, String product_symbol, double brandname_id, String company_consumer_ids, byte product_is_equipment, long max_qty, double quality_bonus) {
        this.unit_id = unit_id;
        this.product_id = product_id;
        this.unit_offer_id = unit_offer_id;
        this.qty = qty;
        this.quality = quality;
        this.prime_cost = prime_cost;
        this.prime_cost_is_null = prime_cost_is_null;
        this.produced_quantity = produced_quantity;
        this.produced_quality = produced_quality;
        this.produced_prime_cost = produced_prime_cost;
        this.old_price = old_price;
        this.price = price;
        this.offer_constraint = offer_constraint;
        this.volume_of_orders = volume_of_orders;
        this.contract_qty = contract_qty;
        this.free = free;
        this.product_name = product_name;
        this.product_symbol = product_symbol;
        this.brandname_id = brandname_id;
        this.company_consumer_ids = company_consumer_ids;
        this.product_is_equipment = product_is_equipment;
        this.max_qty = max_qty;
        this.quality_bonus = quality_bonus;
    }

    public FactoryInfo_For_Offer() {
    }

    public long getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(long unit_id) {
        this.unit_id = unit_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getUnit_offer_id() {
        return unit_offer_id;
    }

    public void setUnit_offer_id(long unit_offer_id) {
        this.unit_offer_id = unit_offer_id;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getPrime_cost() {
        return prime_cost;
    }

    public void setPrime_cost(double prime_cost) {
        this.prime_cost = prime_cost;
    }

    public byte getPrime_cost_is_null() {
        return prime_cost_is_null;
    }

    public void setPrime_cost_is_null(byte prime_cost_is_null) {
        this.prime_cost_is_null = prime_cost_is_null;
    }

    public long getProduced_quantity() {
        return produced_quantity;
    }

    public void setProduced_quantity(long produced_quantity) {
        this.produced_quantity = produced_quantity;
    }

    public double getProduced_quality() {
        return produced_quality;
    }

    public void setProduced_quality(double produced_quality) {
        this.produced_quality = produced_quality;
    }

    public double getProduced_prime_cost() {
        return produced_prime_cost;
    }

    public void setProduced_prime_cost(double produced_prime_cost) {
        this.produced_prime_cost = produced_prime_cost;
    }

    public double getOld_price() {
        return old_price;
    }

    public void setOld_price(double old_price) {
        this.old_price = old_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte getOffer_constraint() {
        return offer_constraint;
    }

    public void setOffer_constraint(byte offer_constraint) {
        this.offer_constraint = offer_constraint;
    }

    public long getVolume_of_orders() {
        return volume_of_orders;
    }

    public void setVolume_of_orders(long volume_of_orders) {
        this.volume_of_orders = volume_of_orders;
    }

    public byte getContract_qty() {
        return contract_qty;
    }

    public void setContract_qty(byte contract_qty) {
        this.contract_qty = contract_qty;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_symbol() {
        return product_symbol;
    }

    public void setProduct_symbol(String product_symbol) {
        this.product_symbol = product_symbol;
    }

    public double getBrandname_id() {
        return brandname_id;
    }

    public void setBrandname_id(double brandname_id) {
        this.brandname_id = brandname_id;
    }

    public int getCompany_consumer_ids() {
        String s = company_consumer_ids.replaceAll("[^{}]", "");
        return Integer.valueOf(s);
    }

    public void setCompany_consumer_ids(String company_consumer_ids) {
        this.company_consumer_ids = company_consumer_ids;
    }

    public byte getProduct_is_equipment() {
        return product_is_equipment;
    }

    public void setProduct_is_equipment(byte product_is_equipment) {
        this.product_is_equipment = product_is_equipment;
    }

    public long getMax_qty() {
        return max_qty;
    }

    public void setMax_qty(long max_qty) {
        this.max_qty = max_qty;
    }

    public double getQuality_bonus() {
        return quality_bonus;
    }

    public void setQuality_bonus(double quality_bonus) {
        this.quality_bonus = quality_bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactoryInfo_For_Offer that = (FactoryInfo_For_Offer) o;
        return unit_id == that.unit_id &&
                product_id == that.product_id &&
                unit_offer_id == that.unit_offer_id &&
                qty == that.qty &&
                Double.compare(that.quality, quality) == 0 &&
                Double.compare(that.prime_cost, prime_cost) == 0 &&
                prime_cost_is_null == that.prime_cost_is_null &&
                produced_quantity == that.produced_quantity &&
                Double.compare(that.produced_quality, produced_quality) == 0 &&
                Double.compare(that.produced_prime_cost, produced_prime_cost) == 0 &&
                Double.compare(that.old_price, old_price) == 0 &&
                Double.compare(that.price, price) == 0 &&
                offer_constraint == that.offer_constraint &&
                volume_of_orders == that.volume_of_orders &&
                contract_qty == that.contract_qty &&
                free == that.free &&
                Double.compare(that.brandname_id, brandname_id) == 0 &&
                company_consumer_ids == that.company_consumer_ids &&
                product_is_equipment == that.product_is_equipment &&
                max_qty == that.max_qty &&
                Double.compare(that.quality_bonus, quality_bonus) == 0 &&
                product_name.equals(that.product_name) &&
                product_symbol.equals(that.product_symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit_id, product_id, unit_offer_id, qty, quality, prime_cost, prime_cost_is_null, produced_quantity, produced_quality, produced_prime_cost, old_price, price, offer_constraint, volume_of_orders, contract_qty, free, product_name, product_symbol, brandname_id, company_consumer_ids, product_is_equipment, max_qty, quality_bonus);
    }

    @Override
    public String toString() {
        return "FactoryInfo_For_Offer{" +
                "unit_id=" + unit_id +
                ", product_id=" + product_id +
                ", unit_offer_id=" + unit_offer_id +
                ", qty=" + qty +
                ", quality=" + quality +
                ", prime_cost=" + prime_cost +
                ", prime_cost_is_null=" + prime_cost_is_null +
                ", produced_quantity=" + produced_quantity +
                ", produced_quality=" + produced_quality +
                ", produced_prime_cost=" + produced_prime_cost +
                ", old_price=" + old_price +
                ", price=" + price +
                ", offer_constraint=" + offer_constraint +
                ", volume_of_orders=" + volume_of_orders +
                ", contract_qty=" + contract_qty +
                ", free=" + free +
                ", product_name='" + product_name + '\'' +
                ", product_symbol='" + product_symbol + '\'' +
                ", brandname_id=" + brandname_id +
                ", company_consumer_ids=" + company_consumer_ids +
                ", product_is_equipment=" + product_is_equipment +
                ", max_qty=" + max_qty +
                ", quality_bonus=" + quality_bonus +
                '}';
    }
}
