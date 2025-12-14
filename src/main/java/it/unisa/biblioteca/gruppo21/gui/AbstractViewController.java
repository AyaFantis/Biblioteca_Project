/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.gui;

/**
 *
 * @author manuel
 */

/**
 * @file AbstractViewController.java
 * @brief Implementazione classe astratta per i controller delle schermate (Utenti, Libri, Prestiti).
 * @class AbstractViewController
 * Serve a non ripetere codice: gestisce il collegamento col Controller Logico
 * e obbliga le sottoclassi a definire come aggiornare le loro tabelle.
 * @author Gruppo 21
 * @version 1.0
 */
public abstract class AbstractViewController {
    
    /** Riferimento al controller che contiene la logica.*/
    protected Controller logicController;

    /**
     * @brief Collega il Controller Logico alla vista.
     * @pre Il parametro logicController non deve essere null.
     * @post La variabile logicController è inizializzata.
     * @post Viene chiamato automaticamente aggiornaTabella() per mostrare i dati.
     * @param logicController Il controller principale.
     */
    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        aggiornaTabella();
    }

    /**
     * @brief Metodo che ogni vista deve implementare per garantire che,
     * appena si apre una schermata, la tabella sia sempre aggiornata.
     * @pre Il logicController deve essere stato impostato.
     * @post La tabella della schermata mostra i dati aggiornati.
     */
    protected abstract void aggiornaTabella();
}
