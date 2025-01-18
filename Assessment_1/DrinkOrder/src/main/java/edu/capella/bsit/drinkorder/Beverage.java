/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorder;

/**
 *
 * @author prall
 */
public class Beverage {
    // Defining Variables
    private String beverageName;
    private int ounces;
    private String addIns;
    private double price;
    
    // Creating Parameterized Constuctor
    public Beverage(String beverageName, int ounces, String addIns, double price) {
        this.beverageName = beverageName;
        this.ounces = ounces;
        this.addIns = addIns;
        this.price = price;
    }
    
    // Creating getter methods
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
    
    // Creating setter methods
    public void setBeverageName(String beverageName) {
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
    
    // Creating the override
    @Override
    public String toString() {
      if (addIns.isEmpty()) {
          return beverageName + " (" + ounces + " oz.) $" + String.format("%.2f", price);
      } else {
          return beverageName + " (" + ounces + " oz.) with " + addIns + " $" + String.format("%.2f", price);
      }
    }
}