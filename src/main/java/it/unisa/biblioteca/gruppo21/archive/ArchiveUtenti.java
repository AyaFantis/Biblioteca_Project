/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import it.unisa.biblioteca.gruppo21.entity.Utente;

/**
 * @file ArchiveUtenti.java
 * @brief Implementazione concreta dell'archivio per la gestione degli utenti.
 * @class ArchiveUtenti
 * Questa classe specializza ArchiveAstratto per gestire la persistenza
 * degli oggetti Utente su file di testo, ed implementa la logica di mapping
 * CSV specifica per i dati anagrafici.
 * @author Gruppo 21
 * @version 1.0
 */
public class ArchiveUtenti extends ArchiveAstratto<Utente> {

    /**
     * @brief Costruttore dell'archivio utenti.
     * @post Viene inizializzato un archivio collegato al file fisico "Utenti.txt".
     */
    public ArchiveUtenti(){
        super("Utenti.txt");
        this.inizializzaDati();
    }
    
    /**
     * @brief Converte un oggetto Utente in una stringa CSV per il salvataggio.
     * @pre Il parametro t non deve essere null
     * @post Restituisce una stringa non nulla.
     * @post Il formato è: Nome;Cognome;Email;Matricola.
     * @param t L'oggetto Utente da serializzare.
     * @return Una stringa formattata pronta per la scrittura su file.
     */
    @Override
    protected String serializza(Utente t) {
        return t.getNome() + ";" + 
           t.getCognome() + ";" + 
           t.getEmail() + ";" + 
           t.getMatricola();
        
    }

    /**
     * @brief Ricostruisce un oggetto Utente da una riga di testo CSV.
     * @pre Il parametro riga non deve essere null.
     * @post Restituisce un oggetto Utente valido se la riga rispetta il formato CSV atteso.
     * @post Restituisce null se la riga non contiene almeno 4 campi.
     * @post Restituisce null in caso di errore imprevisto durante il parsing.
     * @param riga La stringa letta dal file.
     * @return L'oggetto Utente ricostruito, oppure null in caso di errore.
     */
    @Override
    protected Utente deserializza(String riga) {
        try{
            String[] parti = riga.split(";");
            
            if(parti.length != 4){
                return null;
            }
            
            String nome = parti[0].trim();
            String cognome = parti[1].trim();
            String email = parti[2].trim();
            String matricola = parti[3].trim();
            
            return new Utente(nome, cognome, email, matricola);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * @brief Cerca un utente nell'archivio tramite la sua matricola.
     * Implementazione della ricerca per Matricola.
     * @pre Il parametro id (Matricola) non deve essere null.
     * @post Restituisce l'oggetto Utente se esiste nella cache un utente con quella matricola.
     * @post Restituisce null se nessun utente corrisponde alla matricola data.
     * @param id La matricola dell'utente da cercare.
     * @return L'oggetto Utente se presente, altrimenti null.
     */
    @Override
    public Utente cerca(String id) {
        
        for(Utente u : cache){
            if(u.getMatricola().equals(id)){
                return u;
            }
        }
        return null;
    }
    
}
