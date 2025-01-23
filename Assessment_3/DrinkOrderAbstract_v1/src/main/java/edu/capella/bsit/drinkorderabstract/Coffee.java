/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorderabstract;

/**
 *
 * @author prall
 */ 
public class Coffee extends Beverage {
    private String roastType;
    private boolean decaf;
    
    public Coffee(String roastType, boolean decaf, int ounces, String addIns, double price) {
        super("coffee", ounces, addIns, price);
        this.roastType = roastType;
        this.decaf = decaf;
    }
    
    public Coffee(String roastType, boolean decaf, int ounces, double price) {
        super("coffee", ounces, "", price);
        this.roastType = roastType;
        this.decaf = decaf;
    }
    
    // Getters
    public String getRoastType() {
        return roastType;
    }
    
    public boolean isDecaf() {
        return decaf;
    }
    
    // Setters
    public void setRoastType(String roastType) {
        this.roastType = roastType;
    }
    
    public void setDecaf(boolean decaf) {
        this.decaf = decaf;
    }
   
    @Override
    public String toString() {
        String decafString = decaf ? "decaf " : "";
        String coffeeDescription = decafString + roastType;
        String beverageDescription = super.toString();
        return coffeeDescription + " " + beverageDescription;
    }
}
