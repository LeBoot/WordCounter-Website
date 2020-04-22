/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.entity.Text;
import bl.wordcounter.webapp.entity.TextReturn;
import bl.wordcounter.webapp.exception.SavingTextException;
import bl.wordcounter.webapp.exception.TitleTakenException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Boone
 */
public interface TextService {
    
    Text getDisplayText() throws NullPointerException;
    void setDisplayText(int textId) throws NoSuchElementException;
    void clearDisplayText();
    Text saveText(Text text) throws SavingTextException;
    Text getAText(int id) throws NoSuchElementException;
    List<Text> getAllTextsForAccount(int id);
    void deleteText(int id);
    void editText(int accountId, Text text, boolean checkTitle)
            throws SavingTextException, TitleTakenException;
    TextReturn analyze(String input);
    
}
