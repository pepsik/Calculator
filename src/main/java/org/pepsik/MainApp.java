package org.pepsik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.button.KeyboardShortcut;

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

        //Init max, min, pref sizes
        setUpApplicationSizes(primaryStage);

        //Setup all node buttons to enums
        setUpButtons(scene);

        //Init listeners for shortcut keys
        initKeyboardShortcutListeners(scene);

        //Init listeners for resizing buttons
        initResizeListeners(scene);

        //disable memory recall and clear buttons
        CalculatorButton.MEMORY_CLEAR.getButton().setDisable(true);
        CalculatorButton.MEMORY_RECALL.getButton().setDisable(true);

        primaryStage.show();
    }

    private void setUpApplicationSizes(Stage primaryStage){
        //Min size
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);

        //Preference size
        primaryStage.setHeight(PREF_HEIGHT);
        primaryStage.setWidth(PREF_WIDTH);

        //Max size
        primaryStage.setMaxWidth(MAX_WIDTH);
    }

    /**
     * Initializes Keyboard shortcuts
     * @param scene current scene
     */
    private void initKeyboardShortcutListeners(Scene scene) {
        scene.setOnKeyPressed(KeyboardShortcut::findAncExecuteKey);
    }

    /**
     * Set Javafx node buttons to enum and verify it
     * @param scene current scene
     */
    private void setUpButtons(Scene scene){
        Set<Button> buttons = (Set) scene.getRoot().lookupAll((".button"));
        CalculatorButton.setButtons(buttons);
    }

    /**
     * Resize display font size
     */
    private void resizeDisplay() {
        int maxDisplayFont = 48;//crotch
        int minDisplayFont = 32;
        Scene scene = display.getScene();

        double d = display.getScene().getWidth() / display.getText().length() * 1.6; // multiplier to fill display
        if (Double.compare(scene.getWidth(), 270) > 0 && Double.compare(scene.getHeight(), 450) > 0) {
            //48px max display font size if width > 270 and height > 450
            if (d > maxDisplayFont) {
                display.setFont(Font.font(maxDisplayFont));
            } else {
                display.setFont(Font.font(d));
            }
        } else {
            //if size less then max font size is 32
            if (d > minDisplayFont) {
                display.setFont(Font.font(minDisplayFont));
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
        //get display label for font resizing
        display = (Label) scene.getRoot().lookup("#display");

        //Display font resize listener
        display.textProperty().addListener((observable, oldValue, newValue) -> resizeDisplay());

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
