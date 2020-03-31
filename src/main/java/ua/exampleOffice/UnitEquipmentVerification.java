package ua.exampleOffice;

public class UnitEquipmentVerification implements IUnitEquipmentVerification {
    @Override
    public void buyFull() {
        System.out.println("buy full");
    }

    @Override
    public void replace() {
        System.out.println("replace");
    }

    @Override
    public void writeOff() {
        System.out.println("write off");
    }
}
