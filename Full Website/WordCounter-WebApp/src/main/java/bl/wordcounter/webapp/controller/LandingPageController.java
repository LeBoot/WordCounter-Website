/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.service.ErrorService;
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
    ErrorService errorService;
    
    //Thymeleaf
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    String displayHome(Model model) {
        if (errorService.getDisplayErrors()) {
            errorService.setDisplayErrors(false);
            //Display the error
        }
        
        if (sessionService.getSessionStatus() == 0) {
            //Display "log in" button
            //Display "sign up" button
        } else {
            //Display "log out" button
            //Display "my account" button
        }        
        return "Home";
    }
    
}