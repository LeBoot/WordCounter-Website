/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Controller;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Exception.AccountNotFoundException;
import bl.wordcounter.webapp.Exception.InvalidInputException;
import bl.wordcounter.webapp.Service.AccountService;
import bl.wordcounter.webapp.Service.MailService;
import bl.wordcounter.webapp.Service.SessionService;
import bl.wordcounter.webapp.Service.TextService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Boone
 */
@Controller
@RequestMapping("/account")
public class AccountAndSessionController {
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    MailService mailService;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    TextService textService;
    
    
    //ACCOUNTS =================================================================    
    
    /*
    -- AJAX.
    -- Handle a log-in attempt.
    -- If username and password match an account, the session status is changed
        and a successful html status is returned.
    -- If do not match, an error is returend with status bad-request.
    */
    @RequestMapping(value = "/log-in", method = RequestMethod.POST)
    public ResponseEntity<Object> attemptLogin(
            @RequestParam("email-input") String email,
            @RequestParam("password-input") String password
            ) {
        try {
            sessionService.logIn(email, password);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (AccountNotFoundException ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }  
    }
    
    /*
    -- Thymeleaf.
    */
    @RequestMapping(value = "/log-out", method = RequestMethod.GET)
    public String logout() {
        sessionService.logOut();
        return "redirect: /home";
    }
    
    /*
    -- AJAX.
    -- Cycle through all accounts...
    -- If an email matches, then attempt to send an email
        - If email doesn't send, return an error stating as much.
        - If email does send, change the account's password and return success.
    -- If no email matches, return message stating as much.
    */
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<Object> forgotPassword(@RequestParam("email-input-forgot-password") String email) {
        List<Account> list = accountService.getAllAccounts();        
        for (Account a : list) {
            if (a.getEmail().equals(email)) {
                String newPassword = accountService.autogeneratePassword(7);
                String oldPassword = a.getPassword();
                try {
                    mailService.sendForgotPassword(email, newPassword);
                    try {
                        a.setPassword(newPassword);
                        accountService.editAccount(a);
                        return new ResponseEntity<>(HttpStatus.OK);
                    } catch (InvalidInputException e) {
                        a.setPassword(oldPassword);
                        return new ResponseEntity<>(e, HttpStatus.CONFLICT);
                    }
                } catch (Exception ex) {
                    String message = "Unable to send email.";
                    return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);               
                }
            }
        }
        String message = "That email is not associated with any account.";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    

    //SESSIONS =================================================================
    
    @RequestMapping(value = "/create-account", method = RequestMethod.POST)
    public String createAccount() {
        return "Create Account.";
    }
    
    @RequestMapping(value = "/edit-account", method = RequestMethod.POST)
    public String editAccount() {
        return "Edit Account.";
    }
    
    @RequestMapping(value = "/delete-account", method = RequestMethod.GET)
    public String deleteAccount() {
        return "Delete Account.";
    }
    
    @RequestMapping(value = "/display-account", method = RequestMethod.GET)
    public String displayAccount() {
        return "Display Account.";
    }
    
}
