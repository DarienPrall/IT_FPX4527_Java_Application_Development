package edu.capella.bsit.drinkorderabstract;

public class Order {
    // Count of items in order
    private int itemNumber = 0;
    // Array of abstract class can hold any subclass
    private Beverage[] beverages = new Beverage[5];
    // Any class that implements PaymentMethod interface will work as payment
    private PaymentMethod payment;
    
    // Add a beverage to the order. Return value indicates if add() was successful
    public boolean add(Beverage beverage) {
        // Prevent adding more items than array can hold
        if(itemNumber < beverages.length) {
            beverages[itemNumber] = beverage;
            itemNumber++;
            return true;
        }
        else {
            return false;
        }
    }
    
    public double getTotal() {
        double totalPrice = 0;
        for(int count = 0; count < itemNumber; count++) {
            totalPrice += beverages[count].getPrice();
        }
        
        return totalPrice;
    }
    
    public int getItemCount() {
        return itemNumber;
    }
    
    public Beverage[] getItems() {
        return beverages;
    }
    
    // Need to be able to set payment since there is no parameterized constructor
    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }
    
    // Method to call processPayment() implemented by all payment methods
    public boolean payTotal(double amount) {
        return payment.processPayment(amount);
    }
}
