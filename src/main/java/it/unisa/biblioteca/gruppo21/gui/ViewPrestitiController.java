package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.*;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.DatePicker;

/**
 * @file ViewPrestitiController.java
 * @brief Controller delle operazioni di sportello.
 * @class ViewPrestitiController
 * Gestisce la registrazione dei prestiti e delle restituzioni.
 */
public class ViewPrestitiController extends AbstractViewController {
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private TextField txtIdentificativoUtente;
    @FXML private TextField txtIsbnPrestito;
    
    @FXML private DatePicker pickerScadenza;

    @FXML private TableView<Prestito> tablePrestiti;
    
    @FXML private TableColumn<Prestito, String> colMatricolaUtente;
    @FXML private TableColumn<Prestito, String> colNomeUtente;
    @FXML private TableColumn<Prestito, String> colCognomeUtente;
    
    @FXML private TableColumn<Prestito, String> colIsbnLibro;
    @FXML private TableColumn<Prestito, String> colTitoloLibro;
    
    @FXML private TableColumn<Prestito, String> colDataScadenza;
    @FXML private TableColumn<Prestito, String> colStato;

    /**
     * @brief Configura la tabella e la data di default.
     * @post Le colonne mostrano i dettagli di Utente e Libro.
     * @post Il campo data è impostato automaticamente .
     */
    @FXML
    public void initialize() {
        colMatricolaUtente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUtente().getMatricola()));
        colNomeUtente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUtente().getNome()));
        colCognomeUtente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUtente().getCognome()));
        
        colIsbnLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getCodiceISBN()));
        colTitoloLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitolo()));
        
        colDataScadenza.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDataRestituzione() != null) {
                return new SimpleStringProperty(cellData.getValue().getDataRestituzione().format(formatter));
            } else {
                return new SimpleStringProperty("-");
            }
        }
        );
        colStato.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStato().toString()));
        tablePrestiti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        pickerScadenza.setValue(LocalDate.now().plusDays(30));
    }
    
    /**
     * @brief Registra un prestito nel sistema.
     * @pre Il campo Utente (matricola o cognome) non deve essere vuoto.
     * @pre Il campo ISBN non deve essere vuoto.
     * @pre La data di restituzione deve essere valida.
     * @post Se successo: un nuovo prestito viene creato, le copie scalate e la tabella aggiornata.
     */
    @FXML private void handleEffettuaPrestito() {
        if (logicController != null) {
            
            boolean successo = logicController.gestisciPrestito(
                    txtIdentificativoUtente.getText(), 
                    txtIsbnPrestito.getText(),
                    pickerScadenza.getValue()
            );
            if(successo){
                txtIdentificativoUtente.clear();
                txtIsbnPrestito.clear();
                aggiornaTabella();
            }
            
        }
    }

    /**
     * @brief Registra la restituzione di un libro.
     * @pre Il campo Utente e il campo ISBN devono identificare un prestito attivo.
     * @post Se successo: lo stato del prestito diventa RESTITUITO e le copie aumentano.
     */
    @FXML private void handleRestituzione() {
        if (logicController != null) {
            boolean successo = logicController.gestisciRestituzione(
                    txtIdentificativoUtente.getText(), 
                    txtIsbnPrestito.getText()
            );
            
            if(successo){
                txtIdentificativoUtente.clear();
                txtIsbnPrestito.clear();
                aggiornaTabella();
            }
            
        }
    }

    /**
     * @brief Ricarica lo storico dei movimenti.
     */
    @Override
    protected void aggiornaTabella() {
        if (logicController != null){
            tablePrestiti.getItems().setAll(logicController.getListaPrestiti());
            tablePrestiti.refresh();
        }
    }
}