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
 * @file Controller.java
 * @brief Gestore delle interazioni tra l'Interfaccia Utente e il sistema Biblioteca.
 * @class Controller
 * Questa classe funge da intermediario (Pattern Controller). 
 * Si occupa di convertire gli input testuali provenienti dalla GUI in dati strutturati,
 * invocare la Business Logic della Biblioteca e presentare i risultati all'utente.
 * @author Gruppo 21
 * @version 1.0
 */
public class Controller {
    
    private final Biblioteca biblioteca;
    
    /**
     * @brief Costruttore del Controller.
     * @pre Il parametro biblioteca non deve essere null.
     * @post Il controller è inizializzato e collegato all'istanza del modello.
     * @param biblioteca L'istanza della Facade.
     */
    public Controller(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    /**
     * @brief Gestisce la richiesta di iscrizione di un nuovo utente.
     * @pre I parametri non dovrebbero essere null.
     * @post Viene mostrato un messaggio all'utente con l'esito dell'operazione.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param matricola Matricola (deve essere valida).
     * @param email Email (deve essere istituzionale).
     */
    public void gestisciIscrizione(String nome, String cognome, String matricola, String email) {
        // TODO
    }
    
    /**
     * @brief Gestisce il flusso di rimozione utente.
     * @pre matricola non dovrebbe essere null.
     * @post L'esito dell'operazione viene mostrato all'utente.
     * @param matricola La matricola dell'utente.
     */
    public void gestisciRimozioneUtente(String matricola) {
        // TODO
    }
    
    /**
     * @brief Recupera i dati per la visualizzazione della tabella utenti.
     * @post Restituisce la lista corrente degli utenti dal sistema.
     * @return Lista di Utente.
     */
    public List<Utente> getListaUtenti() {
        // TODO
        return null;
    }
    
    /**
     * @brief Gestisce l'aggiunta di un libro, occupandosi del parsing dei dati numerici.
     * @pre I parametri sono stringhe grezze provenienti dalla GUI.
     * @post Caso 1 Se annoStr e copieStr sono interi validi,
     * viene chiamato il servizio di aggiunta e mostrato l'esito.
     * @post Caso 2 Se i campi numerici non sono validi, viene mostrato
     * un messaggio di errore specifico senza invocare il servizio.
     * @param titolo Titolo del libro.
     * @param autore Autore.
     * @param isbn ISBN.
     * @param annoStr Anno.
     * @param copieStr Copie.
     */
    
    public void gestisciAggiuntaLibro(String titolo, String autore, String isbn, String annoStr, String copieStr) {
        // TODO
    }
    
    /**
     * @brief Gestisce la rimozione di un libro.
     * @pre isbn non dovrebbe essere null.
     * @post L'esito viene mostrato all'utente.
     * @param isbn Codice del libro.
     */
    public void gestisciRimozioneLibro(String isbn) {
        // TODO
    }
    
    /**
     * @brief Recupera i dati per la tabella libri.
     * @post Restituisce la lista dei libri.
     * @return Lista di Libro.
     */
    public List<Libro> getListaLibri() {
        // TODO
        return null;
    }
   
    /**
     * @brief Gestisce il flusso di creazione prestito.
     * @pre matricola e isbn non nulli.
     * @post L'esito (Successo, Utente non trovato, Stock esaurito) viene mostrato all'utente.
     * @param matricola ID Utente.
     * @param isbn ID Libro.
     */
    public void gestisciPrestito(String matricola, String isbn) {
        // TODO
    }
    
    /**
     * @brief Gestisce il flusso di restituzione libro.
     * @pre matricola e isbn non nulli.
     * @post Viene mostrato il messaggio di conferma o errore.
     * @param matricola ID Utente.
     * @param isbn ID Libro.
     */
    public void gestisciRestituzione(String matricola, String isbn) {
        // TODO
    }
    
    /**
     * @brief Recupera lo storico prestiti.
     * @post Restituisce la lista di tutti i prestiti attivi.
     * @post La lista non è mai null.
     * @return Lista di Prestito.
     */
    public List<Prestito> getListaPrestiti() {
        // TODO
        return null;
    }
 
    /**
     * @brief Visualizza un messaggio di feedback.
     * * @pre messaggio non nullo.
     * * @post Il messaggio viene inviato allo stream di output.
     * * @param messaggio Testo da mostrare.
     */
    private void mostraMessaggio(String messaggio) {
        // TODO
    }
}
