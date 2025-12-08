/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import java.util.List;

/**
 * @file ServiceUtenti.java
 * @brief Gestisce tutte le operazioni relative agli utenti (Studenti/Docenti).
 * @class ServiceUtenti
 * * Si occupa di iscrivere nuovi utenti controllando che i dati siano corretti
 * e di rimuoverli, ma solo se non hanno libri ancora da restituire.
 */
public class ServiceLibri {

    private final ArchiveLibri arcLibri;
    private final ArchivePrestiti arcPrestiti;
    
    /**
     * @brief Costruisce il servizio libri (Catalogo).
     * @param arcLibri Il registro dove vengono salvati i libri.
     * @param arcPrestiti Il registro dei prestiti. Serve al servizio per impedire la rimozione
     * di un libro dal catalogo se ci sono ancora copie in giro (prestate agli studenti).
     */
    public ServiceLibri(ArchiveLibri arcLibri,ArchivePrestiti arcPrestiti){
    
        this.arcLibri = arcLibri;
        this.arcPrestiti = arcPrestiti;
    }
    
    /**
     * @brief Aggiunge un nuovo libro al catalogo.
     * @pre I parametri titolo, autore, ISBN non devono essere null.
     * @pre Non deve esistere già un libro con lo stesso ISBN nell'archivio.
     * @pre L'anno di pubblicazione deve essere valido (tra 1000 e anno corrente).
     * @pre Il numero di copie deve essere >= 0.
     * @post Il libro viene memorizzato nell'archivio persistente.
     * @param titolo Titolo del libro.
     * @param autore Autore
     * @param codiceISBN ISBN univoco
     * @param annoPubblicazione Anno di pubblicazione.
     * @param numeroCopieDisponibili Quantità iniziale.
     * @return Messaggio di esito.
     */
    public String aggiungi(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
        return null;
    }
    
    /**
     * @brief Aggiorna il numero di copie disponibili di un libro.
     * @pre Deve esistere un libro con il codice ISBN specificato.
     * @pre Il nuovo numero di copie deve essere >= 0.
     * @post Il campo "numero di copie disponibili" del libro è aggiornato.
     * @param codiceISBN Il libro da modificare.
     * @param nuoveCopie Il nuovo numero totale di copie.
     * @return Messaggio di esito.
     */
    public String aggiornaCopie(String codiceISBN, int nuoveCopie){
        return null;
    }
    
    /**
     * @brief Rimuove un libro dal catalogo.
     * @pre Deve esistere un libro con l'ISBN specificato.
     * @pre Nessuna copia del libro deve essere attualmente in prestito.
     * @post Il libro è rimosso fisicamente dall'archivio.
     * @param codiceISBN L'ISBN del libro da rimuovere.
     * @return Messaggio di esito.
     */
    public String rimuovi(String codiceISBN){
        return null;
    }
    
    /**
     * @brief Cerca libri per titolo, autore o ISBN.
     * @post Restituisce una lista di libri che corrispondono ai criteri di ricerca.
     * @param titolo Filtro titolo (opzionale)
     * @param autore Filtro autore (opzionale)
     * @param codiceISBN Filtro ISBN (opzionale)
     * @return Lista dei libri trovati.
     */
    public List<Libro> cerca(String titolo, String autore, String codiceISBN){
        return null;
    }
    
    /**
     * @brief Recupera l'elenco completo dei libri.
     * @return Lista di tutti i libri nel catalogo.
     */
    public List<Libro> getLista(){
        return arcLibri.leggiTutti();
    }
    
    
    
    
    
}
