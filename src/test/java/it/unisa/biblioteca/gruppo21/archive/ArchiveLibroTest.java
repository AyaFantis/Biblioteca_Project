/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.entity.*;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author greta
 */
public class ArchiveLibroTest {
    
    @Test
    public void testSerializza() {
        new File("test_serializza.txt").delete();

        ArchiveLibri archive = new ArchiveLibri(); 

        Libro libro = new Libro("Harry Potter", "Rowling", "978-12345", 2000, 10);

        String risultato = archive.serializza(libro);

        String atteso = "Harry Potter;Rowling;978-12345;2000;10";
        
        assertEquals(atteso, risultato, "La stringa CSV deve corrispondere al formato richiesto");

        new File("libri.txt").delete(); 
    }

    @Test
    public void testDeserializzaCorretto() {
        new File("libri.txt").delete();
        ArchiveLibri archive = new ArchiveLibri();

        String rigaCsv = "Il Signore degli Anelli;Tolkien;888-999;1954;5";

        Libro libro = archive.deserializza(rigaCsv);

        assertNotNull(libro, "La deserializzazione di una stringa valida non deve essere null");
        assertEquals("Il Signore degli Anelli", libro.getTitolo());
        assertEquals("Tolkien", libro.getAutore());
        assertEquals("888-999", libro.getCodiceISBN());
        assertEquals(1954, libro.getAnnoPubblicazione());
        assertEquals(5, libro.getNumeroCopieDisponibili());

        new File("libri.txt").delete();
    }
    
    @Test
    public void testCerca() throws Exception {

        new File("libri.txt").delete();
        ArchiveLibri archive = new ArchiveLibri();

        Libro l1 = new Libro("Divina Commedia", "Dante", "ISBN-111", 1320, 1);
        Libro l2 = new Libro("I Promessi Sposi", "Manzoni", "ISBN-222", 1827, 1);

        archive.aggiungi(l1);
        archive.aggiungi(l2);

        Libro trovato = archive.cerca("ISBN-111");

        assertNotNull(trovato, "Il libro con ISBN-111 dovrebbe essere trovato");
        assertEquals("Divina Commedia", trovato.getTitolo(), "Il titolo del libro trovato deve corrispondere");
        assertEquals("Dante", trovato.getAutore(), "L'autore deve corrispondere");

        Libro nonTrovato = archive.cerca("ISBN-999");

        assertNull(nonTrovato, "La ricerca di un ISBN inesistente deve restituire null");

        new File("libri.txt").delete();
    }
}
