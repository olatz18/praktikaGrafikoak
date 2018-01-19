package grafiko;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartMarketDepth extends JFrame {
	XYSeriesCollection xYSeriesCollection;
		
    public ChartMarketDepth() { };
    
    public ChartMarketDepth(LayoutManager layout) {
        super("market depth chart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(this.createChart());
        setSize(600, 200);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public ChartPanel createChart() {

        // dataset & series
        xYSeriesCollection = new XYSeriesCollection();
        XYSeries xyseries = new XYSeries("bid"); // gorriak
        xyseries.add(95D, 4D);
        xyseries.add(96D, 3D);
        xyseries.add(97D, 2D);
        xyseries.add(98D, 1D);
        xyseries.add(99D, 0D);
        
        XYSeries xyseries1 = new XYSeries("ask"); //urdinak
        xyseries1.add(100D, 1D);
        xyseries1.add(101D, 2D);
        xyseries1.add(102D, 3D);
        xyseries1.add(103D, 4D);
        xyseries1.add(104D, 5D);
        xYSeriesCollection.addSeries(xyseries);
        xYSeriesCollection.addSeries(xyseries1);

        // chart
        JFreeChart jfreechart = ChartFactory.createXYLineChart("", "Price", "Amount", xYSeriesCollection, PlotOrientation.VERTICAL, true, false, false);
     //   jfreechart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF, 0));

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        XYStepAreaRenderer xysteparearenderer = new XYStepAreaRenderer(2); //1 puntuak, 2 barra grafikoa, 3 puntuak eta barrak
        xysteparearenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        xyplot.setRenderer(xysteparearenderer);

        // chart panel
        final ChartPanel chartPanel = new ChartPanel(jfreechart);
        chartPanel.setMaximumDrawHeight(2000);
        chartPanel.setMaximumDrawWidth(3000);
        
        return chartPanel;
    }

    public static void main(String[] args) {
        new ChartMarketDepth(new GridLayout(1, 1));
    }
}
