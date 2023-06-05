package linal;

import java.util.Arrays;

public class LinalUtils {

    public static double linCorrelation(Dot[] dots) {
        double meanX = 0d;
        double meanY = 0d;
        for (var dot : dots) {
            meanX += dot.x();
            meanY += dot.y();
        }
        meanX /= dots.length;
        meanY /= dots.length;
        final double fMeanX = meanX;
        final double fMeanY = meanY;
        double sxy = Arrays.stream(dots).map(x -> (x.x() - fMeanX)*(x.y() - fMeanY)).reduce(0d, Double::sum);
        double sxx = Arrays.stream(dots).map(x -> (x.x() - fMeanX)*(x.x() - fMeanX)).reduce(0d, Double::sum);
        double syy = Arrays.stream(dots).map(x -> (x.y() - fMeanY)*(x.y() - fMeanY)).reduce(0d, Double::sum);
        return sxy/Math.sqrt(sxx*syy);
    }
}
