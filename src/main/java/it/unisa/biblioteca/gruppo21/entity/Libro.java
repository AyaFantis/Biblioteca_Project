/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

/**
 * @file Libro.java
 * @brief Rappresenta un libro all'interno del catalogo della biblioteca.
 * @class Libro
 * Questa classe è un'entità che incapsula le informazioni fondamentali di un libro,
 * inclusa la gestione della quantità totale di copie possedute dalla biblioteca.
 * * @author Gruppo 21
 * @version 1.0
 */

public class Libro {
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
}
