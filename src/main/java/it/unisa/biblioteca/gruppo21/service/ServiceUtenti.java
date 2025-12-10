/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.util.Collections;
import java.util.List;


/**
 * @file ServiceUtenti.java
 * @brief Gestisce la logica di business relativa agli utenti (anagrafica).
 * @class ServiceUtenti
 * * Questa classe funge da intermediario tra il Controller e i dati persistenti degli utenti.
 * * Si assicura che i dati inseriti siano validi e che i vincoli di integrità (es. non cancellare
 * utenti con prestiti attivi) siano rispettati.
 * * @author Gruppo 21
 * @version 1.0
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
     * @pre I parametri nome, cognome, matricola ed email non devono essere null.
     * @pre La matricola deve essere composta da 10 cifre.
     * @pre L'email deve terminare con "@studenti.unisa.it".
     * @pre Non deve esistere già un utente con la stessa matricola nell'archivio.
     * @post Se le condizioni sono rispettate, un nuovo oggetto Utente viene aggiunto all'archivio.
     * @post La dimensione dell'archivio Utenti aumenta di 1.
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param matricola Matricola univoca (10 cifre)
     * @param email Email istituzionale (@studenti.unisa.it)
     * @return Messaggio di esito ("Successo" o descrizione errore).
     */
    public String iscrivi(String nome, String cognome, String matricola, String email){
        return null;
    }
    
    /**
     * @brief Modifica i dati anagrafici di un utente esistente
     * @pre Deve esistere un utente con la matricola specificata.
     * @pre La nuova email deve essere valida.
     * @post I campi nome, cognome e email dell'utente vengono aggiornati
     * @post La matricola rimane invariata
     * @param nuovoNome Nuovo nome.
     * @param nuovoCognome Nuovo cognome.
     * @param nuovaEmail Nuova email.
     * @param matricola Identificativo dell'utente da modificare.
     * @return Messaggio di esito
     */
    public String modificaDatiUtente(String nuovoNome, String nuovoCognome, String nuovaEmail, String matricola){
        return null;
    }
    
    /**
     * @brief Rimuove un utente dal sistema.
     * @pre Deve esistere un utente con la matricola specificata.
     * @pre L'utente NON deve avere libri in prestito attivi
     * @post L'utente viene rimosso fisicamente dall'archivio.
     * @post La dimensione dell'archivio utenti diminuisce di 1.
     * @param matricola La matricola dell'utente da rimuovere.
     * @return Messaggio di esito
     */
    public String rimuovi(String matricola){
        return null;
    }
    
    /**
     * @brief Cerca utenti in base a filtri.
     * @pre Il criterio non deve essere null.
     * @post Restituisce una lista di utenti che soddisfano il criterio.
     * @param filtro Stringa di ricerca (Matricola o Cognome)
     * @return Lista di utenti trovati.
     */
    public List<Utente> cerca (String filtro){
        return null;
    }
    
    /**
     * @brief Recupera l'elenco completo degli utenti.
     * @return Lista di tutti gli utenti registrati.
     */
    public List<Utente> getLista() {
        List<Utente> lista = arcUtenti.leggiTutti();
        
        Collections.sort(lista);
        
        return lista;
    }
}
