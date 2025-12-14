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
    
    private final ArchiveUtenti archivioUtenti;
    private final ArchiveLibri archivioLibri;

    /**
     * @brief Costruttore dell'archivio prestiti.
     * @post Viene inizializzato un archivio collegato al file fisico "Prestiti.txt".
     */
    public ArchivePrestiti(ArchiveUtenti archivioUtenti, ArchiveLibri archivioLibri){
        super("Prestiti.txt");   
        this.archivioUtenti = archivioUtenti;
        this.archivioLibri = archivioLibri;
    }
    
    /**
     * @brief Converte un oggetto Prestito in una stringa CSV.
     * @pre Il parametro p non deve essere null.
     * @pre L'oggetto Prestito deve contenere riferimenti validi a Utente e Libro.
     * @post Restituisce una stringa nel formato: Matricola;ISBN;DataRestituzione;Stato.
     * @param prestitoDaSalvare L'oggetto Prestito da serializzare.
     * @return Stringa formattata per il file.
     */
    @Override
    protected String serializza(Prestito prestitoDaSalvare) {
        String matricolaUtente = prestitoDaSalvare.getUtente().getMatricola(); 
        String isbnLibro = prestitoDaSalvare.getLibro().getCodiceISBN();
        
        String dataRestituzioneStringa = (prestitoDaSalvare.getDataRestituzione() == null) ? "null" : prestitoDaSalvare.getDataRestituzione().toString();

        return matricolaUtente + ";" + isbnLibro + ";" + dataRestituzioneStringa + ";" + prestitoDaSalvare.getStato();
    }

    /**
     * @brief Ricostruisce un oggetto Prestito da una riga CSV.
     * Crea oggetti Utente e Libro contenenti solo gli ID (Matricola/ISBN)
     * @pre La riga non deve essere null.
     * @post Restituisce un oggetto Prestito valido se il parsing ha successo.
     * @post Restituisce null se la data è malformata o lo stato non è valido.
     * @param rigaLetta La stringa letta dal file.
     * @return L'oggetto Prestito ricostruito.
     */
    @Override
    protected Prestito deserializza(String rigaLetta) {
        try {
            String[] parti = rigaLetta.split(";");
            if (parti.length != 4) return null;

            String matricolaUtente = parti[0].trim();
            String isbnLibro = parti[1].trim();
            String dataStringa = parti[2].trim();
            String statoStringa = parti[3].trim();

            LocalDate dataRestituzione = null;
            if (!dataStringa.equals("null")) {
                dataRestituzione = LocalDate.parse(dataStringa); // Formato standard (YYYY-MM-DD)
            }

            Prestito.StatoPrestito statoCorrente = Prestito.StatoPrestito.valueOf(statoStringa);

            Utente utenteReale = archivioUtenti.cerca(matricolaUtente);
            Libro libroReale = archivioLibri.cerca(isbnLibro);
            
            if (utenteReale == null || libroReale == null) {
                System.err.println("Attenzione: Prestito orfano trovato nel file (Utente o Libro mancante). Riga ignorata.");
                return null;
            }

            return new Prestito(utenteReale, libroReale, dataRestituzione, statoCorrente);

        } catch (DateTimeParseException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
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
}
