/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manuel
 */
public class TableModel {
    List<String> headers;
    List<List<String>> data;

    public TableModel() {
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
    }
    
    
}
