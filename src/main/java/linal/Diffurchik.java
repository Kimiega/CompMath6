package linal;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Diffurchik {
    public abstract BiFunction<Double, Double, Double> equation();
    public abstract Function<Double, Double> trueSolution(Double x0, Double y0);
    public abstract String equationToString();
    public abstract String trueSolutionToString();
    @Override
    public String toString() {
        return equationToString();
    }
}
