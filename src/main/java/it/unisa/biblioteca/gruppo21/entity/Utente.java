/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

/**
 * @file Utente.java
 * @brief Rappresenta un utente registrato nella biblioteca.
 * @class Utente
 * * Un utente è identificato univocamente dalla matricola e possiede dati anagrafici
 * e di contatto.
 * * @author Gruppo 21
 * @version 1.0
 */
public class Utente {
    /** Nome dell'utente */
    private final String nome;
    /** Cognome dell'utente */
    private final String cognome;
    /** Indirizzo email istituzionale */
    private String email;
    /** Matricola univoca (ID) */
    private String matricola;
    /** Libri attualmente in possesso */
    private int numeroLibriPossesso;
    
    /**
     * @brief Costruttore completo.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param matricola Matricola univoca (deve essere di 10 cifre).
     * @param email Email istituzionale (deve terminare con @studenti.unisa.it).
     * @param numeroLibriPossesso Numero di libri attualmente in possesso (devono essere massimo 3).
     */
    
    public Utente(String nome, String cognome, String email, String matricola, int numeroLibriPossesso){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.matricola = matricola;
        this.numeroLibriPossesso = numeroLibriPossesso;
    }

    /**
     * @brief Restituisce il nome dell'utente.
     * @return Una stringa rappresentante il nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @brief Restituisce il cognome dell'utente.
     * @return Una stringa rappresentante il cognome.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @brief Restituisce l'email dell'utente.
     * @return Una stringa rappresentante l'email.
     */
    public String getEmail() {
        return email;
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
        return numeroLibriPossesso;
    }

    /**
     * @brief Aggiorna l'indirizzo email dell'utente.
     * @param email La nuova email da impostare.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @brief Aggiorna la matricola dell'utente.
     * @param matricola La nuova matricola da impostare.
     */
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    /**
     * @brief Aggiorna il numero di libri in possesso da quell'utente.
     * @param numeroLibriPossesso Il nuovo numero di libri in possesso da impostare.
     */
    public void setNumeroLibriPossesso(int numeroLibriPossesso) {
        this.numeroLibriPossesso = numeroLibriPossesso;
    }
    
    
    
}
