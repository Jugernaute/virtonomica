package ua.delTest;

public class MethodManager implements IMethod {

    public void test(){
        System.out.println("test");
    }
    @Override
    public void getMethod() {
        System.out.println("get first method");
    }

    @Override
    public void getMethod2() {
        System.out.println("not override method");
    }
}
