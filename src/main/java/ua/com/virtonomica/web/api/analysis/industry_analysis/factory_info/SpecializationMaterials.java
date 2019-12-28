package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

import java.util.Objects;

public class SpecializationMaterials {
    private String material, count, quality;

    public SpecializationMaterials() {
    }

    public SpecializationMaterials(String material, String count, String quality) {
        this.material = material;
        this.count = count;
        this.quality = quality;
    }

    public String getMaterial() {
        return material;
    }

    public void setName(String material) {
        this.material = material;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecializationMaterials)) return false;
        SpecializationMaterials that = (SpecializationMaterials) o;
        return Objects.equals(material, that.material) &&
                Objects.equals(count, that.count) &&
                Objects.equals(quality, that.quality);
    }

    @Override
    public int hashCode() {

        return Objects.hash(material, count, quality);
    }

    @Override
    public String toString() {
        return "{" +
                "material='" + material + '\'' +
                ", count='" + count + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }
}
