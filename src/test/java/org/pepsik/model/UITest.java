package org.pepsik.model;

import com.athaydes.automaton.FXApp;
import com.athaydes.automaton.FXer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pepsik.MainApp;

import static com.athaydes.automaton.assertion.AutomatonMatcher.hasText;
import static org.junit.Assert.assertThat;

public class UITest {

    @BeforeClass
    public static void launchApp() throws Exception {
        System.out.println( "Launching Java App" );

        FXApp.startApp( new MainApp() );

        System.out.println( "App has been launched" );

        // let the window open and show before running tests
        Thread.sleep( 2000 );
    }

    @AfterClass
    public static void cleanup() {
        System.out.println( "Cleaning up" );
        FXApp.doInFXThreadBlocking( () -> FXApp.getStage().close() );
    }

    @Test
    public void automatonTest() {
        System.out.println( "Running test" );
        FXer user = FXer.getUserWith();

        user.clickOn( TextField.class )
                .enterText( "" )
                .type( "Hello Automaton" );

        //noinspection unchecked
        assertThat( user.getAt( TextField.class ), hasText( "Hello Automaton" ) );
    }
}
