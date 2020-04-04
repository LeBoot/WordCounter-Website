/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.enigma.exception.InvalidKeyException;
import bl.wordcounter.webapp.enigma.exception.InvalidPasswordException;
import bl.wordcounter.webapp.entity.Account;
import bl.wordcounter.webapp.exception.EmailUnavailableException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Boone
 */
public interface AccountService {
    
    String autogeneratePassword(int length);
    void changeEmail(int id, String email)
            throws EmailUnavailableException;
    void changePassword(int id, String password)
            throws NoSuchElementException, InvalidKeyException, InvalidPasswordException;
    void deleteAccount(int id);
    List<Account> getAllAccounts();
    Account getAnAccount(int id)
            throws NoSuchElementException;
    void saveNewAccount(String email, String password)
            throws EmailUnavailableException, InvalidPasswordException, InvalidKeyException;
    
}