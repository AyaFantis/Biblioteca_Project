/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import it.unisa.biblioteca.gruppo21.entity.Libro;

/**
 *
 * @author manue
 */
public class ArchiveLibri extends ArchiveAstratto<Libro> {
     
    public ArchiveLibri() {
        super("libri.txt");
    }


    @Override
    protected String serializza(Libro t) {
        return null; 
    }

    @Override
    protected Libro deserializza(String riga) {
        return null; 
    }

    @Override
    public Libro cerca(String id) {
        return null; 
    }
    
}
