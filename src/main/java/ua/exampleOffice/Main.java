package ua.exampleOffice;

public class Main {
    public static void main(String[] args) {
        OfficeVerification officeVerification = new OfficeVerification(new OfficePersonalVerification(), new UnitEquipmentVerification());
        officeVerification.equipmentBuyFull();
        officeVerification.equipmentReplace();
        officeVerification.equipmentWriteOff();
        officeVerification.learningEmployees();
        officeVerification.salaryFix();

        PlantVerification plantVerification = new PlantVerification(new PlantPersonalVerification(), new UnitEquipmentVerification());
        plantVerification.equipmentBuyFull();
        plantVerification.fixSalary();
    }
}
