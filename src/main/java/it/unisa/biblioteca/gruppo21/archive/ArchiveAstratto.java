/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @file ArchiveAstratto.java
 * @brief Implementazione base astratta per la gestione di archivi su file di testo.
 * @class ArchiveAstratto
 * Questa classe fornisce l'implementazione comune per le operazioni di I/O (Input/Output),
 * gestendo una cache in memoria per ottimizzare le letture e sincronizzando le scritture su file.
 * @param <T> Il tipo di entità gestita (es. Libro, Utente).
 */

public abstract class ArchiveAstratto<T> implements ArchiveInterfaccia<T> {
    
    /** Cache in memoria per accesso rapido ai dati senza leggere il disco continuamente. */
    protected List<T> cache;
    /** Riferimento al file fisico su disco. */
    protected File file;
    
    /**
     * @brief Costruttore che inizializza l'archivio.
     * Tenta di creare il file se non esiste e carica immediatamente i dati in memoria (Cache).
     * @param filename Il nome o percorso del file di testo da utilizzare.
     */
    public ArchiveAstratto(String filename){
        
        this.file = new File(filename);
        this.cache = new ArrayList<>();
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            System.err.println("Errore inizializzazione file: " + filename);
        }
    }
    
    /**
     * @brief Carica i dati dal file alla memoria.
     */
    public void inizializzaDati() {
        try {
            caricaDaFile();
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei dati per " + file.getName() + ": " + e.getMessage());
        }
    }
    
   /**
     * @brief Converte un oggetto in una stringa per il salvataggio.
     * Deve essere implementato dalle sottoclassi per definire il formato (es. CSV).
     * @param t L'oggetto da serializzare.
     * @return Stringa rappresentante l'oggetto.
     */
   protected abstract String serializza(T t);
   
   /**
     * @brief Ricostruisce un oggetto da una stringa letta dal file.
     * @param riga La stringa letta dal file.
     * @return L'oggetto ricostruito.
     */
   protected abstract T deserializza(String riga);
    
   /**
     * @brief Ricerca un elemento per ID.
     * Poiché T è generico, non sappiamo come estrarre l'ID. Le sottoclassi devono implementarlo.
     * @param id Identificativo da cercare.
     * @return L'oggetto trovato o null.
     */
    @Override
    public abstract T cerca(String id);

    /**
     * @brief Aggiunge un elemento alla cache e appende la riga al file.
     * @pre elemento != null.
     * @post La cache contiene l'elemento.
     * @post Il file contiene la nuova riga serializzata.
     */
    @Override
    public void aggiungi(T elemento) throws IOException{
        cache.add(elemento);
        salvaTutto();
    }

    /**
     * @brief Rimuove un elemento e riscrive l'intero file.
     * @param elemento L'oggetto da rimuovere.
     */
    @Override
    public void cancella(T elemento)throws IOException{
        cache.remove(elemento);
        salvaTutto();
    }

    /**
     * @brief Restituisce una copia della lista caricata in memoria.
     * @return Una nuova ArrayList contenente gli elementi (per proteggere la cache interna).
     */
    @Override
    public List<T> leggiTutti(){
        return new ArrayList <>(cache); //restituisce una coia della cache
    }

    /**
     * @throws java.io.IOException
     * @brief Metodo di utilità per riscrivere completamente il file dalla cache.
     * Usato durante le operazioni di cancellazione o modifica.
     */
    public void salvaTutto() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(T elem : cache){
                writer.write(serializza(elem));
                writer.newLine();
            }
        }
    }
    
    private void caricaDaFile() throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String riga;
            while((riga = reader.readLine()) != null){
                if(!riga.trim().isEmpty()){
                    T obj = deserializza(riga);
                    if(obj != null) cache.add(obj);
                }
            }
        }
    }
}

   