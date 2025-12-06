/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import it.unisa.biblioteca.gruppo21.entity.Prestito;

/**
 *
 * @author manue
 */
public class ArchivePrestiti extends ArchiveAstratto<Prestito> {

    public ArchivePrestiti(){
        super("Prestiti.txt");   
    }
    @Override
    protected String serializza(Prestito p) {
        return null;
    }

    @Override
    protected Prestito deserializza(String riga) {
        return null;
    }

    @Override
    public Prestito cerca(String id) {
        return null;
    }
    
}
