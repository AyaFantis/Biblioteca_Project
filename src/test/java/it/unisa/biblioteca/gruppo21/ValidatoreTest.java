/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21;

import static org.junit.jupiter.api.Assertions.*;
import it.unisa.biblioteca.gruppo21.service.Validatore;
import org.junit.jupiter.api.Test;
/**
 *
 * @author greta
 */
public class ValidatoreTest {
 @Test
    public void testValidaEmail() {
        assertTrue(Validatore.validaEmail("m.rossi@studenti.unisa.it"), "Email valida deve passare");

        assertFalse(Validatore.validaEmail(null), "Null deve ritornare false");
        
        assertFalse(Validatore.validaEmail("m.rossi@gmail.com"), "Dominio errato");
        
        assertFalse(Validatore.validaEmail("@studenti.unisa.it"), "Email senza username");
        
        assertFalse(Validatore.validaEmail("m. rossi@studenti.unisa.it"), "Non può contenere spazi");
    }

    @Test
    public void testValidaMatricola() {
        assertTrue(Validatore.validaMatricola("0512105555"), "Matricola 10 cifre valida");

        assertFalse(Validatore.validaMatricola(null));
        assertFalse(Validatore.validaMatricola("123"), "Troppo corta");
        assertFalse(Validatore.validaMatricola("12345678901"), "Troppo lunga");

        assertFalse(Validatore.validaMatricola("051210AAAA"), "Non può contenere lettere");
    }

    @Test
    public void testValidaISBN() {
        assertTrue(Validatore.validaISBN("1234567890"), "ISBN semplice valido");

        assertTrue(Validatore.validaISBN("9788-08-1234"), "ISBN con trattini valido");
        assertTrue(Validatore.validaISBN("9788 08 1234"), "ISBN con spazi valido");

        assertFalse(Validatore.validaISBN(null));

        assertFalse(Validatore.validaISBN("978-A-08-1234"), "Non può contenere lettere");
        assertFalse(Validatore.validaISBN("978/88/123"), "Non può contenere caratteri speciali diversi da - o spazio");
        assertFalse(Validatore.validaISBN("12345678901"), "Troppe cifre (>13)");
    }
}
