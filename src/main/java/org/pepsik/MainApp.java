package org.pepsik;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        Set<Button> buttons = (Set) root.lookupAll((".button"));

        //add all button nodes to enum
        CalculatorButton.setButtons(buttons);
        InputNumber.setButtons(buttons);

        //get display label for font resizing
        display = (Label) root.lookup("#display");

        //listeners for resizing buttons font
        initResizeListeners(scene);

        //todo binding width to font size
//        double size = display.getWidth() / (display.getText().length()/3);
//        System.out.println(size);
//        if (size < 48) {
//            display.setFont(Font.font(size));
//        }

        CalculatorButton.MEMORY_RECALL.getButton().setDisable(true);

        primaryStage.show();

    }

    /**
     * Resize calculator display
     *
     * @param width  new display width
     * @param height new display height
     */
    private void resizeDisplayFont(double width, double height) {
        if (Double.compare(width, 270) > 0 && Double.compare(height, 450) > 0) {
            changeCssClass("display_small_font", "display_big_font");
        } else {
            changeCssClass("display_big_font", "display_small_font");
        }
    }

    /**
     * Changes cssStyle between old and new css class
     *
     * @param oldClass old css class
     * @param newCss   new css class
     */
    private void changeCssClass(String oldClass, String newCss) {
        ObservableList<String> cssList = display.getStyleClass();

        cssList.remove(oldClass);

        if (!cssList.contains(newCss)) {
            cssList.add(newCss);
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
