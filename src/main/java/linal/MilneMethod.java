package linal;

import java.util.Arrays;
import java.util.Comparator;

public class MilneMethod implements DiffurSolver {
    private static final int p = 4;
    private final OneStepMethod oneStepMethod;

    public MilneMethod() {
        oneStepMethod = new FourthOrderRungeKuttaMethod();
    }

    @Override
    public String methodName() {
        return "MilneMethod";
    }

    @Override
    public Solution solve(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps) {
        if (h > (xn - x0)/3)
            h = (xn - x0)/3;
        Dot[] sol;
       // do {
            double curEps = Double.POSITIVE_INFINITY;
            Solution oneStepMethodSol = oneStepMethod.solve(diffurchik, y0, x0, xn, h * 2, curEps);
            Dot[] tempDots = oneStepMethodSol.getTable();
            h = oneStepMethodSol.getH();
            sol = darkSolve(diffurchik, tempDots, h, eps, 4);
            h /= 2;
//        } while (Arrays.stream(sol).map((Dot dot) ->
//                Math.abs(diffurchik.trueSolution(x0, y0).apply(dot.x()) - dot.y())).filter(Double::isFinite).max(Comparator.naturalOrder()).get() > eps);
        return new Solution(sol, Arrays.stream(sol).map((Dot dot) ->
                Math.abs(diffurchik.trueSolution(x0, y0).apply(dot.x()) - dot.y())).filter(Double::isFinite).max(Comparator.naturalOrder()).get(), h, methodName());

    }

    private Dot[] darkSolve(Diffurchik diffurchik, Dot[] sol, double h, double eps, int i) {
        if (i >= sol.length)
            return sol;
        double x = sol[i-1].x() + h;
        double yPredict = sol[i-4].y() + 4 * h / 3 *
                (2 * diffurchik.equation().apply(sol[i-3].x(), sol[i-3].y())
                - diffurchik.equation().apply(sol[i-2].x(), sol[i-2].y())
                + 2 * diffurchik.equation().apply(sol[i-1].x(), sol[i-1].y()));
        double prev = yPredict;
        double yCorr;
        do {
            yPredict = prev;
            yCorr = sol[i-2].y() + h /3 *
                    (diffurchik.equation().apply(sol[i-2].x(), sol[i-2].y())
                    + 4 * diffurchik.equation().apply(sol[i-1].x(), sol[i-1].y())
                    + diffurchik.equation().apply(x, yPredict));
            prev = yCorr;
        } while(Math.abs(yCorr - yPredict) > eps);
        sol[i] = new Dot(x, yCorr);
        return darkSolve(diffurchik, sol, h, eps, i + 1);
    }
}
