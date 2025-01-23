package edu.capella.bsit.drinkorderabstract;

public class DrinkOrder {

    public static void main(String[] args) {
        // ORDER 1
        Order drinkOrder = new Order();
        // ORDER 1 BEVERAGES: 3 PRE-ADDED. 2 OF MY OWN
        // 3 PRE-ADDED DRINK ORDERS
        drinkOrder.add(new Coffee("dark roast", false, 20, "cream", 3.29));
        drinkOrder.add(new Coffee("light roast", true, 16, 2.99));
        drinkOrder.add(new Tea("black", false, 8, "lemon, sugar", 1.99));
        // 2 OF MY OWM
        drinkOrder.add(new Tea("green", false, 16, 1.89));
        drinkOrder.add(new Coffee("light roast", true, 8, "cream, sugar", 1.29));
        // ORDER 1 PAYMENT METHOD WILL BE CASH FOR ORDER 1
        // TENDER AMOUNT
        PaymentMethod cashPayment = new Cash(20.00);
        // setPayment needs a Payment Method, in this case cashPayment (Cash is an extension of PaymentMethod)
        drinkOrder.setPayment(cashPayment);
        // PRINT OUT EACH BEVERAGE IN THE DRINK ORDER
        System.out.println("Order: ");
        for(int i = 0; i < drinkOrder.getItemCount(); i++) {
           System.out.println(drinkOrder.getItems()[i]);
        }
        // PRINT OUT THE TOTAL AND PAYMENT STATUS
        System.out.println("\nTotal: " + String.format("$%3.2f", 
                drinkOrder.getTotal()));
        System.out.println(cashPayment.processPayment(drinkOrder.getTotal()) + "\n");
        
        //ORDER 2
        drinkOrder = new Order();
        drinkOrder.add(new Coffee("dark roast", false, 12, "cream", 2.25));
        drinkOrder.add(new Coffee("light roast", true, 16, 2.99));
        drinkOrder.add(new Tea("green", false, 20, "lemon, sugar", 2.50));
        drinkOrder.add(new Tea("black", false, 16, "2 sugars, milk", 2.29));
        drinkOrder.add(new Coffee("dark roast", false, 8, "cream, sugar", 2.89));
        PaymentMethod cardPayment = new CreditCard("123456789000");
        
        System.out.println("Order: ");
        for (int i = 0; i < drinkOrder.getItemCount(); i++) {
            System.out.println(drinkOrder.getItems()[i]);
        }
        
        drinkOrder.setPayment(cardPayment);
        System.out.println("\nTotal: " + String.format("$%3.2f", drinkOrder.getTotal()));
        boolean success = drinkOrder.payTotal(drinkOrder.getTotal());
    }
}
