/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
/**
 *
 * @author greta
 */

public class ArchiveUtenteTest {   
    private final String FILE_NAME = "Utenti.txt";

    @Test
    public void testSerializza() {
        
        new File("test_dummy.txt").delete();
        ArchiveUtenti archive = new ArchiveUtenti();
        
        Utente u = new Utente("Mario", "Rossi", "0512100001", "m.rossi@unisa.it");
        
        String risultato = archive.serializza(u);
        
        String atteso = "Mario;Rossi;0512100001;m.rossi@unisa.it";
        
        assertEquals(atteso, risultato, "La stringa serializzata non corrisponde al formato atteso");
        
        new File(FILE_NAME).delete();
    }

    @Test
    public void testDeserializzaCorretto() {
        new File(FILE_NAME).delete();
        ArchiveUtenti archive = new ArchiveUtenti();
        
        String rigaCsv = "Luca;Verdi;1234567890;l.verdi@unisa.it";
        
        Utente u = archive.deserializza(rigaCsv);
        
        assertNotNull(u, "Deserializzazione valida non deve ritornare null");
        assertEquals("Luca", u.getNome());
        assertEquals("Verdi", u.getCognome());
        assertEquals("1234567890", u.getMatricola());
        assertEquals("l.verdi@unisa.it", u.getEmail());
        
        new File(FILE_NAME).delete();
    }

    @Test
    public void testAggiungiECerca() throws IOException {
        new File(FILE_NAME).delete();
        ArchiveUtenti archive = new ArchiveUtenti();
        
        Utente u1 = new Utente("Anna", "Neri", "001", "anna@test.it");
        Utente u2 = new Utente("Marco", "Gialli", "002", "marco@test.it");
        
        archive.aggiungi(u1);
        archive.aggiungi(u2);
        
        Utente trovato = archive.cerca("001");
        assertNotNull(trovato);
        assertEquals("Anna", trovato.getNome());
        
        Utente nonTrovato = archive.cerca("999");
        assertNull(nonTrovato, "Utente inesistente deve ritornare null");
        
        new File(FILE_NAME).delete();
    }
    
    @Test
    public void testPersistenzaReale() throws IOException {
        new File(FILE_NAME).delete();
        ArchiveUtenti sessione1 = new ArchiveUtenti();
        Utente u = new Utente("Persistente", "User", "111", "p@test.it");
        sessione1.aggiungi(u);
        
        ArchiveUtenti sessione2 = new ArchiveUtenti();
        Utente caricato = sessione2.cerca("111");
        
        assertNotNull(caricato, "L'utente deve essere ricaricato dal file");
        assertEquals("Persistente", caricato.getNome());
        
        new File(FILE_NAME).delete();
    }
    
    @Test
    public void testCancella() throws IOException {
        new File(FILE_NAME).delete();
        ArchiveUtenti archive = new ArchiveUtenti();
        
        Utente u = new Utente("Delete", "Me", "666", "del@test.it");
        archive.aggiungi(u);
        assertNotNull(archive.cerca("666"));
        
        archive.cancella(u);
        
        assertNull(archive.cerca("666"), "Utente deve essere rimosso dalla memoria");
        
        // Verifica su file
        ArchiveUtenti check = new ArchiveUtenti();
        assertNull(check.cerca("666"), "Utente deve essere rimosso dal file");
        
        new File(FILE_NAME).delete();
    }
}

