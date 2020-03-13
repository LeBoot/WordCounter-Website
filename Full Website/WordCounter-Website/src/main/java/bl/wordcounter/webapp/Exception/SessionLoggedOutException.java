/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Exception;

/**
 *
 * @author Boone
 */
public class SessionLoggedOutException extends Exception {
    
    public SessionLoggedOutException(String message) {
        super(message);
    }
    
    public SessionLoggedOutException(String message, Throwable cause) {
        super(message, cause);
    }
    
}