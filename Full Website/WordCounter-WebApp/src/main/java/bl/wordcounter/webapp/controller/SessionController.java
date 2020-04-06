/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.exception.UnsuccessfulLoginException;
import bl.wordcounter.webapp.service.ErrorService;
import bl.wordcounter.webapp.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Boone
 */
@Controller
@RequestMapping("/session")
public class SessionController {
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    ErrorService errorService;
    
    //Thymeleaf
    @RequestMapping(value = "login", method = RequestMethod.POST)
    String attemptLogin(
            @RequestParam("email-input") String email,
            @RequestParam("password-input") String password
            ) {
        errorService.resetHtmlErrors();
        try {
            sessionService.logIn(email, password);   
        } catch (UnsuccessfulLoginException ex) {
            errorService.setHtmlErrors(ex.getMessage());
        }
        return "redirect:/home";
    }
    
    //Thymeleaf
    @RequestMapping(value="logout", method = RequestMethod.GET)
    String logout() {
        errorService.resetHtmlErrors();
        sessionService.logOut();
        return "redirect:/home";
    } 
    
}
