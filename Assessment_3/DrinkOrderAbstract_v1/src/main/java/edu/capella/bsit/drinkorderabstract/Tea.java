/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorderabstract;

/**
 *
 * @author prall
 */
public class Tea extends Beverage {
    private String teaType;
    private boolean iced;
    
    // Tea with AddIns
    public Tea(String teaType, boolean iced, int ounces, String addIns, double price) {
        super("tea", ounces, addIns, price);
        this.teaType = teaType;
        this.iced = iced;
    }
    
    // Tea without Addins
    public Tea(String teaType, boolean iced, int ounces, double price) {
        super("tea", ounces, "", price);
        this.teaType = teaType;
        this.iced = iced;
    }
    
    // Getter
    public String getTeaType() {
        return teaType;
    }
    
    public boolean getIced() {
        return iced;
    }
    
    // Setter
    
   public void setTeaType(String teaType) {
       this.teaType = teaType;
   }
   
   public void setIced(boolean iced) {
       this.iced = iced;
   }
   
   @Override
    public String toString() {
        String icedString = iced ? "iced " : "";
        return icedString + teaType + " Tea " + super.toString();
    }

}
