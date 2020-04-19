/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.entity.Account;
import bl.wordcounter.webapp.exception.EmailNotSentException;
import bl.wordcounter.webapp.exception.EmailUnavailableException;
import bl.wordcounter.webapp.exception.IncorrectPasswordException;
import bl.wordcounter.webapp.exception.InvalidInputException;
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
import org.springframework.web.bind.annotation.RequestParam;

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
        
        String email = accountService.getAnAccount(sessionService.getSessionOwner()).getEmail();
        model.addAttribute("email", email);
        
        return "Account";
    }
    
    //AJAX
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    ResponseEntity<Object> forgotPassword(@RequestParam("formEmail") String email) {
        List<Account> accounts = accountService.getAllAccounts();
        for (Account a : accounts) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                try {
                    String newPassword = accountService.autogeneratePassword(7);
                    mailService.sendForgotPassword(email, newPassword);
                    accountService.changePassword(a.getId(), a.getPassword(), newPassword, newPassword);
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception ex) {
                    return new ResponseEntity<>(ex, HttpStatus.EXPECTATION_FAILED);
                }
            }
        }
        String error = "The email " + email + " is not associated with an account.";
        EmailNotSentException ex = new EmailNotSentException(error);
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
    
    //AJAX
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<Object> deleteAccount(
            @RequestParam("password") String password
    ) {
        try {
            int accountId = sessionService.getSessionOwner();
            accountService.deleteAccount(accountId, password);
            sessionService.logOut();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IncorrectPasswordException ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
    
    //AJAX
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    ResponseEntity<Object> saveNewAccount(
            @RequestParam("formEmail") String formEmail,
            @RequestParam("formPass1") String formPass1,
            @RequestParam("formPass2") String formPass2
    ) {
        try {
            accountService.saveNewAccount(formEmail, formPass1, formPass2);
            sessionService.logIn(formEmail, formPass1);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
    
    //AJAX
    @RequestMapping(value = "/change-email", method = RequestMethod.POST)
    ResponseEntity<Object> changeEmail(
            @RequestParam("formEmail") String formEmail,
            @RequestParam("formPass") String formPass
    ) {
        try {
            int accountId = sessionService.getSessionOwner();
            accountService.changeEmail(accountId, formEmail, formPass);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmailUnavailableException | IncorrectPasswordException | InvalidInputException ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
    
    //AJAX
    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    ResponseEntity<Object> changePassword(
            @RequestParam("oldPass") String oldPass,
            @RequestParam("newPass1") String newPass1,
            @RequestParam("newPass2") String newPass2
    ) {
        try {
            int accountId = sessionService.getSessionOwner();
            accountService.changePassword(accountId, oldPass, newPass1, newPass2);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
    
}
