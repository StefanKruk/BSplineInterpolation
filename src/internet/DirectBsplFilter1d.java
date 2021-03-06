package internet;
public class DirectBsplFilter1d {
    private final int    N;
    private final double z1;
    private final double cplus[];
    private final double cminus[];
    private int          k;
    private double       sum0;

    public DirectBsplFilter1d(final int N) {
        this.N = N;
        z1 = -2.0 + Math.sqrt(3.0);
        cplus = new double[N];
        cminus = new double[N];
    }

    public void reset() {
        for (k = 0; k < N; k++) {
            cplus[k] = 0.0;
            cminus[k] = 0.0;
        }
    }

    public double[] filter(final double s[]) {
        sum0 = 0.0;
        for (k = 0; k < N; k++) {
            sum0 = sum0 + (6.0 * s[k] * Math.pow(z1, k));
        }
        cplus[0] = sum0;
        for (k = 1; k < N; k++) {
            cplus[k] = (6.0 * s[k]) + (z1 * cplus[k - 1]);
        }
        cminus[N - 1] = (z1 / ((z1 * z1) - 1.0)) * (cplus[N - 1] + (z1 * cplus[N - 2]));
        for (k = N - 2; k >= 0; k--) {
            cminus[k] = z1 * (cminus[k + 1] - cplus[k]);
        }
        return cminus;
    }
}