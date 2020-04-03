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
public class UnsuccessfulLoginException extends Exception {
    
    public UnsuccessfulLoginException(String message) {
        super(message);
    }
    
    public UnsuccessfulLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
