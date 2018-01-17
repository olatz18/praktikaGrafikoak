package proiektuAntzerakoa;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.*;

public class Candlestick extends JPanel {
	public Candlestick(String stockSymbol) throws ParseException {
//      super("CandlestickDemo");
//      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      DateAxis    domainAxis       = new DateAxis("Date");
      NumberAxis  rangeAxis        = new NumberAxis("Price");
      CandlestickRenderer renderer = new CandlestickRenderer();
      XYDataset   dataset          = getDataSet(stockSymbol);

      XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

      //Do some setting up, see the API Doc
      renderer.setSeriesPaint(0, Color.BLACK);
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
//      this.pack();
  }
  public Candlestick() {
		// TODO Auto-generated constructor stub
	}
	protected AbstractXYDataset getDataSet(String stockSymbol) throws ParseException {
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

  protected OHLCDataItem[] getData(String stockSymbol) throws ParseException {
      List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();

      	DBKudeatzaileSQLite dbkud = DBKudeatzaileSQLite.getInstantzia();
      	ResultSet rs = dbkud.execSQL( "SELECT data, prezioAltuena, preziorikBaxuena, itxi, bolumena, zabaldu FROM JsonBTC"); 
      	
  		
			try {
				while(rs.next()){
					String fetxa = rs.getString("data");
					System.out.print(fetxa);
					
					
					double open = rs.getDouble("zabaldu");
					double high = rs.getDouble("prezioAltuena");
					double low = rs.getDouble("preziorikBaxuena");
					double close = rs.getDouble("itxi");
					double volume = rs.getDouble("bolumena");
					Date v_date=null;
					try {
						v_date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(fetxa);
						System.out.println("v_date: "+v_date);
						} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						    DateFormat formatter = null;

						    formatter = new SimpleDateFormat("d/M/y");
						    Date date_temp=null;
						    try {
						    	 String katea = formatter.format(v_date);
								 System.out.println("katea: "+katea);
						        date_temp = (Date) formatter.parse(katea); // String of same format a formatter
						        System.out.println("date_temp: "+date_temp);
						    } catch (ParseException ex) {
						        //Logger.getLogger(Attendance_Calculation.class.getName()).log(Level.SEVERE, null, ex);
						        ex.printStackTrace();
						    }
//						    String katea = formatter.format(v_date);
//						    System.out.println(katea);
						    DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//						    Date data = df.parse(katea);
						    
				OHLCDataItem item = new OHLCDataItem(v_date, open, high, low, close, volume);
	            dataItems.add(item);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              
			
      
      
      
      // Collections.reverse(dataItems);

      //Convert the list into an array
      OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

      return data;
      }
  
  

 public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
          
          public void run() {
              try {
					new Candlestick("GOOG").setVisible(true);
				} catch (ParseException e) {
					e.printStackTrace();
				}
          }
      });
     }
}