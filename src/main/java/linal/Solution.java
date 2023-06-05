package linal;

public class Solution {
    private Dot[] table;
    private double eps;
    private String methodName;
    public Solution(Dot[] table, double eps, String methodName) {
        this.table = table;
        this.eps = eps;
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
}
