package ua.com.virtonomica.utils.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitsSupplyContracts {
    private long shop_goods_category_id;
    private String shop_goods_category_name;
    private double shop_supplier_brand_city_value;
    private long offer_id;
    private String product_name;
    private String product_symbol;
    private long supplier_id; //unit supply
    private long supplier_company_id;
    private long supplier_user_id;
    private String supplier_name;
    private double quality;
    private long product_id;
    private long free_for_buy;
    private long party_quantity;
    private long party_quantity_available;
    private double offer_price;
    private double offer_new_price;
    private double offer_transport_cost;
    private double offer_tax_cost; //таможня
    private long quantity_at_supplier_storage;
    private long dispatch_quantity; //доставлено к-сть
    //            'price_constraint' => '0',
    //            'order' => '22',
    //            'dispatch_quality' => '16.2591978747211',
    //            'quality_constraint_min' => '0',
    //            'price_constraint_max' => '0',
    //            'consumer_id' => '5306807',
    //            'created' => '2019-09-19 21:31:58.665912',
    //            'offer_constraint' => '3',
    //            'brandname_id' => NULL,
    //            'supplier_company_name' => 'EXPOCENTRE',
    //            'supplier_is_seaport' => '0',
    //            'offer_max_qty' => NULL,
    //            'contract_duration' => NULL,
//            'offer_tax_cost_new' => '4.9',

    public long getShop_goods_category_id() {
        return shop_goods_category_id;
    }

    public String getShop_goods_category_name() {
        return shop_goods_category_name;
    }

    public double getShop_supplier_brand_city_value() {
        return shop_supplier_brand_city_value;
    }

    public long getOffer_id() {
        return offer_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_symbol() {
        return product_symbol;
    }

    public long getSupplier_id() {
        return supplier_id;
    }

    public long getSupplier_company_id() {
        return supplier_company_id;
    }

    public long getSupplier_user_id() {
        return supplier_user_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public double getQuality() {
        return quality;
    }

    public long getProduct_id() {
        return product_id;
    }

    public long getFree_for_buy() {
        return free_for_buy;
    }

    public long getParty_quantity() {
        return party_quantity;
    }

    public long getParty_quantity_available() {
        return party_quantity_available;
    }

    public double getOffer_price() {
        return offer_price;
    }

    public double getOffer_new_price() {
        return offer_new_price;
    }

    public double getOffer_transport_cost() {
        return offer_transport_cost;
    }

    public double getOffer_tax_cost() {
        return offer_tax_cost;
    }

    public long getQuantity_at_supplier_storage() {
        return quantity_at_supplier_storage;
    }

    public long getDispatch_quantity() {
        return dispatch_quantity;
    }

    @Override
    public String toString() {
        return "UnitsSupplyContracts{" +
                "shop_goods_category_id=" + shop_goods_category_id +
                ", shop_goods_category_name='" + shop_goods_category_name + '\'' +
                ", shop_supplier_brand_city_value=" + shop_supplier_brand_city_value +
                ", offer_id=" + offer_id +
                ", product_name='" + product_name + '\'' +
                ", product_symbol='" + product_symbol + '\'' +
                ", supplier_id=" + supplier_id +
                ", supplier_company_id=" + supplier_company_id +
                ", supplier_user_id=" + supplier_user_id +
                ", supplier_name='" + supplier_name + '\'' +
                ", quality=" + quality +
                ", product_id=" + product_id +
                ", free_for_buy=" + free_for_buy +
                ", party_quantity=" + party_quantity +
                ", party_quantity_available=" + party_quantity_available +
                ", offer_price=" + offer_price +
                ", offer_new_price=" + offer_new_price +
                ", offer_transport_cost=" + offer_transport_cost +
                ", offer_tax_cost=" + offer_tax_cost +
                ", quantity_at_supplier_storage=" + quantity_at_supplier_storage +
                ", dispatch_quantity=" + dispatch_quantity +
                '}';
    }
}
