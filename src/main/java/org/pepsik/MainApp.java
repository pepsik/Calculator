package org.pepsik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pepsik.controller.CalculatorController;

import java.io.IOException;

import static javafx.scene.text.Font.loadFont;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Calculator.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);

        CalculatorController controller = loader.getController();
        controller.setStageAndSetupListeners(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
