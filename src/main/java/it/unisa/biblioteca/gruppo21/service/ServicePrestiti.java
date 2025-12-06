/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.archive.ArchiveUtenti;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import java.util.List;

/**
 *
 * @author manue
 */
public class ServicePrestiti {

    private ArchivePrestiti arcPrestiti;
    private ArchiveLibri arcLibri;
    private ArchiveUtenti arcUtenti;
    
    public ServicePrestiti(ArchivePrestiti arcPrestiti,  ArchiveLibri arcLibri, ArchiveUtenti arcUtenti){
    
        this.arcPrestiti = arcPrestiti;
        this.arcUtenti = arcUtenti;
        this.arcLibri = arcLibri;
    }
    
    public String nuovoPrestito(String matricola, String codiceISBN){
    
        return null;
    }
    
    public String restituzione(String matricola, String codiceISBN){
    
        return null;
    }
    
    public List<Prestito> getLista(){
    
        return arcPrestiti.leggiTutti();
    }
}
