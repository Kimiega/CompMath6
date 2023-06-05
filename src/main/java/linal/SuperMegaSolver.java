package linal;

import graph.DrawerFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SuperMegaSolver {
    private static Thread drawThread = null;
    public static String solveAllProblems(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps) {
        if (drawThread != null)
            drawThread.stop();

        StringBuilder sb = new StringBuilder();

        sb.append("Initial params and true value of diffurchik\n");
        sb.append("Solving of: ").append(diffurchik.equationToString()).append("\n");
        sb.append("True solution is: ").append(diffurchik.trueSolutionToString()).append("\n");
        sb.append(String.format("y0 = %f\n", y0));
        sb.append(String.format("x0 = %f\n", x0));
        sb.append(String.format("xn = %f\n", xn));
        sb.append(String.format("h = %f\n", h));
        sb.append(String.format("eps = %f\n", eps));
        sb.append("x ");
        for (double i = x0; i <=xn; i += h) {
            sb.append(String.format("%11.6f ", i));
        }
        sb.append("\n");
        sb.append("y ");
        for (double i = x0; i <=xn; i += h) {
            sb.append(String.format("%11.6f ", diffurchik.trueSolution(x0, y0).apply(i)));
        }
        sb.append("\n\n");
        List<Solution> solutionList = new ArrayList<>();
        Double xmin = null;
        Double xmax = null;
        Double ymin = null;
        Double ymax = null;
        for (var method : Diffurchiki.diffurSolvers) {
            sb.append(method.methodName()).append("\n");
            Solution sol = method.solve(diffurchik, y0, x0, xn, h, eps);
            solutionList.add(sol);
            sb.append(String.format("n = %d\n", sol.getTable().length));
            for (var dot : sol.getTable()) {
                if (Double.isFinite(dot.x())) {
                    xmin = xmin == null ? dot.x() : Math.min(xmin, dot.x());
                    xmax = xmax == null ? dot.x() : Math.max(xmax, dot.x());
                }
                sb.append(String.format("%11.6f",dot.x())).append(" ");
            }
            sb.append("\n");
            for (var dot : sol.getTable()) {
                if (Double.isFinite(dot.y())) {
                    ymin = ymin == null ? dot.y() : Math.min(ymin, dot.y());
                    ymax = ymax == null ? dot.y() : Math.max(ymax, dot.y());
                }
                sb.append(String.format("%11.6f",dot.y())).append(" ");
            }
            sb.append("\n");
            sb.append(String.format("actual eps = %f\n\n", sol.getEps()));
        }
        if (Objects.equals(xmin, xmax)) {
            xmin-=5;
            xmax+=5;
        }
        if (Objects.equals(ymin, ymax)) {
            ymin -=5;
            ymax +=5;
        }
        Double finalXmin = xmin;
        Double finalXmax = xmax;
        Double finalYmin = ymin;
        Double finalYmax = ymax;
        drawThread = new Thread(() -> {
            DrawerFunctions drawer = new DrawerFunctions(finalXmin, finalXmax, finalYmin, finalYmax);
            drawer.drawFunction(new Equation() {
                @Override
                public Function<Double, Double> equation() {
                    return (Double x) -> diffurchik.trueSolution(x0, y0).apply(x);
                }

                @Override
                public String equationToString() {
                    return diffurchik.trueSolutionToString();
                }
            });
            for (var sol : solutionList) {
                drawer.drawFunction(sol);
            }
        });
        drawThread.start();
        return sb.toString();
    }
}
