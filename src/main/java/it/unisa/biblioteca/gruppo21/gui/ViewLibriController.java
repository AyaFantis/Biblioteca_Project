package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Libro;
import java.util.ArrayList;
import java.util.List;
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
 * @file ViewLibriController.java
 * @brief Gestisce la schermata del Catalogo Libri.
 * @class ViewLibriController
 * Permette di aggiungere libri, modificare il numero di copie disponibili
 * e vedere l'elenco completo.
 */
public class ViewLibriController extends AbstractViewController{

    @FXML private TextField txtIsbn;
    @FXML private TextField txtTitolo;
    @FXML private TextField txtAutore;
    @FXML private TextField txtAnno;
    @FXML private TextField txtCopie;
    @FXML private TextField txtSearch;
    
    @FXML private Button btnInserisci;
    @FXML private Button btnModifica;

    @FXML private TableView<Libro> tableLibri;
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, String> colTitolo;
    @FXML private TableColumn<Libro, String> colAutore;
    @FXML private TableColumn<Libro, Integer> colCopieDisp;
    
    private List<Libro> listaCompletaLibri = new ArrayList<>();

    /**
     * @brief Inizializza la tabella dei libri.
     * @post Le colonne ISBN, Titolo, Autore e Copie sono collegate agli oggetti Libro.
     */
    @FXML
    public void initialize() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("codiceISBN"));
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));
        colCopieDisp.setCellValueFactory(new PropertyValueFactory<>("numeroCopieDisponibili"));
        tableLibri.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * @brief Seleziona un libro dalla tabella per la modifica.
     * @pre L'utente deve cliccare su una riga valida.
     * @post I campi di testo vengono riempiti.
     * @post ISBN, Titolo, Autore e Anno vengono DISABILITATI (non si possono cambiare).
     * @post Solo il campo "Copie" rimane attivo.
     * @param event Il click del mouse.
     */
    @FXML
    private void handleSelezioneTabella(MouseEvent event) {
        Libro l = tableLibri.getSelectionModel().getSelectedItem();
        if (l != null) {
            txtIsbn.setText(l.getCodiceISBN());
            txtTitolo.setText(l.getTitolo());
            txtAutore.setText(l.getAutore());
            txtAnno.setText(String.valueOf(l.getAnnoPubblicazione()));
            txtCopie.setText(String.valueOf(l.getNumeroCopieDisponibili()));
            
            txtIsbn.setEditable(false);
            
            
            txtTitolo.setDisable(true);
            txtAutore.setDisable(true);
            txtAnno.setDisable(true);
            
            txtCopie.setDisable(false); 
            
            btnInserisci.setDisable(true);
            btnModifica.setDisable(false);
        }
    }

    /**
     * @brief Svuota i campi per permettere un nuovo inserimento.
     * @post Tutti i campi di testo sono vuoti e il tasto "Salva" è attivo.
     */
    @FXML
    private void handlePulisciCampi() {
        txtIsbn.clear(); 
        txtTitolo.clear(); 
        txtAutore.clear(); 
        txtAnno.clear(); 
        txtCopie.clear();
        
        txtIsbn.setEditable(true);
        
        
        txtTitolo.setDisable(false);
        txtAutore.setDisable(false);
        txtAnno.setDisable(false);
        
        btnInserisci.setDisable(false);
        btnModifica.setDisable(true);
        tableLibri.getSelectionModel().clearSelection();
    }

    /**
     * @brief Aggiorna lo stock (copie) di un libro.
     * @pre Un libro deve essere selezionato.
     * @pre Il campo Copie deve contenere un numero valido.
     * @post Il numero di copie nel sistema viene aggiornato.
     */
    @FXML private void handleModificaCopie() {
        if (logicController != null) {
            boolean successo = logicController.gestisciAggiornamentoCopie(
                txtIsbn.getText(), 
                txtCopie.getText()
            );
            if (successo) {
                handlePulisciCampi();
                aggiornaTabella();
                txtSearch.clear();
            }
        }
    }

    /**
     * @brief Cerca libri nel catalogo.
     * @pre La lista completa dei libri deve essere caricata.
     * @post La tabella mostra solo i libri che contengono il testo cercato (in titolo, autore o ISBN).
     */
    @FXML
    private void handleRicerca() {
        String testoRicerca = txtSearch.getText();
        List<Libro> risultatiDaMostrare;

        if (testoRicerca == null || testoRicerca.isEmpty()) {
            risultatiDaMostrare = this.listaCompletaLibri;
        } else {
            String lowerCaseFilter = testoRicerca.toLowerCase();
            List<Libro> listaFiltrata = new ArrayList<>();

            for (Libro libro : this.listaCompletaLibri) {
                if (libro.getCodiceISBN().toLowerCase().contains(lowerCaseFilter) ||
                    libro.getTitolo().toLowerCase().contains(lowerCaseFilter) ||
                    libro.getAutore().toLowerCase().contains(lowerCaseFilter)) {
                    listaFiltrata.add(libro);
                }
            }
            risultatiDaMostrare = listaFiltrata;
        }

        ObservableList<Libro> tabellaContainer = FXCollections.observableArrayList(risultatiDaMostrare);
        tableLibri.setItems(tabellaContainer);
    }

    /**
     * @brief Crea un nuovo libro.
     * @pre Tutti i campi (Titolo, Autore, ISBN, Anno, Copie) devono essere pieni.
     * @post Il nuovo libro viene aggiunto al database se l'ISBN non esiste già.
     */
    @FXML private void handleAggiungiLibro() {
        if (logicController != null) {
            boolean successo = logicController.gestisciAggiuntaLibro(
                    txtTitolo.getText(), 
                    txtAutore.getText(), 
                    txtIsbn.getText(), 
                    txtAnno.getText(), 
                    txtCopie.getText()
            );
            
            if(successo){
                handlePulisciCampi();
                aggiornaTabella();
                txtSearch.clear();
            }
        }
    }
    
    /**
     * @brief Rimuove un libro dal catalogo.
     * @pre Il libro deve essere selezionato.
     * @post Il libro viene rimosso solo se non ci sono copie in prestito.
     */
    @FXML private void handleRimuoviLibro() {
        Libro l = tableLibri.getSelectionModel().getSelectedItem();
        if(l != null && logicController != null) {
            logicController.gestisciRimozioneLibro(l.getCodiceISBN());
            handlePulisciCampi();
            aggiornaTabella();
        }
    }

    /**
     * @brief Sincronizza la tabella con i dati del sistema.
     */
    @Override
    protected void aggiornaTabella() {
        if (logicController != null) {
            this.listaCompletaLibri = logicController.getListaLibri();
            handleRicerca(); 
            tableLibri.refresh();
        }
    }
}