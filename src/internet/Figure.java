package internet;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RenderingHints;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Figure {
    private final JFrame frame;
    private final XYPlot plot;
    private int          dataSetsCounter = 0;

    public Figure(final String name, final String xName, final String yName) {
        frame = new JFrame("Figure");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plot = new XYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        final NumberAxis domainAxis = new NumberAxis(xName);
        final ValueAxis rangeAxis = new NumberAxis(yName);
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        final JFreeChart chart = new JFreeChart(plot);
        final RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        chart.setRenderingHints(renderingHints);
        chart.removeLegend();
        chart.setTitle(name);
        final ChartPanel chartPanel = new ChartPanel(chart);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public void line(final double x[], final double y[], final Color color, final float lineWidth) {
        final XYDataset dataset = createDataset(x, y);
        plot.setDataset(dataSetsCounter, dataset);
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        final BasicStroke basicStroke = new BasicStroke(lineWidth);
        renderer.setSeriesStroke(0, basicStroke);
        renderer.setSeriesPaint(0, color);
        plot.setRenderer(dataSetsCounter, renderer);
        dataSetsCounter++;
    }

    public void stem(final double x[], final double y[], final Color color, final float lineWidth) {
        final XYDataset dataset = createDataset(x, y);
        plot.setDataset(dataSetsCounter, dataset);
        final XYBarRenderer renderer = new XYBarRenderer(0.98f);
        renderer.setShadowVisible(false);
        renderer.setSeriesPaint(0, color);
        plot.setRenderer(dataSetsCounter, renderer);
        dataSetsCounter++;
    }

    private XYDataset createDataset(final double x[], final double y[]) {
        final XYSeries series = new XYSeries("");
        for (int i = 0; i < y.length; i++) {
            series.add(x[i], y[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}
