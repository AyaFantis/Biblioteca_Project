/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import org.junit.jupiter.api.Test;
/**
 *
 * @author greta
 */
public class UtenteTest {
    @Test
    public void testCostruttoreEGetter(){
        Utente utente = new Utente("Manuel", "Famiglietti", "m.famiglietti17@studenti.unisa.it", "0612709286");
    
        assertEquals("Manuel", utente.getNome());
        assertEquals("amiglietti", utente.getCognome());
        assertEquals("m.famiglietti17@studenti.unisa.it", utente.getEmail());
        assertEquals("0612709286", utente.getMatricola());
    } 
}
