/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import it.unisa.biblioteca.gruppo21.archive.ArchiveLibri;
import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.archive.ArchiveUtenti;
import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import it.unisa.biblioteca.gruppo21.service.ServicePrestiti;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author greta
 */

public class ServicePrestitiTest {

    private ServicePrestiti service;
    private ArchivePrestiti arcPrestiti;
    private ArchiveLibri arcLibri;
    private ArchiveUtenti arcUtenti;

    private final String FILE_PRESTITI = "Prestiti.txt";
    private final String FILE_LIBRI = "libri.txt";
    private final String FILE_UTENTI = "Utenti.txt";

    private void inizializza() throws IOException {
        new File(FILE_PRESTITI).delete();
        new File(FILE_LIBRI).delete();
        new File(FILE_UTENTI).delete();

        arcPrestiti = new ArchivePrestiti();
        arcLibri = new ArchiveLibri();
        arcUtenti = new ArchiveUtenti();

        service = new ServicePrestiti(arcPrestiti, arcLibri, arcUtenti);

        Utente u = new Utente("Mario", "Rossi", "m.rossi@studenti.unisa.it", "0512100001");
        arcUtenti.aggiungi(u);

        Libro l = new Libro("Harry Potter", "Rowling", "ISBN-HP", 2000, 5);
        arcLibri.aggiungi(l);
        
        Libro lZero = new Libro("Raro", "Autore", "ISBN-ZERO", 2020, 0);
        arcLibri.aggiungi(lZero);
    }

    private void pulisci() {
        new File(FILE_PRESTITI).delete();
        new File(FILE_LIBRI).delete();
        new File(FILE_UTENTI).delete();
    }

    @Test
    public void testNuovoPrestitoValido() throws IOException {
        inizializza();

        String esito = service.nuovoPrestito("0512100001", "ISBN-HP", LocalDate.now());

        assertNotNull(esito, "Il metodo non deve restituire null");
        assertTrue(esito.contains("Successo") || esito.contains("registrato"), "Messaggio di successo atteso");

        assertEquals(1, service.getLista().size(), "Deve esserci 1 prestito in archivio");
        
        Libro l = arcLibri.cerca("ISBN-HP");
        assertEquals(4, l.getNumeroCopieDisponibili(), "Le copie devono scendere da 5 a 4");

        pulisci();
    }

    @Test
    public void testNuovoPrestitoStockEsaurito() throws IOException {
        inizializza();

        String esito = service.nuovoPrestito("0512100001", "ISBN-ZERO", LocalDate.now());

        assertNotNull(esito);
        assertTrue(esito.startsWith("Errore"), "Non deve prestare libri con 0 copie");
        
        assertEquals(0, service.getLista().size());

        pulisci();
    }

    @Test
    public void testNuovoPrestitoDatiErrati() throws IOException {
        inizializza();

        String esitoUtente = service.nuovoPrestito("9999999999", "ISBN-HP", LocalDate.now());
        assertTrue(esitoUtente.startsWith("Errore"), "Utente inesistente deve dare errore");

        String esitoLibro = service.nuovoPrestito("0512100001", "ISBN-INESISTENTE", LocalDate.now());
        assertTrue(esitoLibro.startsWith("Errore"), "Libro inesistente deve dare errore");

        pulisci();
    }

    @Test
    public void testRestituzione() throws IOException {
        inizializza();

        service.nuovoPrestito("0512100001", "ISBN-HP", LocalDate.now());
        assertEquals(1, service.getLista().size());

        String esito = service.restituzione("0512100001", "ISBN-HP");
        
        assertNotNull(esito);
        assertTrue(esito.contains("Successo") || esito.contains("restituito"));

        Libro l = arcLibri.cerca("ISBN-HP");
        assertEquals(5, l.getNumeroCopieDisponibili(), "Le copie devono tornare a 5 dopo la restituzione");

        pulisci();
    }
}
