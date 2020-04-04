/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.entity.usererror.UserError;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class ErrorServiceImpl implements ErrorService {

    UserError userError = new UserError(); 
    
    @Override
    public void resetHtmlErrors() {
        userError.emptyErrorList();
        userError.setDisplayErrors(false);
    }
    
    @Override
    public void setHtmlErrors(String errorMessage) {
        userError.addError(errorMessage);
        userError.setDisplayErrors(true);
    }
    
    @Override
    public void addError(String errorMessage) {
        userError.addError(errorMessage);
    }

    @Override
    public void emptyErrorList() {
        userError.emptyErrorList();
    }

    @Override
    public List<String[]> getErrorList() {
        return userError.getErrorList();
    }

    @Override
    public boolean getDisplayErrors() {
        return userError.getDisplayErrors();
    }

    @Override
    public void setDisplayErrors(boolean value) {
        userError.setDisplayErrors(value);
    }
    
}
