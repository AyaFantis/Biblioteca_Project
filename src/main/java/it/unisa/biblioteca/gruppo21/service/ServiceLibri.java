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
 *
 * @author manue
 */
public class ServiceLibri {

    private ArchiveLibri arcLibri;
    private ArchivePrestiti arcPrestiti;
    
    public ServiceLibri(ArchiveLibri arcLibri,ArchivePrestiti arcPrestiti){
    
        this.arcLibri = arcLibri;
        this.arcPrestiti = arcPrestiti;
    }
    
    public String aggiungi(String titolo, String autore, String codiceISBN, int annoPubblicazione, int numeroCopieDisponibili){
    
        return null;
    }
    
    public String rimuovi(String codiceISBN){
    
        return null;
    }
    
    public List<Libro> cerca(String titolo, String autore, String codiceISBN){
    
        return arcLibri.leggiTutti();
    }
    
    public List<Libro> getLista(){
        return arcLibri.leggiTutti();
    }
    
    
    
    
    
}
