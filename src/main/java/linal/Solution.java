package linal;

public class Solution {
    private Dot[] table;
    private double eps;
    private String methodName;
    private double h;
    public Solution(Dot[] table, double eps, double h, String methodName) {
        this.table = table;
        this.eps = eps;
        this.h = h;
        this.methodName = methodName;
    }

    public Dot[] getTable() {
        return table;
    }

    public double getEps() {
        return eps;
    }

    public String getMethodName() {
        return methodName;
    }
    public double getH() {
        return h;
    }
}
