/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.exception.EmailNotSentException;

/**
 *
 * @author Boone
 */
public interface MailService {
    
    void sendForgotPassword(String recipientAddress, String newPassword)
            throws EmailNotSentException;
    
}
