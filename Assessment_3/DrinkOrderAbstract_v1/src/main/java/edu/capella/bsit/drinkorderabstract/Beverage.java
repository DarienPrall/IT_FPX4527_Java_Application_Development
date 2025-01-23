/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorderabstract;

/**
 *
 * @author prall
 */
public abstract class Beverage {
    private String beverageName;
    private int ounces;
    private String addIns;
    private double price;
    
    protected Beverage(String beverageName, int ounces, String addIns, double price) {
        this.beverageName = beverageName;
        this.ounces = ounces;
        this.addIns = addIns;
        this.price = price;
    }
    
    // Getters
    public String getBeverageName() {
        return beverageName;
    }
    
    public int getOunces() {
        return ounces;
    }
    
    public String getAddIns() {
        return addIns;
    }
    
    public double getPrice() {
        return price;
    }
    
    // Setters
    
    public void setBeverageName(String beverageName){
        this.beverageName = beverageName;
    }
    
    public void setOunces(int ounces) {
        this.ounces = ounces;
    }
    
    public void setAddIns(String addIns) {
        this.addIns = addIns;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return beverageName + " (" + ounces + " oz.)" + (addIns.isEmpty() ? "" : " with " + addIns) + " $" + String.format("%.2f", price);
    }

}
