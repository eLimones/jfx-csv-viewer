/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @author manuel
 */
public class TablePresenter {

    static TableModel toModel(List<String> keys, ObservableList<ObservableMap<String, StringProperty>> data) {
        TableModel table = new TableModel();
        table.headers.addAll(keys);

        data.forEach((row) -> {
            List<String> rowList = new ArrayList<>();
            keys.forEach((key) -> {
                rowList.add(row.get(key).get());
            });
            table.data.add(rowList);
        });

        return table;
    }

    static ObservableList<ObservableMap<String, StringProperty>> toBoundList(TableModel table) {
        ObservableList<ObservableMap<String, StringProperty>> boundList = FXCollections.observableArrayList();

        table.data.forEach((tableRow) -> {
            ObservableMap<String, StringProperty> rowMap = FXCollections.observableHashMap();
            for (int i = 0; i < table.headers.size(); i++) {
                rowMap.put(table.headers.get(i),
                        new SimpleStringProperty(tableRow.get(i)));
            }
            boundList.add(rowMap);
            
        });
        
        return boundList;
    }
}
