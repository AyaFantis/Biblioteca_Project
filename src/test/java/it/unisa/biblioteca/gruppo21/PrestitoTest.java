/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.entity.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
/**
 *
 * @author greta
 */
public class PrestitoTest {
    @Test
    public void testCostruttoreEGetter() {
        Utente utente = new Utente("Mario", "Rossi", "001", "m.rossi@unisa.it");
        Libro libro = new Libro("Java Base", "Autore", "ISBN-123", 2020, 5);
        LocalDate dataFine = LocalDate.of(2024, 12, 31); // Una data futura di esempio

        Prestito prestito = new Prestito(utente, libro, dataFine, Prestito.StatoPrestito.ATTIVO);

        assertEquals(utente, prestito.getUtente());
        assertEquals(libro, prestito.getLibro());
        
        assertEquals(dataFine, prestito.getDataRestituzione());
        assertEquals(Prestito.StatoPrestito.ATTIVO, prestito.getStato());
    }

    @Test
    public void testCambioStato() {
        Utente u = new Utente("Luigi", "Verdi", "002", "l.verdi@unisa.it");
        Libro l = new Libro("Harry Potter", "Rowling", "ISBN-999", 2000, 3);
        
        Prestito prestito = new Prestito(u, l, null, Prestito.StatoPrestito.ATTIVO);
        assertEquals(Prestito.StatoPrestito.ATTIVO, prestito.getStato());

        prestito.setStato(Prestito.StatoPrestito.RESTITUITO);

        assertEquals(Prestito.StatoPrestito.RESTITUITO, prestito.getStato(), "Lo stato dovrebbe essere aggiornato a RESTITUITO");
    }
    
    @Test
    public void testPrestitoInRitardo() {
        Utente u = new Utente("Test", "User", "003", "test@unisa.it");
        Libro l = new Libro("Libro Test", "Autore", "ISBN-000", 2022, 1);
        
        Prestito prestito = new Prestito(u, l, null, Prestito.StatoPrestito.IN_RITARDO);
        
        assertEquals(Prestito.StatoPrestito.IN_RITARDO, prestito.getStato());
    }
}
