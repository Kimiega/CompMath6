package linal;

import java.util.Arrays;
import java.util.Comparator;

public class ShulgaMethod implements DiffurSolver {
    private static final int p = 4;
    private final OneStepMethod oneStepMethod;

    public ShulgaMethod() {
        oneStepMethod = new FourthOrderRungeKuttaMethod();
    }

    @Override
    public String methodName() {
        return "ShulgaMethod";
    }

    @Override
    public Solution solve(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps) {
        if (h > (xn - x0)/3)
            h = (xn - x0)/3;
        Dot[] sol = new Dot[0];
        Dot[] tempSol = oneStepMethod.solve(diffurchik, y0, x0, xn, h, Double.POSITIVE_INFINITY).getTable();

        for (int i=4; i < tempSol.length; i++) {
            sol = darkSolve(diffurchik, tempSol, h, eps, 4);
        }

        return new Solution(sol, Arrays.stream(sol).map((Dot dot) ->
                Math.abs(diffurchik.trueSolution(x0, y0).apply(dot.x()) - dot.y())).max(Comparator.naturalOrder()).get(), methodName());

    }

    private Dot[] darkSolve(Diffurchik diffurchik, Dot[] sol, double h, double eps, int i) {
        if (i >= sol.length)
            return sol;
        eps = Double.POSITIVE_INFINITY;
        double x = sol[i-1].x() + h;
        double yPredict = sol[i-4].y() + 4 * h / 3 *
                (2 * diffurchik.equation().apply(sol[i-3].x(), sol[i-3].y())
                        - diffurchik.equation().apply(sol[i-2].x(), sol[i-2].y())
                        + 2 * diffurchik.equation().apply(sol[i-1].x(), sol[i-1].y()));
        double yCorr = sol[i-2].y() + h /3 *
                (diffurchik.equation().apply(sol[i-2].x(), sol[i-2].y())
                        + 4 * diffurchik.equation().apply(sol[i-1].x(), sol[i-1].y())
                        + diffurchik.equation().apply(x, yPredict));
        while (Math.abs(yCorr - yPredict) > eps) {
            yPredict = yCorr;
            yCorr = sol[i-2].y() + h /3 *
                    (diffurchik.equation().apply(sol[i-2].x(), sol[i-2].y())
                            + 4 * diffurchik.equation().apply(sol[i-1].x(), sol[i-1].y())
                            + diffurchik.equation().apply(x, yPredict));
        }
        sol[i] = new Dot(x, yPredict);
        return darkSolve(diffurchik, sol, h, eps, i + 1);
    }
}