/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.archive.ArchiveUtenti;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.util.List;


/**
 *
 * @author manue
 */
public class Biblioteca {
   
    private final ServiceUtenti serviceUtenti;
    private final ServiceLibri serviceLibri;
    private final ServicePrestiti servicePrestiti;
    
    public Biblioteca(){
    
        ArchiveUtenti archivioUtenti = new ArchiveUtenti();
        ArchiveLibri archivioLibri = new ArchiveLibri();
        ArchivePrestiti archivioPrestiti = new ArchivePrestiti();
        
        this.serviceUtenti = new ServiceUtenti(archivioUtenti, archivioPrestiti);
        this.serviceLibri = new ServiceLibri(archivioLibri, archivioPrestiti);
        this.servicePrestiti = new ServicePrestiti(archivioPrestiti, archivioLibri, archivioUtenti);

    }
    
    //
    // METODI UTENTE
    //
    
    public String iscriviUtente(String nome, String cognome, String matricola, String email){
        return serviceUtenti.iscrivi(nome, cognome, matricola, email);
    }
    
    public String modificaUtente(String nuovoNome, String nuovoCognome, String nuovaEmail, String matricola){
        //TODO
        return null;
    }
    public String rimuoviUtente(String matricola){
        return serviceUtenti.rimuovi(matricola);
    }
    
    public List<Utente> getElencoUtenti() { 
        return serviceUtenti.getLista();
    }
    
    public List<Utente> cercaUtenti(String nome, String cognome, String matricola){
        //TODO
        return null;
    }
    
    // 
    //   METODI LIBRO
    //
    
    public String aggiungiLibro(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
        return serviceLibri.aggiungi(titolo, autore, codiceISBN, annoPubblicazione, numeroCopieDisponibili);
    }

    public String rimuoviLibro(String codiceISBN){
       return serviceLibri.rimuovi(codiceISBN); 
    }
    
    public List<Libro> getElencoLibri() { 
        return serviceLibri.getLista(); 
    }
    
    public List<Libro> cercaLibri(String titolo, String autore, String codiceISBN) {
        return serviceLibri.cerca(titolo, autore, codiceISBN);
    }
    
    //
    //METODI PRESTITO
    //
    public String effettuaPrestito(String matricola, String codiceISBN){
        return servicePrestiti.nuovoPrestito(matricola, codiceISBN);
    }
    
    public String restituisciLibro(String matricola, String codiceISBN){
        return servicePrestiti.restituzione(matricola, codiceISBN);
    }
    
    public List<Prestito> getStoricoPrestiti(){
        return servicePrestiti.getLista();
    }
    
    public void controllaRitardi(){
        //TODO
    }
    
    
    
    
 
    
    
    
    
    
}
