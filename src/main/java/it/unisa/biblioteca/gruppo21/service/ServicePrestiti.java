/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.archive.ArchiveUtenti;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import java.util.List;

/**
 *
 * @author manue
 */
public class ServicePrestiti {

    private final ArchivePrestiti arcPrestiti;
    private final ArchiveLibri arcLibri;
    private final ArchiveUtenti arcUtenti;
    
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
     * @param matricola La matricola dell'utente.
     * @param codiceISBN L'ISBN del libro.
     * @return Messaggio di esito.
     */
    public String nuovoPrestito(String matricola, String codiceISBN){
        return null;
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
        return null;
    }
    
    /**
     * @brief Controlla i ritardi nei prestiti attivi.
     * @pre L'archivio prestiti deve essere accessibile.
     * @post Per ogni prestito ATTIVO in cui DataCorrente > DataScadenzaPrevista
     * lo stato viene aggiornato a IN_RITARDO.
     * @return Lista dei prestiti identificati come in ritardo.
     */
    public List<Prestito> controllaRitardi(){
        return null;
    }
    
    /**
     * @brief Recupera lo storico completo dei prestiti.
     * @return Lista di tutti i prestiti (attivi e conclusi).
     */
    public List<Prestito> getLista(){
        return arcPrestiti.leggiTutti();
    }
}
