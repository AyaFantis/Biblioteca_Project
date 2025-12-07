package it.unisa.biblioteca.gruppo21;

import it.unisa.biblioteca.gruppo21.gui.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Hello world!
 */
public class App extends Application {
 @Override
    public void start(Stage primaryStage) {
        
        ViewLibreria root = new ViewLibreria();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
