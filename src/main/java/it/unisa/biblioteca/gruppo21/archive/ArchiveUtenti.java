/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import it.unisa.biblioteca.gruppo21.entity.Utente;

/**
 *
 * @author manue
 */
public class ArchiveUtenti extends ArchiveAstratto<Utente> {

    public ArchiveUtenti(){
        super("Utenti.txt");
    }
    @Override
    protected String serializza(Utente t) {
        return null;
    }

    @Override
    protected Utente deserializza(String riga) {
        return null;
    }

    @Override
    public Utente cerca(String id) {
        return null;
    }
    
}
