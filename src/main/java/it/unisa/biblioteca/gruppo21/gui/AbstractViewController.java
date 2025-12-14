/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.biblioteca.gruppo21.gui;

/**
 *
 * @author manuel
 */
public abstract class AbstractViewController {
    
    protected Controller logicController;

    public void setLogicController(Controller logicController) {
        this.logicController = logicController;
        aggiornaTabella();
    }

    protected abstract void aggiornaTabella();
}
