/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author manue
 */
public class ViewLibreria {

    private final Controller controller;
    private GridPane rootLayout;

    
    private TableView<Utente> tabellaUtenti;
    private TableView<Libro> tabellaLibri;
    private TableView<Prestito> tabellaPrestiti;

    public ViewLibreria(Controller controller) {
        this.controller = controller;
        // TODO
    }

    public Parent getInterfaccia() {
        // TODO
        return null;
    }


    private VBox costruisciSidebar() {
        // TODO
        
        return null;
    }

    private HBox costruisciVistaUtenti() {
        // TODO
        return null;
    }

    private HBox costruisciVistaLibri() {
        // TODO
        return null;
    }

    private VBox costruisciVistaPrestiti() {
        // TODO
        return null;
    }

    public void aggiornaDatiUtenti() {
        // TODO
    }
    
    public void aggiornaDatiLibri() {
        // TODO
    }
}