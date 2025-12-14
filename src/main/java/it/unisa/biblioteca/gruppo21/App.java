package it.unisa.biblioteca.gruppo21;

import it.unisa.biblioteca.gruppo21.gui.*;
import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.service.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * @file App.java
 * @brief Punto di ingresso (Entry Point) dell'applicazione JavaFX.
 * @class App
 * Il suo compito è istanziare i componenti principali dell'architettura MVC/MVP
 * (Model, View, Controller), collegarli tra loro, e avviare 
 * il ciclo di vita dell'interfaccia grafica.
 * @author Gruppo 21
 * @version 1.0
 */
public class App extends Application {
    
    /**
     * @brief Metodo di avvio del ciclo di vita JavaFX.
     * Viene chiamato automaticamente dal runtime JavaFX dopo il metodo main.
     * Esegue la configurazione iniziale, mostrandola sullo Stage primario.
     * @param primaryStage la finestra principale fornita da JavaFX.
     */
 @Override
    public void start(Stage primaryStage) {
        try {
            
            Biblioteca bibliotecaManager = new Biblioteca();
            Controller logicController = new Controller(bibliotecaManager);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewMain.fxml"));
            Parent root = loader.load();

            ViewMainController mainGuiController = loader.getController();

            mainGuiController.setLogicController(logicController);

            Scene scene = new Scene(root);
            primaryStage.setTitle("GUI Test - Biblioteca UniSA");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("ERRORE: Impossibile caricare l'interfaccia grafica.");
            System.err.println("Controlla che i file FXML siano nella cartella 'src/main/resources/fxml/'");
        }
    }
    
    /**
     * @brief Metodo Main standard di Java.
     * Punto di ingresso assoluto del programma.
     * Delegare l'esecuzione al launcher di JavaFX chiamando launch()}.
     * @param args Argomenti da riga di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
