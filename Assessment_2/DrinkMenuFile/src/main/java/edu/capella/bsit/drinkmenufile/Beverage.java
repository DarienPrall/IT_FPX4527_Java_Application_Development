package edu.capella.bsit.drinkmenufile;

public class Beverage {
    private final String beverageName;
    private final int ounces;
    private final String addIns;
    private final double price;
    
    public Beverage(String name, int size, double price) {
        this.beverageName = name;
        this.ounces = size;
        this.addIns = "";
        this.price = price;
    }
    
    public Beverage(String name, int size, String addIns, double price) {
        this.beverageName = name;
        this.ounces = size;
        this.addIns = addIns;
        this.price = price;
    }
    
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
    
    public String getPriceAsString() {
        return String.format("$03.2", price);
    }
}
