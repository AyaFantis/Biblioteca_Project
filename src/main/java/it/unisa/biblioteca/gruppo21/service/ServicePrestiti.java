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

    private final ArchivePrestiti archivioPrestiti;
    private final ArchiveLibri archivioLibri;
    private final ArchiveUtenti archivioUtenti;
    
    /**
     * @brief Costruttore del servizio prestiti.
     * @param archivioPrestiti Archivio per la persistenza dei prestiti.
     * @param archivioLibri Archivio per l'accesso ai dati dei libri (stock).
     * @param archivioUtenti Archivio per la verifica dell'esistenza utenti.
     */
    public ServicePrestiti(ArchivePrestiti archivioPrestiti,  ArchiveLibri archivioLibri, ArchiveUtenti archivioUtenti){
        this.archivioPrestiti = archivioPrestiti;
        this.archivioUtenti = archivioUtenti;
        this.archivioLibri = archivioLibri;
    }
    
    /**
     * @brief Trova un utente sia per matricola che per cognome.
     */
    private Utente trovaUtente(String identificativoInput) {
        
        Utente utenteTrovato = archivioUtenti.cerca(identificativoInput);
        if (utenteTrovato != null) return utenteTrovato;

        List<Utente> candidatiPerCognome = new ArrayList<>();
        for (Utente u : archivioUtenti.leggiTutti()){
            if(u.getCognome().equalsIgnoreCase(identificativoInput)){
                candidatiPerCognome.add(u);
            }
        }
        
        if (candidatiPerCognome.size() == 1) return candidatiPerCognome.get(0);
        
        return null;
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
     * @param identificativoUtente La matricola dell'utente.
     * @param codiceISBNLibro L'ISBN del libro.
     * @param dataScadenza La data di restituzione prevista inserita dal bibliotecario.
     * @return Messaggio di esito.
     */
    public String nuovoPrestito(String identificativoUtente, String codiceISBNLibro, LocalDate dataScadenza){
        
        Utente utenteRichiedente = trovaUtente(identificativoUtente);
        if(utenteRichiedente == null) return "Errore: Utente non trovato (o trovate più persone con lo stesso cognome).";
        
        Libro libroRichiesto = archivioLibri.cerca(codiceISBNLibro);
        if (libroRichiesto == null) return "Errore: Libro non trovato.";
        
        if (utenteRichiedente.getNumeroLibriPossesso() >= Utente.MAX_PRESTITI){       
            return "Errore: Limite massimo di prestiti raggiunto per questo utente.";
        }
        if (libroRichiesto.getNumeroCopieDisponibili() <= 0){       
            return "Errore: Copie esaurite per il libro richiesto.";
        }
        if (dataScadenza == null ){
            return "Errore: Data di restituzione obbligatoria.";
        } 
        if(dataScadenza.isBefore(LocalDate.now())){
            return "Errore: Data di restituzione non può essere nel passato";
        }
        
        Prestito nuovoPrestito = new Prestito(utenteRichiedente, libroRichiesto, dataScadenza, Prestito.StatoPrestito.ATTIVO);       
        libroRichiesto.setNumeroCopieDisponibili(libroRichiesto.getNumeroCopieDisponibili() -1);
        utenteRichiedente.aggiungiPrestito(nuovoPrestito);
        
        try{
            archivioPrestiti.aggiungi(nuovoPrestito);
            archivioLibri.salvaTutto();
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
     * @param matricola L'utente che identificativoUtente.
     * @param codiceISBNLibro Il libro restituito.
     * @return Messaggio di esito.
     */
    public String restituzione(String identificativoUtente, String codiceISBNLibro){
        
        Utente utenteCheRestituisce = trovaUtente(identificativoUtente);
        if (utenteCheRestituisce == null) return "Errore: Utente non trovato.";
        
        List<Prestito> prestitiAttiviUtente = utenteCheRestituisce.getPrestitiAttivi();
        
        for (Prestito prestitoCorrente : prestitiAttiviUtente){
        
            if(prestitoCorrente.getStato() == Prestito.StatoPrestito.ATTIVO &&
               prestitoCorrente.getLibro().getCodiceISBN().equals(codiceISBNLibro)){
            
                prestitoCorrente.setStato(Prestito.StatoPrestito.RESTITUITO);
                Libro libroRestituito = prestitoCorrente.getLibro();
                libroRestituito.setNumeroCopieDisponibili(libroRestituito.getNumeroCopieDisponibili() + 1);
                
                utenteCheRestituisce.rimuoviPrestito(prestitoCorrente);
                
                try{
                    archivioPrestiti.salvaTutto();
                    archivioLibri.salvaTutto();
                    return "Successo: Libro restituito.";
                } catch (IOException ex){
                    return "Errore salvataggio";
                }
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
        
        List<Prestito> tutti = archivioPrestiti.leggiTutti();
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
        
        return getPrestitiAttiviOrdinati();
    }
    
    public List<Prestito> getPrestitiAttiviOrdinati(){
    
        List<Prestito> tutti = archivioPrestiti.leggiTutti();        
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
