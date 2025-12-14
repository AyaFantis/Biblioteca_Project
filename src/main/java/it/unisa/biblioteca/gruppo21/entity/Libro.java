/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

import java.util.Objects;
/**
 * @file Libro.java
 * @brief Rappresenta un libro all'interno del catalogo della biblioteca.
 * @class Libro
 * Questa classe è un'entità che incapsula le informazioni fondamentali di un libro,
 * inclusa la gestione della quantità totale di copie possedute dalla biblioteca.
 * * @author Gruppo 21
 * @version 1.0
 */

public class Libro implements Comparable<Libro>{
    /** Titolo del libro */
    private final String titolo;
    /** Autore o autori del libro */
    private final String autore;
    /** Codice ISBN univoco (International Standard Book Number)*/
    private final String codiceISBN;
    /** Anno di pubblicazione del libro */
    private final int annoPubblicazione;
    /** Quantità totale di copie fisiche possedute dalla biblioteca */
    private int numeroCopieDisponibili;
    
  /**
     * @brief Costruttore della classe Libro.
     * Inizializza un nuovo oggetto Libro con i dettagli specificati.
     * @param titolo Il titolo del libro.
     * @param autore L'autore o gli autori del libro.
     * @param codiceISBN Il codice ISBN univoco del libro.
     * @param annoPubblicazione L'anno di pubblicazione.
     * @param numeroCopieDisponibili Il numero totale di copie fisiche disponibili inizialmente.
  */
     
    public Libro(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
        this.titolo = titolo;
        this.autore = autore;
        this.codiceISBN = codiceISBN;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroCopieDisponibili = numeroCopieDisponibili;
    }
    
    /**
     * @brief Restituisce il titolo del libro.
     * @return Una stringa rappresentante il titolo.
     */
    public String getTitolo(){
        return titolo;
    }

     /**
     * @brief Restituisce l'autore del libro.
     * @return Una stringa rappresentante l'autore.
     */
    public String getAutore() {
        return autore;
    }

     /**
     * @brief Restituisce il codice ISBN del libro.
     * @return Una stringa rappresentante il codice ISBN.
     */
    public String getCodiceISBN() {
        return codiceISBN;
    }

     /**
     * @brief Restituisce l'anno di pubblicazione del libro.
     * @return Una stringa rappresentante l'anno di pubblicazione.
     */
    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
    
    /**
     * @brief Restituisce la quantità totale di copie.
     * * Nota: Questo valore rappresenta lo stock totale, non le copie attualmente disponibili
     * (per quelle bisogna sottrarre i prestiti attivi).
     * @return Intero rappresentante le copie totali.
     */
    public int getNumeroCopieDisponibili(){
        return numeroCopieDisponibili;
    }
    
    /**
     * @brief Aggiorna il numero di copie disponibili del libro.
     * @param newNum Il nuovo numero di copie disponibili da impostare.
     */
    public void setNumeroCopieDisponibili(int newNum){
        numeroCopieDisponibili = newNum;
    }
    
     /**
     * @brief Restituisce una rappresentazione testuale del libro.
     * Restituisce una stringa formattata come "Titolo - Autore".
     * @return Una stringa contenente titolo e autore separati da un trattino.
     */
    @Override
    public String toString(){
        
        return titolo + " - " + autore;
    }
    
    /**
     * @brief Calcola il codice hash per l'oggetto Libro.
     * Il calcolo si basa esclusivamente sul codice ISBN. 
     * Questo garantisce che oggetti considerati uguali dal metodo equals() abbiano lo stesso hash code,
     * rispettando il contratto generale di Java per l'uso nelle collezioni
     * @return Intero rappresentante l'hash del codice ISBN
     */
    @Override
    public int hashCode(){
    
        return codiceISBN.hashCode();
    }
    
    /**
     * @brief Verifica l'ugualglianza logica tra due libri.
     * L'identità di un libro è determinata univocamente dal suo codice ISBN.
     * Titolo, autore e altri campi vengono ignorati nel confronto poichè l'ISBN
     * identifica univocamente l'edizione.
     * * @param o L'oggetto con cui confrontare l'istanza corrente.
     * @return true se l'oggetto passato è un Libro e ha lo stesso ISBN, falsa altrimenti.
     */
    @Override 
    public boolean equals (Object o){
    
        if (this == o)
            return true;
        
        if (o == null || getClass() != o.getClass())
            return false;
        
        Libro libro = (Libro) o;
        return codiceISBN.equals(libro.codiceISBN);
    }
    
    /**
     * @brief Definisce l'ordinamento naturale degli oggetti Libro.
     * L'ordinamento viene effettuati alfabetica per Titolo.
     * In caso di titolo identici, viene utilizzato l'Autore come criteria secondario.
     * * @param o Il libro da confrontare.
     * @return Un numero negativo se questo libro precede l'altro, zero se sono uguali
     * (per titolo e autore), un numero positivo se segue l'altro.
     */
    @Override
    public int compareTo(Libro o) {
        int esitoTitolo = this.titolo.compareToIgnoreCase(o.getTitolo());
        
        if (esitoTitolo != 0){
        
            return esitoTitolo;
        }
        
        return this.autore.compareToIgnoreCase(o.getAutore());
    }
}

