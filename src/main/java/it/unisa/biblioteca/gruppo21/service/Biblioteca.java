/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.archive.ArchiveUtenti;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.time.LocalDate;
import java.util.List;


/**
 * @file Biblioteca.java
 * @brief Classe Facade che espone le funzionalità principali del sistema.
 ** @class Biblioteca
 * Questa classe agisce come un "pannello di controllo" unificato
 * nascondendo la complessità dei sottosistemi (Archivi e Servizi) al livello superiore (Controller/ViewLibreria).
 * Gestisce il ciclo di vita (creazione e iniezione delle dipendenze) dei componenti di backend.
 ** @author Gruppo 21
 * @version 1.0
 */
public class Biblioteca {
    /** Riferimento al service per la gestione degli utenti */
    private final ServiceUtenti serviceUtenti;
    /** Riferimento al service per la gestione dei libri */
    private final ServiceLibri serviceLibri;
    /** Riferimento al service per la gestione dei prestiti */
    private final ServicePrestiti servicePrestiti;

    /**
     * @brief Costruttore della classe Biblioteca.
     ** Inizializza gli archivi di Utenti, Libri e Prestiti, collegando le dipendenze necessarie.
     * Durante l'inizializzazione, ripristina i collegamenti tra prestiti attivi e utenti in memoria.
     ** @post Il sistema è pronto per elaborare richieste.
     */
    public Biblioteca(){
    
        ArchiveUtenti archivioUtenti = new ArchiveUtenti();
        ArchiveLibri archivioLibri = new ArchiveLibri();
        
        ArchivePrestiti archivioPrestiti = new ArchivePrestiti(archivioUtenti, archivioLibri);
        
        for(Prestito p : archivioPrestiti.leggiTutti()) {
            if(p.getStato() == Prestito.StatoPrestito.ATTIVO || p.getStato() == Prestito.StatoPrestito.IN_RITARDO) {
                
                p.getUtente().aggiungiPrestito(p);
            }
        }
        
        this.serviceUtenti = new ServiceUtenti(archivioUtenti, archivioPrestiti);
        this.serviceLibri = new ServiceLibri(archivioLibri, archivioPrestiti);
        this.servicePrestiti = new ServicePrestiti(archivioPrestiti, archivioLibri, archivioUtenti);
        
        this.controllaRitardi();
    }
    
    //
    // METODI SERVICE UTENTE
    //
    
    /**
     * @brief Registra un nuovo utente nel sistema.
     * Vengono effettiati controlli sulla validità della matricola e dell'email.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param matricola Matricola univoca (10 cifre).
     * @param email Email istituzionale (@studenti.unisa.it).
     * @return Una stringa contenete un messaggio di successo o la descrizione dell'errore
     */
    public String iscriviUtente(String nome, String cognome, String matricola, String email){
        return serviceUtenti.iscrivi(nome, cognome, matricola, email);
    }
    
    /**
     * @brief Modifica i dati anagrafici di un utente esistente.
     * Permette di aggiornare Nome, Cognome ed Email. La Matricola viene usata come chiave di ricerca
     * e non può essere modificata.
     * @param nuovoNome Il nuovo nome da impostare.
     * @param nuovoCognome Il nuovo cognome da impostare.
     * @param nuovaEmail La nuova email da impostare.
     * @param matricola La matricola dell'utente da modificare.
     * @return Una stringa contenete l'esito dell'operazione
     */
    public String modificaUtente(String nuovoNome, String nuovoCognome, String nuovaEmail, String matricola){
        return serviceUtenti.modificaDatiUtente(nuovoNome, nuovoCognome, nuovaEmail, matricola);
    }

    /**
     * @brief Rimuove un utente dal sistema.
     * L'operazione viene rifiutata se l'utente ha ancora prestiti attivi.
     * @param matricola La matricola dell'utente da rimuovere.
     * @return Una stringa che descrive l'esito dell'operazione.
     */
    public String rimuoviUtente(String matricola){
        return serviceUtenti.rimuovi(matricola);
    }
    
    /**
     * @brief Recupera l'elenco completo degli utenti registrati.
     * @return Lista di oggetti Utente.
     */
    public List<Utente> getElencoUtenti() { 
        return serviceUtenti.getLista();
    }
    
    /**
     * @brief Cerca utenti in base a criteri anagrafici.
     * Passa l'operazione a ServiceUtenti.
     * @param cognome Filtro sul cognome (opzionale).
     * @param matricola Filtro sulla matricola (opzionale).
     * @return Lista degli utenti che corrispondono ai criteri specificati.
     */
    public List<Utente> cercaUtente(String cognome, String matricola){
        return serviceUtenti.cerca(cognome);
    }
 
    // 
    //   METODI SERVICE LIBRO
    //
    
    /**
     * @brief Aggiunge un nuovo libro al catalogo.
     * @param titolo Titolo del libro.
     * @param autore Autore/i del libro.
     * @param codiceISBN Codice ISBN univoco.
     * @param annoPubblicazione Anno di pubblicazione.
     * @param numeroCopieDisponibili Numero totale di copie fisiche.
     * @return Una stringa che descrive l'esito dell'operazione.
     */
    public String aggiungiLibro(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
        return serviceLibri.aggiungi(titolo, autore, codiceISBN, annoPubblicazione, numeroCopieDisponibili);
    }
    
    /**
     * @brief Aggiorna il numero di copie disponibili di un libro.
     * @param isbn Codice ISBN del libro.
     * @param nuoveCopie Nuovo numero totale di copie.
     * @return Una stringa contenente
     */
    public String aggiornaCopieLibro(String isbn, int nuoveCopie) {
        return serviceLibri.aggiornaCopie(isbn, nuoveCopie);
    }

    /**
     * @brief Rimuove un libro dal catalogo.
     * Operazione consentita solo se non ci sono copie in prestito.
     * Passa l'operazione a ServiceLibri.
     * @param codiceISBN Il codice ISBN del libro da rimuovere.
     * @return Una stringa contenete l'esito dell'operazione.
     */
    public String rimuoviLibro(String codiceISBN){
       return serviceLibri.rimuovi(codiceISBN); 
    }
    
    /**
     * @brief Recupera l'elenco completo dei libri nel catalogo.
     ** Passa l'operazione a ServiceLibri.
     * @return Lista di oggetti Libro.
     */
    public List<Libro> getElencoLibri() { 
        return serviceLibri.getLista(); 
    }
    
    /**
     * @brief Cerca libri per titolo, autore o ISBN.
     ** Passa l'operazione a ServiceLibri.
     ** @param titolo Titolo da cercare.
     * @param autore Autore da cercare.
     * @param codiceISBN ISBN da cercare.
     * @return Lista dei libri trovati.
     */
    public List<Libro> cercaLibro(String titolo, String autore, String codiceISBN) {
        return serviceLibri.cerca(titolo, autore, codiceISBN);
    }
    
    //
    //METODI SERVICE PRESTITO
    //

    /**
     * @brief Registra un nuovo prestito per un utente.
     * Verifica la disponibilità del libro e il limite massimo di prestiti per utente.
     * @param identificativo Nome o cognome dell'utente richiedente.
     * @param codiceISBN L'ISBN del libro richiesto.
     * @param dataScadenza Data di restituzione.
     * @return Una stringa contenete l'esito (Successo o Errore).
     */
    public String effettuaPrestito(String identificativo, String codiceISBN, LocalDate dataScadenza){
        return servicePrestiti.nuovoPrestito(identificativo, codiceISBN, dataScadenza);
    }
    
    /**
     * @brief Registra la restituzione di un libro.
     * Aggiorna lo stato del prestito a RESTITUITO e incrementa le copie disponibili
     * Passa l'operazione a ServicePrestiti
     * @param matricola La matricola dell'utente che restituisce.
     * @param codiceISBN L'ISBN del libro restituito.
     * @return Una stringa contenete l'esito dell'operazione
     */
    public String restituisciLibro(String matricola, String codiceISBN){
        return servicePrestiti.restituzione(matricola, codiceISBN);
    }
    
    /**
     * @brief Recupera lo storico completo di tutti i prestiti registrati.
     * Include sia i prestiti attivi che quelli conclusi (restituiti).
     * Passa l'operazione a ServicePrestiti.
     * @return Lista di oggetti Prestito.
     */
    public List<Prestito> getStoricoPrestiti(){
        return servicePrestiti.getLista();
    }
    
    /**
     * @brief Controlla la presenza di ritardi nei prestiti attivi.
     * Scansiona i prestiti e aggiorna lo stato a IN_RITARDO se la data di scadenza è superata.
     * Passa l'operazione a ServicePrestiti.
     */
    public void controllaRitardi(){
       servicePrestiti.controllaRitardi();
    }  
    
}
