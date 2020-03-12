/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import java.util.List;

/**
 *
 * @author Boone
 */
public interface AccountService {
    
    Account retrieveAccount(int id);
    List<Account> getAllAccounts();
    Account createAccount(Account account);
    Account editAccount(Account account);
    void deleteAccount(int id);
    
}
