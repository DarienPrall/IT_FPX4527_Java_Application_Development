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
        Beverage drink = new Beverage("water", 8, "lemon", 0.49);
        
        System.out.println(drink.toString());
    }
}
