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
    
    ObservableList<ObservableMap<String, StringProperty>> oPeople;
    
    @FXML
    private TableView<ObservableMap<String, StringProperty>> mainTable;
   
    @FXML
    private void addColumnAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        TableColumn column  = new TableColumn("something");
//        mainTable.getColumns().add(column);
        //oPeople.add(new Person("algo", "something"));
        for (ObservableMap<String, StringProperty> person : oPeople) {
            System.out.println("firstName:" + person.get("firstName").get());
            System.out.println("lastName:" + person.get("lastName").get());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Map list
        ObservableMap<String, StringProperty> dp0  = FXCollections.observableHashMap();
        dp0.put("firstName", new SimpleStringProperty("manuel"));
        dp0.put("lastName", new SimpleStringProperty("asdf"));
        
        ObservableMap<String, StringProperty> dp1  = FXCollections.observableHashMap();
        dp1.put("firstName", new SimpleStringProperty("pirru"));
        dp1.put("lastName", new SimpleStringProperty("qwer"));
        
        oPeople = FXCollections.observableArrayList();
        oPeople.addAll(dp0, dp1);
        
        TableColumn<ObservableMap<String, StringProperty>, String> firstNameColumn  = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new MapValueFactory("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setEditable(true);
        
        TableColumn<ObservableMap<String, StringProperty>, String> lastNameColumn  = new TableColumn("Last Name");
        lastNameColumn.setCellValueFactory(new MapValueFactory("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setEditable(true);

        mainTable.setEditable(true);
        mainTable.getColumns().addAll(firstNameColumn, lastNameColumn);
        mainTable.setItems(oPeople);
        
        oPeople.get(0).get("firstName").addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }    
}
