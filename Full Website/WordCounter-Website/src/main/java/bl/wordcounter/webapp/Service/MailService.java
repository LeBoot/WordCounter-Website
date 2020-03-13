/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

/**
 *
 * @author Boone
 */
public interface MailService {
    
    public void sendForgotPassword(String recipientAddress, String newPassword) throws Exception;
    
}
