/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Account;
import bl.wordcounter.webapp.Entity.Text;
import bl.wordcounter.webapp.Exception.InvalidInputException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Boone
 */
public interface TextService {
    
    Text retrieveText(int id)
            throws NoSuchElementException;
    List<Text> retrieveAllTexts();
    List<Text> retrieveAllTextsForAccount(Account a);
    Text saveText(Text text)
            throws InvalidInputException;
    void deleteText(int id)
            throws NoSuchElementException;

}
