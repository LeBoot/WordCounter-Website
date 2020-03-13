/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Controller;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Entity.Text;
import bl.wordcounter.webapp.Exception.SessionLoggedOutException;
import bl.wordcounter.webapp.Service.AccountService;
import bl.wordcounter.webapp.Service.SessionService;
import bl.wordcounter.webapp.Service.TextService;
import java.util.List;
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
public class MainController {
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    TextService textService;
    
    //METHODS ==================================================================
    
    /*
    -- Use Thymeleaf.
    -- The hamburger menu should change whether logged in vs logged out.
    -- Logged out:
        - Log in button, sign up button.
    -- Logged in:
        - Log out button, my account button.
    */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    String displayHomepage(Model model) {
        boolean isLoggedIn = false;
        try {
            Account account = sessionService.getSessionOwner();
            isLoggedIn = true;
        } catch (SessionLoggedOutException ex) {}
        return "home";
    }
    
    /*
    -- Use Thymeleaf.
    -- If not logged in, the page should redirect.
    -- If logged in, display div "hello, email".
    -- If logged in and no stored texts, display a div that says so.
    -- If logged in and has stored texts, display all texts in a table.
    */
    @RequestMapping(value = "/my-account", method = RequestMethod.GET)
    public String displayAccountPage(Model model) {
        boolean isListPopulated = true;
        try {
            Account account = sessionService.getSessionOwner();
            model.addAttribute("greeting", account.getEmail());
            
            List<Text> texts = textService.retrieveAllTextsForAccount(account);
            if (texts.isEmpty()) {
                isListPopulated = false;
            } else {
                model.addAttribute("allTexts", texts);
            }       
            
            return "myAccount";
        } catch (SessionLoggedOutException ex) {
            return "home";
        }
    }
    
}