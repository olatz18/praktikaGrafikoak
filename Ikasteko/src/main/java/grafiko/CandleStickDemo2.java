package grafiko;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.*;
import java.util.*;
import java.util.List;

/**
*   Mendekotasuna: jfreechart 1.0.19 (Maven bidez)
*/
public class CandleStickDemo2 extends JFrame {
    public CandleStickDemo2(String stockSymbol) {
        super("CandlestickDemo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DateAxis    domainAxis       = new DateAxis("Date");
        NumberAxis  rangeAxis        = new NumberAxis("Price");
        CandlestickRenderer renderer = new CandlestickRenderer();
        XYDataset   dataset          = getDataSet(stockSymbol);

        XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
       

        //Do some setting up, see the API Doc
        renderer.setSeriesPaint(0, Color.BLACK);
//        renderer.setSeriesPaint(0, Color.GREEN);
//        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setDrawVolume(false);
        rangeAxis.setAutoRangeIncludesZero(false);
        domainAxis.setTimeline( SegmentedTimeline.newMondayThroughFridayTimeline() );
        

        //Now create the chart and chart panel
        JFreeChart chart = new JFreeChart(stockSymbol, null, mainPlot, false);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(600, 300));

        mainPlot.setDomainPannable(true);
        mainPlot.setRangePannable(true);

        this.add(chartPanel);
        this.pack();
    }
    protected AbstractXYDataset getDataSet(String stockSymbol) {
        //This is the dataset we are going to create
        DefaultOHLCDataset result = null;
        //This is the data needed for the dataset
        OHLCDataItem[] data;

        //This is where we go get the data, replace with your own data source
        data = getData(stockSymbol);

        //Create a dataset, an Open, High, Low, Close dataset
        result = new DefaultOHLCDataset(stockSymbol, data);

        return result;
    }

    protected OHLCDataItem[] getData(String stockSymbol) {
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        try {
        		File url = new File("data.csv");
        		Scanner in = new Scanner(url);
        		in.useDelimiter("\r");
        		DateFormat df = new SimpleDateFormat("M/d/y");
           // Date df= new Date();

            String inputLine;
            while (  in.hasNext() ) {
            		inputLine = in.next();
            		
            		String[] tokens = inputLine.split(",");
            		List<String> list = Arrays.asList(tokens);
            		Iterator<String> st = list.iterator();

                Date date       = df.parse( st.next().replace("\"", "") );
                double open     = Double.parseDouble( st.next().replace("\"", "") );
                double high     = Double.parseDouble( st.next().replace("\"", "") );
                double low      = Double.parseDouble( st.next().replace("\"", "") );
                double close    = Double.parseDouble( st.next().replace("\"", "") );
                double volume   = Double.parseDouble( st.next().replace("\"", "") );

                OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);
                dataItems.add(item);
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
         Collections.reverse(dataItems);

        //Convert the list into an array
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

        return data;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new CandleStickDemo2("GOOG").setVisible(true);
            }
        });
    }
}
