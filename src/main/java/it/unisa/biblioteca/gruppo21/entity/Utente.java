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
public class Utente {
    private final String nome;
    private final String cognome;
    private String indirizzoEmail;
    private int matricola;
    private int numeroLibriPossesso;
    
    public Utente(String nome, String cognome, String indirizzoEmail, int matricola, int numeroLibriPossesso){
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzoEmail = indirizzoEmail;
        this.matricola = matricola;
        this.numeroLibriPossesso = numeroLibriPossesso;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    public int getMatricola() {
        return matricola;
    }

    public int getNumeroLibriPossesso() {
        return numeroLibriPossesso;
    }

    public void setIndirizzoEmail(String indirizzoEmail) {
        this.indirizzoEmail = indirizzoEmail;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void setNumeroLibriPossesso(int numeroLibriPossesso) {
        this.numeroLibriPossesso = numeroLibriPossesso;
    }
    
    
    
}
