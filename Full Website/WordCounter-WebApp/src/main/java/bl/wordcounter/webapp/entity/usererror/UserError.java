/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.entity.usererror;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Boone
 */
public class UserError {
    
    private static List<String[]> errorList = new ArrayList<String[]>();
    private static boolean displayErrors = false;
    
    /*
    Error List =================================================================
    */
    public void addError(String errorMessage) {
        String[] s = new String[2];
        s[0] = errorMessage;
        s[1] = "Unnamed Error";
        errorList.add(s);
    }
    
    public void emptyErrorList() {
        errorList.clear();
    }
    
    public List<String[]> getErrorList() {
        return errorList;
    }
    
    /*
    Display Errors =============================================================
    */
    
    public boolean getDisplayErrors() {
        return displayErrors;
    }
    
    public void setDisplayErrors(boolean value) {
        displayErrors = value;
    }
            
}
