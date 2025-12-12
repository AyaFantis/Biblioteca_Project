package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Prestito;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.format.DateTimeFormatter;

public class ViewPrestitiController {

    @FXML private TextField txtMatricolaPrestito;
    @FXML private TextField txtIsbnPrestito;

    @FXML private TableView<Prestito> tablePrestiti;
    @FXML private TableColumn<Prestito, String> colMatricolaUtente;
    @FXML private TableColumn<Prestito, String> colIsbnLibro;
    @FXML private TableColumn<Prestito, String> colDataScadenza;
    @FXML private TableColumn<Prestito, String> colStato;

    private Controller logicController;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        colMatricolaUtente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUtente().getMatricola()));
        colIsbnLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getCodiceISBN()));
        colDataScadenza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataRestituzione().format(formatter)));
        colStato.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStato().toString()));
    }

    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        aggiornaTabella();
    }

    @FXML private void handleEffettuaPrestito() {
        if (logicController != null) {
            boolean successo = logicController.gestisciPrestito(
                    txtMatricolaPrestito.getText(), 
                    txtIsbnPrestito.getText()
            );
            if(successo){
                txtMatricolaPrestito.clear();
                txtIsbnPrestito.clear();
                aggiornaTabella();
            }
            
        }
    }

    @FXML private void handleRestituzione() {
        if (logicController != null) {
            logicController.gestisciRestituzione(txtMatricolaPrestito.getText(), txtIsbnPrestito.getText());
            aggiornaTabella();
        }
    }

    private void aggiornaTabella() {
        if (logicController != null) tablePrestiti.getItems().setAll(logicController.getListaPrestiti());
    }
}