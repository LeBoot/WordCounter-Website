/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Exception.AccountNotFoundException;
import bl.wordcounter.webapp.Exception.SessionLoggedOutException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class SessionServiceImpl implements SessionService {

    /*
    AccountService:
        Autowire to AccountServiceImpl to access account information.
    */
    @Autowired
    AccountService accountService;
    
    /*
    Static Final variable LOGGED_OUT:
        Value = -1, which represents that no one is logged in to the program.
    */
    private static final int LOGGED_OUT = -1;
     
    /*
    Static variable currentSession:
        Value is the accountID of whoever is logged in.
        If the value is LOGGED_OUT (-1), then no one is logged in.
        Each time the program begins, the currentSession is initialized to LOGGED_OUT.
    */
    private static int currentSession = LOGGED_OUT;
    
    
    @Override
    public int logIn(String email, String password) throws AccountNotFoundException {
        List<Account> allAccounts = accountService.getAllAccounts();
        for (Account a : allAccounts) {
            if (a.getEmail().equals(email) && a.getPassword().equals(password)) {
                currentSession = a.getId();
                return currentSession;
            }
        }
        throw new AccountNotFoundException("Account Not Found.");
    }

    @Override
    public int logOut() {
        currentSession = LOGGED_OUT;
        return currentSession;
    }

    @Override
    public Account getSessionOwner() throws SessionLoggedOutException {
        if (currentSession == LOGGED_OUT) {
            throw new SessionLoggedOutException("Session is logged out.");
        } else {
            return accountService.retrieveAccount(currentSession);
        }
    }
    
}
