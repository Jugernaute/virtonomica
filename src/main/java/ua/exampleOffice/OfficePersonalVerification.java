package ua.exampleOffice;

public class OfficePersonalVerification implements IUnitPersonalVerification {
    @Override
    public void fixSalary() {
        System.out.println("office fix salary");
    }

    @Override
    public void learningEmployees() {
        System.out.println("learning employees");
    }
}
