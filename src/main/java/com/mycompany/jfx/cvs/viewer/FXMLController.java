package com.mycompany.jfx.cvs.viewer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


public class FXMLController implements Initializable {
    
    ObservableList<Person> oPeople;
    
    @FXML
    private TableView<Person> mainTable;
   
    @FXML
    private void addColumnAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        TableColumn column  = new TableColumn("something");
//        mainTable.getColumns().add(column);
        //oPeople.add(new Person("algo", "something"));
        for (Person person : oPeople) {
            System.out.println("firstName:" + person.getFirstName());
            System.out.println("lastName:" + person.getLastName());
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Clasic list
        Person person0 = new Person("manuel", "asdf");
        Person person1 = new Person("pirru", "qwer");
        List<Person> people = new ArrayList<>();
        people.add(person0);
        people.add(person1);

        for (Person person : people) {
            System.out.println("firstName:" + person.getFirstName());
            System.out.println("lastName:" + person.getLastName());
            
        }
        
        //Map list
        Map<String, String> dp0  = new HashMap<>();
        dp0.put("firstName", "manuel");
        dp0.put("lastName", "asdf");
        
        Map<String, String> dp1  = new HashMap<>();
        dp1.put("firstName", "pirru");
        dp1.put("lastName", "qwer");
        
        List<Map<String, String>> dPeople = new ArrayList<>();
        dPeople.add(dp0);
        dPeople.add(dp1);
        
        for (Map<String, String> person : dPeople) {
            System.out.println("firstName:" + person.get("firstName"));
            System.out.println("lastName:" + person.get("lastName"));
        }
        
        dp1.put("age", "1");
        
        
        oPeople = FXCollections.observableArrayList();
        oPeople.addAll(person0, person1);
        
        TableColumn<Person, String> firstNameColumn  = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setEditable(true);
        
        TableColumn<Person, String> lastNameColumn  = new TableColumn("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setEditable(true);

        mainTable.setEditable(true);
        mainTable.getColumns().addAll(firstNameColumn, lastNameColumn);
        mainTable.setItems(oPeople);
        
        person1.firstNameProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }    
}
