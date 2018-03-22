/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jfx.cvs.viewer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person{
    
    private StringProperty firstName;
    private StringProperty lastName;

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }
    
    public StringProperty firstNameProperty(){
        return firstName;
    }
    
    public StringProperty lastNameProperty(){
        return lastName;
    }
    
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}
