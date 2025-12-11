/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;


import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import org.junit.jupiter.api.Test;
/**
 *
 * @author greta
 */
public class LibroTest {
    @Test
    public void testCostruttoreEGetter(){
        Libro libro = new Libro("Il Piccolo Principe", "Antoine de Saint-Exupéry", "8804648821", 2015, 10);
    
        assertEquals("Il Piccolo Principe", libro.getTitolo());
        assertEquals("Antoine de Saint-Exupéry", libro.getAutore());
        assertEquals("8804648821", libro.getCodiceISBN());
        assertEquals(2015, libro.getAnnoPubblicazione());
        assertEquals(10, libro.getNumeroCopieDisponibili());
    }
    
    @Test
    public void testModificaNumeroCopie() {
        Libro libro = new Libro("Harry Potter", "J.K. Rowling", "0747549559", 1997, 5);
        
        assertEquals(5, libro.getNumeroCopieDisponibili());
        libro.setNumeroCopieDisponibili(3);
        assertEquals(3, libro.getNumeroCopieDisponibili(), "Il numero di copie dovrebbe essere aggiornato a 3");
    }
    
    @Test
    public void testOrdinamentoNaturale() {
        Libro l1 = new Libro("Anna Karenina", "Tolstoj", "1111111111", 2000, 1);
        Libro l2 = new Libro("Zanna Bianca", "London", "2222222222", 1990, 1);
        
        assertTrue(l1.compareTo(l2) < 0, "Anna Karenina dovrebbe venire prima di Zanna Bianca");
        
        Libro l3 = new Libro("Anna Karenina", "UnAltroAutore", "3333333333", 2010, 1);
        
        assertTrue(l1.compareTo(l3) < 0, "A parità di titolo, si deve ordinare per autore");
        
        Libro l4 = new Libro("anna karenina", "tolstoj", "4444444444", 2000, 1);
        assertEquals(0, l1.compareTo(l4), "L'ordinamento dovrebbe ignorare le maiuscole");
    }
    
    @Test
    public void testToString() {
        Libro libro = new Libro("La gaia scienza", "Nietzsche", "8818027409", 2010, 5);
        String stringa = libro.toString();
        assertTrue(stringa.contains("La gaia scienza"));
        assertTrue(stringa.contains("Nietzsche"));
    }
}
