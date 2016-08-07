package org.pepsik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.pepsik.controller.button.CalculatorButton;

import java.io.IOException;
import java.util.Set;

public class MainApp extends Application {

    private static final int MIN_HEIGHT = 365;
    private static final int MIN_WIDTH = 215;
    private static final int PREF_HEIGHT = 365;
    private static final int PREF_WIDTH = 215;


    /**
     * Calculator display for resize
     */
    private Label display;

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Calculator.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Calculator");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);

        primaryStage.setHeight(PREF_HEIGHT);
        primaryStage.setWidth(PREF_WIDTH);

        //add all button nodes to enum
        CalculatorButton.setButtons((Set) root.lookupAll((".button"))); //generific

        //get display label for font resizing
        display = (Label) root.lookup("#display");

        //listeners for resizing buttons font
        initResizeListeners(scene);

        primaryStage.show();

    }

    /**
     * Resize calculator display
     *
     * @param width  new display width
     * @param height new display height
     */
    private void resizeDisplayFont(double width, double height) {
        if (Double.compare(width, 270) > 0
                && Double.compare(height, 450) > 0) {
            display.setStyle("-fx-font: 48px arial;");
        } else {
            display.setStyle("-fx-font: 24px arial;");
        }
    }

    /**
     * Resize buttons font
     * @param scene current scene
     */
    private void initResizeListeners(Scene scene) {
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            CalculatorButton.resizeButtons(newSceneWidth.doubleValue(), scene.getHeight());

            resizeDisplayFont(newSceneWidth.doubleValue(), scene.getHeight());
        });

        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            CalculatorButton.resizeButtons(scene.getWidth(), newSceneHeight.doubleValue());

            resizeDisplayFont(scene.getWidth(), newSceneHeight.doubleValue());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
