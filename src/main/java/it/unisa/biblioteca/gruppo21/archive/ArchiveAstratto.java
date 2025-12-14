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
 * @author Gruppo 21
 * @version 1.0
 */

public abstract class ArchiveAstratto<T> implements ArchiveInterfaccia<T> {
    
    /** Lista in memoria per l'accesso rapido ai dati. */
    protected List<T> cache;
    
    /** Riferimento al file fisico. */
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
     * @brief Carica i dati dal file fisico alla struttura dati in memoria.
     * Questo metodo gestisce internamente le eccezioni di Input/Output garantendo che il sistema non
     * termini in modo anomalo in caso di errori di lettura.
     * @pre L'oggetto File deve essere stato inizializzato nel costruttore.
     * @post Se il file è valido, la lista interna (cache) viene popolata con i dati letti.
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
     * @param id Identificativo da cercare.
     * @return L'oggetto trovato o null.
     */
    @Override
    public abstract T cerca(String id);

    /**
     * @brief Aggiunge un nuovo elemento al sistema.
     * Aggiorna sia la lista in memoria che il file persistente.
     * @pre elemento != null.
     * @post La cache contiene il nuovo elemento.
     * @param elemento L'oggetto da aggiungere.
     * @throws IOException 
     */
    @Override
    public void aggiungi(T elemento) throws IOException{
        cache.add(elemento);
        salvaTutto();
    }

    /**
     * @brief Rimuove un elemento esistente dal sistema.
     * @pre L'elemento deve esistere nella cache.
     * @post L'elemento viene rimosso dalla lista in memoria.
     * @param elemento L'oggetto da rimuovere.
     * @throws IOException
     */
    @Override
    public void cancella(T elemento)throws IOException{
        cache.remove(elemento);
        salvaTutto();
    }

    /**
     * @brief Restituisce una copia della lista caricata in memoria.
     * @return Una nuova istanza di ArrayList contenente tutti gli elementi caricati(per proteggere la cache interna).
     */
    @Override
    public List<T> leggiTutti(){
        return new ArrayList <>(cache); 
    }

    /**
     * @brief Metodo di utilità per riscrivere completamente il file dalla cache.
     * Usato durante le operazioni di cancellazione o modifica.
     * @throws IOException 
     */
    public void salvaTutto() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(T elem : cache){
                writer.write(serializza(elem));
                writer.newLine();
            }
        }
    }
    
    /**
     * @brief Legge il file fisico e popola la cache.
     * @pre Il file deve esistere.
     * @post La cache contiene gli oggetti deserializzati correttamente.
     * @throws IOException.
     */
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

   