/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorder;

/**
 *
 * @author prall
 */
public class Tea extends Beverage {
    private String teaType;
    private boolean iced;
    
    public Tea(String teaType, boolean iced, String beverageName, int ounces, String addIns, double price) {
        super(beverageName, ounces, addIns, price);
        this.teaType = teaType;
        this.iced = iced;
    }
    
    // Creating getter methods
    public String getTemperature() {
        return teaType;
    }
    
    public boolean isIced() {
        return iced;
    }
    
    // Creating setter methods
    public void setTemperature() {
        this.teaType = teaType;
    }
    
    public void setIced() {
        this.iced = iced;
    }
    
    @Override
    public String toString() {
        String icedString = iced ? "Iced " : "";
        String teaDescription = icedString + teaType;
        String beverageDescription = super.toString();
        return teaDescription + " " + beverageDescription;
    }
}
