/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.enigma.Enigma;
import bl.wordcounter.webapp.entity.Account;
import bl.wordcounter.webapp.exception.UnsuccessfulLoginException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    AccountService accountService;
   
    Enigma enigma = new Enigma();
    
    private static int SESSION_STATUS = LOGGED_OUT;
    private static int SESSION_OWNER = NO_OWNER;
    
    @Override
    public int getSessionStatus() {
        return SESSION_STATUS;
    }

    @Override
    public int getSessionOwner() {
        return SESSION_OWNER;
    }
    
    @Override
    public void logOut() {
        SESSION_STATUS = LOGGED_OUT;
        SESSION_OWNER = NO_OWNER;
    }
    
    @Override
    public void logIn(String inputEmail, String inputPassword) throws UnsuccessfulLoginException {
        List<Account> accounts = accountService.getAllAccounts();
        for (Account a : accounts) {
            if (a.getEmail().equalsIgnoreCase(inputEmail)) {
                if (enigma.doesPasswordMatch(inputPassword, a.getPassword(), a.getPasskey())) {
                    SESSION_STATUS = LOGGED_IN;
                    SESSION_OWNER = a.getId();
                    break;
                } else {
                    throw new UnsuccessfulLoginException("Incorrect password.");
                }
            } else {
                throw new UnsuccessfulLoginException("Email not associated with an account.");
            }
        }
    }
    
}