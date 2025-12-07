/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.*;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.util.List;


/**
 *
 * @author manue
 */
public class ServiceUtenti {
   
    private ArchiveUtenti arcUtenti;
    private ArchivePrestiti arcPrestiti;
    
    public ServiceUtenti(ArchiveUtenti arcUtenti, ArchivePrestiti arcPrestiti){
    
        this.arcUtenti = arcUtenti;
        this.arcPrestiti = arcPrestiti;
    }
    
    public String iscrivi(String nome, String cognome, String matricola, String email){
    
        return null;
    }
    
    public String rimuovi(String matricola){
    
        return null;
    }
    
    public List<Utente> getLista() {
        return arcUtenti.leggiTutti();
    }
}
