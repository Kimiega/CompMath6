package linal;

public class ImprovedEulerMethod extends OneStepMethod {
    private static final int p = 2;
    public ImprovedEulerMethod() {
        super(p);
    }

    @Override
    public String methodName() {
        return "ImprovedEulerMethod";
    }

        @Override
    protected Dot[] darkSolve(Diffurchik diffurchik, Dot[] sol, double h, int i) {
        i++;
        if (i >= sol.length)
            return sol;
        double x0 = sol[i-1].x();
        double y0 = sol[i-1].y();
        double x = x0+h;
        double y = y0 + h/2 *
                (diffurchik.equation().apply(x0, y0) + diffurchik.equation().apply(x, y0 + h * diffurchik.equation().apply(x0, y0)));
        sol[i] = new Dot(x, y);
        return darkSolve(diffurchik, sol, h, i);
    }

}
