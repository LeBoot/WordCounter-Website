/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.controller;

import bl.wordcounter.webapp.entity.Account;
import bl.wordcounter.webapp.entity.Text;
import bl.wordcounter.webapp.entity.TextReturn;
import bl.wordcounter.webapp.exception.SavingTextException;
import bl.wordcounter.webapp.exception.TitleTakenException;
import bl.wordcounter.webapp.service.AccountService;
import bl.wordcounter.webapp.service.SessionService;
import bl.wordcounter.webapp.service.TextService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Boone
 */
@Controller
@RequestMapping("/text")
public class TextController {
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    TextService textService;
    
    @Autowired
    AccountService accountService;
    
    private static String placeholderTitle;
    private static String placeholderContent;
    
    //AJAX
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    ResponseEntity<Object> deleteText(@PathVariable(value="id") String id) {
        textService.deleteText(Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //AJAX
    @RequestMapping(value = "get-list", method = RequestMethod.GET)
    ResponseEntity<Object> getAllTextsForAccount() {
        List<Text> list = textService.getAllTextsForAccount(sessionService.getSessionOwner());
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }
    
    //AJAX
    @RequestMapping(value = "new-1", method = RequestMethod.POST)
    ResponseEntity<Object> saveNewText1(
            @RequestParam("textTitle") String title,
            @RequestParam("textContent") String content
            ) {
        Account account = accountService.getAnAccount(sessionService.getSessionOwner());
        List<Text> list = textService.getAllTextsForAccount(account.getId());
        for (Text t : list) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                placeholderTitle = title;
                placeholderContent = content;
                String message = "One of your saved texts already has that title.";
                TitleTakenException ex = new TitleTakenException(message);
                return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
            }
        }
        
        Text newText = new Text();
        newText.setAccount(account);
        newText.setTitle(title);
        newText.setContent(content);
        try {
            textService.saveText(newText);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SavingTextException ex) {
            return new ResponseEntity<>(ex, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    //AJAX
    @RequestMapping(value = "new-2", method = RequestMethod.GET)
    ResponseEntity<Object> saveNewText2() {
        Text newText = new Text();
        Account account = accountService.getAnAccount(sessionService.getSessionOwner());        
        newText.setAccount(account);
        newText.setTitle(placeholderTitle);
        newText.setContent(placeholderContent);
        try {
            textService.saveText(newText);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SavingTextException ex) {
            return new ResponseEntity<>(ex, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    //AJAX
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    ResponseEntity<Object> getAText(@PathVariable(value="id") String id) {
        try {
            Text text = textService.getAText(Integer.parseInt(id));
            return new ResponseEntity<>(text, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(ex, HttpStatus.NO_CONTENT);
        }
    }
    
    //AJAX
    @RequestMapping(value = "analyze", method = RequestMethod.POST)
    ResponseEntity<Object> analyzeText2(@RequestParam("textContent") String content) {
        try {
            TextReturn tr = textService.analyze(content);
            return new ResponseEntity<>(tr, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }  
    }
    
}
