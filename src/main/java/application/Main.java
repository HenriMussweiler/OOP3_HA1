package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!"); // Set the stage title
        Label label = new Label("Hello World!"); // Create a label
        Scene scene = new Scene(label, 400, 200); // Create a scene
        primaryStage.setScene(scene); // Set the scene
        primaryStage.show(); // Display the stage
    }
}