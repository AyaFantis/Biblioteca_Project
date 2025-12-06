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
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.util.List;


/**
 *
 * @author manue
 */
public class Biblioteca {
   
    private ServiceUtenti serviceUtenti;
    private ServiceLibri serviceLibri;
    private ServicePrestiti servicePrestiti;
    
    public Biblioteca(){
    
        ArchiveUtenti archivioUtente = new ArchiveUtenti();
        ArchiveLibri archivioLibro = new ArchiveLibri();
        ArchivePrestiti archivioPrestito = new ArchivePrestiti();

    }
    
    // METODI UTENTE
    public String iscriviUtente(String nome, String cognome, String matricola, String email){
        //TODO
        return serviceUtenti.iscrivi(nome, cognome, matricola, email);
    }
    
    public String rimuoviUtente(String matricola){
    
        //TODO
        return serviceUtenti.rimuovi(matricola);
    }
    
    public List<Utente> getElencoUtenti() { 
    
        return serviceUtenti.getListaUtenti();
    }
    
    // METODI LIBRO
    public String aggiungiLibro(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
    
        //TODO
        return serviceLibri.aggiungi(titolo, autore, codiceISBN, annoPubblicazione, numeroCopieDisponibili);
    }

    public String rimuoviLibro(String codiceISBN){
    
        //TODO
       return serviceLibri.rimuovi(codiceISBN); 
    }
    
    public List<Libro> getElencoLibri() { 
          
        //TODO
        return serviceLibri.getListaLibri(); 
      }
    
    public List<Libro> cercaLibri(String keyword) {
        
        //TODO
        return serviceLibri.cerca(keyword);
    }
 
    
    
    
    
    
}
