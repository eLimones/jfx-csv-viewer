package com.mycompany.jfx.cvs.viewer;

import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manuel
 */
public class TablePresenterTest {
    
    public TablePresenterTest() {
    }

    @Test
    public void bindingsAreaConvertedToModel() {
        //arrage
        ObservableList<ObservableMap<String, StringProperty>> data;
        data = FXCollections.observableArrayList();
        
        ObservableMap<String, StringProperty> row0 = FXCollections.observableHashMap();
        ObservableMap<String, StringProperty> row1 = FXCollections.observableHashMap();
        
        List<String> keys = Arrays.asList("key0","key1");
        
        row0.put("key0", new SimpleStringProperty("0valor0"));
        row0.put("key1", new SimpleStringProperty("0valor1"));
        
        row1.put("key0", new SimpleStringProperty("1valor0"));
        row1.put("key1", new SimpleStringProperty("1valor1"));
        
        data.addAll(row0, row1);
     
        //act
        TableModel table = TablePresenter.toModel(keys, data);
        
        //assert
        assertEquals(keys, table.headers);
        assertFalse(keys == table.headers);
        assertEquals(2, table.data.size());
        assertEquals(Arrays.asList("0valor0", "0valor1"), table.data.get(0));
        assertEquals(Arrays.asList("1valor0", "1valor1"), table.data.get(1));
    }
    
     @Test
    public void onlyExtractsKeyedFields() {
        //arrage
        ObservableList<ObservableMap<String, StringProperty>> data;
        data = FXCollections.observableArrayList();

        ObservableMap<String, StringProperty> row0 = FXCollections.observableHashMap();
        ObservableMap<String, StringProperty> row1 = FXCollections.observableHashMap();
        
        List<String> keys = Arrays.asList("key0","key1");
        
        row0.put("key0", new SimpleStringProperty("0valor0"));
        row0.put("key1", new SimpleStringProperty("0valor1"));
        row0.put("key2", new SimpleStringProperty("0valor2"));
        
        row1.put("key0", new SimpleStringProperty("1valor0"));
        row1.put("key1", new SimpleStringProperty("1valor1"));
        row1.put("key2", new SimpleStringProperty("1valor2"));
        
        data.addAll(row0, row1);
     
        //act
        TableModel table = TablePresenter.toModel(keys, data);
        
        //asssert
        assertEquals(keys, table.headers);
        assertFalse(keys == table.headers);
        assertEquals(2, table.data.size());
        assertEquals(Arrays.asList("0valor0", "0valor1"), table.data.get(0));
        assertEquals(Arrays.asList("1valor0", "1valor1"), table.data.get(1));
    }
    
    @Test
    public void transformsTableModelToObsceneObject() {
        TableModel table = new TableModel();
        table.headers.addAll(Arrays.asList("header0", "header1"));
        table.data.add(Arrays.asList("a", "0"));
        table.data.add(Arrays.asList("b", "1"));
        table.data.add(Arrays.asList("c", "2"));
        
        ObservableList<ObservableMap<String, StringProperty>> data  = 
                 TablePresenter.toBoundList(table);
        
        assertEquals("a", data.get(0).get("header0").get());
        assertEquals("b", data.get(1).get("header0").get());
        assertEquals("c", data.get(2).get("header0").get());
        assertEquals("0", data.get(0).get("header1").get());
        assertEquals("1", data.get(1).get("header1").get());
        assertEquals("2", data.get(2).get("header1").get());
        
    }
    
}
