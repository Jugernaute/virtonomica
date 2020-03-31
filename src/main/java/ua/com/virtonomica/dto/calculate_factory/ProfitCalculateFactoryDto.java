package ua.com.virtonomica.dto.calculate_factory;

import org.jetbrains.annotations.NotNull;
import ua.com.virtonomica.utils.reports.MarketingReport;
import ua.com.virtonomica.web.api.analysis.industry_analysis.calculateFactoryByIngradients.ResultDataAfterCalculateFactory;

import java.util.Objects;

public class ProfitCalculateFactoryDto implements Comparable<ProfitCalculateFactoryDto> {
    private long id;
    private String name;
//    private RetailProduct retailProduct;
    private ResultDataAfterCalculateFactory resultDataAfterCalculateFactory;
//    private List<IngredientsOfSpecialization> ingredients;
    private MarketingReport marketingReport;
    private long profit;

    public ProfitCalculateFactoryDto(/*RetailProduct retailProduct, */long id, String name, ResultDataAfterCalculateFactory resultDataAfterCalculateFactory, MarketingReport marketingReport, long profit) {
        this.id = id;
        this.name = name;
//        this.retailProduct = retailProduct;
        this.resultDataAfterCalculateFactory = resultDataAfterCalculateFactory;
        this.marketingReport = marketingReport;
        this.profit = profit;
    }

    public ProfitCalculateFactoryDto() {
    }

//    public RetailProduct getRetailProduct() {
//        return retailProduct;
//    }
//
//    public void setRetailProduct(RetailProduct retailProduct) {
//        this.retailProduct = retailProduct;
//    }

    public MarketingReport getMarketingReport() {
        return marketingReport;
    }

    public void setMarketingReport(MarketingReport marketingReport) {
        this.marketingReport = marketingReport;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitCalculateFactoryDto dto = (ProfitCalculateFactoryDto) o;
        return profit == dto.profit &&
//                Objects.equals(retailProduct, dto.retailProduct) &&
                Objects.equals(resultDataAfterCalculateFactory, dto.resultDataAfterCalculateFactory) &&
                Objects.equals(marketingReport, dto.marketingReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*retailProduct,*/ resultDataAfterCalculateFactory, marketingReport, profit);
    }

    @Override
    public String toString() {
        return "ProfitCalculateFactoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resultDataAfterCalculateFactory=" + resultDataAfterCalculateFactory +
                ", marketingReport=" + marketingReport +
                ", profit=" + profit +
                '}';
    }

    public ResultDataAfterCalculateFactory getResultDataAfterCalculateFactory() {
        return resultDataAfterCalculateFactory;
    }

    public void setResultDataAfterCalculateFactory(ResultDataAfterCalculateFactory resultDataAfterCalculateFactory) {
        this.resultDataAfterCalculateFactory = resultDataAfterCalculateFactory;
    }

    @Override
    public int compareTo(@NotNull ProfitCalculateFactoryDto profitCalculateFactoryDto) {
        if (this.getProfit() == profitCalculateFactoryDto.getProfit()) {
            return 0;
        } else if (this.getProfit() < profitCalculateFactoryDto.getProfit()) {
            return -1;
        } else {
            return 1;
        }
    }
}
