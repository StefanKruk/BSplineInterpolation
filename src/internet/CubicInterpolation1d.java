
package internet;

import java.awt.Color;

public class CubicInterpolation1d {
    private double[] mirrorW1d(final double s[]) {
        final double[] s_mirror = new double[s.length + 3];
        s_mirror[0] = s[1];
        for (int k = 0; k < s.length; k++) {
            s_mirror[k + 1] = s[k];
        }
        s_mirror[s_mirror.length - 2] = s[s.length - 2];
        return s_mirror;
    }

    public double[] coeffs(final double s[]) {
        final DirectBsplFilter1d directFilter = new DirectBsplFilter1d(s.length);
        final double coeffs[] = directFilter.filter(s);
        final double coeffs_mirror[] = mirrorW1d(coeffs);
        return coeffs_mirror;
    }

    public double interp(final double coeffs_mirror[], final double x1) {
        final BSplines bS = new BSplines();
        final int k = (int) Math.floor(x1);
        // @formatter:off
        final double y1 = (coeffs_mirror[k + 0] * bS.bspline((x1 - k) + 1))
                        + (coeffs_mirror[k + 1] * bS.bspline((x1 - k) + 0)) 
                        + (coeffs_mirror[k + 2] * bS.bspline((x1 - k) - 1))
                        + (coeffs_mirror[k + 3] * bS.bspline((x1 - k )- 2));
        // @formatter:on
        return y1;
    }

    public double[] interpolate(final double s[], final int rate) {
        final double coeffs_mirror[] = coeffs(s);
        final double s_interp[] = new double[(rate * s.length) - (rate - 1)];
        for (int k = 0; k < s_interp.length; k++) {
            s_interp[k] = interp(coeffs_mirror, k * (1.0 / rate));
        }
        return s_interp;
    }

    public static void main(final String[] args) {
        final double y[] = { 1.75, 2, 2.15, 2.25, 2.25, 2.25, 2.25, 2.25, 2.25, 2.5, 3, 3, 3, 3, 3, 2.5, 2.15, 2.15,
                2.15, 2.05, 1.75 };
        final double x[] = new double[y.length];
        for (int k = 0; k < y.length; k++) {
            x[k] = k;
        }
        final int rate = 100;
        final CubicInterpolation1d cubicInterpolation1d = new CubicInterpolation1d();
        final double y_interp[] = cubicInterpolation1d.interpolate(y, rate);
        final double x_interp[] = new double[y_interp.length];
        for (int k = 0; k < x_interp.length; k++) {
            x_interp[k] = (double) k / (double) rate;
        }
        final Figure figure = new Figure("Spline interpolation", "", "");
        figure.stem(x, y, Color.BLUE, 1.0f);
        figure.line(x_interp, y_interp, Color.RED, 2.0f);
    }
}