package com.mycompany.jfx.cvs.viewer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


public class FXMLController implements Initializable {
    private int columnCounter = 0;
    
    ObservableList<ObservableMap<String, StringProperty>> data;
    
    @FXML
    private TableView<ObservableMap<String, StringProperty>> mainTable;
   
    @FXML
    private void addColumnAction(ActionEvent event) {
        String columnName = "col" + columnCounter;
        for(ObservableMap<String, StringProperty> row  : data){
            row.put(columnName, new SimpleStringProperty(""));
        }
        
        TableColumn<ObservableMap<String, StringProperty>, String> newColumn  = new TableColumn(columnName);
        newColumn.setCellValueFactory(new MapValueFactory(columnName));
        newColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        newColumn.setEditable(true);
        
        mainTable.getColumns().add(newColumn);
        
        columnCounter++;
    }
    
    @FXML
    private void somethingAction(ActionEvent event) {
        data.forEach((row) -> {
            row.forEach((t, u) -> {
                System.out.print(t + ":" + "'" + u.get() + "', ");
            });
            System.out.println(" ");
        });
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            data.add(FXCollections.observableHashMap());
        }

        mainTable.setEditable(true);
        mainTable.setItems(data);
    }    
}
