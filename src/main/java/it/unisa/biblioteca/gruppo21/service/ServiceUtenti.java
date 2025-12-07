/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.util.List;


/**
 *
 * @author manue
 */

/**
 * @file ServiceUtenti.java
 * 
 * @author manue
 */
public class ServiceUtenti {
   
    private final ArchiveUtenti arcUtenti;
    private final ArchivePrestiti arcPrestiti;
    
    /**
     * @brief Costruttore del servizio utenti.
     * @param arcUtenti Riferimento all'archivio utenti.
     * @param arcPrestiti Riferimento all'archivio prestiti
     */
    public ServiceUtenti(ArchiveUtenti arcUtenti, ArchivePrestiti arcPrestiti){
        this.arcUtenti = arcUtenti;
        this.arcPrestiti = arcPrestiti;
    }
    
    /**
     * @brief Registra un nuovo utente nel sistema.
     * Verifica che la matricola non esista già o che i dati siano nel formato giusto.
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param matricola Matricola univoca (10 cifre)
     * @param email Email istituzionale (@studenti.unisa.it)
     * @return Messaggio di esito ("Successo" o descrizione errore).
     */
    public String iscrivi(String nome, String cognome, String matricola, String email){
        return null;
    }
    
    public String modificaDatiUtente(String nuovoNome, String nuovoCognome, String nuovaEmail, String matricola){
        return null;
    }
    
    public String rimuovi(String matricola){
        return null;
    }
    
    public List<Utente> cerca (String criterio){
        return null;
    }
    
    public List<Utente> getLista() {
        return arcUtenti.leggiTutti();
    }
}
