package ua.com.virtonomica.web.api.analysis.industry_analysis.factory_info;

import java.util.*;

public class UnitSpecialization {
    private String specialization;
    private String qlt_modif;
    private String productId;
    private List<SpecializationMaterials>materials;

    UnitSpecialization(String specialization,String productId, String qlt_modif, List<SpecializationMaterials> materials) {
        this.specialization = specialization;
        this.qlt_modif = qlt_modif;
        this.productId=productId;
        this.materials = materials;
    }

    UnitSpecialization() {
    }

    public String getSpecialization() {
        return specialization;

    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<SpecializationMaterials> getMaterials() {
        return materials;
    }

    public void setMaterials(List<SpecializationMaterials> materials) {
        this.materials = materials;
    }

    public String getQlt_modif() {
        return qlt_modif;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQlt_modif(String qlt_modif) {
        this.qlt_modif = qlt_modif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitSpecialization)) return false;
        UnitSpecialization that = (UnitSpecialization) o;
        return Objects.equals(specialization, that.specialization) &&
                Objects.equals(qlt_modif, that.qlt_modif) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(materials, that.materials);
    }

    @Override
    public int hashCode() {

        return Objects.hash(specialization, qlt_modif, productId, materials);
    }

    @Override
    public String toString() {
        return "UnitSpecialization{" +
                "specialization='" + specialization + '\'' +
                ", qlt_modif='" + qlt_modif + '\'' +
                ", productId='" + productId + '\'' +
                ", materials=" + materials +
                '}';
    }
}
