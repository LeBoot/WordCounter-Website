/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Controller;

import bl.wordcounter.webapp.Service.AccountService;
import bl.wordcounter.webapp.Service.MailService;
import bl.wordcounter.webapp.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    
    //ACCOUNTS =================================================================
    
    @RequestMapping(value = "/log-in", method = RequestMethod.GET)
    public String login() {
        return "Login.";
    }
    
    @RequestMapping(value = "/log-out", method = RequestMethod.GET)
    public String logout() {
        return "Logout.";
    }
    
    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword() {
        return "Forgot Password.";
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
