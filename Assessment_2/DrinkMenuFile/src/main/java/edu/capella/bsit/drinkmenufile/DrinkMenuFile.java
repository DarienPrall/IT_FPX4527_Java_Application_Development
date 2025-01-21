package edu.capella.bsit.drinkmenufile;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class DrinkMenuFile {

/*
 *-----------------------------MAIN METHOD--------------------------------------
 */
    public static void main(String[] args) {
        // The filename needs to be assigned to a variable
        String fileName = ("menu.data.txt");
        
        // The menu data generated from the getMenuData method needs to be assigned to a variable
        // Method 1 Execution
        String menuData = getMenuData(fileName);
        
        // The name of the output file needs to be created and assigned to a variable
        File outputFile = new File("output.txt");
        
        // writeMenuText method will create the output file in the file directory with the created name
        //Method 2 2xecution
        writeMenuText(outputFile, menuData);
        
        // With getMenuData nad writeMenuText methods both called, we can pass in the outputfile to print the contents to the terminal
        printFileContents(outputFile);

    }

/*
 *-----------------------------GETMENUDATA METHOD-------------------------------
 */
    public static String getMenuData(String fileName) {
        // The file conents needs a place to be stored, hence the StringBuilder which is an array
        StringBuilder menuString = new StringBuilder();
        
        // We want to ensure there is a valid file to read, if not, the catch statements will handle the error exception
        try {
        // The scanner takes the menu.data file and loops through each line of the file 
        Scanner scanner = new Scanner(new File(fileName));
        
        // We want to loop through the file until all lines have been reviewed (.hasNextLine())
        while (scanner.hasNextLine()) {
            
        // The current line we are on is stored to a variable
            String line = scanner.nextLine();
            
        // With each line, it splits the contents be each space (" ") delimiter
            String[] parts = line.split(" ");
            
        // We need to check to make sure 3 items were stored to the parts array, if there are, we can assigned each part by accessing its parts index
            if (parts.length == 3) {
                
                // We know the first item will be the beverage name
                String name = parts[0];
                
                // We know the second item will be the size (ounces)
                int size = Integer.parseInt(parts[1]);
                
                // We know the third is the price of the beverage
                double price = Double.parseDouble(parts[2]);
                
                // However, we need to format each line properly for readability
                // String.format allows us to identify the data type and how many spaces to allocate for it (%-15s means to allocate 15 spaces for a string)
                // We can then include the oz and $ be passing them in as parameter variables
                menuString.append(String.format("%-15s%-3d%-7s%-2s%-5.2f\n", name, size, "oz.", "$", price));;
            }
        }
        // When we use the scanner, we have to close it
        scanner.close();
     
    // Here is out error handling, for instance, if the fileName menu.data.txt does not exist, it will throw an error    
    } catch (FileNotFoundException e) {
        
        System.out.println("Error: There is no file named '" + fileName + "'.");
    } catch (Exception e) {
        System.out.println("There was an error: " + e.getMessage());
    }
        // With the menuString containing the formated content, we can now return it
        return menuString.toString();
    }
    
/*
 * -----------------------------WRITEMENUTEXT METHOD----------------------------
 */
    // This method takes the outputFile and the contents of the menuText
    public static void writeMenuText(File outputFile, String menuText) {
        try {
            // We want to ensure that if the file doesn't exist, we are creating the file
            if(!outputFile.exists()) {
                boolean fileCreated = outputFile.createNewFile();
                if (fileCreated) {
                    System.out.println("New file has been created: " + outputFile.getName());
            //Otherwise, let the user know the file already exists
                } else {
                    System.out.println("File already exists");
                }
            }
            
            // PrintWriter takes the contents of the output file (currently empty)
            PrintWriter writer = new PrintWriter(outputFile);
            
            // We need to generate the headers for readability
            //HEADER START
            writer.println(String.format("%-15s%-10s%-10s", "Beverage", "Size", "Price"));
            writer.println("___________________________________");
            //HEADER END
            
            // Now we can apply the menuContent, remembering it has already be formatted so we can just print it
            writer.print(menuText);
            
            // We can close the writer as we no longer need it.
            writer.close();
            
            // We create an output to the user so they know the operation was successfull and what the file name is
            System.out.println("Menu has successfully been written to: " + outputFile.getName());
            
        // Here we define the catch statements to handle error exceptions in the event the file does not exist
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file " + outputFile.getName() + " counld not be found.");
        } catch (IOException e) {
            System.out.println("Error: An error occured while writing to the file");
        }
    }

    /*
     *-----------------------------PRINTFILECONTENTS METHOD---------------------
     */
    
    // With the ouputFile now containing the header from writeMenu text and the contents from getMenuData, we can print the contents of the output file
    public static void printFileContents(File outputFile) {
        try {
            // We handle this the same way with scanner
            Scanner scanner = new Scanner(outputFile);
            
            // Loop through each line and print it
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file " + outputFile + " could not be found");
        }
    }
}
