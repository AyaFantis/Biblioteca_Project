package it.unisa.biblioteca.gruppo21.gui;

import it.unisa.biblioteca.gruppo21.entity.Libro;
import it.unisa.biblioteca.gruppo21.entity.Prestito;
import it.unisa.biblioteca.gruppo21.entity.Utente;
import it.unisa.biblioteca.gruppo21.service.Biblioteca;
import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @file Controller.java
 * @brief Gestore delle interazioni tra l'Interfaccia Utente e il sistema Biblioteca.
 * @class Controller
 * Questa classe funge da intermediario (Pattern Controller). 
 * Si occupa di convertire gli input testuali provenienti dalla GUI in dati strutturati,
 * invocare la Business Logic della Biblioteca e presentare i risultati all'utente.
 * @author Gruppo 21
 * @version 1.0
 */
public class Controller {
    
    private final Biblioteca biblioteca;
    
    /**
     * @brief Costruttore del Controller.
     * @pre Il parametro biblioteca non deve essere null.
     * @post Il controller è inizializzato e collegato all'istanza del modello.
     * @param biblioteca L'istanza della Facade.
     */
    public Controller(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    /**
     * @brief Gestisce la richiesta di iscrizione di un nuovo utente.
     * @pre I parametri non dovrebbero essere null.
     * @post Viene mostrato un messaggio all'utente con l'esito dell'operazione.
     * @return true se l'iscrizione è avvenuta con successo; false se si è verificato un errore.
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param matricola Matricola (deve essere valida).
     * @param email Email (deve essere istituzionale).
     */
    public boolean gestisciIscrizione(String nome, String cognome, String matricola, String email) {
        String esito = biblioteca.iscriviUtente(nome, cognome, matricola, email);
        mostraMessaggio(esito);
        return !esito.startsWith("Errore");
    }
    
    /**
     * @brief Modifica un utente esistente.
     * @param nome Nuovo nome.
     * @param cognome Nuovo cognome.
     * @param email Nuova email.
     * @param matricola Matricola per identificare l'utente.
     * @return true se successo.
     */
    public boolean gestisciModificaUtente(String nome, String cognome, String email, String matricola) {
        String esito = biblioteca.modificaUtente(nome, cognome, email, matricola);
        mostraMessaggio(esito);
        return !esito.startsWith("Errore");
    }
    
    /**
     * @brief Gestisce il flusso di rimozione utente.
     * @pre Matricola non deve essere null.
     * @post L'esito dell'operazione viene mostrato all'utente.
     * @param matricola La matricola dell'utente da cancellare.
     */
    public void gestisciRimozioneUtente(String matricola) {
        String esito = biblioteca.rimuoviUtente(matricola);
        mostraMessaggio(esito);
    }
    
    /**
     * @brief Recupera i dati per la visualizzazione della tabella utenti.
     * @post Restituisce la lista corrente degli utenti dal sistema.
     * @return Lista di Utente.
     */
    public List<Utente> getListaUtenti() {
        return biblioteca.getElencoUtenti();
    }
    
    /**
     * @brief Gestisce l'aggiunta di un libro, occupandosi del parsing dei dati numerici.
     * @pre I parametri sono stringhe grezze provenienti dalla GUI.
     * @post Caso 1 Se annoStr e copieStr sono interi validi,
     * viene chiamato il servizio di aggiunta e mostrato l'esito.
     * @post Caso 2 Se i campi numerici non sono validi, viene mostrato
     * un messaggio di errore specifico senza invocare il servizio.
     * @return true se l'iscrizione è avvenuta con successo; false se si è verificato un errore.
     * @param titolo Titolo del libro.
     * @param autore Autore.
     * @param isbn ISBN.
     * @param annoStr Anno.
     * @param copieStr Copie.
     */
    
    public boolean gestisciAggiuntaLibro(String titolo, String autore, String isbn, String annoStr, String copieStr) {
        try {
            int anno = Integer.parseInt(annoStr);
            int copie = Integer.parseInt(copieStr);
            
            String esito = biblioteca.aggiungiLibro(titolo, autore, isbn, anno, copie);
            mostraMessaggio(esito);
            return !esito.startsWith("Errore");
        } catch (NumberFormatException e) {
            mostraMessaggio("Anno e Copie devono essere numeri interi validi.");
            return false;
        }
    }
    
    /**
     * @brief Gestisce l'aggiornamento del numero di copie di un libro.
     * @param isbn Codice ISBN del libro da modificare.
     * @param copieStr Nuovo numero di copie.
     * @return true se l'operazione ha successo, false altrimenti.
     */
    public boolean gestisciAggiornamentoCopie(String isbn, String copieStr) {
        try {
            int copie = Integer.parseInt(copieStr);
            
            String esito = biblioteca.aggiornaCopieLibro(isbn, copie);
            mostraMessaggio(esito);
            return !esito.startsWith("Errore");
            
        } catch (NumberFormatException e) {
            mostraMessaggio("Errore: 'Copie' deve essere un numero intero valido.");
            return false;
        }
    }
    
    /**
     * @brief Gestisce la rimozione di un libro.
     * @pre isbn non dovrebbe essere null.
     * @post L'esito viene mostrato all'utente.
     * @param isbn Codice del libro.
     */
    public void gestisciRimozioneLibro(String isbn) {
        String esito = biblioteca.rimuoviLibro(isbn);
        mostraMessaggio(esito);
    }
    
    /**
     * @brief Recupera i dati per la tabella libri.
     * @post Restituisce la lista dei libri.
     * @return Lista di Libro.
     */
    public List<Libro> getListaLibri() {
        return biblioteca.getElencoLibri();
    }
   
    /**
     * @brief Gestisce il flusso di creazione prestito.
     * @pre matricola e isbn non nulli.
     * @post L'esito (Successo, Utente non trovato, Stock esaurito) viene mostrato all'utente.
     * @return true se l'iscrizione è avvenuta con successo; false se si è verificato un errore.
     * @param identificativo Matricola o Cognome dell'utente.
     * @param isbn ID Libro.
     * @param dataRestituzione Data restituzione del libro.
     */
    public boolean gestisciPrestito(String identificativo, String isbn, LocalDate dataRestituzione) {
        String esito = biblioteca.effettuaPrestito(identificativo, isbn, dataRestituzione);
        mostraMessaggio(esito);
        return !esito.startsWith("Errore");
    }
    
    /** 
     * @brief Gestisce il flusso di restituzione libro.
     * @pre matricola e isbn non nulli.
     * @post Viene mostrato il messaggio di conferma o errore.
     * @param matricola ID Utente.
     * @param isbn ID Libro.
     * @return true se la restituzione è avvenuta con successo mentre false se l'operazione è fallita.
     *
     */
    public boolean gestisciRestituzione(String matricola, String isbn) {
        String esito = biblioteca.restituisciLibro(matricola, isbn);
        mostraMessaggio(esito);
        return !esito.startsWith("Errore");
    }
    
    /**
     * @brief Recupera lo storico prestiti.
     * @post Restituisce la lista di tutti i prestiti attivi.
     * @post La lista non è mai null.
     * @return Lista di Prestito.
     */
    public List<Prestito> getListaPrestiti() {
        return biblioteca.getStoricoPrestiti();
    }
 
    /**
     * @brief Visualizza un messaggio di feedback.
     * @pre messaggio non nullo.
     * @post Il messaggio viene inviato allo stream di output.
     * @param messaggio Testo da mostrare.
     */
    private void mostraMessaggio(String messaggio) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); 
        popupStage.setTitle("Avviso Biblioteca");
        popupStage.setMinWidth(350);
        popupStage.setMinHeight(180);

        Label label = new Label(messaggio);
        label.setWrapText(true);
        
        label.setStyle("-fx-font-size: 14px; -fx-padding: 20px; -fx-text-fill: #003366;");

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> popupStage.close());
        
        closeButton.setStyle("-fx-background-color: #FFB347; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20; -fx-cursor: hand;");

        VBox layout = new VBox(15); 
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #FFF8E7; -fx-border-color: #FFB347; -fx-border-width: 2;");

        Scene scene = new Scene(layout);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
