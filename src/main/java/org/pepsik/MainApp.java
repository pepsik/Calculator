package org.pepsik;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.button.InputNumber;

import java.io.IOException;
import java.util.Set;

public class MainApp extends Application {

    private static final int MIN_HEIGHT = 365;
    private static final int MIN_WIDTH = 215;
    private static final int PREF_HEIGHT = 365;
    private static final int PREF_WIDTH = 215;
    public static final int MAX_WIDTH = 550;


    /**
     * Calculator display for resize
     */
    private Label display;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Calculator.fxml"));
        primaryStage.setTitle("Calculator");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);

        primaryStage.setHeight(PREF_HEIGHT);
        primaryStage.setWidth(PREF_WIDTH);

        primaryStage.setMaxWidth(MAX_WIDTH);

        //get and set all node buttons to enums
        Set<Button> buttons = (Set) root.lookupAll((".button"));
        CalculatorButton.setButtons(buttons);
        InputNumber.setButtons(buttons);

        //Listeners for resizing buttons
        initResizeListeners(scene);

        //get display label for font resizing
        display = (Label) root.lookup("#display");

        //Display font resize listener
        display.textProperty().addListener((observable, oldValue, newValue) -> {
            resizeDisplay();
        });

        //disable memory recall and clear after init
        CalculatorButton.MEMORY_CLEAR.getButton().setDisable(true);
        CalculatorButton.MEMORY_RECALL.getButton().setDisable(true);

        primaryStage.show();
    }

    private void installEventHandler(final Node keyNode) {

    }


    /**
     * Resize display font size
     */
    private void resizeDisplay() {
        double d = display.getScene().getWidth() / display.getText().length() * 1.5;
        if (Double.compare(display.getScene().getWidth(), 270) > 0 && Double.compare(display.getScene().getHeight(), 450) > 0) {
            //48px max display font size if width > 270 and height > 450
            if (d > 48) {
                display.setFont(Font.font(48));
            } else {
                display.setFont(Font.font(d));
            }
        } else {
            //if size less then max font size is 32
            if (d > 32) {
                display.setFont(Font.font(32));
            } else {
                display.setFont(Font.font(d));
            }
        }
    }

    /**
     * Resize buttons font
     *
     * @param scene current scene
     */

    private void initResizeListeners(Scene scene) {
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            CalculatorButton.resizeButtons(newSceneWidth.doubleValue(), scene.getHeight());
            resizeDisplay();

        });

        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            CalculatorButton.resizeButtons(scene.getWidth(), newSceneHeight.doubleValue());
            resizeDisplay();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
