/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.entity.Account;
import bl.wordcounter.webapp.service.AccountService;
import bl.wordcounter.webapp.service.MailService;
import bl.wordcounter.webapp.service.SessionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Boone
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    MailService mailService;
    
    
    //Thymeleaf
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    String displayAccountPage(Model model) {
        if (sessionService.getSessionStatus() == 0) {
            return "redirect:/home";
        }
        return "Account";
    }
    
    //AJAX
    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    ResponseEntity<Object> forgotPassword(String email) {
        List<Account> accounts = accountService.getAllAccounts();
        for (Account a : accounts) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                try {
                    String newPassword = accountService.autogeneratePassword(7);
                    mailService.sendForgotPassword(email, newPassword);
                    accountService.changePassword(a.getId(), newPassword);
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception ex) {
                    return new ResponseEntity<>(ex, HttpStatus.EXPECTATION_FAILED);
                }
            }
        }
        String error = "The email " + email + " is not associated with an account.";
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    //AJAX
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    ResponseEntity<Object> deleteAccount() {
        int accountId = sessionService.getSessionOwner();
        sessionService.logOut();        
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //AJAX
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    ResponseEntity<Object> saveNewAccount() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //AJAX
    @RequestMapping(value = "/change-email", method = RequestMethod.POST)
    ResponseEntity<Object> changeEmail() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //AJAX
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    ResponseEntity<Object> changePassword() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
