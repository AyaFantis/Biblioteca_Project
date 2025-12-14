/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @file ServiceUtenti.java
 * @brief Gestisce tutte le operazioni relative agli utenti (Studenti/Docenti).
 * @class ServiceUtenti
 * Si occupa di iscrivere nuovi utenti controllando che i dati siano corretti
 * e di rimuoverli, ma solo se non hanno libri ancora da restituire.
 * @author Gruppo 21
 * @version 1.0
 */
public class ServiceLibri {

    /** Riferimento all'archivio dei Libri. */
    private final ArchiveLibri arcLibri;
    /** Riferimento all'archivio dei Prestiti. */
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
    public String aggiungi(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili) {
        if (titolo == null || titolo.length() == 0) {
            return "Errore:Il titolo è obbligatorio.";
        }
        if (autore == null || autore.length() == 0) {
            return "Errore:L'autore è obbligatorio.";
        }
        
        if (!Validatore.validaISBN(codiceISBN)) { 
            return "Errore: ISBN non valido.";
        }
        
        int annoCorrente = Year.now().getValue();
        if (annoPubblicazione < 1000 || annoPubblicazione > annoCorrente) {
            return "Errore: Anno di pubblicazione non valido (1000 - " + annoCorrente + ").";
        }
        
        if (numeroCopieDisponibili < 0) {
            return "Errore: Il numero di copie non può essere negativo.";
        }

        if (arcLibri.cerca(codiceISBN) != null) {
            return "Errore: Un libro con questo ISBN è già presente in catalogo.";
        }

        Libro nuovoLibro = new Libro(titolo, autore, codiceISBN, annoPubblicazione, numeroCopieDisponibili);
        
        try {
            arcLibri.aggiungi(nuovoLibro);
            return "Libro aggiunto correttamente al catalogo.";
        } catch (IOException e) {
            return "Errore di sistema durante il salvataggio: " + e.getMessage();
        }
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
   public String aggiornaCopie(String codiceISBN, int nuoveCopie) {
       
        if (codiceISBN == null || codiceISBN.length() == 0) {
            return "Errore: ISBN non specificato.";
        }
        if (nuoveCopie < 0) {
            return "Errore: Il numero di copie non può essere negativo.";
        }

        Libro libro = arcLibri.cerca(codiceISBN);
        if (libro == null) {
            return "Errore: Nessun libro trovato con questo ISBN.";
        }

        try {
            libro.setNumeroCopieDisponibili(nuoveCopie);
            arcLibri.salvaTutto(); 
            return "Copie aggiornate con successo. Totale attuale: " + nuoveCopie;
        } catch (IOException e) {
            return "Errore durante l'aggiornamento: " + e.getMessage();
        }
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
        
        if (codiceISBN == null)
            return "Errore: ISBN nullo";
        
        Libro libro = arcLibri.cerca(codiceISBN);
        if(libro == null){
        
            return "Errore: Libro non trovato in catologo.";
        }
        List<Prestito> prestiti = arcPrestiti.leggiTutti();
        for(Prestito p : prestiti){
        
            if (p.getStato() == Prestito.StatoPrestito.ATTIVO && p.getLibro().getCodiceISBN().equals(codiceISBN)){
                
                return "Errore: Impossibile rimuovere. Ci sono copie attualmente in prestito.";
            }
        }
        try {
            arcLibri.cancella(libro);
            return "Successo: Libro rimosso dal catalogo.";
        }  catch (IOException ex) {
            return "Errore critico durante la cancellazione: " + ex.getMessage();
            
        }
    }
    
    /**
     * @brief Recupera l'elenco completo dei libri.
     * @return Lista di tutti i libri nel catalogo.
     */
    public List<Libro> getLista(){
        List<Libro> lista = arcLibri.leggiTutti();
        
        Collections.sort(lista);
        return lista;
    }
    
    /**
     * @brief Cerca libri per titolo, autore e/o ISBN.
     * I parametri null o vuoti vengono ignorati nella ricerca.
     * Se tutti i parametri sono null, restituisce la lista completa.
     * @param titolo Titolo (o parte di esso) da cercare.
     * @param autore Autore (o parte di esso) da cercare.
     * @param isbn ISBN da cercare.
     * @return Lista dei libri che soddisfano tutti i criteri specificati.
     */
    public List<Libro> cerca(String titolo, String autore, String isbn) {
        List<Libro> tutti = getLista();
        List<Libro> risultati = new ArrayList<>();

        if ((titolo == null || titolo.isEmpty()) && 
            (autore == null || autore.isEmpty()) && 
            (isbn == null || isbn.isEmpty())) {
            return new ArrayList<>(tutti);
        }

         for(Libro l : tutti){
            boolean match = true;
            if (isbn != null && !isbn.isEmpty()) {
                if (!l.getCodiceISBN().equals(isbn)) { 
                    match = false;
                }
            }
            
            if (match && titolo != null && !titolo.isEmpty()) {
                if (!l.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                    match = false;
                }
            }
            
            if (match && autore != null && !autore.isEmpty()) {
                if (!l.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                    match = false;
                }
            }
            
            if (match) {
                risultati.add(l);
            }
        }
        return risultati;
    }
}
    
