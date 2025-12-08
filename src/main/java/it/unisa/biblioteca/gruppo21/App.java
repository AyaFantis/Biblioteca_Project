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
