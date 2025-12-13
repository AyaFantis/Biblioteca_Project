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
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;

/**
 * @file ServicePrestiti.java
 * @brief Gestore della logica di business per la circolazione dei libri (Prestiti e Restituzioni).
 * @class ServicePrestiti
 * Questa classe implementa le regole di dominio relative ai prestiti, coordinando
 * le interazioni tra Utenti, Libri e l'archivio storico dei Prestiti.
 * Verifica la disponibilità delle copie e i limiti imposti agli utenti.
 * @author Gruppo 21
 * @version 1.0
 */
public class ServicePrestiti {

    private final ArchivePrestiti arcPrestiti;
    private final ArchiveLibri arcLibri;
    private final ArchiveUtenti arcUtenti;
    
    /**
     * @brief Costruttore del servizio prestiti.
     * @param arcPrestiti Archivio per la persistenza dei prestiti.
     * @param arcLibri Archivio per l'accesso ai dati dei libri (stock).
     * @param arcUtenti Archivio per la verifica dell'esistenza utenti.
     */
    public ServicePrestiti(ArchivePrestiti arcPrestiti,  ArchiveLibri arcLibri, ArchiveUtenti arcUtenti){
        this.arcPrestiti = arcPrestiti;
        this.arcUtenti = arcUtenti;
        this.arcLibri = arcLibri;
    }
    
    /**
     * @brief Registra un nuovo prestito.
     * @pre Deve esistere un utente registrato con la matricola fornita.
     * @pre Deve esistere un libro in catalogo con l'ISBN fornito.
     * @pre Il libro deve avere almeno una copia disponibile (Copie > 0).
     * @pre L'utente non deve avere già 3 libri in prestito.
     * @post Viene creato un nuovo oggetto Prestito con stato ATTIVO.
     * @post Il numero di copie disponibili del libro viene decrementato di 1.
     * @post Il prestito viene aggiunto alla lista dei prestiti attivi dell'utente.
     * @param identificativo La matricola dell'utente.
     * @param codiceISBN L'ISBN del libro.
     * @param dataScadenza La data di restituzione prevista inserita dal bibliotecario.
     * @return Messaggio di esito.
     */
    public String nuovoPrestito(String identificativo, String codiceISBN, LocalDate dataScadenza){
        
        //Cerco prima per matricola
        Utente utente = arcUtenti.cerca(identificativo);
        
        //Se non ho trovato nulla per matricola, passo alla ricerca per cognome
        if(utente == null){
            List<Utente> candidati = new ArrayList<>();
            List<Utente> tutti = arcUtenti.leggiTutti();
            
            for (Utente u : tutti){
                if(u.getCognome().equalsIgnoreCase(identificativo)){
                    candidati.add(u);
                }
            }
            
            if (candidati.isEmpty()) {
                return "Errore: Utente non trovato (né come Matricola né come Cognome).";
            } else if (candidati.size() > 1) {
                return "Errore: Ci sono più utenti con il cognome '" + identificativo + "'. Usa la Matricola.";
            } else {
                // Trovato esattamente un utente con quel cognome
                utente = candidati.get(0);
            }
        }
        
        Libro libro = arcLibri.cerca(codiceISBN);
        
        if (utente == null){       
            return "Errore: Utente non trovato.";
        }
        if (libro == null){       
            return "Errore: Libro non trovato.";
        }
        if (utente.getNumeroLibriPossesso() >= Utente.MAX_PRESTITI){       
            return "Errore: Limite massimo di prestiti raggiunto per questo utente.";
        }
        if (libro.getNumeroCopieDisponibili() <= 0){       
            return "Errore: Copie esaurite per il libro richiesto.";
        }
        if (dataScadenza == null ){
            return "Errore: Data di restituzione obbligatoria.";
        } 
        if(dataScadenza.isBefore(LocalDate.now())){
            return "Errore: Data di restituzione non può essere nel passato";
        }
        
        Prestito nuovoPrestito = new Prestito(utente, libro, dataScadenza, Prestito.StatoPrestito.ATTIVO);       
        libro.setNumeroCopieDisponibili(libro.getNumeroCopieDisponibili() -1);
        utente.aggiungiPrestito(nuovoPrestito);
        
        try{
        
            arcPrestiti.aggiungi(nuovoPrestito);
            return "Successo: Prestito registrato correttamente.";
        } catch (IOException ex) {       
            return "Errore: Impossibile salvare il prestito su file.";
        }
                
    }
    
    /**
     * @brief Registra la restituzione di un libro
     * @pre Deve esistere un prestito ATTIVO per la coppia utente, libro specificata.
     * @post Lo stato del prestito viene modificato da ATTIVO a RESTITUITO.
     * @post La data di restituzione effettiva viene registrata.
     * @param matricola L'utente che restituisce.
     * @param codiceISBN Il libro restituito.
     * @return Messaggio di esito.
     */
    public String restituzione(String matricola, String codiceISBN){
        List<Prestito> elenco = arcPrestiti.leggiTutti();
        
        for (Prestito p : elenco){
        
            if(p.getStato() == Prestito.StatoPrestito.ATTIVO &&
               p.getUtente().getMatricola().equals(matricola) &&
               p.getLibro().getCodiceISBN().equals(codiceISBN)){
            
                p.setStato(Prestito.StatoPrestito.RESTITUITO);
                Libro libro = p.getLibro();
                libro.setNumeroCopieDisponibili(libro.getNumeroCopieDisponibili() + 1);
                
                Utente utente = p.getUtente();
                utente.rimuoviPrestito(p);
                
                return "Successo: Libro restituito.";
            }
        }
        return "Errore: Nessun prestito attivo trovato per i dati forniti.";
       
    }
    
    /**
     * @brief Controlla i ritardi nei prestiti attivi.
     * @pre L'archivio prestiti deve essere accessibile.
     * @post Per ogni prestito ATTIVO in cui DataCorrente > DataScadenzaPrevista
     * lo stato viene aggiornato a IN_RITARDO.
     * @return Lista dei prestiti identificati come in ritardo.
     */
    public List<Prestito> controllaRitardi(){
        
        List<Prestito> tutti = arcPrestiti.leggiTutti();
        List<Prestito> inRitardo = new ArrayList<>();
        LocalDate oggi = LocalDate.now();
        
        for (Prestito p : tutti){
        
            if (p.getStato() == Prestito.StatoPrestito.ATTIVO && p.getDataRestituzione().isBefore((oggi))){
            
                p.setStato(Prestito.StatoPrestito.IN_RITARDO);
                inRitardo.add(p);
            }
        }
        return inRitardo;
    }
    
    /**
     * @brief Recupera lo storico completo dei prestiti.
     * @return Lista di tutti i prestiti (attivi e conclusi).
     */
    public List<Prestito> getLista(){
        return arcPrestiti.leggiTutti();
    }
    
    public List<Prestito> getPrestitiAttiviOrdinati(){
    
        List<Prestito> tutti = arcPrestiti.leggiTutti();        
        List<Prestito> attivi = new ArrayList<>();
        
        for (Prestito p : tutti){
        
            if(p.getStato() == Prestito.StatoPrestito.ATTIVO){
            
                attivi.add(p);
            }
        }
        Collections.sort(attivi);
        return attivi;
    }
    
}
