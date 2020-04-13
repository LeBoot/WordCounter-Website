/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.entity;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Boone
 */
@Data
public class TextReturn {
    
    private List<Integer> occuranceList;
    private List<String> labelList;
    
}
