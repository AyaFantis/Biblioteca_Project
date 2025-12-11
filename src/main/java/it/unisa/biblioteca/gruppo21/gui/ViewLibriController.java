package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Libro;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewLibriController {

    @FXML private TextField txtIsbn;
    @FXML private TextField txtTitolo;
    @FXML private TextField txtAutore;
    @FXML private TextField txtAnno;
    @FXML private TextField txtCopie;

    @FXML private TableView<Libro> bookTable;
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, String> colTitolo;
    @FXML private TableColumn<Libro, String> colAutore;
    @FXML private TableColumn<Libro, Integer> colCopieDisp;

    private Controller logicController;

    @FXML
    public void initialize() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("codiceISBN"));
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));
        colCopieDisp.setCellValueFactory(new PropertyValueFactory<>("numeroCopieDisponibili"));
    }

    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        aggiornaTabella();
    }

    @FXML private void handleAggiungiLibro() {
        if (logicController != null) {
            logicController.gestisciAggiuntaLibro(txtTitolo.getText(), txtAutore.getText(), txtIsbn.getText(), txtAnno.getText(), txtCopie.getText());
            txtIsbn.clear(); txtTitolo.clear(); txtAutore.clear(); txtAnno.clear(); txtCopie.clear();
            aggiornaTabella();
        }
    }
    
    @FXML private void handleRimuoviLibro() {
        Libro l = bookTable.getSelectionModel().getSelectedItem();
        if(l != null && logicController != null) {
            logicController.gestisciRimozioneLibro(l.getCodiceISBN());
            aggiornaTabella();
        }
    }

    private void aggiornaTabella() {
        if (logicController != null) bookTable.getItems().setAll(logicController.getListaLibri());
    }
}