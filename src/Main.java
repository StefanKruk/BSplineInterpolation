import java.awt.Color;

import internet.BSplines;
import internet.Figure;

public class Main {
    public static void main(final String[] args) {
        final int rate = 100;
        double x[];
        double b[];
        final int M = 200;
        x = new double[M];
        b = new double[M];
        for (int k = 0; k < x.length; k++) {
            x[k] = (k - 100.0) / 30;
        }
        final BSplines bSplines = new BSplines();
        for (int k = 0; k < x.length; k++) {
            b[k] = bSplines.bspline(x[k]);
        }
        final Figure figure = new Figure("bsplines", "x", "betta");
        figure.line(x, b, Color.MAGENTA, 2.0f);
    }
}
