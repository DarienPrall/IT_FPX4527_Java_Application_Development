/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.drinkorderabstract;

/**
 *
 * @author prall
 */
public class Cash implements PaymentMethod {
    private double amountTendered;
    
    public Cash(double amountTendered) {
        this.amountTendered = amountTendered;
    }
    
    @Override
    public boolean processPayment(double amount) {
        double changeDue = amountTendered - amount;
        System.out.println("Amount Tendered: $" + String.format("%.2f", amountTendered));
        System.out.println("Change Due: $" + String.format("%.2f", changeDue));
        return true;
    }
}
