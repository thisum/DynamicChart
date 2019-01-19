package charts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.thisum.sensorlist.DataObj;

import java.util.ArrayList;
import java.util.List;

/**
 * A demonstration application showing a time series chart where you can dynamically add
 * (random) data by clicking on a saveDataBtn.
 */
public class DynamicDataDemo extends ApplicationFrame implements ActionListener, DataObserver
{

    private static final double TWO_PI = Math.PI * 2;
    private FastFourierTransformer fastFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
    /**
     * The time series data.
     */
//    private TimeSeries seriesA;
//    private TimeSeries avgSeries;
//    private TimeSeries gradSeries;
    private int count = 0;

    private StringBuffer buf1 = new StringBuffer();
    private StringBuffer buf2 = new StringBuffer();

    //    private XYSeries seriesA;
    private XYSeries avgSeries;
    private XYSeries gradSeries;
    private boolean recordData;
    private boolean start = false;
    private int writeCount = 1;
    private int dataPoints = 0;
    private JLabel countLbl = new JLabel();
    private MovingAvgClass movingAvgClass;
    private MovingGradientClass movingGradientClass;
    private double avg = 0.0d;
    private double grad = 0.0d;

    /**
     * The most recent value added.
     */
    private double lastValue = 100.0;

    /**
     * Constructs a new demonstration application.
     *
     * @param title the frame title.
     */
    public DynamicDataDemo(final String title)
    {

        super(title);
//        this.seriesA = new TimeSeries("Heart VAl", Millisecond.class);
//        this.avgSeries = new TimeSeries("Series B", Millisecond.class);
//        this.gradSeries = new TimeSeries("Series C", Millisecond.class);

//        this.seriesA = new XYSeries("Heart VAl", false);
//        this.avgSeries = new XYSeries("AVG", false);
        this.gradSeries = new XYSeries("GRAD", false);

//        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(this.seriesA);
//        dataset.addSeries(this.avgSeries);
        dataset.addSeries(this.gradSeries);
        final JFreeChart chart = createChart(dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);
        final JButton button = new JButton("Save Data");
        final JButton button1 = new JButton("Start Data Record");
        button.setActionCommand("ADD_DATA");
        button1.setActionCommand("START_DATA");
        button.addActionListener(this);
        button1.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(countLbl, 0);
        buttonPanel.add(button, 1);
        buttonPanel.add(button1, 2);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        content.add(buttonPanel, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);

        SocketListener socketListener = new SocketListener();
        socketListener.setDataObjserver(this);
        Thread thread = new Thread(socketListener);
        thread.start();

        movingAvgClass = new MovingAvgClass(5);
        movingGradientClass = new MovingGradientClass(3);
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset)
    {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Sensor Vals",
                "Count",
                "Value",
                dataset,
                true,
                true,
                false
        );

//        final JFreeChart result = ChartFactory.createXYLineChart(
//                "Sensor Vals",
//                "Count",
//                "Value",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );

        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
//        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis.setFixedAutoRange(600);  // 60 seconds
        axis = plot.getRangeAxis();
//        axis.setRange(0.0, 200.0);
        return result;
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************

    /**
     * Handles a click on the button by adding new (random) data.
     *
     * @param e the action event.
     */
    public void actionPerformed(final ActionEvent e)
    {
        if( e.getActionCommand().equals("ADD_DATA") )
        {
            start = false;
            writeCount++;
            writeToFile();
        }
        else if( e.getActionCommand().equals("START_DATA") )
        {
            dataPoints = 0;
            start = true;
        }
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public static void main(final String[] args)
    {

        final DynamicDataDemo demo = new DynamicDataDemo("Dynamic Data Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

//        SocketWriter socketListener = new SocketWriter();
//        Thread thread = new Thread(socketListener);
//        thread.start();

    }

    @Override
    public void onDataAvailable(DataObj dataObj)
    {
        ++count;
//        this.seriesA.add(count, dataObj.getOri());
//        this.avgSeries.add(count, dataObj.getA());
        this.gradSeries.add(count, dataObj.getB());

        if( start )
        {
            dataPoints++;
            countLbl.setText(""+dataPoints);
            buf1.append(dataObj.getA());
            buf1.append(",");
            buf1.append(dataObj.getB());
            buf1.append(",");
            buf1.append(dataObj.getCount());
            buf1.append("\n");
        }

        if(dataPoints == 256)
        {
            start = false;
        }


//        this.avgSeries.add(new Millisecond(), dataObj.getAx());
//        this.gradSeries.add(new Millisecond(), dataObj.getAy());
    }

    @Override
    public void onDataAvailable(int d)
    {
        ++count;
        avg = movingAvgClass.calculateAvg(d);
        grad = movingGradientClass.calculateGrad((float)avg);

//        this.avgSeries.add(count, avg);
        this.gradSeries.add(count, grad);

        if( start )
        {
            dataPoints++;
            countLbl.setText(""+dataPoints);

            buf1.append(d);
            buf1.append("\n");

            buf2.append(grad);
            buf2.append("\n");
        }
        if(dataPoints == 256)
        {
            start = false;
        }
    }

    private double[] runHammingWindow(double[] array)
    {
        int length = array.length;
        for( int i = 0; i < length; i++ )
        {
            array[i] *= calculateHammingValue(length, i);
        }
        return array;
    }

    private double calculateHammingValue(int length, int i)
    {
        return 0.54d - 0.46 * Math.cos((TWO_PI * i / (length - 1d)));
    }


    private void writeToFile()
    {

        try( Writer fileWriter = new FileWriter("raw_data.txt") )
        {
            try
            {
                fileWriter.write("A\n" + buf1.toString());
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        buf1.setLength(0);

        try( Writer fileWriter = new FileWriter("grad_data.txt") )
        {
            try
            {
                fileWriter.write("A\n" + buf2.toString());
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        buf2.setLength(0);
    }
}