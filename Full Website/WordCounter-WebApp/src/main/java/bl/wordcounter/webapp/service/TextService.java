/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.entity.Text;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Boone
 */
public interface TextService {
    
    Text saveText(Text text);
    Text getAText(int id) throws NoSuchElementException;
    List<Text> getAllTextsForAccount(int id);
    void deleteText(int id);
    
}