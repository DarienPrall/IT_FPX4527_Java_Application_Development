package edu.capella.bsit.drinkmenufile;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class DrinkMenuFile {

/*
 * MAIN METHOD    
*/
    public static void main(String[] args) {
        String fileName = ("menu.data.txt");
        String menuData = getMenuData(fileName);
        File outputFile = new File("output.txt");
        writeMenuText(outputFile, menuData);
        printFileContents(outputFile);

    }
    
    public static String getMenuData(String fileName) {
        StringBuilder menuString = new StringBuilder();
        try {
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            if (parts.length == 3) {
                String name = parts[0];
                int size = Integer.parseInt(parts[1]);
                double price = Double.parseDouble(parts[2]);
                menuString.append(String.format("%-15s%-3d%-7s%-2s%-5.2f\n", name, size, "oz.", "$", price));;
            }
        }

        scanner.close();

    } catch (FileNotFoundException e) {
        System.out.println("Error: The file '" + fileName + "' was not found.");
    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
        return menuString.toString();
    }
    
    // Method to write the formatted menu to a text file. 
    public static void writeMenuText(File outputFile, String menuText) {
        try {
            if(!outputFile.exists()) {
                boolean fileCreated = outputFile.createNewFile();
                if (fileCreated) {
                    System.out.println("New file has been created: " + outputFile.getName());
                } else {
                    System.out.println("File already exists");
                }
            }
            PrintWriter writer = new PrintWriter(outputFile);
            writer.println(String.format("%-15s%-10s%-10s", "Beverage", "Size", "Price"));
            writer.println("___________________________________");
            writer.print(menuText);
            writer.close();
            System.out.println("Menu has successfully been written to: " + outputFile.getName());
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file " + outputFile.getName() + " counld not be found.");
        } catch (IOException e) {
            System.out.println("Error: An error occured while writing to the file");
        }
    }
    
    public static void printFileContents(File outputFile) {
        try {
            Scanner scanner = new Scanner(outputFile);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file " + outputFile + " could not be found");
        }
    }
}
