/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

/**
 *
 * @author manue
 */
public class Libro {
    private final String titolo;
    private final String autore;
    private final String codiceISBN;
    private final int annoPubblicazione;
    private int numeroCopieDisponibili;
    
    public Libro(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
        this.titolo = titolo;
        this.autore = autore;
        this.codiceISBN = codiceISBN;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroCopieDisponibili = numeroCopieDisponibili;
    }
    
    public String getTitolo(){
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getCodiceISBN() {
        return codiceISBN;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
    
    public int getNumeroCopieDisponibili(){
        return numeroCopieDisponibili;
    }
    
    public void setNumeroCopieDisponibili(int newNum){
        numeroCopieDisponibili = newNum;
    }
}
