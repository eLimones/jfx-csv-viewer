/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manuel
 */
public class SimpleCSVPrinterTest {
    
    public SimpleCSVPrinterTest() {
    }

    @Test
    public void testSomeMethod() throws IOException {
        TableModel table = new TableModel();
        table.headers.addAll(Arrays.asList("header0", "header1"));
        table.data.add(Arrays.asList("a", "0"));
        table.data.add(Arrays.asList("b", "1"));
        
        CharArrayWriter writer = new CharArrayWriter();
        
        SimpleCSVPrinter  printer = new SimpleCSVPrinter(table, writer);
        printer.print();
        
        assertArrayEquals("header0,header1\r\na,0\r\nb,1\r\n".toCharArray(), writer.toCharArray());
    }
    
}
