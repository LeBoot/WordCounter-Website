/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Boone
 */
public interface AnalysisService {
    
    List<Entry<String, Integer>> analyze(String input);
  
}