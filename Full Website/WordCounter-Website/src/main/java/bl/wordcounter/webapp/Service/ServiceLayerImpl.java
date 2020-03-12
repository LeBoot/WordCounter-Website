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
public class ServiceLayerImpl implements ServiceLayer {

    //Keep a list of whatever characters are not to be included;
            //namely, those which might appear at the end of sentence
    static String[] charactersToExclude = {".", ",", "!", ";", "?", "\""};
    
    @Override
    public Map<String, Integer> getResultsAlphabetically(String input) {        
        //Split the string at the spaces
        String[] stringParts = input.split(" "); 
        
        //Keep a map of Word : Number of Occurances
        //TreeMap orders alphabeticallly
        Map<String, Integer> wordOccurances = new TreeMap<>();
        
        //Loop through each word found in the incoming string (which has been
            //sorted into an array, split at the delimiter)
        for (String myWord : stringParts) {
            //strip any additional white spaces and ignore case (by making lowercase)
            String word = myWord.toLowerCase().strip();
            
            //loop through each character-to-be-excluded.  Do it three times
            //in case multiple excluded characters are present (e.g., "?!" or "...")
            for (int i = 0; i < 3; i++) {
                for (String character : charactersToExclude) {
                    word = word.replace(character, "");
                }
            }
            
            //Ensure that the "word" is not a blank space
            if (!word.isBlank()) {
                //If the word has already been found, add 1 to the value of occurances
                if (wordOccurances.keySet().contains(word)) {
                    wordOccurances.put(word, wordOccurances.get(word) + 1);
                } 
                //if the word has not yet been found, add it to the map, with 1 occurance
                else {
                    wordOccurances.put(word, 1);
                }
            }
        }
        
        //return the map
        return wordOccurances;
    }

    @Override
    public List<Entry<String, Integer>> getResultsByOccurance(Map<String, Integer> map) {
        //These three lines convert the map into the list and sort it by value (occurances)
        List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);
        
        return list;
    }
    
}
