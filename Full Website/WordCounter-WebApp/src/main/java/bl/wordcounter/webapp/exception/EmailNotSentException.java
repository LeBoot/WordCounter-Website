/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.exception;

/**
 *
 * @author Boone
 */
public class EmailNotSentException extends Exception {
 
    public EmailNotSentException(String message) {
        super(message);
    }
    
    public EmailNotSentException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
