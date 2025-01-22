package edu.capella.bsit.drinkorderabstract;

public class DrinkOrderAbstract {

    public static void main(String[] args) {
        Order drinkOrder = new Order();
        
        drinkOrder.add(new Coffee("dark roast", false, 20, "cream", 3.29));
        drinkOrder.add(new Coffee("light roast", true, 16, 2.99));
        drinkOrder.add(new Tea("black", false, 8, "lemon, sugar", 1.99));
        PaymentMethod cashPayment = new Cash(20.00);
        drinkOrder.setPayment(cashPayment);
        
        for(Beverage d : drinkOrder.getItems()) {
            if(d != null) {
                System.out.println(d);
            }
        }
        
        System.out.println("\nTotal: " + String.format("$%3.2f", 
                drinkOrder.getTotal()));
        boolean success = drinkOrder.payTotal(drinkOrder.getTotal());
        
        System.out.println();
        
        drinkOrder = new Order();
        drinkOrder.add(new Coffee("dark roast", false, 12, "cream", 2.25));
        drinkOrder.add(new Coffee("light roast", true, 16, 2.99));
        drinkOrder.add(new Tea("green", false, 20, "lemon, sugar", 2.50));
        PaymentMethod cardPayment = new CreditCard("123456789000");
        
        for(Beverage d : drinkOrder.getItems()) {
            if(d != null) {
                System.out.println(d);
            }
        }
        
        drinkOrder.setPayment(cardPayment);
        System.out.println("\nTotal: " + String.format("$%3.2f", drinkOrder.getTotal()));
        success = drinkOrder.payTotal(drinkOrder.getTotal());    
    }
}
