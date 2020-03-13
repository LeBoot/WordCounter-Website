/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Entity.Text;
import bl.wordcounter.webapp.Exception.InvalidInputException;
import bl.wordcounter.webapp.Repository.TextRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class TextServiceImpl implements TextService {

    @Autowired
    TextRepository textRepo;  
    
    /*
    Needed to validate that a text is associated with a valid account (when saving).
    */
    @Autowired
    AccountService accountService;
    
    @Override
    public Text retrieveText(int id) throws NoSuchElementException {
        Optional<Text> t = textRepo.findById(id);
        return t.orElseThrow();
    }

    @Override
    public List<Text> retrieveAllTexts() {
        return textRepo.findAll();
    }

    @Override
    public List<Text> retrieveAllTextsForAccount(Account a) {
        return textRepo.findAllForAccount(a.getId());
    }

    @Override
    public Text saveText(Text text) throws InvalidInputException {
        try {
            validateText(text, 50, 5000);
            return textRepo.save(text);
        } catch (InvalidInputException e) {
            throw e;
        }
    }

    @Override
    public void deleteText(int id) throws NoSuchElementException {
        try {
            Text t = retrieveText(id);
            textRepo.delete(t);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }
    
    private void validateText(Text text, int maxTitle, int maxContent) throws InvalidInputException {
        String title = text.getTitle();
        String content = text.getContent();
        String titleMessage = "Please enter a title that is fewer than " + maxTitle + " characters.";
        String contentMessage = "Please enter content that is fewer than " + maxContent + " characters.";
        String accountMessage = "Cannot save because the requested account is not found.";
        
        if (title.isBlank() || title.length() > maxTitle) {
            throw new InvalidInputException(titleMessage);
        }
        
        if (content.isBlank() || content.length() > maxContent) {
            throw new InvalidInputException(contentMessage);
        }
        
        try {
            accountService.retrieveAccount(text.getAccount().getId());
        } catch (NoSuchElementException e) {
            throw new InvalidInputException(accountMessage);
        }
    }
    
}
