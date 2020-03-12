/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Boone
 */
public interface ServiceLayer {
    
    Map<String, Integer> getResultsAlphabetically(String input);
    List<Entry<String, Integer>> getResultsByOccurance(Map<String, Integer> map);
  
}