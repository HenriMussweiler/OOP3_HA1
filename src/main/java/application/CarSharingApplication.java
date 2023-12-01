package application;

import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class CarSharingApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Car Sharing Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Testdaten erstellen
        //Platform.runLater(() -> mainController.createTestData());
    }

    public static void main(String[] args) {
        //Starte die Anwendung
        launch(args);
    }
}
