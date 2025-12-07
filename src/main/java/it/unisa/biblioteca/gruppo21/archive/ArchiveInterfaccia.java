/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import java.io.IOException;
import java.util.List;

/**
 * @file ArchiveInterfaccia.java
 * @brief Interfaccia generica per la gestione della persistenza dei dati 
 * @interface ArchiveInterfaccia
 * Definisce il contratto standard per le operazioni CRUD (Create, Read, Delete) 
 * su un archivio dati, indipendentemente dal supporto di memorizzazione (File, Database, Memoria).
 * @param <T> Il tipo di entità gestita dall'archivio.
 * @author Gruppo 21
 * @version 1.0
 */

public interface ArchiveInterfaccia<T>{
    
    /**
     * @throws java.io.IOException
     * @brief Aggiunge un nuovo elemento all'archivio.
     * @pre Il parametro elemento non deve essere null.
     * @post L'archivio contiene l'elemento passato come parametro.
     * @post La dimensione dell'archivio è incrementata di 1.
     * @param elemento L'oggetto di tipo T da persistere.
     */
    void aggiungi(T elemento) throws IOException;
    
    /**
     * @throws java.io.IOException
     * @brief Rimuove un elemento esistente dall'archivio.
     * @pre Il parametro elemento non deve essere null.
     * @pre L'elemento deve essere presente nell'archivio.
     * @post L'archivio non contiene più l'elemento specificato.
     * @post La dimensione dell'archivio è decrementata di 1.
     * @param elemento L'oggetto di tipo T da rimuovere.
     */
    void cancella(T elemento) throws IOException;
    
    /**
     * @brief Cerca un elemento nell'archivio tramite il suo identificativo univoco.
     * @pre Il parametro id non deve essere null.
     * @post Se esiste un elemento con l'ID specificato, il metodo lo restituisce.
     * @post Se non esiste alcun elemento con l'ID specificato, il metodo restituisce null.
     * @param id La stringa che identifica univocamente l'elemento (es. Matricola, ISBN).
     * @return L'oggetto di tipo T trovato, oppure null.
     */
    T cerca(String id);
    
    /**
     * @throws java.io.IOException
     * @brief Recupera tutti gli elementi presenti nell'archivio.
     * @pre Nessuna pre-condizione specifica (l'archivio può essere vuoto).
     * @post Restituisce una List contenente tutti gli oggetti di tipo T salvati.
     * @post La lista restituita non è mai null (se l'archivio è vuoto, restituisce una lista vuota).
     * @return Una lista di oggetti T.
     */
    List<T> leggiTutti() throws IOException;
}

