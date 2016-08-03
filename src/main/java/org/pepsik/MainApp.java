package org.pepsik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.pepsik.controller.CalculatorButton;

import java.util.Set;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Calculator.fxml"));
        primaryStage.setTitle("Calculator");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

//        primaryStage.initStyle(StageStyle.DECORATED);

        primaryStage.setMinHeight(365);
        primaryStage.setMinWidth(215);

        primaryStage.setHeight(365);
        primaryStage.setWidth(215);

        CalculatorButton.setButtons((Set) root.lookupAll((".button")));
        Label display = (Label) root.lookup("#display");

        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            CalculatorButton.resizeButtons(newSceneWidth.doubleValue(), scene.getHeight());

            if (Double.compare(newSceneWidth.doubleValue(), 270) > 0
                    && Double.compare(scene.getHeight(), 450) > 0) {
                display.setStyle("-fx-font: 48px arial;");
            } else {
                display.setStyle("-fx-font: 24px arial;");
            }
        });

        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            CalculatorButton.resizeButtons(scene.getWidth(), newSceneHeight.doubleValue());

            if (Double.compare(scene.getWidth(), 270) > 0
                    && Double.compare(newSceneHeight.doubleValue(), 450) > 0) {
                display.setStyle("-fx-font: 48px arial;");
            } else {
                display.setStyle("-fx-font: 24px arial;");
            }
        });

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
