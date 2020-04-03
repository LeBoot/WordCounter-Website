/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.entity.Account;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Boone
 */
public interface AccountService {
    
    Account saveAccount(Account account);
    Account getAnAccount(int id) throws NoSuchElementException;
    List<Account> getAllAccounts();
    void deleteAccount(int id);
    
}
