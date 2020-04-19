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
public class IncorrectPasswordException extends Exception {
    
    public IncorrectPasswordException(String message) {
        super(message);
    }
    
    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
