package charts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by thisum_kankanamge on 4/9/18.
 */
public class ObjTable
{
    private final ObservableList<RowObj> dataList = FXCollections.observableArrayList();
    private JTable jTable;
    private DefaultTableModel model;
    private Vector<RowObj> rowObjVector = new Vector<>();

    public ObjTable()
    {
        setupTable();
        readDataAndFillTable();
    }

    private void setupTable()
    {
        model = new DefaultTableModel();
        jTable = new JTable(model);
        model.addColumn("Status");
        model.addColumn("object");
    }

    private void readDataAndFillTable()
    {
//        JSONParser jsonParser = new JSONParser();
//        try(FileReader fileReader = new FileReader("objects.json"))
//        {
//            Object o = jsonParser.parse(fileReader);
//            JSONObject jsonObject = (JSONObject)o;
//
//            JSONArray jsonArray = (JSONArray)jsonObject.get("objects");
//            Iterator iti = jsonArray.iterator();
//            while( iti.hasNext() )
//            {
////                model.addRow();
//                dataList.add(new RowObj(String.valueOf(iti.next())));
//            }
//        }
//        catch( ParseException | IOException e )
//        {
//            e.printStackTrace();
//        }
//
//        model.getDataVector().add(new RowObj("test"));

    }

    public String updateStatus(int i)
    {
        dataList.forEach(r->r.setStatus(""));

        int row = i % dataList.size();
        row = (row == 0) ? dataList.size()-1 : row-1;
        RowObj rowObj = dataList.get(row);
        rowObj.setStatus("->");
        model.fireTableDataChanged();

        return rowObj.getObject();
    }

    public void updateCount(int i, boolean increment)
    {
        int row = i % dataList.size();
        row = (row == 0) ? dataList.size()-1 : row-1;
        RowObj rowObj = dataList.get(row);

        rowObj.setCount(increment ? rowObj.getCount()+1 : rowObj.getCount()-1);
    }

    public String clearTable()
    {
        String object = "";
        dataList.forEach(r->{r.setStatus(""); r.setCount(0);});
        if(!dataList.isEmpty())
        {
            object = dataList.get(0).getObject();
            dataList.get(0).setStatus("->");
        }
        model.fireTableDataChanged();

        return object;
    }

    public JTable getTable()
    {
        return jTable;
    }

}
