package com.mycompany.jfx.cvs.viewer;

import com.sun.javafx.perf.PerformanceTracker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class FXMLController implements Initializable {

    private int columnCounter = 0;
    private Stage stage;
    ObservableList<ObservableMap<String, StringProperty>> data;
    
    @FXML
    private MenuItem deleteRowsMenuItem;

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
    
     @FXML
    private void saveAction(ActionEvent event) {
        System.out.println("hello world");
        showFileDialog(true).map((file) -> {
            BufferedWriter writer = null;
            try {
                writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return writer;
        }).ifPresent((writer) -> {
            try {
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
                data.forEach((row) -> {
                    try {
                        Set<String> keySet = row.keySet();
                        List<String> list = keySet.stream().map((key) -> {
                            return row.get(key).get();
                        }).collect(Collectors.toList());
                        csvPrinter.printRecord(list);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                csvPrinter.flush();
                csvPrinter.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private Optional<File> showFileDialog(boolean isSave){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);
        return Optional.ofNullable(file);
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

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
