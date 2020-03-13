/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Exception.InvalidInputException;
import bl.wordcounter.webapp.Repository.AccountRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
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
    
    @Override
    public Account retrieveAccount(int id) throws NoSuchElementException {
        Optional<Account> a = accountRepo.findById(id);
        return a.orElseThrow();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }
    
    @Override
    public Account createAccount(Account account) throws InvalidInputException {
        try {
            validateAccount(account, true);
            return accountRepo.save(account);
        } catch (InvalidInputException e) {
            throw e;
        }
    }

    @Override
    public Account editAccount(Account account) throws InvalidInputException {
        try {
            validateAccount(account, false);
            return accountRepo.save(account);
        } catch (InvalidInputException e) {
            throw e;
        }
    }

    @Override
    public void deleteAccount(int id) throws NoSuchElementException {
        try {
            Account a = retrieveAccount(id);
            accountRepo.delete(a);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    
    @Override
    public String autogeneratePassword(int length) {
        Random rand = new Random();

        String password = "";
        String numbers = "0123456789";
        String letters = "abcdefghijkmnopqurstuvwxyz";
        String characters = numbers + letters + letters.toUpperCase();
        
        if (length < 4) {
            length = 4; //arbitrary
        } else if (length > 20) {
            length = 20; //database restriction
        }
        
        int min = 0;
        int max = length - 1;
        
        while(password.length() < length) {
            int n = rand.nextInt((max - min) + 1) + min;
            try {
                password += characters.substring(n, n + 1);
            } catch (StringIndexOutOfBoundsException e) {}
        }
        
        return password;
    }  
    
    private void validateAccount(Account a, boolean isAccountNew) throws InvalidInputException {
        //validate email format
        try {
            validateEmail(a.getEmail(), 5, 50);
        } catch (InvalidInputException e) {
            throw e;
        }
        
        //validate password format
        try {
            validatePassword(a.getPassword(), 4, 20);
        } catch (InvalidInputException e) {
            throw e;
        }
        
        //validate email not already used       
        List<Account> allAccounts = accountRepo.findAll();
        String message = "Sorry, but that email is already associated with another account.";
        if (isAccountNew) {
            for (Account x : allAccounts) {
                if (x.getEmail().equals(a.getEmail())) {
                    throw new InvalidInputException(message);
                }
            }
        } else {
            for (Account x : allAccounts) {
                if (x.getEmail().equals(a.getEmail()) && (x.getId() != a.getId())) {
                    throw new InvalidInputException(message);
                }
            }
        }        
    }
    
    private void validateEmail(String email, int min, int max) throws InvalidInputException {
        String message = "Please enter a valid email between " + min + " and " + max + " characters.";
        if (
           (email.length() < min) ||
           (email.length() > max) ||
           (!email.contains("@")) ||
           (!email.contains(".")) ){
            throw new InvalidInputException(message);
        }
    }
    
    private void validatePassword(String password, int min, int max) throws InvalidInputException {
        String message = "Please enter a password between " + min + " and " + max + " characters.";
        if (
           (password.length() < min) ||
           (password.length() > max) ){
            throw new InvalidInputException(message);
        }
    }    
    
}