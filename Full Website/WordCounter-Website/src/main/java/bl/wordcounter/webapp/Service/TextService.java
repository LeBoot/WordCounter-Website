/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import bl.wordcounter.webapp.Entity.Text;

/**
 *
 * @author Boone
 */
public interface TextService {
    
    Text retrieveText(int id);
    Text createText(Text text);
    Text editText(Text text);
    void deleteText(int id);

}
