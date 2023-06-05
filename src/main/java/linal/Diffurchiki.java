package linal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Diffurchiki {
    private static final Diffurchik diffurchik1 = new Diffurchik() {
        @Override
        public BiFunction<Double, Double, Double> equation() {
            return (Double x, Double y) -> y + (1 + x) * y * y;
        }

        @Override
        public Function<Double, Double> trueSolution(Double x0, Double y0) {
            final var c = -Math.exp(x0) / y0 - x0 * Math.exp(x0);
            return (Double x) -> -Math.exp(x) / (x * Math.exp(x) + c);
        }

        @Override
        public String equationToString() {
            return "y' = y + (1 + x) y^2";
        }

        @Override
        public String trueSolutionToString() {
            return "y = - exp(x) / (x * exp(x) + c)";
        }
    };

    private static final Diffurchik diffurchik2 = new Diffurchik() {
        @Override
        public BiFunction<Double, Double, Double> equation() {
            return (Double x, Double y) -> y + x;
        }

        @Override
        public Function<Double, Double> trueSolution(Double x0, Double y0) {
            final var c = (y0 + x0 + 1) / Math.exp(x0);
            return (Double x) ->  c * Math.exp(x) - x - 1;
        }

        @Override
        public String equationToString() {
            return "y' = y + x";
        }

        @Override
        public String trueSolutionToString() {
            return "y =  c * exp(x) - x - 1";
        }
    };

    private static final Diffurchik diffurchik3 = new Diffurchik() {
        @Override
        public BiFunction<Double, Double, Double> equation() {
            return (Double x, Double y) -> Math.cos(x) * y;
        }

        @Override
        public Function<Double, Double> trueSolution(Double x0, Double y0) {
            final var c = y0 / Math.exp(Math.sin(x0));
            return (Double x) ->  c * Math.exp(Math.sin(x));
        }

        @Override
        public String equationToString() {
            return "y' = cos (x) * y";
        }

        @Override
        public String trueSolutionToString() {
            return "c * exp(sin(x))";
        }
    };

    public static List<DiffurSolver> diffurSolvers = List.of(
            new ImprovedEulerMethod(), new FourthOrderRungeKuttaMethod(), new MilneMethod()//, new ShulgaMethod()
    );
    public static List<Diffurchik> diffurchikiList = List.of(diffurchik1, diffurchik2, diffurchik3);
}
