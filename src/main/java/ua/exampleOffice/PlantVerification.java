package ua.exampleOffice;

public class PlantVerification {
    private IUnitPersonalVerification iUnitPersonalVerification;
    private IUnitEquipmentVerification iUnitEquipmentVerification;

    public PlantVerification(IUnitPersonalVerification iUnitPersonalVerification, IUnitEquipmentVerification iUnitEquipmentVerification) {
        this.iUnitPersonalVerification = iUnitPersonalVerification;
        this.iUnitEquipmentVerification = iUnitEquipmentVerification;
    }

    void fixSalary(){
        iUnitPersonalVerification.fixSalary();
    }

    void equipmentBuyFull(){
        iUnitEquipmentVerification.buyFull();
    }
}
