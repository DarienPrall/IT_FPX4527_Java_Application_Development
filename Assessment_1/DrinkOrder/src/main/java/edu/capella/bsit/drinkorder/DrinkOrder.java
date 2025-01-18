/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.capella.bsit.drinkorder;

/**
 *
 * @author prall
 */
public class DrinkOrder {

    public static void main(String[] args) {
        
        Beverage[] beverages = new Beverage[5];
        beverages[0] = new Beverage("Water", 8, "", 0.49);
        beverages[1] = new Coffee("Dark Roast", false, "Coffee", 20, "Cream", 3.29);
        beverages[2] = new Coffee("Light Roast", true, "Coffee", 16, "Sugar", 2.99);
        beverages[3] = new Tea("Black", false, "Tea", 8, "Lemon", 1.99);
        beverages[4] = new Tea("Green", true, "Tea", 16, "", 2.25);
        
        for (int i = 0; i < beverages.length; i++) {
            System.out.println(beverages[i].toString());
        }
        
        
        // TESTS
        //Beverage drink = new Beverage("Water", 8, "Lemon", 0.49);
        //Coffee coffee1 = new Coffee("Dark Roast", true, "Coffee", 20, "Cream", 2.95);
        //Coffee coffee2 = new Coffee("Light Roast", false, "Coffee", 16, "", 2.60);
        //Tea tea1 = new Tea("Black", false, "Tea", 16, "Milk", 2.69);
        
        //System.out.println(drink.toString());
        //System.out.println(coffee1.toString());
        //System.out.println(coffee2.toString());
        //System.out.println(tea1.toString());

    }
}
