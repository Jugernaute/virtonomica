package ua.delTest;

public class MethodManager2/* extends MethodManager*/ implements IMethod {
    @Override
    public void getMethod() {
        System.out.println("second method");
    }

    @Override
    public void getMethod2() {
        System.out.println("override method");
    }
}
