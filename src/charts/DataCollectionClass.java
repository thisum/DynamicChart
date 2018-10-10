package charts;

import org.jfree.ui.RefineryUtilities;
import org.thisum.sensorlist.DataObj;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DataCollectionClass extends JFrame implements ActionListener, DataObserver
{
    private int count = 1;

    private StringBuffer rawBuffer = new StringBuffer();
    private StringBuffer avgBugger = new StringBuffer();

    private boolean recordData;
    private boolean start = false;
    private int writeCount = 1;
    private int dataPoints = 0;
    private JLabel countLbl = new JLabel("0");
    private JLabel sampleLbl = new JLabel("0");
    private MovingAvgClass movingAvgClass;
    private MovingGradientClass movingGradientClass;
    private double avg = 0.0d;
    private double grad = 0.0d;
    private JButton saveDataBtn;
    private JButton resetBtn;
    private JButton switchToggleBtn;
    private JButton clearLastBtn;
    private JButton showTableBtn;
    private int toggleCount = 0;
    private java.util.List<Integer> rawData = new ArrayList<>();
    private List<Double> avgData = new ArrayList<>();
    private String currentTask = "";
    private JTextField nameTxt;
    private JLabel currentTaskLbl;
    private int samples = 0;
    private GraphPopPanel graphPopPanel;
    private JLabel statusLbl;
    private JButton recordButton;
    private JLabel readingLbl;
    private ImageIcon recordIcon;
    private ImageIcon readyIcon;
    private long clicketTime = 0;
    private long diff = 0;
    private double totalFreq = 0.0;
    private int sampleSize = 43;

    public static void main(String[] args)
    {
        final DataCollectionClass demo = new DataCollectionClass("Dynamic Data Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    public DataCollectionClass(final String title)
    {

        super(title);
        this.setLayout(new GridLayout(1, 1));

        JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setSize(600,500);
        GridBagConstraints gbc = new GridBagConstraints();

        try
        {
            BufferedImage readyPic = ImageIO.read(new File("res/ready.png"));
            BufferedImage recordPic = ImageIO.read(new File("res/record.png"));
            readyIcon = new ImageIcon(readyPic);
            recordIcon = new ImageIcon(recordPic);

            statusLbl = new JLabel(readyIcon);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }


        saveDataBtn = new JButton("Save Data");
        resetBtn = new JButton("Reset");
        switchToggleBtn = new JButton("Start");
        clearLastBtn = new JButton("Clear Last");
        showTableBtn = new JButton("Show Table");
        recordButton = new JButton("Record");

        readingLbl = new JLabel("data");
        readingLbl.setFont (readingLbl.getFont ().deriveFont (40.0f));
        readingLbl.setPreferredSize(new Dimension(100, 50));
        nameTxt = new JTextField("** Add name here **");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Cnt: "), 0);
        buttonPanel.add(countLbl, 1);
        buttonPanel.add(new JLabel("Samples: "), 2);
        buttonPanel.add(sampleLbl, 3);
        buttonPanel.add(clearLastBtn, 4);
        buttonPanel.add(saveDataBtn, 5);
        buttonPanel.add(resetBtn, 6);

        graphPopPanel = new GraphPopPanel();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        basePanel.add(statusLbl,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        basePanel.add(recordButton,gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        basePanel.add(readingLbl,gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        basePanel.add(nameTxt,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 7;
        basePanel.add(graphPopPanel,gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        basePanel.add(buttonPanel,gbc);

        currentTaskLbl = new JLabel();
//        nameTxt.setSize(150, 40);

        saveDataBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        switchToggleBtn.addActionListener(this);
        clearLastBtn.addActionListener(this);
        showTableBtn.addActionListener(this);
        recordButton.addActionListener(this);



//        JPanel dataPanel = new JPanel();
//        dataPanel.add(nameTxt, 0);
//        dataPanel.add(currentTaskLbl, 1);
//
//        JPanel footerPanel = new JPanel();
//        footerPanel.add(buttonPanel, 0);
//        footerPanel.add(dataPanel, 1);
//
//        final JPanel content = new JPanel(new BorderLayout());
////        content.add(chartPanel);
//        content.add(mainPanel);
//        content.add(footerPanel, BorderLayout.SOUTH);
//        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 800));
        setContentPane(basePanel);

        SocketListener socketListener = new SocketListener();
        socketListener.setDataObjserver(this);
        Thread thread = new Thread(socketListener);
        thread.start();

        movingAvgClass = new MovingAvgClass(5);
        movingGradientClass = new MovingGradientClass(3);


        this.setPreferredSize(new Dimension(800, 600));
    }

    public void actionPerformed(final ActionEvent e)
    {
        if( e.getSource() == saveDataBtn )
        {
            start = false;
            writeCount++;
            currentTask = graphPopPanel.updateCount(samples, true);
            if(currentTask != null && !currentTask.isEmpty() && !rawData.isEmpty())
            {
                rawBuffer.append(currentTask);
                rawBuffer.append(":");
                rawBuffer.append(rawData.toString());
            }


            boolean success = writeToFile();
            if(success)
            {
                resetBtn.doClick();
            }
        }
        else if( e.getSource() == resetBtn )
        {
            dataPoints = 0;
            toggleCount = 0;
            samples = 0;
            start = false;
            rawData.clear();
            avgData.clear();
            rawBuffer.setLength(0);
            avgBugger.setLength(0);
            sampleLbl.setText(samples+"");
            countLbl.setText(dataPoints+"");
            switchToggleBtn.setText("Start");
//            graphPopPanel.clearTable();
            graphPopPanel.updateCount(0, true);
            recordButton.setEnabled(true);
            clearLastBtn.setEnabled(true);
//            start = true;
        }
        else if(e.getSource() == switchToggleBtn)
        {

            toggleCount++;
            if(toggleCount%2 == 0 )
            {
                start = false;
                switchToggleBtn.setText("Start");
            }
            else
            {
                clicketTime = System.nanoTime();
                samples++;

                if(rawData.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Data caption error!!!. Please re-do the last gesture");
                    samples--;
                    return;
                }
                else if(currentTask != null && !currentTask.isEmpty())
                {
                    rawBuffer.append(currentTask);
                    rawBuffer.append(":");
                    rawBuffer.append(rawData.toString());
                    rawBuffer.append("\n");
                }

                rawData.clear();
                avgData.clear();
                start = true;
                sampleLbl.setText(samples+"");
                if(graphPopPanel != null)
                {
                    currentTask = graphPopPanel.updateCount(samples, true);
                }
                switchToggleBtn.setText("Stop");
            }
        }
        else if(e.getSource() == clearLastBtn)
        {
            dataPoints -= rawData.size();
            samples--;
            sampleLbl.setText(samples+"");
            countLbl.setText(dataPoints+"");
            rawData.clear();
            avgData.clear();
            graphPopPanel.updateCount(samples, false);
            clearLastBtn.setEnabled(false);
        }
        else if(e.getSource() == showTableBtn)
        {

        }
        else if(e.getSource() == recordButton)
        {
            toggleCount++;
            if(toggleCount%2 == 0 )
            {
                start = false;
                diff = System.nanoTime() - clicketTime;
                samples++;
                totalFreq += (count/(diff/1000000000.0));
                graphPopPanel.setFreq(String.valueOf(count/(diff/1000000000.0)), String.valueOf(totalFreq/samples));

                sampleLbl.setText(samples+"");
                recordButton.setText("Record");
                statusLbl.setIcon(readyIcon);
                if(graphPopPanel != null)
                {
                    currentTask = graphPopPanel.updateCount(samples, true);
                }
                clearLastBtn.setEnabled(true);

                if(samples > 0 && rawData.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Data caption error!!!. Please re-do the last gesture");
                    return;
                }
                if(samples == 42)
                {
                    JOptionPane.showMessageDialog(this, "Experiment completed. Please save the data" );
                    recordButton.setEnabled(false);
                }
            }
            else
            {
                if(samples == 42)
                {
                    JOptionPane.showMessageDialog(this, "Experiment completed. Please save the data" );
                    recordButton.setEnabled(false);
                    return;
                }

                recordButton.setText("Stop");
                statusLbl.setIcon(recordIcon);
                clearLastBtn.setEnabled(false);


                if(currentTask != null && !currentTask.isEmpty() && !rawData.isEmpty())
                {
                    rawBuffer.append(currentTask);
                    rawBuffer.append(":");
                    rawBuffer.append(rawData.toString());
                    rawBuffer.append("\n");

//                    avgBugger.append(currentTask);
//                    avgBugger.append(":");
//                    avgBugger.append(avgData.toString());
//                    avgBugger.append("\n\n\n");
                }

                rawData.clear();
                avgData.clear();
                count = 0;
                clicketTime = System.nanoTime();
                sampleSize = 43;
                start = true;
            }
        }
    }

    @Override
    public void onDataAvailable(DataObj dataObj)
    {

    }

    @Override
    public void onDataAvailable(int d)
    {
//        avg = movingAvgClass.calculateAvg(d);
//        grad = movingGradientClass.calculateGrad(d);

        readingLbl.setText(d + "");

        if( sampleSize > 0 )
        {
            count++;
            dataPoints++;
            countLbl.setText(""+dataPoints);
            rawData.add(d);
            sampleSize--;
//            avgData.add(avg);
        }
    }

    private boolean writeToFile()
    {
        File file = new File(nameTxt.getText() + "_raw_data.txt");
        if(file.exists())
        {
            JOptionPane.showMessageDialog(this, "File Exists. User another name");
            return false;
        }
        else
        {
            try( Writer fileWriter = new FileWriter(nameTxt.getText() + "_raw_data.txt") )
            {
                try
                {
                    fileWriter.write(rawBuffer.toString());
                    return true;
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
        }

//
//        try( Writer fileWriter = new FileWriter(nameTxt.getText() + "_avg_data.txt") )
//        {
//            try
//            {
//                fileWriter.write(rawBuffer.toString());
//            }
//            catch( IOException e )
//            {
//                e.printStackTrace();
//            }
//        }
//        catch( IOException e )
//        {
//            e.printStackTrace();
//        }
        return false;
    }
}
