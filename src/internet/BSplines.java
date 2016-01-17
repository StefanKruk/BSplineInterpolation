
package internet;

public class BSplines {
    public double bspline(final double x) {
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
}