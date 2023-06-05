package linal;

public abstract class OneStepMethod implements DiffurSolver {
    private static final int MAX_N = 1024;
    private int p;

    protected OneStepMethod(int p) {
        this.p = p;
    }
    public Solution solve(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps) {
        int n = (int)Math.floor((xn - x0)/h)+1;
        if (n > MAX_N) {
            h = (xn-x0)/MAX_N;
            n = (int)Math.floor((xn - x0)/h)+1;
        }

        Dot[] tempSol = new Dot[n];
        tempSol[0] = new Dot(x0, y0);
        Dot[] sol0;
        Dot[] sol1 = darkSolve(diffurchik, tempSol, h, 0);

        do {
            sol0 = sol1;
            h /= 2;
            n = (int)Math.floor((xn - x0)/h)+1;
            tempSol = new Dot[n];
            tempSol[0] = new Dot(x0, y0);
            sol1 = darkSolve(diffurchik, tempSol, h, 0);
        } while (Math.abs((sol1[sol1.length-1].y()-sol0[sol0.length-1].y())/(Math.pow(2, p)-1)) > eps && n < MAX_N);

        return new Solution(sol1, Math.abs((sol1[sol1.length-1].y()-sol0[sol0.length-1].y())/(Math.pow(2, p)-1)), h, methodName());
    }

    protected abstract Dot[] darkSolve(Diffurchik diffurchik, Dot[] sol, double h, int i);

}
