package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewUtentiController {

    @FXML private TextField txtMatricola;
    @FXML private TextField txtNome;
    @FXML private TextField txtCognome;
    @FXML private TextField txtEmail;

    @FXML private TableView<Utente> userTable;
    @FXML private TableColumn<Utente, String> colMatricola;
    @FXML private TableColumn<Utente, String> colNome;
    @FXML private TableColumn<Utente, String> colCognome;
    @FXML private TableColumn<Utente, String> colEmail;

    private Controller logicController;

    @FXML
    public void initialize() {
        colMatricola.setCellValueFactory(new PropertyValueFactory<>("matricola"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        aggiornaTabella();
    }

    @FXML private void handleAggiungiUtente() {
        if (logicController != null) {
            logicController.gestisciIscrizione(txtNome.getText(), txtCognome.getText(), txtMatricola.getText(), txtEmail.getText());
            txtNome.clear(); txtCognome.clear(); txtMatricola.clear(); txtEmail.clear();
            aggiornaTabella();
        }
    }
    
    @FXML private void handleRimuoviUtente() {
        Utente u = userTable.getSelectionModel().getSelectedItem();
        if (u != null && logicController != null) {
            logicController.gestisciRimozioneUtente(u.getMatricola());
            aggiornaTabella();
        }
    }

    private void aggiornaTabella() {
        if (logicController != null) userTable.getItems().setAll(logicController.getListaUtenti());
    }
}