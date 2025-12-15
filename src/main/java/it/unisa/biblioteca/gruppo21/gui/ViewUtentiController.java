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

/**
 * @file ViewUtentiController.java
 * @brief Gestisce la schermata degli Utenti.
 * @class ViewUtentiController
 * Permette di inserire nuovi studenti, modificarli e vedere la lista
 * di chi è iscritto. Mostra anche quali libri hanno in prestito.
 */
public class ViewUtentiController extends AbstractViewController {

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
    
    private List<Utente> listaCompletaUtenti = new ArrayList<>();

    /**
     * @brief Inizializza la tabella.
     * @post Le colonne sono collegate ai dati dell'Utente.
     * @post La colonna 'Libri' è configurata per mostrare l'elenco dei prestiti.
     */
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
                String titolo = p.getLibro().getTitolo();
                
                testo.append(titolo).append(" (");
                
                if (p.getDataRestituzione() != null) {
                    testo.append("Scad: ").append(p.getDataRestituzione().format(formatter));
                }
                testo.append(")\n");
            }
            return new SimpleStringProperty(testo.toString());
        });
        tableUtenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * @brief Riempie i campi di testo quando clicchi su un utente in tabella.
     * @pre L'utente deve aver cliccato una riga valida (non vuota).
     * @post I campi mostrano i dati dell'utente selezionato.
     * @post La matricola non è modificabile.
     * @param event Il click del mouse.
     */
    @FXML
    private void handleSelezioneTabella(MouseEvent event) {
        
        Utente u = tableUtenti.getSelectionModel().getSelectedItem();
        
        if (u != null) {
            txtMatricola.setText(u.getMatricola());
            txtNome.setText(u.getNome());
            txtCognome.setText(u.getCognome());
            txtEmail.setText(u.getEmail());
            
            txtMatricola.setEditable(false);
            
            btnInserisci.setDisable(true);
            btnModifica.setDisable(false);
        }
    }

    /**
     * @brief Svuota i campi per permettere un nuovo inserimento.
     * @post Tutti i campi di testo sono vuoti.
     * @post Il tasto "Registra" è attivo, "Modifica" è disabilitato.
     */
    @FXML
    private void handlePulisciCampi() {
        txtMatricola.clear();
        txtNome.clear();
        txtCognome.clear();
        txtEmail.clear();
        
        
        txtMatricola.setEditable(true);
        
        btnInserisci.setDisable(false);
        btnModifica.setDisable(true);
        
        tableUtenti.getSelectionModel().clearSelection();
    }

    /**
     * @brief Filtra la tabella mentre scrivi nella barra di ricerca.
     * Cerca per nome, cognome o matricola.
     * @pre La lista completa degli utenti deve essere caricata.
     * @post La tabella mostra solo gli utenti che corrispondono al filtro.
     */
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

    /**
     * @brief Registri un nuovo utente.
     * @pre I campi Nome, Cognome, Matricola ed Email devono essere compilati.
     * @post Se l'operazione ha successo, i campi vengono puliti e la tabella aggiornata.
     */
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
    
    /**
     * @brief Invia la richiesta di modifica utente.
     * @pre Un utente deve essere stato selezionato dalla tabella.
     * @post I dati aggiornati vengono salvati nel sistema.
     */
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
    
    /**
     * @brief Cancella l'utente selezionato in tabella.
     * @pre Un utente deve essere selezionato.
     * @post L'utente viene rimosso se non ha prestiti pendenti.
     */
    @FXML private void handleRimuoviUtente() {
        Utente u = tableUtenti.getSelectionModel().getSelectedItem();
        if (u != null && logicController != null) {
            logicController.gestisciRimozioneUtente(u.getMatricola());
            handlePulisciCampi();
            aggiornaTabella();
        }
    }

    /**
     * @brief Aggiorna i dati della tabella.
     * @post La lista locale viene sincronizzata con il database.
     */
    @Override
    protected void aggiornaTabella() {
        if (logicController != null) {
            this.listaCompletaUtenti = logicController.getListaUtenti();
            handleRicerca(); 
            tableUtenti.refresh();
        }
    }
}