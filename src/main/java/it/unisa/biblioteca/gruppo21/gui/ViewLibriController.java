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

    @FXML
    public void initialize() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("codiceISBN"));
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));
        colCopieDisp.setCellValueFactory(new PropertyValueFactory<>("numeroCopieDisponibili"));
        tableLibri.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * @brief Gestione semplice del click sulla tabella.
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
    
    @FXML private void handleRimuoviLibro() {
        Libro l = tableLibri.getSelectionModel().getSelectedItem();
        if(l != null && logicController != null) {
            logicController.gestisciRimozioneLibro(l.getCodiceISBN());
            handlePulisciCampi();
            aggiornaTabella();
        }
    }

    @Override
    protected void aggiornaTabella() {
        if (logicController != null) {
            this.listaCompletaLibri = logicController.getListaLibri();
            handleRicerca(); 
            tableLibri.refresh();
        }
    }
}