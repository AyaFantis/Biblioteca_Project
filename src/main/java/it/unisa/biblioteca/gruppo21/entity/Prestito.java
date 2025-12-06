/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.entity;

import java.time.LocalDate;

/**
 *
 * @author manue
 */
public class Prestito {
   
    public enum StatoPrestito {
        ATTIVO,
        RESTITUITO,
        IN_RITARDO
    }
    
    private final Utente utente;
    private final Libro libro;
    private final LocalDate dataRestituzione;
    private StatoPrestito stato;
    
    public Prestito(Utente utente, Libro libro, LocalDate dataRestituzione){
    
        this.utente = utente;
        this.libro = libro;
        this.dataRestituzione = dataRestituzione;
    }

    public Utente getUtente() {
        return utente;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getDataRestituzione() {
        return dataRestituzione;
    }

    public StatoPrestito getStato() {
        return stato;
    }

    public void setStato(StatoPrestito stato) {
        this.stato = stato;
    }
    
    
    
}
