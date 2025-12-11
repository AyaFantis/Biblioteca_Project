/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import it.unisa.biblioteca.gruppo21.archive.ArchivePrestiti;
import it.unisa.biblioteca.gruppo21.archive.ArchiveUtenti;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import it.unisa.biblioteca.gruppo21.service.ServiceUtenti;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author greta
 */

public class ServiceUtentiTest {

    private ServiceUtenti service;
    private ArchiveUtenti arcUtenti;
    private ArchivePrestiti arcPrestiti;

    private final String FILE_UTENTI = "Utenti.txt";
    private final String FILE_PRESTITI = "Prestiti.txt";

    private void inizializza() {
        new File(FILE_UTENTI).delete();
        new File(FILE_PRESTITI).delete();

        arcUtenti = new ArchiveUtenti();
        arcPrestiti = new ArchivePrestiti();
        service = new ServiceUtenti(arcUtenti, arcPrestiti);
    }

    private void pulisci() {
        new File(FILE_UTENTI).delete();
        new File(FILE_PRESTITI).delete();
    }

    // --- TEST ISCRIZIONE ---

    @Test
    public void testIscrizioneValida() {
        inizializza();

        String esito = service.iscrivi("Mario", "Rossi", "0512101234", "m.rossi@studenti.unisa.it");

        assertTrue(esito.contains("Successo"), "Dovrebbe confermare l'iscrizione");
        assertEquals(1, service.getLista().size(), "L'archivio deve contenere 1 utente");

        pulisci();
    }

    @Test
    public void testIscrizioneDatiMancanti() {
        inizializza();

        // Nome mancante
        String esitoNome = service.iscrivi(null, "Rossi", "0512101234", "m.rossi@studenti.unisa.it");
        assertTrue(esitoNome.startsWith("Errore"), "Nome null deve dare errore");

        // Cognome mancante
        String esitoCognome = service.iscrivi("Mario", null, "0512101234", "m.rossi@studenti.unisa.it");
        assertTrue(esitoCognome.startsWith("Errore"), "Cognome null deve dare errore");

        pulisci();
    }

    @Test
    public void testIscrizioneDatiNonValidi() {
        inizializza();

        // Matricola corta
        String esitoMat = service.iscrivi("Mario", "Rossi", "123", "m.rossi@studenti.unisa.it");
        assertTrue(esitoMat.contains("Matricola"), "Matricola non valida deve dare errore");

        // Email esterna (non unisa)
        String esitoEmail = service.iscrivi("Mario", "Rossi", "0512101234", "mario.rossi@gmail.com");
        assertTrue(esitoEmail.contains("Email"), "Email non istituzionale deve dare errore");

        pulisci();
    }

    @Test
    public void testIscrizioneDuplicata() {
        inizializza();

        // Iscrivo il primo
        service.iscrivi("Mario", "Rossi", "0512101234", "m.rossi@studenti.unisa.it");

        // Provo a iscrivere un altro con STESSA matricola
        String esito = service.iscrivi("Luigi", "Verdi", "0512101234", "l.verdi@studenti.unisa.it");

        assertTrue(esito.startsWith("Errore"), "Non deve accettare matricole duplicate");
        assertEquals(1, service.getLista().size(), "Deve rimanere solo 1 utente");

        pulisci();
    }

    // --- TEST MODIFICA ---

    @Test
    public void testModificaDatiUtente() {
        inizializza();

        // Setup: creo utente
        String mat = "0512101234";
        service.iscrivi("Mario", "Rossi", mat, "m.rossi@studenti.unisa.it");

        // Azione: modifico email e nome
        String esito = service.modificaDatiUtente("MarioNew", "RossiNew", "nuova@studenti.unisa.it", mat);

        assertTrue(esito.contains("Successo"));

        // Verifica dati aggiornati
        Utente u = service.getLista().get(0);
        assertEquals("MarioNew", u.getNome());
        assertEquals("nuova@studenti.unisa.it", u.getEmail());

        pulisci();
    }

    @Test
    public void testModificaUtenteInesistente() {
        inizializza();

        String esito = service.modificaDatiUtente("A", "B", "a@studenti.unisa.it", "9999999999");
        assertTrue(esito.startsWith("Errore"), "Non può modificare utente che non esiste");

        pulisci();
    }

    // --- TEST ORDINAMENTO ---

    @Test
    public void testGetListaOrdinata() {
        inizializza();

        // Inserisco in ordine sparso
        service.iscrivi("Zaira", "Z", "0000000002", "z@studenti.unisa.it");
        service.iscrivi("Alberto", "A", "0000000001", "a@studenti.unisa.it");

        List<Utente> lista = service.getLista();

        // Mi aspetto ordinamento per Cognome (o quello definito nel compareTo di Utente)
        // Assumendo compareTo su Cognome -> Nome
        assertEquals("Alberto", lista.get(0).getNome());
        assertEquals("Zaira", lista.get(1).getNome());

        pulisci();
    }
}
