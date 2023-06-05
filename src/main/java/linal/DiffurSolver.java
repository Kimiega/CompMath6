package linal;

public interface DiffurSolver {
    String methodName();
    Solution solve(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps);

}
