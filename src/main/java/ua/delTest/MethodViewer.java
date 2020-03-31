package ua.delTest;

public class MethodViewer {
    private IMethod iMethod;

    public MethodViewer(IMethod iMethod) {
        this.iMethod = iMethod;
    }

    public void renderMethod(){
        iMethod.getMethod();
        iMethod.getMethod2();
    }
}
