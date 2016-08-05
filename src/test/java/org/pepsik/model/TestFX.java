package org.pepsik.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.pepsik.MainApp;

public class TestFX extends Application {

//    @Rule
//    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @BeforeClass
    public static void initJFX() throws InterruptedException {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            new MainApp().start(new Stage()); // Create and
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // initialize
                        // your app.

                    }
                });
            }
        });
        t.start();

        Thread.sleep(1000);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // noop
    }

    @Test
    public void test() throws InterruptedException {
        Button button = new Button("number_1");

        Event.fireEvent(button, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));

        Thread.sleep(1000);
        System.out.println(button.toString());

    }

}