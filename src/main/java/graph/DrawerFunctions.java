package graph;


import linal.Dot;
import linal.Equation;
import linal.Solution;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DrawerFunctions {
    private final static int DEFAULT_WIDTH = 700;
    private final static int DEFAULT_HEIGHT = 700;
    private final static Color[] COLORS = new Color[] {StdDraw.RED, StdDraw.BLUE, StdDraw.GREEN, StdDraw.MAGENTA, StdDraw.PINK, StdDraw.BLACK};

    private final double SCALEX;
    private final double SCALEY;
    private final double offsetX;
    private final double offsetY;
    private final double FUNC_STEP;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    private static int COLOR_ITERATOR = 0;//((int)(Math.random()*COLORS.length))%COLORS.length;
    private static int funcIter = 1;
    public DrawerFunctions() {
        this(-10d, 10d, -10d, 10d);
    }
    public DrawerFunctions(double xMin, double xMax, double yMin, double yMax) {
        //init(scale);
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.SCALEX = (xMax-xMin)/10d;
        this.SCALEY = (yMax-yMin)/10d;
        this.xMin -= SCALEX;
        this.xMax += SCALEX;
        this.yMin -= SCALEY;
        this.yMax += SCALEY;
        this.offsetX = SCALEX*2/3;
        this.offsetY = SCALEY*2/3;
        this.FUNC_STEP = SCALEX*0.01;
        funcIter = 1;
        COLOR_ITERATOR = ((int)(Math.random()*COLORS.length))%COLORS.length;
        StdDraw.setCanvasSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        StdDraw.setXscale(this.xMin, this.xMax);
        StdDraw.setYscale(this.yMin, this.yMax);
        drawGrid();
    }

    public void drawFunction(Equation equation) {
        Color color= getNextColor();
        printFuncString(equation.equationToString(), color);
        boolean isFirst = true;
        double prevX = 0;
        double prevY = 0;
        for (double x = xMin + offsetX; x < xMax - offsetX; x+=FUNC_STEP) {
            if (!Double.isFinite(equation.equation().apply(x)))
                continue;
            if (!isFirst)
                try {
                        StdDraw.setPenColor(color);
                        StdDraw.line(prevX, prevY, x, equation.equation().apply(x));
                } catch (IllegalArgumentException ignored) {}
            prevX = x;
            prevY = equation.equation().apply(x);
            isFirst = false;
        }
    }

    public void drawGrid() {

        StdDraw.line(xMin + offsetX, yMin + offsetY, xMax - offsetX,yMin + offsetY);
        StdDraw.line(xMin + offsetX, yMin + offsetY, xMin + offsetX,yMax - offsetY);
        for (double x = xMin + offsetX; x < xMax - offsetX; x += SCALEX) {
            StdDraw.line(x, yMin + offsetY + SCALEY/5,x, yMin + offsetY - SCALEY/5);
            StdDraw.text(x, yMin + offsetY - SCALEY/3, String.format("%.1f", x));
        }
        StdDraw.text(xMax - offsetX ,yMin + offsetY - SCALEY/3, "X");
        for (double y = yMin + offsetY; y < yMax - offsetY; y += SCALEY) {
            StdDraw.line(xMin + offsetX + SCALEX/5, y ,xMin + offsetX - SCALEX/5, y);
            StdDraw.text(xMin + offsetX - SCALEX/3, y, String.format("%.1f", y), 90);
        }
        StdDraw.text(xMin + offsetX - SCALEX/3 ,yMax - offsetY, "Y");
    }
    private synchronized Color getNextColor() {
        Color color = COLORS[COLOR_ITERATOR];
        COLOR_ITERATOR = (COLOR_ITERATOR + 1) % COLORS.length;
        return color;
    }
    private synchronized void printFuncString(String func, Color color){
        StdDraw.setPenColor(color);
        StdDraw.textRight(xMax - SCALEX / 2, yMax - funcIter * SCALEY/2, String.format("%d. %s", funcIter, func));
        funcIter++;
    }
    public void drawFunction(Solution solution) {
        Color color = getNextColor();
        StdDraw.setPenColor(color);
        printFuncString(solution.getMethodName(), color);
        Dot prev = null;
        for (var dot : solution.getTable()) {
            if (!Double.isFinite(dot.x()) || !Double.isFinite(dot.y()))
                continue;
            printDot(dot, color);
            if (prev == null)
                prev = dot;
            else {
                StdDraw.line(prev.x(), prev.y(), dot.x(), dot.y());
                prev = dot;
            }
        }

    }

    public synchronized void printDots(Dot[] dots) {
        Arrays.stream(dots).forEach(this::printDot);
    }
    public synchronized void printDot(Dot dot) {
        printDot(dot.x(), dot.y());
    }
    public synchronized void printDot(Dot dot, Color color) {
        printDot(dot.x(), dot.y(), color);
    }
    public synchronized void printDot(double x, double y) {
        printDot(x, y, Color.ORANGE);
    }
    public synchronized void printDot(double x, double y, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledEllipse(x, y, SCALEX/10, SCALEY/10);
    }
}
