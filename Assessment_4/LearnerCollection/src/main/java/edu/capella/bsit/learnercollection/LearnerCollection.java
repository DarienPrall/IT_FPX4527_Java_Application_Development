/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.capella.bsit.learnercollection;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author prall
 */
public class LearnerCollection {

    public static void main(String[] args) {
        // TESTING LEARNER CREATION
        // Learner darienPrall = new Learner("2772550", "Prall", "Darien");
        // System.out.println(darienPrall.toString());
        
        ArrayList<Learner> learners = new ArrayList<>();
        learners.add(new Learner("DM1991", "Matthews", "Dave"));
        learners.add(new Learner("CB1991", "Beaford", "Carter"));
        learners.add(new Learner("SL1991", "Lessard", "Stefan"));
        learners.add(new Learner("TR2008", "Reynolds", "Tim"));
        learners.add(new Learner("LM1991", "Moore", "LeRoi"));
        
        Collections.sort(learners);
        System.out.println("Sorted by ID:");
        learners.forEach(learner -> System.out.printf("%s, %-9s\t(%s)%n", learner.getLastName(), learner.getFirstName(), learner.getNameID()));
        System.out.println();
        Collections.sort(learners, new ComparatorByName());
        System.out.println("Sorted by Name: ");
        learners.forEach(learner -> System.out.printf("%s, %-9s\t(%s)%n", learner.getLastName(), learner.getFirstName(), learner.getNameID()));
   
        
    }
    
    
}
