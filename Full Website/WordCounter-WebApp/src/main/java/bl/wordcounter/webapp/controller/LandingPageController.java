/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.entity.Text;
import bl.wordcounter.webapp.exception.UnsuccessfulLoginException;
import bl.wordcounter.webapp.service.AccountService;
import bl.wordcounter.webapp.service.SessionService;
import bl.wordcounter.webapp.service.TextService;
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
public class LandingPageController {
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    TextService textService;
    
    //Thymeleaf
    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    String displayHome(Model model) {        
        boolean isLoggedIn = false;
        boolean doAnalyze = false;
        String email = "";
        String doAnalyzeContent = "";
        
        if (sessionService.getSessionStatus() == SessionService.LOGGED_IN) {
            isLoggedIn = true;
            email = accountService.getAnAccount(sessionService.getSessionOwner()).getEmail();
            try {
                Text text = textService.getDisplayText();
                doAnalyzeContent = text.getContent();
                doAnalyze = true;
                textService.clearDisplayText();
            } catch (NullPointerException ex) {               
            }
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("doAnalyze", doAnalyze);
        model.addAttribute("email", email);
        model.addAttribute("doAnalyzeContent", doAnalyzeContent);
        
        return "Home";
    }
    
    //AJAX
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    String logout() {
        sessionService.logOut();
        return "redirect:/home";
    }
    
}