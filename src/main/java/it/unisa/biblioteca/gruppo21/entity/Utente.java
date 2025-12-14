/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @file Utente.java
 * @brief Rappresenta un utente registrato nella biblioteca.
 * @class Utente
 * Un utente è identificato univocamente dalla matricola e possiede dati anagrafici
 * e di contatto.
 * @author Gruppo 21
 * @version 1.0
 */
public class Utente implements Comparable<Utente>{
    
    /** Numero massimo di libri che può prendere in prestito un utente */
    public static final int MAX_PRESTITI = 3;
    
    /** Nome dell'utente */
    private String nome;
    
    /** Cognome dell'utente */
    private String cognome;
    
    /** Indirizzo email istituzionale */
    private String email;
    
    /** Matricola univoca (ID) */
    private final String matricola;
    
    /** Lista dei libri attualmente in possesso */
    private final List<Prestito> prestitiAttivi;
    
    /**
     * @brief Costruttore completo.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param matricola Matricola univoca (deve essere di 10 cifre).
     * @param email Email istituzionale (deve terminare con @studenti.unisa.it).
     */
    
    public Utente(String nome, String cognome, String email, String matricola){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.matricola = matricola;
        this.prestitiAttivi = new ArrayList<>();
    }

    /**
     * @brief Restituisce il nome dell'utente.
     * @return Una stringa rappresentante il nome.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * @brief Aggiorna il nome dell'utente.
     * @param nome La nuova email da impostare.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @brief Restituisce il cognome dell'utente.
     * @return Una stringa rappresentante il cognome.
     */
    public String getCognome() {
        return cognome;
    }
    
     /**
     * @brief Aggiorna il cognome dell'utente.
     * @param cognome La nuova email da impostare.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @brief Restituisce l'email dell'utente.
     * @return Una stringa rappresentante l'email.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @brief Aggiorna l'indirizzo email dell'utente.
     * @param email La nuova email da impostare.
     */
    public void setEmail(String email) {
        this.email = email;
    } 

    /**
     * @brief Restituisce la matricola dell'utente.
     * @return Una stringa rappresentante la matricola.
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * @brief Restituisce il numero di libri attualmente in possesso dell'utente.
     * @return Un intero rappresentante il numero di libri in possesso.
     */
    public int getNumeroLibriPossesso() {
        return prestitiAttivi.size();
    }
    
    /**
     * @brief Restituisce la lista dei prestiti
     * @return Una lista di oggetti Prestito rappresentante i libri attualmente in possesso dell'utente.
     */
    public List<Prestito> getPrestitiAttivi(){
        return prestitiAttivi;
    }
    
    /**
     * @brief Aggiunge un prestito alla lista dei libri attualmente in possesso dell'utente. 
     * @param prestito L'oggetto Prestito da aggiungere alla lista.
     */
    public void aggiungiPrestito(Prestito prestito){
        prestitiAttivi.add(prestito);
    }
    
    /**
     * @brief Rimuove un prestito dalla lista dei prestiti attivi.
     * Viene utilizzato quando un libro viene restituito, per aggiornare la lista
     * dei libri attualmente in possesso dell'utente
     * @param prestito 
     */
    public void rimuoviPrestito(Prestito prestito){
        if(prestitiAttivi.contains(prestito)){
            prestitiAttivi.remove(prestito);
        }
    }
    
    /**
     * @brief Restituisce una rappresentazione testuale dell'oggeto Utente.
     * Formatta i dati principali in una stringa leggibile, utile per il debugging
     * e per i log di sistema.
     * @return Una stringa nel formato "Utente: [Nome] [Cognome] - Mat: [Matricola]".
     */
    @Override
    public String toString() { 
        return "Utente: " + nome + " " + cognome + " - Mat: " + matricola;
    }

    /**
     * @brief Calcola il codice hash per l'oggetto Utente.
     * Il calcolo si basa esclusivamente sul matricola. 
     * Questo garantisce che oggetti considerati uguali dal metodo equals() 
     * abbiano lo stesso hash code, rispettando il contratto generale di Java.
     * @return Intero rappresentante l'hash del codice ISBN
     */
    @Override
    public int hashCode(){
    
        return matricola.hashCode();
    }
    
    /**
     * @brief Verifica l'uguaglianza logica tra due utenti.
     * L'identità di un utente è determinata univocamente dalla sua matricola.
     * Campi mutabili come email, nome o cognome non influenzano l'uguaglianza
     * dell'oggetto (Entity Equality).
     * @param o L'oggetto con cui confrontare l'istanza corrente.
     * @return true se l'oggetto passato è un Utente e ha la stessa matricola, false altrimenti.
     */
    @Override
    public boolean equals(Object o){
    
        if(this == o)
            return true;
        
        if(o == null || getClass() != o.getClass())
            return false;
        
        Utente utente = (Utente) o;
        return matricola.equals(utente.matricola);
    }
    
    /**
     * @brief Definisce l'ordinamento naturale degli oggetti Utente.
     * L'ordinamento viene effettuato alfabeticamente (case-insensitive) per Cognome.
     * In caso di omonimia (cognomi uguali), viene utilizzato il Nome come criterio secondario.
     * * @note Questo metodo è "inconsistent with equals": l'ordinamento si basa su 
     * dati anagrafici per facilitare la visualizzazione nelle liste, mentre l'uguaglianza 
     * si basa sulla matricola univoca.
     * * @param u L'utente da confrontare.
     * @return Un numero negativo se questo utente precede l'altro, zero se sono uguali 
     * (per nome e cognome), un numero positivo se segue l'altro.
     */
    @Override
    public int compareTo(Utente u) {
        int risultatoCognome = this.cognome.compareToIgnoreCase(u.getCognome());
        
        if(risultatoCognome != 0){
        
            return risultatoCognome;
        }
        
        return this.nome.compareToIgnoreCase(u.getNome());
    }
    
}
