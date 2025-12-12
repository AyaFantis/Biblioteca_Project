/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import it.unisa.biblioteca.gruppo21.entity.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * @file ArchivePrestiti.java
 * @brief Implementazione concreta dell'archivio per la gestione dei prestiti.
 * @class ArchivePrestiti
 * Questa classe gestisce la persistenza dell'entità associativa Prestito.
 * Affronta la complessità del salvataggio di relazioni (Utente-Libro) e di tipi di dato
 * complessi come date ed enumerazioni.
 * @author Gruppo 21
 * @version 1.0
 */
public class ArchivePrestiti extends ArchiveAstratto<Prestito> {

    /**
     * @brief Costruttore dell'archivio prestiti.
     * @post Viene inizializzato un archivio collegato al file fisico "Prestiti.txt".
     */
    public ArchivePrestiti(){
        super("Prestiti.txt");   
    }
    
    /**
     * @brief Converte un oggetto Prestito in una stringa CSV.
     * @pre Il parametro p non deve essere null.
     * @pre L'oggetto Prestito deve contenere riferimenti validi a Utente e Libro.
     * @post Restituisce una stringa nel formato: Matricola;ISBN;DataRestituzione;Stato.
     * @param p L'oggetto Prestito da serializzare.
     * @return Stringa formattata per il file.
     */
    @Override
    protected String serializza(Prestito p) {
        String mat = p.getUtente().getMatricola(); 
        String isbn = p.getLibro().getCodiceISBN();
        
        String dataStr = (p.getDataRestituzione() == null) ? "null" : p.getDataRestituzione().toString();

        return mat + ";" + isbn + ";" + dataStr + ";" + p.getStato();
    }

    /**
     * @brief Ricostruisce un oggetto Prestito da una riga CSV.
     * Crea oggetti Utente e Libro contenenti solo gli ID (Matricola/ISBN)
     * @pre La riga non deve essere null.
     * @post Restituisce un oggetto Prestito valido se il parsing ha successo.
     * @post Restituisce null se la data è malformata o lo stato non è valido.
     * @param riga La stringa letta dal file.
     * @return L'oggetto Prestito ricostruito.
     */
    @Override
    protected Prestito deserializza(String riga) {
        try {
            String[] parti = riga.split(";");
            if (parti.length != 4) return null;

            String matricola = parti[0];
            String isbn = parti[1];
            String dataStr = parti[2];
            String statoStr = parti[3];

            // 1. Parsing della Data
            LocalDate dataRestituzione = null;
            if (!dataStr.equals("null")) {
                dataRestituzione = LocalDate.parse(dataStr); // Formato ISO standard (YYYY-MM-DD)
            }

            // 2. Parsing dello Stato (Enum)
            Prestito.StatoPrestito stato = Prestito.StatoPrestito.valueOf(statoStr);

            // 3. Creazione Oggetti "Stub" (Contenitori solo per l'ID)
            // Non abbiamo i dati completi (Nome, Titolo, ecc), ma per l'archivio basta l'ID.
            // Il Service poi userà questi ID per recuperare i dati completi dagli altri archivi se serve.
            Utente uStub = new Utente("", "", "", matricola); 
            Libro lStub = new Libro("", "", isbn, 0, 0);

            return new Prestito(uStub, lStub, dataRestituzione, stato);

        } catch (DateTimeParseException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            // DateTimeParseException: Data corrotta
            // IllegalArgumentException: Stato enum non valido
            return null;
        }
    }

    /**
     * @brief Cerca un prestito per ID.
     * @param id Identificativo generico.
     */
    
    @Override
    public Prestito cerca(String id) {
        return null;
    }
    
    public Prestito cercaPrestito(String matricola, String isbn) {
        for (Prestito p : cache) {
            if (p.getUtente().getMatricola().equals(matricola) && 
                p.getLibro().getCodiceISBN().equals(isbn) &&
                p.getStato() == Prestito.StatoPrestito.ATTIVO) {
                return p;
            }
        }
        return null;
    }
    
}
