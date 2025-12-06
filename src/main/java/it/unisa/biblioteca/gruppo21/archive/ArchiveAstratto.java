/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.archive;

import java.io.File;
import java.io.IOException;
import java.util.List;



/**
 *
 * @author manue
 */
public abstract class ArchiveAstratto<T> implements ArchiveInterfaccia<T> {
    protected List<T> cache;
    protected File file;
    
    public ArchiveAstratto(String filename){
    }
    
   protected abstract String serializza(T t);
   protected abstract T deserializza(String riga);
    
    @Override
    public abstract T cerca(String id);

    @Override
    public void aggiungi(T elemento){
    }

    @Override
    public void cancella(T elemento){
    }

    @Override
    public List<T> leggiTutti(){
        return null;
    }

    protected void salvaTutto() throws IOException {
    }
}

   