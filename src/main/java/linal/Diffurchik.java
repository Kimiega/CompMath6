package linal;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Diffurchik {
    BiFunction<Double, Double, Double> equation();
    Function<Double, Double> trueSolution(Double x0, Double y0);
    String equationToString();
    String trueSolutionToString();
}
