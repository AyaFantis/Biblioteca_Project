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
 * @file ViewLibreria.java
 * @brief Classe principale per la gestione dell'Interfaccia Grafica (GUI) JavaFX.
 * @class ViewLibreria
 * Rappresenta la "View" nel pattern MVC. Si occupa di costruire il layout grafico,
 * gestire i componenti visuali  e visualizzare i dati forniti dal Controller.
 * @author Gruppo 21
 * @version 1.0
 */
public class ViewLibreria {

    /** Riferimento al Controller per inoltrare le azioni dell'utente. */
    private final Controller controller;
    /** Layout radice dell'interfaccia. */
    private GridPane rootLayout;

    /** Tabella per la visualizzazione degli utenti. */
    private TableView<Utente> tabellaUtenti;
    /** Tabella per la visualizzazione dei libri. */
    private TableView<Libro> tabellaLibri;
    /** Tabella per la visualizzazione dei prestiti. */
    private TableView<Prestito> tabellaPrestiti;

    /**
     * @brief Costruttore della View.
     * Inizializza i componenti grafici e costruisce la scena principale.
     * @pre Il parametro controller non deve essere null.
     * @post L'oggetto View è istanziato.
     * @post Il layout principale (rootLayout) è inizializzato e configurato.
     * @post Le tabelle sono istanziate.
     * @param controller Il controller che gestisce la logica applicativa.
     */
    public ViewLibreria(Controller controller) {
        this.controller = controller;
        // TODO
    }

    /**
     * @brief Restituisce il nodo radice dell'interfaccia grafica.
     * Metodo necessario per passare la scena allo "Stage" principale di JavaFX.
     * @pre La View deve essere stata correttamente istanziata.
     * @post Restituisce un oggetto Parent non nullo.
     * @post L'oggetto restituito contiene l'intero albero della scena (Sidebar + Viste).
     * @return Il nodo radice (root) dell'interfaccia.
     */
    public Parent getInterfaccia() {
        // TODO
        return null;
    }

    /**
     * @brief Costruisce il pannello laterale (Sidebar) per la navigazione.
     * @post Restituisce un VBox configurato con i pulsanti di navigazione.
     * @return Il componente grafico della sidebar.
     */
    private VBox costruisciSidebar() {
        // TODO
        return null;
    }

    /**
     * @brief Costruisce la vista per la gestione degli Utenti.
     * Include la tabella utenti e i form per aggiunta/rimozione.
     * @post Restituisce un contenitore (HBox/VBox) con la tabella utenti inizializzata.
     * @return Il pannello di gestione utenti.
     */
    private HBox costruisciVistaUtenti() {
        // TODO
        return null;
    }

    /**
     * @brief Costruisce la vista per la gestione dei Libri.
     * Include la tabella libri e i campi di input per i nuovi arrivi.
     * @post Restituisce un contenitore con la tabella libri inizializzata.
     * @return Il pannello di gestione libri.
     */
    private HBox costruisciVistaLibri() {
        // TODO
        return null;
    }

    /**
     * @brief Costruisce la vista per la gestione dei Prestiti.
     * @post Restituisce un contenitore con la tabella prestiti.
     * @return Il pannello di gestione prestiti.
     */
    private VBox costruisciVistaPrestiti() {
        // TODO
        return null;
    }

    /**
     * @brief Aggiorna i dati visualizzati nella tabella Utenti.
     * Richiede al controller la lista aggiornata e la sostituisce nella TableView.
     * @pre Il controller deve essere collegato e funzionante.
     * @post La vista viene rinfrescata (repaint) per mostrare i nuovi dati.
     */
    public void aggiornaDatiUtenti() {
        // TODO
    }
    
    /**
     * @brief Aggiorna i dati visualizzati nella tabella Libri.
     * @pre Il controller deve essere collegato.
     * @post La vista viene aggiornata.
     */
    public void aggiornaDatiLibri() {
        // TODO
    }
}