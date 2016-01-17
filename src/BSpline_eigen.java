import java.awt.Color;

import internet.DirectBsplFilter1d;
import internet.Figure;

public class BSpline_eigen {
    public static double bSpline(final double x) {
        double helper = 0.0;
        if ((Math.abs(x) >= 0) && (Math.abs(x) < 1)) {
            helper = ((2.0 / 3.0) - (Math.abs(x) * Math.abs(x))) + ((Math.abs(x) * Math.abs(x) * Math.abs(x)) / 2.0);
        } else if ((Math.abs(x) >= 1) && (Math.abs(x) < 2)) {
            helper = ((2 - Math.abs(x)) * (2 - Math.abs(x)) * (2 - Math.abs(x))) / 6.0;
        } else if (Math.abs(x) >= 2) {
            helper = 0.0;
        }
        return helper;
    }

    public static double[] outSolution(final double[] y) {
        final int n = y.length - 1;
        final double[] right = new double[n + 1];
        final double[] md = new double[n + 1];
        final double[] Solution = new double[n + 1];
        /* Initialization */
        for (int k = 0; k <= n; k++) {
            right[k] = y[k];
            if ((k == 0) || (k == n)) {
                md[k] = 1.0;
            } else {
                md[k] = 2.0 / 3.0;
            }
        }
        /* Elimination of lower diagonal */
        right[1] = right[1] - (right[0] / 6.0);
        for (int k = 2; k <= (n - 1); k++) {
            md[k] = md[k] - (1.0 / (36.0 * md[k - 1]));
            right[k] = right[k] - (right[k - 1] / (6.0 * md[k - 1]));
        }
        /* Solving the system from below */
        Solution[0] = right[0];
        Solution[n] = right[n];
        for (int k = 1; k <= (n - 1); k++) {
            Solution[n - k] = (right[n - k] - (Solution[(n - k) + 1] / 6.0)) / md[n - k];
        }
        return Solution;
    }

    public static double[] interpolate(final double[] y, final int rate) {
        final double[] coeffs = coeffs(y);
        final double s_interp[] = new double[(rate * y.length) - (rate - 1)];
        for (int k = 0; k < s_interp.length; k++) {
            s_interp[k] = interp(coeffs, k * (1.0 / rate));
        }
        return s_interp;
    }

    public static double[] coeffs(final double s[]) {
        final DirectBsplFilter1d directFilter = new DirectBsplFilter1d(s.length);
        final double coeffs[] = outSolution(s);
        final double coeffs_mirror[] = mirrorW1d(coeffs);
        return coeffs_mirror;
    }

    public static double interp(final double coeffs_mirror[], final double x) {
        final int k = (int) Math.floor(x);
        // @formatter:off
        final double y1 = (coeffs_mirror[k + 0] * bSpline((x - k) + 1))
                        + (coeffs_mirror[k + 1] * bSpline((x - k) + 0))
                        + (coeffs_mirror[k + 2] * bSpline((x - k) - 1))
                        + (coeffs_mirror[k + 3] * bSpline((x - k )- 2));
        // @formatter:on
        return y1;
    }

    public static double outBSplinesnat(final int k, final int n, final double x) {
        double help;
        help = bSpline((n * x) - k);
        if (k == 0) {
            help = (2.0 * bSpline((n * x) + 1.0)) + bSpline(n * x);
        }
        if (k == 1) {
            help = (-1.0 * bSpline((n * x) + 1.0)) + bSpline((n * x) - 1.0);
        }
        if (k == (n - 1)) {
            help = (-1.0 * bSpline((n * x) - n - 1.0)) + bSpline(((n * x) - n) + 1.0);
        }
        if (k == n) {
            help = (2.0 * bSpline((n * x) - n - 1.0)) + bSpline((n * x) - n);
        }
        return help;
    }

    private static double[] mirrorW1d(final double s[]) {
        final double[] s_mirror = new double[s.length + 3];
        s_mirror[0] = s[1];
        for (int k = 0; k < s.length; k++) {
            s_mirror[k + 1] = s[k];
        }
        s_mirror[s_mirror.length - 2] = s[s.length - 2];
        return s_mirror;
    }

    public static void main(final String[] args) {
        final Figure figure = new Figure("Interpolation", "x", "y");
        final double y[] = { 1.75, 2, 2.15, 2.25, 2.25, 2.25, 2.25, 2.25, 2.25, 2.5, 3, 3, 3, 3, 3, 2.5, 2.15, 2.15,
                2.15, 2.05, 1.75 };
        final int rate = 100;
        final double y_interp[] = interpolate(y, rate);
        final double x_interp[] = new double[y_interp.length];
        for (int k = 0; k < x_interp.length; k++) {
            x_interp[k] = (double) k / (double) rate;
        }
        figure.line(x_interp, y_interp, Color.red, 2.0f);
    }
}
