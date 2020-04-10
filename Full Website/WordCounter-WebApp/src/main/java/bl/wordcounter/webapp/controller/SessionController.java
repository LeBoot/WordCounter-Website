/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.exception.UnsuccessfulLoginException;
import bl.wordcounter.webapp.service.SessionService;
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
@RequestMapping("/session")
public class SessionController {
    
    @Autowired
    SessionService sessionService;
    
    //AJAX
    @RequestMapping(value = "login", method = RequestMethod.POST)
    ResponseEntity<Object> attemptLogin(
            @RequestParam("formEmail") String email,
            @RequestParam("formPass") String password
            ) {
        if (sessionService.getSessionStatus() == SessionService.LOGGED_IN) {
            sessionService.logOut();
        }
        try {
            sessionService.logIn(email, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UnsuccessfulLoginException ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
    
    //Thymeleaf
    @RequestMapping(value="logout", method = RequestMethod.GET)
    String logout() {
        sessionService.logOut();
        return "redirect:/home";
    } 
    
}
