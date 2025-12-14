package it.unisa.biblioteca.gruppo21.gui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @file ViewMainController.java
 * @brief Controller del menu principale e della navigazione.
 * @class ViewMainController
 * Gestisce i bottoni laterali e carica le diverse schermate (Utenti, Libri, Prestiti)
 * nell'area centrale della finestra.
 */
public class ViewMainController {

    @FXML private StackPane contentArea;
    @FXML private Button btnNavUtenti;
    @FXML private Button btnNavLibri;
    @FXML private Button btnNavPrestiti;

    private Controller logicController;
    
    private Map<String, Parent> viewCache = new HashMap<>();
    private Map<String, Object> controllerCache = new HashMap<>();
    
    /**
     * @brief Imposta il controller logico e avvia la schermata iniziale.
     * @pre logicController != null.
     * @post Viene mostrata la schermata "Gestione Utenti" come default.
     * @param logicController Il controller logico dell'app.
     */
    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        showViewUtenti();
    }

    /**
     * @brief Mostra la schermata Utenti.
     * @post Il bottone "Area Utenti" si illumina.
     * @post La vista Utenti appare al centro.
     */
    @FXML private void showViewUtenti() { 
        attivaBottone(btnNavUtenti); 
        loadView("ViewUtenti.fxml"); 
    }
    
    /**
     * @brief Mostra la schermata Libri.
     * @post La vista Libri appare al centro.
     */
    @FXML private void showViewLibri() { 
        attivaBottone(btnNavLibri); 
        loadView("ViewLibri.fxml"); 
    }
    
    /**
     * @brief Mostra la schermata Prestiti.
     * @post La vista Prestiti appare al centro.
     */
    @FXML private void showViewPrestiti() { 
        attivaBottone(btnNavPrestiti); 
        loadView("ViewPrestiti.fxml"); 
    }

    /**
     * @brief Cambia colore al bottone selezionato nel menu.
     * @param bottoneAttivo Il bottone appena cliccato.
     */
    private void attivaBottone(Button bottoneAttivo) {
        btnNavUtenti.getStyleClass().remove("selected");
        btnNavLibri.getStyleClass().remove("selected");
        btnNavPrestiti.getStyleClass().remove("selected");
        bottoneAttivo.getStyleClass().add("selected");
    }

    /**
     * @brief Carica un file FXML e lo mette al centro della finestra.
     * @pre Il file specificato deve esistere nella cartella resources.
     * @post La nuova schermata è visibile.
     * @param fxmlFileName Nome del file.
     */
    private void loadView(String fxmlFileName) {
        try {
            Parent view = viewCache.get(fxmlFileName);
            Object fxmlController = controllerCache.get(fxmlFileName);
            
            if (view == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFileName));
                view = loader.load();
                // Collega i sotto-controller
                fxmlController = loader.getController();
                
                //Salvo per il fuuturo
                viewCache.put(fxmlFileName, view);
                fxmlController = loader.getController();
            }
            
            if (fxmlController instanceof ViewUtentiController) {
                ((ViewUtentiController) fxmlController).setLogicController(this.logicController);
            } else if (fxmlController instanceof ViewLibriController) {
                ((ViewLibriController) fxmlController).setLogicController(this.logicController);
            } else if (fxmlController instanceof ViewPrestitiController) {
                ((ViewPrestitiController) fxmlController).setLogicController(this.logicController);
            }

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            FadeTransition ft = new FadeTransition(Duration.millis(300), view);
            ft.setFromValue(0.0); ft.setToValue(1.0); ft.play();

        } catch (IOException e) {
            System.err.println("Errore caricamento vista: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}