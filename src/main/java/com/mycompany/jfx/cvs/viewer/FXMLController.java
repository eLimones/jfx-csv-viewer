package com.mycompany.jfx.cvs.viewer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class FXMLController implements Initializable {

    private int columnCounter = 0;
    
    @FXML
    private MenuItem deleteRowsMenuItem;

    ObservableList<ObservableMap<String, StringProperty>> data;

    @FXML
    private TableView<ObservableMap<String, StringProperty>> mainTable;

    @FXML
    private void addColumnAction(ActionEvent event) {
        String defaultColName = "col" + columnCounter;
        TextInputDialog dialog = new TextInputDialog(defaultColName);
        dialog.setTitle("New column");
        dialog.setHeaderText("");
        dialog.setContentText("Enter column name:");
        dialog.showAndWait().ifPresent(this::addColumn);
    }

    @FXML
    private void deleteColumnAction(ActionEvent event) {
        List<String> choices = new ArrayList<>(data.get(0).keySet());

        if (choices.size() > 0) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Delete column");
            dialog.setHeaderText("");
            dialog.setContentText("Choose a column:");

            dialog.showAndWait().ifPresent((columnName) -> {
                deleteColumn(columnName);
            });
        }

    }

    @FXML
    private void addRowsAction(ActionEvent event) {
        ObservableMap<String, StringProperty> newRow = FXCollections.observableHashMap();
        data.get(0).keySet().forEach((key) -> {
            newRow.put(key, new SimpleStringProperty(""));
        });
        data.add(newRow);
        
    }

    void deleteColumn(String columnName) {
        mainTable
                .getColumns()
                .stream()
                .filter((column) -> {
                    return column.getText().equals(columnName);
                })
                .findAny()
                .ifPresent((column) -> {
                    mainTable.getColumns().remove(column);
                });

        data.forEach((row) -> {
            row.remove(columnName);
        });
    }

    void addColumn(String columnName) {
        for (ObservableMap<String, StringProperty> row : data) {
            row.put(columnName, new SimpleStringProperty(""));
        }

        TableColumn<ObservableMap<String, StringProperty>, String> newColumn = new TableColumn(columnName);
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
    
     @FXML
    private void deleteRowsAction(ActionEvent event) {
        int index = mainTable.getSelectionModel().getSelectedIndex();
        data.remove(index);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            data.add(FXCollections.observableHashMap());
        }

        mainTable.setEditable(true);
        mainTable.setItems(data);
        
        deleteRowsMenuItem
                .disableProperty()
                .bind(
                        Bindings.isEmpty(
                                mainTable.getSelectionModel().getSelectedItems()
                        )
                );
    }
}
