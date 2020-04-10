/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.service.AccountService;
import bl.wordcounter.webapp.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    //Thymeleaf
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    String displayHome(Model model) {        
        boolean isLoggedIn = false;
        String email = "";
        if (sessionService.getSessionStatus() == SessionService.LOGGED_IN) {
            isLoggedIn = true;
            email = accountService.getAnAccount(sessionService.getSessionOwner()).getEmail();
        }
        
        model.addAttribute("email", email);
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "Home";
    }
    
}