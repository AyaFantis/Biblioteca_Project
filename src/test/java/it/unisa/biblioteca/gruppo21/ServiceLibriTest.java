/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.service.ServiceLibri;
import java.io.File;
import java.time.Year;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author greta
 */

public class ServiceLibriTest {
    
    // Attributi della classe
    private ServiceLibri service;
    private ArchiveLibri arcLibri;
    private ArchivePrestiti arcPrestiti;
    
    private final String FILE_LIBRI = "libri.txt";
    private final String FILE_PRESTITI = "Prestiti.txt";

    private void inizializza() {
  
        new File(FILE_LIBRI).delete();
        new File(FILE_PRESTITI).delete();
        
        // 2. Crea i nuovi oggetti
        arcLibri = new ArchiveLibri();
        arcPrestiti = new ArchivePrestiti();
        service = new ServiceLibri(arcLibri, arcPrestiti);
    }

    private void pulisci() {
        new File(FILE_LIBRI).delete();
        new File(FILE_PRESTITI).delete();
    }

    @Test
    public void testAggiungiLibroValido() {
        inizializza();

        String esito = service.aggiungi("Harry Potter", "Rowling", "1234567890", 2000, 10);
        
        assertTrue(esito.contains("correttamente"), "Dovrebbe confermare l'aggiunta");
        assertEquals(1, service.getLista().size(), "La lista deve contenere 1 libro");

        pulisci();
    }

    @Test
    public void testAggiungiInputNonValidi() {
        inizializza();

        String esitoTitolo = service.aggiungi(null, "Autore", "1234567890", 2020, 5);
        assertTrue(esitoTitolo.startsWith("Errore"), "Titolo null deve dare errore");

        String esitoIsbn = service.aggiungi("Titolo", "Autore", "123", 2020, 5);
        assertTrue(esitoIsbn.contains("ISBN"), "ISBN corto deve dare errore");

        int annoFuturo = Year.now().getValue() + 5;
        String esitoAnno = service.aggiungi("Titolo", "Autore", "1234567890", annoFuturo, 5);
        assertTrue(esitoAnno.contains("Anno"), "Anno futuro deve dare errore");

        String esitoCopie = service.aggiungi("Titolo", "Autore", "1234567890", 2020, -1);
        assertTrue(esitoCopie.contains("negativo"), "Copie negative deve dare errore");

        pulisci();
    }

    @Test
    public void testAggiungiDuplicato() {
        inizializza();

        service.aggiungi("Libro A", "Autore A", "1111111111", 2020, 5);
        
        String esito = service.aggiungi("Libro B", "Autore B", "1111111111", 2021, 3);
        
        assertTrue(esito.startsWith("Errore"), "Non deve permettere ISBN duplicati");
        assertEquals(1, service.getLista().size(), "Non deve aver aggiunto il secondo libro");

        pulisci();
    }

    @Test
    public void testAggiornaCopie() {
        inizializza();

        String isbn = "8888888888";
        service.aggiungi("Test Update", "Autore", isbn, 2020, 5);
        
        String esito = service.aggiornaCopie(isbn, 15);
        
        assertTrue(esito.contains("successo"));
        Libro l = service.getLista().get(0);
        assertEquals(15, l.getNumeroCopieDisponibili());

        pulisci();
    }

    @Test
    public void testAggiornaCopieErrori() {
        inizializza();

        String isbn = "9999999999";
        
        String esitoNonTrovato = service.aggiornaCopie(isbn, 10);
        assertTrue(esitoNonTrovato.startsWith("Errore"));
        
        service.aggiungi("Test", "Autore", isbn, 2020, 5);
        String esitoNegativo = service.aggiornaCopie(isbn, -5);
        assertTrue(esitoNegativo.contains("negativo"));

        pulisci();
    }

    @Test
    public void testCercaConFiltri() {
        inizializza();

        service.aggiungi("Harry Potter 1", "Rowling", "9999999999", 2000, 5);
        service.aggiungi("Harry Potter 2", "Rowling", "9999999998", 2002, 5);
        service.aggiungi("Il Signore degli Anelli", "Tolkien", "9999999997", 1954, 3);

        List<Libro> resAutore = service.cerca("", "Rowling", "");
        assertEquals(2, resAutore.size());

        List<Libro> resTitolo = service.cerca("Signore", "", "");
        assertEquals(1, resTitolo.size());

        List<Libro> resIsbn = service.cerca("", "", "9999999998");
        assertEquals(1, resIsbn.size());

        List<Libro> resZero = service.cerca("Harry", "Tolkien", "");
        assertEquals(0, resZero.size());
        
        pulisci();
    }
    
    @Test
    public void testGetListaOrdinata() {
        inizializza();
        
        service.aggiungi("Zanna Bianca", "London", "9999999996", 1900, 1);
        service.aggiungi("Anna Karenina", "Tolstoj", "9999999995", 1870, 1);
        
        List<Libro> lista = service.getLista();
        
        assertEquals("Anna Karenina", lista.get(0).getTitolo());
        assertEquals("Zanna Bianca", lista.get(1).getTitolo());

        pulisci();
    }
}
