/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public interface ArchiveInterfaccia<T>{
    void aggiungi(T elemento) throws IOException;
    
    void cancella(T elemento) throws IOException;
    
    T cerca(String id);
    
    List<T> leggiTutti() throws IOException;
}

