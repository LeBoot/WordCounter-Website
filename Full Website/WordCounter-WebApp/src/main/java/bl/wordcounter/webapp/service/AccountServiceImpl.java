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
import bl.wordcounter.webapp.exception.IncorrectPasswordException;
import bl.wordcounter.webapp.exception.InvalidInputException;
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
    public void changeEmail(int id, String email, String password)
            throws EmailUnavailableException, InvalidInputException, IncorrectPasswordException {
        validateEmail(email);
        checkEmailAvailability(email);
        Account account = getAnAccount(id);
        if (enigma.doesPasswordMatch(password, account.getPassword(), account.getPasskey())) {
            account.setEmail(email);
            accountRepo.save(account);
        } else {
            String message = "That is the incorrect password for the account.";
            throw new IncorrectPasswordException(message);
        }
    }
    
    @Override
    public void changePassword(int id, String oldPass, String newPass1, String newPass2)
            throws NoSuchElementException, InvalidKeyException, InvalidPasswordException,
            IncorrectPasswordException, InvalidInputException {
        Account account = getAnAccount(id);
        if (enigma.doesPasswordMatch(oldPass, account.getPassword(), account.getPasskey())) {
            confirmPasswordsMatch(newPass1, newPass2);
            encryptPasswordAndSave(account, newPass1);
        } else {
            String message = "That is the incorrect password for the account.";
            throw new IncorrectPasswordException(message);
        }
    }
    
    @Override
    public void changePasswordMail(int id, String newPassword) throws InvalidPasswordException, InvalidKeyException {
        Account account = getAnAccount(id);
        encryptPasswordAndSave(account, newPassword);
    }
    
    @Override
    public void deleteAccount(int id, String password) throws IncorrectPasswordException {
        Account account = getAnAccount(id);
        if (enigma.doesPasswordMatch(password, account.getPassword(), account.getPasskey())) {
            accountRepo.deleteById(id);
        } else {
            String message = "That is the incorrect password for the account.";
            throw new IncorrectPasswordException(message);
        }
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
    public void saveNewAccount(String email, String password1, String password2)
            throws EmailUnavailableException, InvalidPasswordException, InvalidKeyException, InvalidInputException {
        validateEmail(email);
        checkEmailAvailability(email);
        confirmPasswordsMatch(password1, password2);
        Account account = new Account();
        account.setEmail(email);
        encryptPasswordAndSave(account, password1);
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
    
    private void validateEmail(String email) throws InvalidInputException {
        int min = 8;
        int max = 50;
        
        if (email.length() > max) {
            String message = "Please use an email that is no more than " + max + " characters in length.";
            throw new InvalidInputException(message);
        }
        
        if (
            (email.length() < min) ||
            (!email.contains("@")) ||
            (!email.contains("."))
            ){
            String message = "Please use a valid email.";
            throw new InvalidInputException(message);
        } 
    }
    
    private void confirmPasswordsMatch(String pass1, String pass2) throws InvalidInputException {
        if (!pass1.equals(pass2)) {
            String message = "Passwords do not match.";
            throw new InvalidInputException(message);
        }
    }
    
}
