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

public class ViewMainController {

    @FXML private StackPane contentArea;
    @FXML private Button btnNavUtenti;
    @FXML private Button btnNavLibri;
    @FXML private Button btnNavPrestiti;

    private Controller logicController;
    
    private Map<String, Parent> viewCache = new HashMap<>();
    private Map<String, Object> controllerCache = new HashMap<>();
    
    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        showViewUtenti(); // Default view
    }

    @FXML private void showViewUtenti() { 
        attivaBottone(btnNavUtenti); 
        loadView("ViewUtenti.fxml"); 
    }
    
    @FXML private void showViewLibri() { 
        attivaBottone(btnNavLibri); 
        loadView("ViewLibri.fxml"); 
    }
    
    @FXML private void showViewPrestiti() { 
        attivaBottone(btnNavPrestiti); 
        loadView("ViewPrestiti.fxml"); 
    }

    private void attivaBottone(Button bottoneAttivo) {
        btnNavUtenti.getStyleClass().remove("selected");
        btnNavLibri.getStyleClass().remove("selected");
        btnNavPrestiti.getStyleClass().remove("selected");
        bottoneAttivo.getStyleClass().add("selected");
    }

    private void loadView(String fxmlFileName) {
        try {
            Parent view = viewCache.get(fxmlFileName);
            Object fxmlController = controllerCache.get(fxmlFileName);
            
            // Nota il percorso /fxml/
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