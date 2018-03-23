/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author manuel
 */
public class SimpleCSVPrinter {
    
    private final CSVPrinter printer;
    private final TableModel table;
    

    SimpleCSVPrinter(TableModel table, Appendable writer) throws IOException {
        this.printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
        this.table = table;
    }

    void print() throws IOException {
        this.printer.printRecord(table.headers);
        this.printer.printRecords(table.data);
        this.printer.flush();
        this.printer.close();
    }
    
}
