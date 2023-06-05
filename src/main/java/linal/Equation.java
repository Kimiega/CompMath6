package linal;

import java.util.function.Function;

public interface Equation {
    Function<Double, Double> equation();
    String equationToString();
}
