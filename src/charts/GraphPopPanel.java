package charts;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GraphPopPanel extends JPanel
{
    private XYChart.Series[] dataSeries;
    private int numLines = 0;
    private GridPane gridPane;
    private int count = 0;
    private boolean graphSet;
    private JTable jTable;
    private List<KeyVal> objData = new ArrayList<>();
    private List<KeyVal> graspData = new ArrayList<>();
    private List<KeyVal> finalData = new ArrayList<>();
    private Map<String, String> rows = new HashMap<>();
    private DefaultTableModel defaultTableModel;
    private JTextArea textArea;
    private JLabel nextNoLbl;
    private JLabel iconLabel;
    private JLabel avgLabel = new JLabel("");
    private JLabel freqLbl = new JLabel("");
    private List<ImageIcon> iconsList = new ArrayList<>();


    public GraphPopPanel()
    {
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("To perform: ");
        defaultTableModel.addColumn("Current Task");
        textArea = new JTextArea();
        nextNoLbl = new JLabel("Todo: ");
        iconLabel = new JLabel();
        textArea.setPreferredSize(new Dimension(400, 120));

        JPanel panel = new JPanel();
        panel.setSize(400,400);
        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nextNoLbl, gbc);
        nextNoLbl.setFont (nextNoLbl.getFont ().deriveFont (40.0f));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(iconLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        panel.add(textArea,gbc);
        textArea.setFont (textArea.getFont ().deriveFont (35.0f));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(freqLbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(avgLabel, gbc);

        this.add(panel);

        try
        {
            for(int i=1; i<15; i++)
            {
                BufferedImage icon = ImageIO.read(new File("res/"+i+".png"));
                iconsList.add(new ImageIcon(icon));
            }

        }
        catch( Exception e )
        {
            e.printStackTrace();
        }


        readData();
        int k = 0;
        for( KeyVal d : finalData)
        {
            k++;
            System.out.println(k + " : " +d.getKey() + "   " + d.getVal());
        }
        updateCount(0, true);
    }

    private void readData()
    {
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader("objects.json"))
        {
            Object o = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject)o;

            JSONObject objects = (JSONObject)jsonObject.get("objects");
            Set keys = objects.keySet();
            for( Object key : keys)
            {
                objData.add(new KeyVal(key, objects.get(key)));
            }

            objects = (JSONObject)jsonObject.get("grasps");
            keys = objects.keySet();
            for( Object key : keys)
            {
                for( KeyVal d : objData)
                {
                    graspData.add(new KeyVal(d.getKey() + "-" + String.valueOf(key), d.getVal() + "_" + String.valueOf(objects.get(key))));
                }
            }

            objects = (JSONObject)jsonObject.get("repetitions");
            keys = objects.keySet();
            int i = 0;
            for( Object key : keys)
            {
                Collections.shuffle(graspData, new Random(12000 + (i*100)));
                for( KeyVal d : graspData)
                {
                    finalData.add(new KeyVal(d.getKey() + "-" + String.valueOf(key), d.getVal() + "_" + String.valueOf(objects.get(key))));
                }
                i++;
            }
        }
        catch( ParseException | IOException e )
        {
            e.printStackTrace();
        }
    }

    public String updateCount(int count, boolean increment)
    {
        if(count >= finalData.size())
        {
            return finalData.get(finalData.size()-1).getKey();
        }
        KeyVal keyVal = finalData.get(count);
        updateUI(keyVal.getVal());

        if(count -1 >= 0)
        {
            return finalData.get(count-1).getKey();
        }
        return "";
    }

    private void updateUI(String text)
    {
        String []s = text.split("_",3);
        String []number = s[0].split(":");

        iconLabel.setIcon(iconsList.get(Integer.parseInt(number[0].trim())-1));
        textArea.setText(number[1].trim() + "\n" + s[1].trim());
    }

    public void setFreq(String freq, String avg)
    {
        freqLbl.setText(freq + "Hz");
        avgLabel.setText("avg freq: " + avg + "Hz");
    }
}
