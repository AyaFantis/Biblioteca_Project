package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ViewUtentiController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    
    @FXML private TextField txtMatricola;
    @FXML private TextField txtNome;
    @FXML private TextField txtCognome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSearch;
    
    @FXML private Button btnInserisci;
    @FXML private Button btnModifica;

    @FXML private TableView<Utente> tableUtenti;
    @FXML private TableColumn<Utente, String> colMatricola;
    @FXML private TableColumn<Utente, String> colNome;
    @FXML private TableColumn<Utente, String> colCognome;
    @FXML private TableColumn<Utente, String> colEmail;
    
    @FXML private TableColumn<Utente, String> colLibri;
    

    private Controller logicController;
    
    private List<Utente> listaCompletaUtenti = new ArrayList<>();
    private List<Libro> listaCompletaLibri = new ArrayList<>();

    @FXML
    public void initialize() {
        colMatricola.setCellValueFactory(new PropertyValueFactory<>("matricola"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLibri.setCellValueFactory(cellData -> {
            Utente u = cellData.getValue();
            List<Prestito> prestitiPersonali = u.getPrestitiAttivi();
            
            if (prestitiPersonali == null || prestitiPersonali.isEmpty()) {
                return new SimpleStringProperty("Nessun prestito");
            }
            
            StringBuilder testo = new StringBuilder();
            
            for (Prestito p : prestitiPersonali){
                String isbn = p.getLibro().getCodiceISBN();
                String titolo = trovaTitoloDaISBN(isbn);
                
                testo.append(titolo).append(" (");
                
                if (p.getDataRestituzione() != null) {
                    testo.append(p.getDataRestituzione().format(formatter));
                }
                testo.append(")\n");
            }
            return new SimpleStringProperty(testo.toString());
        });
        tableUtenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * @brief Metodo semplice per trovare il titolo di un libro dato l'ISBN.
     * Scorre la lista dei libri caricata in memoria. Molto intuitivo per uno studente.
     */
    private String trovaTitoloDaISBN(String isbn) {
        if (listaCompletaLibri == null) return "Libro sconosciuto";
        
        for (Libro l : listaCompletaLibri) {
            if (l.getCodiceISBN().equals(isbn)) {
                return l.getTitolo();
            }
        }
        return "ISBN: " + isbn; // Se non lo trovo, stampo almeno l'ISBN
    }
    
    /**
     * @brief Gestisce il click del mouse sulla tabella.
     * Recupera l'elemento selezionato e riempie i campi di testo.
     * @param event L'evento del mouse.
     */
    @FXML
    private void handleSelezioneTabella(MouseEvent event) {
        
        Utente u = tableUtenti.getSelectionModel().getSelectedItem();
        
        if (u != null) {
            txtMatricola.setText(u.getMatricola());
            txtNome.setText(u.getNome());
            txtCognome.setText(u.getCognome());
            txtEmail.setText(u.getEmail());
            
            txtMatricola.setDisable(true);
            btnInserisci.setDisable(true);
            btnModifica.setDisable(false);
        }
    }

    @FXML
    private void handlePulisciCampi() {
        txtMatricola.clear();
        txtNome.clear();
        txtCognome.clear();
        txtEmail.clear();
        
        
        txtMatricola.setDisable(false);
        btnInserisci.setDisable(false);
        btnModifica.setDisable(true);
        
        tableUtenti.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleRicerca() {
        String testoRicerca = txtSearch.getText();
        List<Utente> risultatiDaMostrare;

        if (testoRicerca == null || testoRicerca.isEmpty()) {
            risultatiDaMostrare = this.listaCompletaUtenti;
        } else {
            String lowerCaseFilter = testoRicerca.toLowerCase();
            List<Utente> listaFiltrata = new ArrayList<>();

            for (Utente utente : this.listaCompletaUtenti) {
                if (utente.getMatricola().toLowerCase().contains(lowerCaseFilter) ||
                    utente.getCognome().toLowerCase().contains(lowerCaseFilter) ||
                    utente.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    listaFiltrata.add(utente);
                }
            }
            risultatiDaMostrare = listaFiltrata;
        }

        ObservableList<Utente> tabellaContainer = FXCollections.observableArrayList(risultatiDaMostrare);
        tableUtenti.setItems(tabellaContainer);
    }

    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        aggiornaTabella();
    }

    @FXML private void handleAggiungiUtente() {
        if (logicController != null) {
            boolean successo = logicController.gestisciIscrizione(
                    txtNome.getText(), 
                    txtCognome.getText(), 
                    txtMatricola.getText(), 
                    txtEmail.getText()
            );
            
            if(successo){
                handlePulisciCampi();
                aggiornaTabella();
                txtSearch.clear();
            }
            
        }
    }
    
    @FXML
    private void handleModificaUtente() {
        if (logicController != null) {
            boolean successo = logicController.gestisciModificaUtente(
                txtNome.getText(), 
                txtCognome.getText(), 
                txtEmail.getText(),
                txtMatricola.getText()
            );
            
            if (successo) {
                handlePulisciCampi();
                aggiornaTabella();
                txtSearch.clear();
            }
        }
    }
    
    @FXML private void handleRimuoviUtente() {
        Utente u = tableUtenti.getSelectionModel().getSelectedItem();
        if (u != null && logicController != null) {
            logicController.gestisciRimozioneUtente(u.getMatricola());
            handlePulisciCampi();
            aggiornaTabella();
        }
    }

    private void aggiornaTabella() {
        if (logicController != null) {
            this.listaCompletaUtenti = logicController.getListaUtenti();
            handleRicerca(); 
            tableUtenti.refresh();
        }
    }
}