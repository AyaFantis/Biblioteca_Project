/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

import java.time.LocalDate;

/**
 * @file Prestito.java
 * @brief Rappresenta un'operazione di prestito.
 * @class Prestito
 * * Contiene i riferimenti (tramite ID) all'utente e al libro coinvolti, oltre
 * alle date di inizio e fine prestito e allo stato corrente.
 * * @author Gruppo 21
 * @version 1.0
 */
public class Prestito implements Comparable<Prestito>{

    /**
     * @brief Enumerazione che definisce gli stati possibili del ciclo di vita di un prestito.
     */
    public enum StatoPrestito {
        /** Il libro è attualmente in possesso dell'utente e i termini temporali sono rispettati. */
        ATTIVO,
        /** Il libro è stato restituito alla biblioteca. */
        RESTITUITO,
        /** Il libro è ancora in possesso dell'utente ma la data di restituzione è stata superata. */
        IN_RITARDO
    }
    
    /** Riferimento all'oggetto Utente che ha effettuato il prestito. */
    private final Utente utente;
    /** Riferimento all'oggetto Libro prestato. */
    private final Libro libro;
    /** Data entro la quale il libro deve essere restituito. 
     * @note Questa è la data di scadenza prevista, non la data effettiva di restituzione.
     */
    private final LocalDate dataRestituzione;
    
    /** Stato corrente del prestito (es. ATTIVO, RESTITUITO, IN_RITARDO). */
    private StatoPrestito stato;
    
    /**
     * @brief Costruttore della classe Prestito.
     * Inizializza un nuovo oggetto Prestito associando un utente e un libro con una data di scadenza.
     * @note È buona norma inizializzare lo stato (es. a ATTIVO) nel costruttore per evitare valori null.
     * @param utenteCoinvolto L'utente che richiede il prestito.
     * @param libroPrestato Il libro oggetto del prestito.
     * @param dataRestituzione La data prevista per la restituzione del libro.
     * @param statoIniziale Lo stato attuale del prestito.
     */
    public Prestito(Utente utenteCoinvolto, Libro libroPrestato, LocalDate dataRestituzione, StatoPrestito statoIniziale){
        this.utente = utenteCoinvolto;
        this.libro = libroPrestato;
        this.dataRestituzione = dataRestituzione;
        this.stato = statoIniziale;
    }

    /**
     * @brief Restituisce l'utente associato al prestito.
     * @return L'oggetto Utente contraente del prestito.
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @brief Restituisce il libro oggetto del prestito.
     * @return L'oggetto Libro prestato.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * @brief Restituisce la data di scadenza del prestito.
     * @return Un oggetto che rappresenta il termine ultimo per la restituzione.
     */
    public LocalDate getDataRestituzione() {
        return dataRestituzione;
    }

    /**
     * @brief Restituisce lo stato attuale del prestito.
     * @return Un valore dell'enumerazione StatoPrestito(null se non inizializzato).
     */
    public StatoPrestito getStato() {
        return stato;
    }

    /**
     * @brief Modifica lo stato del prestito.
     * Utilizzato per aggiornare il ciclo di vita del prestito 
     * @param nuovoStato Il nuovo stato da assegnare al prestito.
     */
    public void setStato(StatoPrestito nuovoStato) {
        this.stato = nuovoStato;
    }
    
    /**
     * @brief Definisce l'ordinamento naturale degli oggetti Prestito.
     * I prestiti vengono ordinati in ordine cronologico crescente in base alla data di restituzione prevista.
     * Questo ordinamento è utile per identifare rapidamente i prestiti in scadenza o scaduti nelle liste.
     * * @param altroPrestito Il prestito da confrontare con l'istanza corrente.
     * @return Un valore negativo se la scadenza di questo prestito è antecedente a quella dell'altro,
     * zero se le date coicidono, un valore positivo se è successiva.
     */
    @Override
    public int compareTo(Prestito altroPrestito) {
        return this.dataRestituzione.compareTo(altroPrestito.dataRestituzione);
    }
   
    
}
