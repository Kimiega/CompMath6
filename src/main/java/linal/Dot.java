package linal;

public class Dot {
    private double X;
    private double Y;
    public Dot(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }
    public double x() {
        return X;
    }
    public double y() {
        return Y;
    }
    public void setX(double x) {
        this.X = x;
    }
    public void setY(double y) {
        this.Y = y;
    }

    @Override
    public String toString() {
        return X + " " + Y;
    }
}
