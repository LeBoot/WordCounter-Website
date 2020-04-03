/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Exception.AccountNotFoundException;
import bl.wordcounter.webapp.Exception.SessionLoggedOutException;

/**
 *
 * @author Boone
 */
public interface SessionService {
    
    int logIn(String email, String password)
            throws AccountNotFoundException;
    int logOut();
    Account getSessionOwner()
            throws SessionLoggedOutException;
    
}
