/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.entity.*;
import it.unisa.biblioteca.gruppo21.entity.Prestito.StatoPrestito;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
/**
 *
 * @author greta
 */
public class ArchivePrestitiTest {
    
    private final String FILE_PRESTITI = "Prestiti.txt";
    private final String FILE_UTENTI = "Utenti.txt";
    private final String FILE_LIBRI = "libri.txt";
    
    private ArchiveUtenti arcUtenti;
    private ArchiveLibri arcLibri;
    
    private void pulisciFiles() {
        new File(FILE_PRESTITI).delete();
        new File(FILE_UTENTI).delete();
        new File(FILE_LIBRI).delete();
    }

    @Test
    public void testSerializza() {
        pulisciFiles();
        
        arcUtenti = new ArchiveUtenti();
        arcLibri = new ArchiveLibri();
        ArchivePrestiti archive = new ArchivePrestiti(arcUtenti, arcLibri);
        
        Utente u = new Utente("M", "R", "email", "001");
        Libro l = new Libro("T", "A", "ISBN-1", 2020, 5);
        LocalDate data = LocalDate.of(2025, 12, 31);
        
        Prestito p = new Prestito(u, l, data, StatoPrestito.ATTIVO);
        
        String risultato = archive.serializza(p);
        
        String atteso = "001;ISBN-1;2025-12-31;ATTIVO";
        
        assertEquals(atteso, risultato);
        
        pulisciFiles();
    }

    @Test
    public void testDeserializza() throws IOException {
        pulisciFiles();
        
        arcUtenti = new ArchiveUtenti();
        Utente u = new Utente("Mario", "Rossi", "m.rossi@studenti.unisa.it", "001");
        arcUtenti.aggiungi(u);
        
        arcLibri = new ArchiveLibri();
        Libro l = new Libro("Titolo", "Autore", "ISBN-1", 2020, 5);
        arcLibri.aggiungi(l);
        
        ArchivePrestiti archive = new ArchivePrestiti(arcUtenti, arcLibri);
        
        String riga = "001;ISBN-1;2025-12-31;ATTIVO";
        
        Prestito p = archive.deserializza(riga);
        
        assertNotNull(p);
        assertEquals("001", p.getUtente().getMatricola());
        assertEquals("ISBN-1", p.getLibro().getCodiceISBN());
        assertEquals(LocalDate.of(2025, 12, 31), p.getDataRestituzione());
        assertEquals(StatoPrestito.ATTIVO, p.getStato());
        
        pulisciFiles();
    }
    
    @Test
    public void testPersistenzaCompleta() throws IOException {
        pulisciFiles();
        
        arcUtenti = new ArchiveUtenti();
        Utente u = new Utente("M", "R", "email", "999");
        arcUtenti.aggiungi(u);
        
        arcLibri = new ArchiveLibri();
        Libro l = new Libro("T", "A", "ISBN-999", 2020, 5);
        arcLibri.aggiungi(l);
        
        ArchivePrestiti sessione1 = new ArchivePrestiti(arcUtenti, arcLibri);
        
        Prestito p = new Prestito(u, l, LocalDate.now(), StatoPrestito.IN_RITARDO);
        sessione1.aggiungi(p);
        
        ArchivePrestiti sessione2 = new ArchivePrestiti(arcUtenti, arcLibri);
        
        assertEquals(1, sessione2.leggiTutti().size());
        
        Prestito caricato = sessione2.leggiTutti().get(0);
        assertEquals("999", caricato.getUtente().getMatricola());
        assertEquals(StatoPrestito.IN_RITARDO, caricato.getStato());
        
        pulisciFiles();
    }

    @Test
    public void testDeserializzaCorrotta() {
        pulisciFiles();
        
        arcUtenti = new ArchiveUtenti();
        arcLibri = new ArchiveLibri();
        ArchivePrestiti archive = new ArchivePrestiti(arcUtenti, arcLibri);
        
        assertNull(archive.deserializza("001;ISBN;DataSbagliata;ATTIVO"));
        
        assertNull(archive.deserializza("001;ISBN;2020-01-01;STATO_ASSURDO"));
        
        assertNull(archive.deserializza("001;ISBN"));
        
        pulisciFiles();
    }
}
