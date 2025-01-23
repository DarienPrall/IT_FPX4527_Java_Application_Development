/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.learnercollection;

/**
 *
 * @author prall
 */
public class Learner implements Comparable<Learner> {
    private String nameID, lastName, firstName;
    
    public Learner(String nameID, String lastName, String firstName) {
        this.nameID = nameID;
        this.lastName = lastName;
        this.firstName = firstName;
    }
    
    // Getters
    public String getNameID() {
        return nameID;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    // Setters
    
    public void setNameID(String nameID) {
        this.nameID = nameID;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return lastName + ", " + firstName + "     " + nameID;
    }
    
    @Override
    public int compareTo(Learner other) {
        return this.nameID.compareTo(other.nameID);
    }
  
}
