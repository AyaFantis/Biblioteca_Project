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

    @Override
    protected void aggiornaTabella() {
        if (logicController != null){
            tablePrestiti.getItems().setAll(logicController.getListaPrestiti());
            tablePrestiti.refresh();
        }
    }
}