/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.entity.*;
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
        assertEquals("Famiglietti", utente.getCognome());
        assertEquals("m.famiglietti17@studenti.unisa.it", utente.getEmail());
        assertEquals("0612709286", utente.getMatricola());
    } 
        
    @Test
    public void testSetEmail() {
        Utente u = new Utente("Luca", "Verdi", "123", "vecchia@email.it");
        u.setEmail("nuova@email.it");
        assertEquals("nuova@email.it", u.getEmail());
    }
    
@Test
    public void testAggiungiPrestito() {
        // 1. Creo l'Utente
        Utente utente = new Utente("Mario", "Rossi", "001", "m.rossi@unisa.it");
        
        // 2. Creo un Libro (necessario per il costruttore di Prestito)
        Libro libro = new Libro("Java Base", "Autore X", "ISBN-111", 2020, 5);

        // 3. Creo il Prestito passando gli OGGETTI, non le stringhe
        // Parametri: Utente, Libro, DataRestituzione (null perché è nuovo), Stato
        Prestito prestito = new Prestito(utente, libro, null, Prestito.StatoPrestito.ATTIVO);

        // 4. Testo il metodo aggiungiPrestito
        utente.aggiungiPrestito(prestito);

        // 5. Verifiche
        assertEquals(1, utente.getPrestitiAttivi().size(), "La lista deve contenere 1 prestito");
        assertTrue(utente.getPrestitiAttivi().contains(prestito), "Il prestito specifico deve essere nella lista");
    }

    @Test
    public void testRimuoviPrestito() {
        // 1. Setup
        Utente utente = new Utente("Luca", "Verdi", "002", "l.verdi@unisa.it");
        Libro libro = new Libro("Harry Potter", "Rowling", "ISBN-222", 2000, 3);
        
        // Creo il prestito correttamente
        Prestito prestito = new Prestito(utente, libro, null, Prestito.StatoPrestito.ATTIVO);
        
        // Lo aggiungo manualmente per preparare il test
        utente.aggiungiPrestito(prestito);
        assertEquals(1, utente.getPrestitiAttivi().size());

        // 2. Azione: Rimuovo
        utente.rimuoviPrestito(prestito);

        // 3. Verifica
        assertTrue(utente.getPrestitiAttivi().isEmpty(), "La lista deve essere vuota dopo la rimozione");
    }
    @Test
    public void testToString() {
        Utente u = new Utente("Mario", "Rossi", "m.rossi@unisa.it", "0512100001");
        String output = u.toString();
        
        assertTrue(output.contains("Mario"), "Il toString deve contenere il nome");
        assertTrue(output.contains("Rossi"), "Il toString deve contenere il cognome");
        assertTrue(output.contains("0512100001"), "Il toString deve contenere la matricola");
    }
    
    @Test
    public void testOrdinamentoPerCognomePoiNome() {
        Utente u1 = new Utente("Mario", "Bianchi", "email1", "001");
        Utente u2 = new Utente("Anna", "Rossi", "email2", "002");
        Utente u3 = new Utente("Luca", "Rossi", "email3", "003");

        assertTrue(u1.compareTo(u2) < 0, "Bianchi deve precedere Rossi");
        
        assertTrue(u2.compareTo(u3) < 0, "Anna deve precedere Luca a parità di cognome");
    }
}
