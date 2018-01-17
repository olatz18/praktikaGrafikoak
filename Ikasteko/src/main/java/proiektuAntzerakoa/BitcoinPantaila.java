package proiektuAntzerakoa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.XYDataset;

public class BitcoinPantaila extends JFrame{

	public BitcoinPantaila(){
		 super("Bitcoin-ei buruzko datuak");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			ChartPanel panelaChart= ChartMarketDepth.getCreateChart();
			JPanel candels = new JPanel();
			try {
				candels = new Candlestick("Candle Stick");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			TableModelBTC TableModel = new TableModelBTC();
//			JTable table = new JTable(TableModel);
//			JScrollPane scrollPane = new JScrollPane(table);
			add(candels, BorderLayout.NORTH);
//			add(scrollPane, BorderLayout.WEST);
			add(panelaChart, BorderLayout.EAST);
			JButton bueltatu = new JButton("Bueltatu");
			getContentPane().add(bueltatu, BorderLayout.SOUTH);
			pack();
			setVisible(true);
			bueltatu.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//new LehenengoPantaila();
					setVisible(false);
					
				}
	});
			
	}
	public static void main(String[] args) {
		new BitcoinPantaila();
	}
}

