package linal;

public class FourthOrderRungeKuttaMethod extends OneStepMethod {
    private static final int p = 4;
    public FourthOrderRungeKuttaMethod() {
        super(p);
    }

    @Override
    public String methodName() {
        return "FourthOrderRungeKuttaMethod";
    }

    @Override
    protected Dot[] darkSolve(Diffurchik diffurchik, Dot[] sol, double h, int i) {
        while (i < sol.length - 1) {
            i++;
            double x0 = sol[i - 1].x();
            double y0 = sol[i - 1].y();
            double k1 = h * diffurchik.equation().apply(x0, y0);
            double k2 = h * diffurchik.equation().apply(x0 + h / 2, y0 + k1 / 2);
            double k3 = h * diffurchik.equation().apply(x0 + h / 2, y0 + k2 / 2);
            double k4 = h * diffurchik.equation().apply(x0 + h, y0 + k3);
            double x = x0 + h;
            double y = y0 + 1d / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
            sol[i] = new Dot(x, y);
        }
        return sol;
    }

}
