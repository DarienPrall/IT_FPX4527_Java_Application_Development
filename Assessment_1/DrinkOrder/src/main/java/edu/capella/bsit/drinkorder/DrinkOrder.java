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
        Beverage drink = new Beverage("Water", 8, "Lemon", 0.49);
        Coffee coffee1 = new Coffee("Dark Roast", true, "Coffee", 20, "Cream", 2.95);
        Coffee coffee2 = new Coffee("Light Roast", false, "Coffee", 16, "", 2.60);
        
        System.out.println(drink.toString());
        System.out.println(coffee1.toString());
        System.out.println(coffee2.toString());

    }
}
