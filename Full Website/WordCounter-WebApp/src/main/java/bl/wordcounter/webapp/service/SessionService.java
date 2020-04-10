/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.exception.UnsuccessfulLoginException;

/**
 *
 * @author Boone
 */
public interface SessionService {
    
    static final int LOGGED_OUT = 0;
    static final int LOGGED_IN = 1;
    static final int NO_OWNER = -1;
    
    int getSessionStatus();
    int getSessionOwner();
    void logOut();
    int logIn(String inputEmail, String inputPassword)
            throws UnsuccessfulLoginException;
    
}
