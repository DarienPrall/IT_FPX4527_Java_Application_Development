/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.capella.bsit.learnercollection;

import java.util.Comparator;

/**
 *
 * @author prall
 */
public class ComparatorByName implements Comparator<Learner> {
    @Override
    public int compare(Learner learner1, Learner learner2) {
        String lastName1 = learner1.getLastName();
        String lastName2 = learner2.getLastName();
        
        int lastNameComparison = lastName1.compareTo(lastName2);
        if (lastNameComparison == 0) {
            String firstName1 = learner1.getFirstName();
            String firstName2 = learner2.getFirstName();
            return firstName1.compareTo(firstName2);
        }
        return lastNameComparison;
    }
}
