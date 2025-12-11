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
    
    private final String FILE_NAME = "Prestiti.txt";

    @Test
    public void testSerializza() {
        new File("dummy.txt").delete();
        ArchivePrestiti archive = new ArchivePrestiti();
        
        Utente u = new Utente("M", "R", "001", "email");
        Libro l = new Libro("T", "A", "ISBN-1", 2020, 5);
        LocalDate data = LocalDate.of(2025, 12, 31);
        
        Prestito p = new Prestito(u, l, data, StatoPrestito.ATTIVO);
        
        String risultato = archive.serializza(p);
        
        String atteso = "001;ISBN-1;2025-12-31;ATTIVO";
        
        assertEquals(atteso, risultato);
        
        new File(FILE_NAME).delete();
    }

    @Test
    public void testDeserializza() {
        new File(FILE_NAME).delete();
        ArchivePrestiti archive = new ArchivePrestiti();
        
        String riga = "001;ISBN-1;2025-12-31;ATTIVO";
        
        Prestito p = archive.deserializza(riga);
        
        assertNotNull(p);
        assertEquals("001", p.getUtente().getMatricola());
        assertEquals("ISBN-1", p.getLibro().getCodiceISBN());
        assertEquals(LocalDate.of(2025, 12, 31), p.getDataRestituzione());
        assertEquals(StatoPrestito.ATTIVO, p.getStato());
        
        new File(FILE_NAME).delete();
    }
    
    @Test
    public void testPersistenzaCompleta() throws IOException {
        new File(FILE_NAME).delete();
        ArchivePrestiti sessione1 = new ArchivePrestiti();
        
        Utente u = new Utente("M", "R", "999", "email");
        Libro l = new Libro("T", "A", "ISBN-999", 2020, 5);
        Prestito p = new Prestito(u, l, LocalDate.now(), StatoPrestito.IN_RITARDO);
        
        sessione1.aggiungi(p);
        
        ArchivePrestiti sessione2 = new ArchivePrestiti();
        
        assertEquals(1, sessione2.leggiTutti().size());
        
        Prestito caricato = sessione2.leggiTutti().get(0);
        assertEquals("999", caricato.getUtente().getMatricola());
        assertEquals(StatoPrestito.IN_RITARDO, caricato.getStato());
        
        new File(FILE_NAME).delete();
    }

    @Test
    public void testDeserializzaCorrotta() {
        ArchivePrestiti archive = new ArchivePrestiti();
        
        assertNull(archive.deserializza("001;ISBN;DataSbagliata;ATTIVO"));
        
        assertNull(archive.deserializza("001;ISBN;2020-01-01;STATO_ASSURDO"));
        
        assertNull(archive.deserializza("001;ISBN"));
    }
}
