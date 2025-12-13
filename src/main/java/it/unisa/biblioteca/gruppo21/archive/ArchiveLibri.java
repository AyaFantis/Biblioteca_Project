/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import it.unisa.biblioteca.gruppo21.entity.Libro;

/**
 * @file ArchiveLibri.java
 * @brief Implementazione concreta dell'archivio per la gestione dei libri.
 * @class ArchiveLibri
 * Questa classe specializza ArchiveAstratto per gestire la persistenza
 * degli oggetti Libro su file di testo (formato CSV).
 * Implementa la logica di serializzazione specifica e la ricerca per chiave primaria ISBN.
 * @author Gruppo 21
 * @version 1.0
 */
public class ArchiveLibri extends ArchiveAstratto<Libro> {
     
    /**
     * @brief Costruttore dell'archivio libri.
     * @post Viene inizializzato un archivio collegato al file fisico "libri.txt".
     */
    public ArchiveLibri() {
        super("libri.txt");
    }


    /**
     * @brief Converte un oggetto Libro in una stringa CSV per il salvataggio.
     * @pre Il parametro  t non deve essere null
     * @post Restituisce una stringa non nulla.
     * @post Il formato è: Titolo;Autore;ISBN;Anno;Quantità.
     * @param t L'oggetto Libro da serializzare.
     * @return Una stringa formattata pronta per la scrittura su file.
     */
    @Override
    protected String serializza(Libro t) {
        return t.getTitolo() + ";" + 
           t.getAutore() + ";" + 
           t.getCodiceISBN() + ";" + 
           t.getAnnoPubblicazione() + ";" + 
           t.getNumeroCopieDisponibili();
    }

    /**
     * @brief Ricostruisce un oggetto Libro da una riga di testo CSV.
     * @pre Il parametro riga non deve essere null.
     * @post Restituisce un oggetto Libro valido se la riga rispetta il formato CSV atteso.
     * @post Restituisce null se la riga non contiene 5 campi.
     * @post Restituisce null se i campi numerici (Anno, Quantità) non sono interi validi.
     * @param riga La stringa letta dal file.
     * @return L'oggetto Libro ricostruito, oppure null in caso di errore di parsing.
     */
    @Override
    protected Libro deserializza(String riga) {
        try{
            String[] parti = riga.split(";");
            
            if(parti.length != 5){
                return null;
            }
            
            String titolo = parti[0].trim();
            String autore = parti[1].trim();
            String isbn = parti[2].trim();
            
            int anno = Integer.parseInt(parti[3]);
            int copie = Integer.parseInt(parti[4]);
            
            return new Libro(titolo, autore, isbn, anno, copie);
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            return null;   
        } 
    }
    
/**
     * @brief Cerca un libro nell'archivio tramite il suo codice ISBN.
     * @pre Il parametro ISBN non deve essere null.
     * @post Restituisce l'oggetto Libro se esiste nella cache un libro con quell'ISBN.
     * @post Restituisce null se nessun libro corrisponde all'ISBN dato.
     * @param id Il codice ISBN del libro da cercare.
     * @return L'oggetto Libro se presente, altrimenti null.
     */
    @Override
    public Libro cerca(String id) {
        
        for(Libro l : cache){
            if(l.getCodiceISBN().equals(id)){
                return l;
            }
        }
        return null; 
    }
    
}
