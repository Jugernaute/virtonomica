package ua.exampleOffice;

public class PlantPersonalVerification implements IUnitPersonalVerification{

    @Override
    public void fixSalary() {
        System.out.println("Plant fix salary");
    }

    @Override
    public void learningEmployees() {
        System.out.println("learning employees");
    }
}
