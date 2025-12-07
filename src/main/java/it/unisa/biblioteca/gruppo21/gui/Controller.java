/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import it.unisa.biblioteca.gruppo21.service.Biblioteca;
import java.util.List;

/**
 *
 * @author manue
 */
public class Controller {
    
    private Biblioteca biblioteca;
    
    public Controller(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    public void gestisciIscrizione(String nome, String cognome, String matricola, String email) {
        // TODO
    }
    
    public void gestisciRimozioneUtente(String matricola) {
        // TODO
    }
    
    public List<Utente> getListaUtenti() {
        // TODO
        return null;
    }
    
    public void gestisciAggiuntaLibro(String titolo, String autore, String isbn, String annoStr, String copieStr) {
        // TODO
    }
    
    public void gestisciRimozioneLibro(String isbn) {
        // TODO
    }
    
    public List<Libro> getListaLibri() {
        // TODO
        return null;
    }
   
    public void gestisciPrestito(String matricola, String isbn) {
        // TODO
    }
    
    public void gestisciRestituzione(String matricola, String isbn) {
        // TODO
    }
    
    public List<Prestito> getListaPrestiti() {
        // TODO
        return null;
    }
 
    private void mostraMessaggio(String messaggio) {
        // TODO
    }
}
