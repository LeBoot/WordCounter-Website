/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Exception.InvalidInputException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Boone
 */
public interface AccountService {
    
    Account retrieveAccount(int id)
            throws NoSuchElementException;
    List<Account> getAllAccounts();
    Account createAccount(Account account)
            throws InvalidInputException;
    Account editAccount(Account account)
            throws InvalidInputException;
    void deleteAccount(int id)
            throws NoSuchElementException;
    
}
