package ua.exampleOffice;

public class OfficeVerification {
    private IUnitPersonalVerification iUnitPersonalVerification;
    private IUnitEquipmentVerification iUnitEquipmentVerification;

    public OfficeVerification(IUnitPersonalVerification iUnitPersonalVerification,
                              IUnitEquipmentVerification iUnitEquipmentVerification) {
        this.iUnitPersonalVerification = iUnitPersonalVerification;
        this.iUnitEquipmentVerification = iUnitEquipmentVerification;
    }

    public void salaryFix (){
        iUnitPersonalVerification.fixSalary();
    }

    public void learningEmployees(){
        iUnitPersonalVerification.learningEmployees();
    }

    public void equipmentReplace (){
        iUnitEquipmentVerification.replace();
    }

    public void equipmentBuyFull(){

        iUnitEquipmentVerification.buyFull();
    }

    public void equipmentWriteOff(){
        iUnitEquipmentVerification.writeOff();
    }
}
