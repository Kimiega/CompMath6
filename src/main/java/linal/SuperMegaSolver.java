package linal;

import graph.DrawerFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SuperMegaSolver {

//    public static String solveAllProblems(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps) {
//        int n = dotsRaw.length;
//        Dot[] dots = new Dot[n];
//        for (int i = 0; i < n; ++i) {
//            dots[i] = new Dot(dotsRaw[i][0], dotsRaw[i][1]);
//        }
//        return solveAllProblems(dots);
//    }

    public static String solveAllProblems(Diffurchik diffurchik, double y0, double x0, double xn, double h, double eps) {
        StringBuilder sb = new StringBuilder();
//        double xmin = dots[0].x();
//        double xmax = dots[0].x();
//        double ymin = dots[0].y();
//        double ymax = dots[0].y();
//        for (var dot : dots) {
//            if (xmin > dot.x())
//                xmin = dot.x();
//            if (xmax < dot.x())
//                xmax = dot.x();
//            if (ymin > dot.y())
//                ymin = dot.y();
//            if (ymax < dot.y())
//                ymax = dot.y();
//        }
//        if (xmin == xmax) {
//            xmin-=5;
//            xmax+=5;
//        }
//        if (ymin == ymax) {
//            ymin -=5;
//            ymax +=5;
//        }
        sb.append("Initial params and true value of diffurchik\n");
        sb.append("Solving of: ").append(diffurchik.equationToString()).append("\n");
        sb.append(String.format("h = %f\n", h));
        sb.append("x0 xn\n");
        sb.append(x0).append(" ").append(xn).append("\n");
        sb.append(String.format("y0 = %f\n", y0));
        sb.append(String.format("eps = %f\n", eps));
        sb.append("x ");
        for (double i = x0; i <=xn; i += h) {
            sb.append(String.format("%8.4f ", i));
        }
        sb.append("\n");
        sb.append("y ");
        for (double i = x0; i <=xn; i += h) {
            sb.append(String.format("%8.4f ", diffurchik.trueSolution(x0, y0).apply(i)));
        }
        sb.append("\n");
        List<Solution> solutionList = new ArrayList<>();
        Double xmin = null;
        Double xmax = null;
        Double ymin = null;
        Double ymax = null;
        for (var method : Diffurchiki.diffurSolvers) {
            sb.append(method.methodName()).append("\n");
            Solution sol = method.solve(diffurchik, y0, x0, xn, h, eps);
            solutionList.add(sol);
            for (var dot : sol.getTable()) {
                if (Double.isFinite(dot.x())) {
                    xmin = xmin == null ? dot.x() : Math.min(xmin, dot.x());
                    xmax = xmax == null ? dot.x() : Math.max(xmax, dot.x());
                }
                sb.append(String.format("%8.4f",dot.x())).append(" ");
            }
            sb.append("\n");
            for (var dot : sol.getTable()) {
                if (Double.isFinite(dot.y())) {
                    ymin = ymin == null ? dot.y() : Math.min(ymin, dot.y());
                    ymax = ymax == null ? dot.y() : Math.max(ymax, dot.y());
                }
                sb.append(String.format("%8.4f",dot.y())).append(" ");
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
        DrawerFunctions drawer = new DrawerFunctions(xmin, xmax, ymin, ymax);
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
        return sb.toString();
    }
}
