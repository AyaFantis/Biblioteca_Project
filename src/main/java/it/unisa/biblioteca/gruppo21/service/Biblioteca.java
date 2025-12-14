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
 * @class Biblioteca
 * Questa classe agisce come un "pannello di controllo" unificato
 * nascondendo la complessità dei sottosistemi (Archivi e Servizi) al livello superiore (Controller/ViewLibreria).
 * Gestisce il ciclo di vita (creazione e iniezione delle dipendenze) dei componenti di backend.
 * @author Gruppo 21
 * @version 1.0
 */
public class Biblioteca {
   
    private final ServiceUtenti serviceUtenti;
    private final ServiceLibri serviceLibri;
    private final ServicePrestiti servicePrestiti;

    /**
     * @brief Costruttore della classe Biblioteca.
     * Inizializza gli archivi, collegando le dipendenze
     * @post Il sistema è pronto per elaborare richieste.
     */
    public Biblioteca(){
    
        ArchiveUtenti archivioUtenti = new ArchiveUtenti();
        ArchiveLibri archivioLibri = new ArchiveLibri();
        
        archivioUtenti.inizializzaDati();
        archivioLibri.inizializzaDati();
        
        ArchivePrestiti archivioPrestiti = new ArchivePrestiti(archivioUtenti, archivioLibri);
        
        archivioPrestiti.inizializzaDati();
        
        for(Prestito p : archivioPrestiti.leggiTutti()) {
            if(p.getStato() == Prestito.StatoPrestito.ATTIVO || p.getStato() == Prestito.StatoPrestito.IN_RITARDO) {
                
                p.getUtente().aggiungiPrestito(p);
            }
        }
        
        this.serviceUtenti = new ServiceUtenti(archivioUtenti, archivioPrestiti);
        this.serviceLibri = new ServiceLibri(archivioLibri, archivioPrestiti);
        this.servicePrestiti = new ServicePrestiti(archivioPrestiti, archivioLibri, archivioUtenti);
        
        
    }
    
    //
    // METODI UTENTE
    //
    
    /**
     * @brief Registra un nuovo utente nel sistema.
     * Passa l'operazione al ServiceUtenti.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param matricola Matricola univoca (10 cifre).
     * @param email Email istituzionale (@studenti.unisa.it).
     * @return Messaggio di esito.
     */
    public String iscriviUtente(String nome, String cognome, String matricola, String email){
        return serviceUtenti.iscrivi(nome, cognome, matricola, email);
    }
    
    /**
     * @brief Modifica i dati anagrafici di un utente esistente.
     * Permette di aggiornare Nome, Cognome ed Email. La Matricola viene usata come chiave di ricerca
     * e non può essere modificata.
     * Passa l'operazione al ServiceUtenti.
     * @param nuovoNome Il nuovo nome da impostare.
     * @param nuovoCognome Il nuovo cognome da impostare.
     * @param nuovaEmail La nuova email da impostare.
     * @param matricola La matricola dell'utente da modificare.
     * @return Messaggio di esito.
     */
    public String modificaUtente(String nuovoNome, String nuovoCognome, String nuovaEmail, String matricola){
        return serviceUtenti.modificaDatiUtente(nuovoNome, nuovoCognome, nuovaEmail, matricola);
    }

    /**
     * @brief Rimuove un utente dal sistema.
     * L'operazione viene rifiutata se l'utente ha ancora prestiti attivi.
     * Passa l'operazione al ServiceUtenti.
     * @param matricola La matricola dell'utente da rimuovere.
     * @return Messaggio che descrive l'esito dell'operazione.
     */
    public String rimuoviUtente(String matricola){
        return serviceUtenti.rimuovi(matricola);
    }
    
    /**
     * @brief Recupera l'elenco completo degli utenti registrati.
     * Passa l'operazione al ServiceUtenti.
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
    public List<Utente> cercaUtenti(String cognome, String matricola){
        return serviceUtenti.cerca(cognome);
    }
    
    // public List<Utente> cercaUtenti(String matricola){ //per poter cercare con matricola
        //return serviceUtenti.cerca(matricola);
    //}
    
    // 
    //   METODI LIBRO
    //
    
    /**
     * @brief Aggiunge un nuovo libro al catalogo.
     * Passa l'operazione a ServiceLibri.
     * @param titolo Titolo del libro.
     * @param autore Autore/i del libro.
     * @param codiceISBN Codice ISBN univoco.
     * @param annoPubblicazione Anno di pubblicazione.
     * @param numeroCopieDisponibili Numero totale di copie fisiche.
     * @return Messaggio che descrive l'esito dell'operazione.
     */
    public String aggiungiLibro(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
        return serviceLibri.aggiungi(titolo, autore, codiceISBN, annoPubblicazione, numeroCopieDisponibili);
    }
    
    /**
     * @brief Aggiorna il numero di copie disponibili di un libro.
     * @param isbn Codice ISBN del libro.
     * @param nuoveCopie Nuovo numero totale di copie.
     * @return Messaggio di esito.
     */
    public String aggiornaCopieLibro(String isbn, int nuoveCopie) {
        return serviceLibri.aggiornaCopie(isbn, nuoveCopie);
    }

    /**
     * @brief Rimuove un libro dal catalogo.
     * Operazione consentita solo se non ci sono copie in prestito.
     * Passa l'operazione a ServiceLibri.
     * @param codiceISBN Il codice ISBN del libro da rimuovere.
     * @return Messaggio di esito.
     */
    public String rimuoviLibro(String codiceISBN){
       return serviceLibri.rimuovi(codiceISBN); 
    }
    
    /**
     * @brief Recupera l'elenco completo dei libri nel catalogo.
     * Passa l'operazione a ServiceLibri.
     * @return Lista di oggetti Libro.
     */
    public List<Libro> getElencoLibri() { 
        return serviceLibri.getLista(); 
    }
    
    /**
     * @brief Cerca libri per titolo, autore o ISBN.
     * Passa l'operazione a ServiceLibri.
     * @param titolo Titolo da cercare.
     * @param autore Autore da cercare.
     * @param codiceISBN ISBN da cercare.
     * @return Lista dei libri trovati.
     */
    public List<Libro> cercaLibri(String titolo, String autore, String codiceISBN) {
        return serviceLibri.cerca(titolo, autore, codiceISBN);
    }
    
    //
    //METODI PRESTITO
    //

    /**
     * @brief Registra un nuovo prestito per un utente.
     * Verifica la disponibilità del libro e il limite massimo di prestiti per utente.
     * Passa l'operazione a ServicePrestiti.
     * @param identificativo Nome o cognome dell'utente richiedente.
     * @param codiceISBN L'ISBN del libro richiesto.
     * @param dataScadenza Data di restituzione.
     * @return Messaggio di esito.
     */
    public String effettuaPrestito(String identificativo, String codiceISBN, LocalDate dataScadenza){
        return servicePrestiti.nuovoPrestito(identificativo, codiceISBN, dataScadenza);
    }
    
    /**
     * @brief Registra la restituzione di un libro.
     * Libera una copia del libro.
     * Passa l'operazione a ServicePrestiti
     * @param matricola La matricola dell'utente che restituisce.
     * @param codiceISBN L'ISBN del libro restituito.
     * @return Messaggio di esito.
     */
    public String restituisciLibro(String matricola, String codiceISBN){
        return servicePrestiti.restituzione(matricola, codiceISBN);
    }
    
    /**
     * @brief Recupera lo storico completo di tutti i prestiti registrati.
     * Include sia i prestiti attivi che quelli conclusi.
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
     * Può innescare l'invio di notifiche.
     */
    public void controllaRitardi(){
       servicePrestiti.controllaRitardi();
    }  
}
