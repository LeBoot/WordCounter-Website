/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    //This is a list of characters to remove from words
    static final String[] EXCLUSIONS = {"...", ".", ",", "!", ";", "?", "\"", "'"};
    
    @Override
    public List<Entry<String, Integer>> analyze(String input) {
        Map<String, Integer> wordOccurances = new TreeMap<>();
        
        String[] stringParts = input.split(" ");
        for (String s : stringParts) {
            String word = s.toLowerCase().strip();
            for (int i = 1; i <= 2; i++) {
                for (String character : EXCLUSIONS) {
                    word = word.replace(character, "");
                }
            }
            if (!word.isBlank()) {
                if (wordOccurances.containsKey(word)) {
                    wordOccurances.put(word, wordOccurances.get(word) + 1);
                } else {
                    wordOccurances.put(word, 1);
                }
            }
        }
        
        return reverseMap(wordOccurances); 
    }
    
    private List<Entry<String, Integer>> reverseMap(Map<String, Integer> map) {
        List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);
        return list;
    }
    
}
