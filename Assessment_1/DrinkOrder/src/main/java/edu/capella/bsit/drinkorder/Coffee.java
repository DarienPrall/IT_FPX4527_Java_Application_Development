/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorder;

/**
 *
 * @author prall
 */
public class Coffee extends Beverage {
    private String roastType;
    private boolean decaf;
    
    public Coffee(String roastType, boolean decaf, String beverageName, int ounces, String addIns, double price) {
        super(beverageName, ounces, addIns, price);
        this.roastType = roastType;
        this.decaf = decaf;
    }
    
    // Creating getters
    public String getRoastType() {
        return roastType;
    }
    
    public boolean isDecaf() {
        return decaf;
    }
    
    // Creating setters
    public void setRoastType(String roastType) {
        this.roastType = roastType;
    }
    
    public void setDecaf(boolean decaf) {
        this.decaf = decaf;
    }
    
    // Creating overrides
    @Override
    public String toString() {
        String decafString = decaf ? "Decaf " : "";
        String coffeeDescription = decafString + roastType;
        String beverageDescription = super.toString();
        return coffeeDescription + " " + beverageDescription;
    }
}
