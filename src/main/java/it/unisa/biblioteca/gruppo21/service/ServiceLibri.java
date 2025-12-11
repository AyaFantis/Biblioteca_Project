/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
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
    public String aggiungi(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili) {
        if (titolo == null || titolo.length() == 0) {
            return "Errore:Il titolo è obbligatorio.";
        }
        if (autore == null || autore.length() == 0) {
            return "Errore:L'autore è obbligatorio.";
        }
        
        if (!Validatore.validaISBN(codiceISBN)) {
            return "Errore: Codice ISBN non valido (richiesto formato a 13 cifre).";
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

        // 3. Creazione e Persistenza
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
        // 1. Validazione Input
        if (codiceISBN == null || codiceISBN.length() == 0) {
            return "Errore: ISBN non specificato.";
        }
        if (nuoveCopie < 0) {
            return "Errore: Il numero di copie non può essere negativo.";
        }

        // 2. Verifica Esistenza
        Libro libro = arcLibri.cerca(codiceISBN);
        if (libro == null) {
            return "Errore: Nessun libro trovato con questo ISBN.";
        }

        // 3. Aggiornamento e Persistenza
        try {
            libro.setNumeroCopieDisponibili(nuoveCopie);
            // Ipotizziamo che salvaTutto renda persistenti le modifiche in memoria
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
        return null;
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

        // Se non viene passato nessun filtro, restituire tutto o niente.
        if ((titolo == null || titolo.isEmpty()) && 
            (autore == null || autore.isEmpty()) && 
            (isbn == null || isbn.isEmpty())) {
            return new ArrayList<>(tutti);
        }

        for (Libro l : tutti) {
            boolean match = true;
            //Filtro su ISBN (ricerca esatta o parziale)
            if (isbn != null && !isbn.isEmpty()) {
                if (!l.getCodiceISBN().equals(isbn)) { // O contains, se parziale
                    match = false;
                }
            }
            //Filtro su Titolo
            if (match && titolo != null && !titolo.isEmpty()) {
                if (!l.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                    match = false;
                }
            }
            //Filtro su Autore 
            if (match && autore != null && !autore.isEmpty()) {
                if (!l.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                    match = false;
                }
            }
            // Se il libro ha superato tutti i filtri attivi, lo aggiungiamo
            if (match) {
                risultati.add(l);
            }
        }
        return risultati;
    }
}
    
