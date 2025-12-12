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
        
        Utente u = new Utente("Mario", "Rossi", "m.rossi@unisa.it",  "0512100001");
        
        String risultato = archive.serializza(u);
        
        String atteso = "Mario;Rossi;m.rossi@unisa.it;0512100001";
        
        assertEquals(atteso, risultato, "La stringa serializzata non corrisponde al formato atteso");
        
        new File(FILE_NAME).delete();
    }

    @Test
    public void testDeserializzaCorretto() {
        new File(FILE_NAME).delete();
        ArchiveUtenti archive = new ArchiveUtenti();
        
        String rigaCsv = "Luca;Verdi;l.verdi@unisa.it;1234567890";
        
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
        
        Utente u1 = new Utente("Anna", "Neri", "anna@test.it", "0010010011");
        Utente u2 = new Utente("Marco", "Gialli", "marco@test.it", "0020020022");
        
        archive.aggiungi(u1);
        archive.aggiungi(u2);
        
        Utente trovato = archive.cerca("0010010011");
        assertNotNull(trovato);
        assertEquals("Anna", trovato.getNome());
        
        Utente nonTrovato = archive.cerca("9999999999");
        assertNull(nonTrovato, "Utente inesistente deve ritornare null");
        
        new File(FILE_NAME).delete();
    }
    
    @Test
    public void testPersistenzaReale() throws IOException {
        new File(FILE_NAME).delete();
        ArchiveUtenti sessione1 = new ArchiveUtenti();
        Utente u = new Utente("Persistente", "User", "p@test.it", "1111111111");
        sessione1.aggiungi(u);
        
        ArchiveUtenti sessione2 = new ArchiveUtenti();
        Utente caricato = sessione2.cerca("1111111111");
        
        assertNotNull(caricato, "L'utente deve essere ricaricato dal file");
        assertEquals("Persistente", caricato.getNome());
        
        new File(FILE_NAME).delete();
    }
    
    @Test
    public void testCancella() throws IOException {
        new File(FILE_NAME).delete();
        ArchiveUtenti archive = new ArchiveUtenti();
        
        Utente u = new Utente("Delete", "Me", "del@test.it", "66666666666");
        archive.aggiungi(u);
        assertNotNull(archive.cerca("66666666666"));
        
        archive.cancella(u);
        
        assertNull(archive.cerca("66666666666"), "Utente deve essere rimosso dalla memoria");
        
        // Verifica su file
        ArchiveUtenti check = new ArchiveUtenti();
        assertNull(check.cerca("66666666666"), "Utente deve essere rimosso dal file");
        
        new File(FILE_NAME).delete();
    }
}

