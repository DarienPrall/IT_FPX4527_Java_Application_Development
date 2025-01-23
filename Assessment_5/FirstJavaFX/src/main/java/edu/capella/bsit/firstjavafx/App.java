package edu.capella.bsit.firstjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        /*
        ORIGINAL CODE
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
         */
        
        // CREATING THE APP TITLE
        stage.setTitle("JavaFX 2x2 Grid Example");
        
        // DEFINING A GRID
        GridPane grid = new GridPane();
        
        // ASSIGNING MY NAME
        Label myName = new Label("Darien Prall");
        
        // ADDING MY NAME TO THE TOP-LEFT
        grid.add(myName, 0, 0);
        
        // STORING IMAGE TO AN IMAGE CLASS VARIABLE
        Image rainbowTrout = new Image("Assessment_5_PNG_Image.PNG");
        
        // STORING THE IMAGE TO AN IMAGEVIEW
        ImageView myImageView = new ImageView(rainbowTrout);
        
        // DEFINING THE PROPERTIES OF THE IMAGE
        myImageView.setFitWidth(100);
        myImageView.setFitHeight(100);
        
        // ADDING THE IMAGE TO THE TOP-RIGHT OF THE GRID
        grid.add(myImageView, 1, 0);
        
        
       // CREATING SHAPES
       Circle myCircle = new Circle(50, Color.RED);
       Polygon myPolygon = new Polygon(0, 0, 50, 100, 100, 0);
       myPolygon.setFill(Color.BLUE);
       Rectangle myRectangle = new Rectangle(60, 40, Color.GREEN);
       
       // ADDING CREATED SHAPES TO THE BOTTOM-LEFT GRID
       grid.add(myCircle, 0, 1);
       grid.add(myPolygon, 0, 1);
       grid.add(myRectangle, 0, 1);
       
        
        // CREATING AND SHOWING THE APPLICATION PROPERTIES
        Scene scene = new Scene(grid, 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}