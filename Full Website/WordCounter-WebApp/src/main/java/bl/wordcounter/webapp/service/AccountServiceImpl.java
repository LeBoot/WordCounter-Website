/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.enigma.Enigma;
import bl.wordcounter.webapp.enigma.exception.InvalidKeyException;
import bl.wordcounter.webapp.enigma.exception.InvalidPasswordException;
import bl.wordcounter.webapp.entity.Account;
import bl.wordcounter.webapp.exception.EmailUnavailableException;
import bl.wordcounter.webapp.repository.AccountRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepo;
    
    Enigma enigma = new Enigma();

    @Override
    public String autogeneratePassword(int length) {
        return enigma.autogeneratePassword(length);
    }
    
    @Override
    public void changeEmail(int id, String email) throws EmailUnavailableException {
        checkEmailAvailability(email);
        Account account = getAnAccount(id);
        accountRepo.save(account);
    }
    
    @Override
    public void changePassword(int id, String password)
            throws NoSuchElementException, InvalidKeyException, InvalidPasswordException {
        Account account = getAnAccount(id);
        encryptPasswordAndSave(account, password);
    }
    
    @Override
    public void deleteAccount(int id) {
        accountRepo.deleteById(id);
    }
    
    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }
    
    @Override
    public Account getAnAccount(int id) throws NoSuchElementException {
        Optional<Account> optA = accountRepo.findById(id);
        return optA.orElseThrow();
    }
    
    @Override
    public void saveNewAccount(String email, String password)
            throws EmailUnavailableException, InvalidPasswordException, InvalidKeyException {
        checkEmailAvailability(email);
        Account account = new Account();
        account.setEmail(email);
        encryptPasswordAndSave(account, password);
    }
    
    //HELPER METHODS ===========================================================
    
    private void encryptPasswordAndSave(Account account, String password)
            throws InvalidPasswordException, InvalidKeyException {
        String[] output = enigma.encryptPassword(password);
        account.setPassword(output[0]);
        account.setPasskey(output[1]);
        accountRepo.save(account);
    }
    
    private void checkEmailAvailability(String email) throws EmailUnavailableException {
        List<Account> accounts = accountRepo.findAll();
        for (Account a : accounts) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                String message = "The email " + email + " is already associated with an account.";
                throw new EmailUnavailableException(message);
            }
        }
    }
    
}
