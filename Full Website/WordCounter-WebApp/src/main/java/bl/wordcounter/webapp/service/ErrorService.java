/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import java.util.List;

/**
 *
 * @author Boone
 */
public interface ErrorService {
 
    void resetHtmlErrors();
    void setHtmlErrors(String errorMessage);
    
    //Pass-through methods:
    public void addError(String errorMessage);
    void emptyErrorList();
    List<String[]> getErrorList();
    boolean getDisplayErrors();
    void setDisplayErrors(boolean value);
    
}
