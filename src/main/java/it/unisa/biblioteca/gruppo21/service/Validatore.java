/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.service;

/**
 * @file Validatore.java
 * @brief Classe di utilità per la verifica della correttezza formale dei dati di input.
 * @class Validatore
 * Questa classe implementa controlli robusti sui dati in ingresso 
 * @author Gruppo 21
 * @version 1.0
 */
public class Validatore {
    
     /**
     * @brief Verifica se una stringa rappresenta un indirizzo email istituzionale valido.
     * @pre L'input email può essere una stringa qualsiasi o null.
     * @post Il metodo restituisce true se e solo se:
     * 1. L'input non è null.
     * 2. L'input rispetta il pattern regex (dominio "@studenti.unisa.it").
     * @post Il metodo restituisce false in tutti gli altri casi.
     * @param email La stringa da validare.
     * @return Esito della validazione.
     */
    public static boolean validaEmail(String email){
        return false;
    }
    
    /**
     * @brief Verifica se una stringa rappresenta una matricola valida.
     * @pre L'input matricola può essere una stringa qualsiasi o null.
     * @post Il metodo restituisce true se e solo se:
     * 1. L'input non è null.
     * 2. L'input è composto esattamente da 10 caratteri numerici [0-9].
     * @post Il metodo restituisce false in tutti gli altri casi
     * @param matricola La stringa da validare.
     * @return Esito della validazione.
     */
    
    public static boolean validaMatricola(String matricola){
        return false;
    }
    
    /**
     * @brief Verifica la validità di un codice ISBN.
     * @pre L'input ISBN può essere NULL o una stringa qualsiasi.
     * @post Restituisce true se la stringa rispetta il formato numerico.
     * @param isbn Stringa da validare.
     * @return Esito della validazione.
     */
    public static boolean validaISBN(String isbn){
        return false;
    }
}
