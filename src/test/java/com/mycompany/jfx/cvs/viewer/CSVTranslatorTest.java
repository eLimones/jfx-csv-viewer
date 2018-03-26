/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manuel
 */
public class CSVTranslatorTest {
    
    public CSVTranslatorTest() {
    }

    @Test
    public void parsesTableToCSV() throws IOException {
        TableModel table = new TableModel();
        table.headers.addAll(Arrays.asList("header0", "header1"));
        table.data.add(Arrays.asList("a", "0"));
        table.data.add(Arrays.asList("b", "1"));
        
        CharArrayWriter writer = new CharArrayWriter();
        
        CSVTranslator  printer = new CSVTranslator(writer);
        printer.print(table);
        
        assertArrayEquals("header0,header1\r\na,0\r\nb,1\r\n".toCharArray(), writer.toCharArray());
    }
    
    @Test
    public void parsesCSVToTable() throws IOException {
        String csvString = "header0,header1\r\na,0\r\nb,1\r\n";
        
        CharArrayWriter writer = new CharArrayWriter();
        CSVTranslator  printer = new CSVTranslator(writer);
        
        CharArrayReader reader = new CharArrayReader(csvString.toCharArray());
        TableModel table = printer.parse(reader);
        
        assertEquals(Arrays.asList("header0", "header1"), table.headers);
        assertEquals(Arrays.asList("a", "0"), table.data.get(0));
        assertEquals(Arrays.asList("b", "1"),table.data.get(1));
    }
    
}
