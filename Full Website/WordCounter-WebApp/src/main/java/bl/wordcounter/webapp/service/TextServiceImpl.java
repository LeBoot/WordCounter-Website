/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.service;

import bl.wordcounter.webapp.entity.Text;
import bl.wordcounter.webapp.entity.TextReturn;
import bl.wordcounter.webapp.exception.SavingTextException;
import bl.wordcounter.webapp.repository.TextRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Boone
 */
@Service
public class TextServiceImpl implements TextService {

    @Autowired
    TextRepository textRepo;
    
    @Override
    public Text saveText(Text text) throws SavingTextException {
        validateText(text);
        return textRepo.save(text);
    }

    @Override
    public Text getAText(int id) throws NoSuchElementException {
        Optional<Text> textOpt = textRepo.findById(id);
        return textOpt.orElseThrow();
    }

    @Override
    public List<Text> getAllTextsForAccount(int id) {
        try {
            List<Text> list = textRepo.findAllForAccount(id);
            list = truncateList(list);
            return list;
        } catch (Exception e) {
            List<Text> emptyList = new ArrayList<>();
            return emptyList;
        }
    }

    @Override
    public void deleteText(int id) {
        textRepo.deleteById(id);
    }
    
    //This is a list of characters to remove from words
    static final String[] EXCLUSIONS = {"...", ".", ",", "!", ";", "?", "\"", "'", ")", "(", "}", "{", ":"};
    
    @Override
    public TextReturn analyze(String input) {
        Map<String, Integer> wordOccurances = new TreeMap<>();
        
        //Build the map of all words in alphabetical order
        int max = 0;
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
                
                if (wordOccurances.get(word) > max) {
                    max = wordOccurances.get(word);
                }
            }  
        }
        
        //Sorty the list by largest value to smallest value
        List<String> labels = new ArrayList<>();
        List<Integer> occurances = new ArrayList<>();
        for (int i = max; i > 0; i--) {
            for (String key : wordOccurances.keySet()) {
                if (wordOccurances.get(key) == i) {
                    labels.add(key);
                    occurances.add(i);
                }
            }
        }

        return new TextReturn(occurances, labels);
    }
    
    private void validateText(Text text) throws SavingTextException {
        
        int titleMax = 50;
        int titleLength = text.getTitle().length();
        if ((titleLength > titleMax) || (titleLength < 1)) {
            String message = "Please use a title that is fewer than " + titleMax + " characters.";
            throw new SavingTextException(message);
        }
        
        int contentMax = 5000;
        int contentLength = text.getContent().length();
        if ((contentLength > contentMax) || (contentLength < 1)) {
            String message = "Content must be fewer than " + contentMax + " characters.";
            throw new SavingTextException(message);
        }
    }
    
    private List<Text> truncateList(List<Text> list) {
        int maxLength = 100;
        for (Text text : list) {
            String content = text.getContent();
            if (content.length() > maxLength) {
                content = content.substring(0, maxLength) + "...";
                text.setContent(content);
            }
        }
        return list;
    }
    
}