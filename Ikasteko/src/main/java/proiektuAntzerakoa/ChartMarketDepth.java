package proiektuAntzerakoa;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument.Iterator;

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
	static XYSeriesCollection xYSeriesCollection;

	
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
   
    public static ChartPanel createChart() {
    	List<String[]> getErostekoPrezioak = BTCKud.getInstantzia().getErostekoPrezioak();
    	HashMap <Double, Double> erosihm = BTCKud.getInstantzia().prezioBolumenHM(getErostekoPrezioak);
    	HashMap<Double,Double> erosiOrdenatua = BTCKud.getInstantzia().ordenatuTH(erosihm);
    	Double handiena = BTCKud.getInstantzia().ErostekoPreziorikAltuena(erosiOrdenatua);
    	HashMap <Double, Double> erosiTarteak = BTCKud.getInstantzia().tarteakAteraErosi(erosiOrdenatua, handiena);
    	HashMap <Double, Double> erosiTarteakOr = BTCKud.getInstantzia().ordenatuTH(erosiTarteak);
   	 	
    	 List<String[]> getSaltzekoPrezioak = BTCKud.getInstantzia().getSaltzekoPrezioak();	
    	 HashMap <Double, Double> salduhm = BTCKud.getInstantzia().prezioBolumenHM(getSaltzekoPrezioak); 
    	 HashMap<Double,Double> salduOrdenatua = BTCKud.getInstantzia().ordenatuTH(salduhm);
    	 Double txikiena = BTCKud.getInstantzia().SaltzekoPreziorikMerkeena(salduOrdenatua);
    	 HashMap<Double,Double> salduTarteak = BTCKud.getInstantzia().tarteakAteraSaldu(salduOrdenatua, txikiena);
    	 HashMap <Double, Double> salduTarteakOr = BTCKud.getInstantzia().ordenatuTH(salduTarteak);
    	
//        // dataset & series
        xYSeriesCollection = new XYSeriesCollection();
       
        XYSeries xyseries = new XYSeries("erosi");
        
        List<Double> erostekoKeys = new LinkedList<Double>(erosiTarteakOr.keySet());
        Collections.reverse(erostekoKeys);
//        System.out.println("erosi --> xyseries: ");
        Double bolumenaE =0.0;
        for(Double key : erostekoKeys){
        	bolumenaE = bolumenaE + erosiTarteak.get(key);
        	xyseries.add(key , bolumenaE);
//        	System.out.println("prezio horretan kop: " + erosiTarteak.get(key)+" 		bolumena: "+ bolumenaE);
//        	System.out.println("erosteko prezioa: " + key+"		bolumena: "+ bolumenaE);

        }

//        
      XYSeries xyseries1 = new XYSeries("saldu");
      List<Double> saltzekoKeys = new LinkedList<Double>(salduTarteakOr.keySet());

//      System.out.println("saldu --> xyseries1:");
      Double bolumenaS = 0.0;
      for(Double key : saltzekoKeys){
    	  bolumenaS = salduTarteak.get(key)+ bolumenaS;
      	xyseries1.add(key, bolumenaS);
      	
//      	System.out.println("xyseries1.add( " + key + ", "+ salduTarteak.get(key)+")");
    	
      }
//        xyseries1.add(100D, 1D);
//        xyseries1.add(101D, 2D);
//        xyseries1.add(102D, 3D);
//        xyseries1.add(103D, 4D);
//        xyseries1.add(104D, 5D);
       xYSeriesCollection.addSeries(xyseries);
       xYSeriesCollection.addSeries(xyseries1);

        // chart
        JFreeChart jfreechart = ChartFactory.createXYLineChart("", "Prezioa(€)", "Kantitatea", xYSeriesCollection, PlotOrientation.VERTICAL, true, false, false);
     //   jfreechart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF, 0));
        

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        XYStepAreaRenderer xysteparearenderer = new XYStepAreaRenderer(2);
        xysteparearenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        xyplot.setRenderer(xysteparearenderer);

        // chart panel
        final ChartPanel grafikoa = new ChartPanel(jfreechart);
        
        grafikoa.setMaximumDrawHeight(2000);
        grafikoa.setMaximumDrawWidth(3000);
        
        return grafikoa;
    }

    public static ChartPanel getCreateChart() {
		return createChart();

	}

    public static void main(String[] args) {
        new ChartMarketDepth(new GridLayout(1, 1));
    }
}