/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author manuel
 */
public class CSVTranslator {

    private final CSVPrinter printer;

    CSVTranslator(Appendable writer) throws IOException {
        this.printer = new CSVPrinter(writer, CSVFormat.DEFAULT);

    }

    void print(TableModel table) throws IOException {
        this.printer.printRecord(table.headers);
        this.printer.printRecords(table.data);
        this.printer.flush();
        this.printer.close();
    }

    TableModel parse(Reader reader) throws IOException {
        TableModel table  =  new TableModel();
        
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        
        csvParser.getHeaderMap().forEach((key, index) -> {
            table.headers.add(key);
        });
        
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        
        csvRecords.forEach((record) -> {
            List<String> list = new ArrayList<>();
            csvParser.getHeaderMap().forEach((key, index) -> {
                list.add(record.get(key));
            });
            table.data.add(list);
        });
        
        return table;
    }
}
