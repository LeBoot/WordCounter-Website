/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Boone
 */
public class WordCounter {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        int choice = askChoice(2);
        String string = askString();       
        
        switch (choice) {
            case 1:
                printMapAlphabetically(parseString(string));
                break;
            case 2:
                printMapByOccurance(parseString(string));
                break;
            default:
                System.out.println("\nThat was bad input.  Bye now.");
                System.exit(0);
        }
        
    }
    
    private static int askChoice(int max) {
        boolean isInputValid = false;
        int input = 0;
        
        while (!isInputValid) {
            System.out.println("Will you want your list sorted (1) alphabetically or (2) by occurance?");
            try {
                input = Integer.parseInt(sc.nextLine());
                if ((input > 0) && (input <= max)) {
                    isInputValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 and " + max + ".");
            }
        }
        
        return input;
    }
    
    private static String askString() {
        boolean isInputValid = false;
        String input = "default";
        
        while (!isInputValid) {
            System.out.println("\nPlease enter the text you wish to analyze: ");
            input = sc.nextLine();
            if (input.isBlank()) {
                System.out.println("Please enter valid text.");
            } else if (input.equalsIgnoreCase("eggs")) {
                input = getGreenEggs();
                isInputValid = true;
            } else {
                isInputValid = true;
            }
        }
        
        return input;
    }
    
    private static void printMapByOccurance(Map<String, Integer> myMap) {       
        //These three lines convert the map into the list and sort it by value (occurances)
        List<Entry<String, Integer>> list = new ArrayList<>(myMap.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);
        
        System.out.println("\nTotal Distinct Words: " + myMap.keySet().size());
        System.out.println("");
        
        list.forEach((entry) -> {
            System.out.println(entry);
        });
        
    }
    
    
    private static void printMapAlphabetically(Map<String, Integer> myMap) {
        System.out.println("\nTotal Distinct Words: " + myMap.keySet().size());
        System.out.println("");
        
        myMap.keySet().forEach((key) -> {
            System.out.println(key + " : " + myMap.get(key));
        });
    }
    
    private static Map<String, Integer> parseString(String myString) {
        //Keep a list of whatever characters are not to be included;
            //namely, those which might appear at the end of sentence
        String[] charactersToExclude = {".", ",", "!", ";", "?", "\""};
        
        //Split the string at the spaces
        String[] stringParts = myString.split(" "); 
        
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
    
    private static String getGreenEggs() {
        return "I am Sam. Sam I am " +
            "That Sam-I-am! That Sam-I-am! " +
            "I do not like that Sam-I-am! " +
            "Do you like green eggs and ham? " +
            "I do not like them, Sam-I-am. " +
            "I do not like green eggs and ham. " +
            "Would you like them here or there? " +
            "I would not like them here or there. " +
            "I would not like them anywhere. " +
            "I do not like green eggs and ham. " +
            "I do not like them, Sam-I-am. " +
            "Would you like them in a house? " +
            "Would you like them with a mouse? " +
            "I do not like them in a house. " +
            "I do not like them with a mouse. " +
            "I do not like them here or there. " +
            "I do not like them anywhere. " +
            "I do not like green eggs and ham. " +
            "I do not like them, Sam-I-am. " +
            "Would you eat them in a box? " +
            "Would you eat them with a fox? " +
            "Not in a box. Not with a fox. " +
            "Not in a house. Not with a mouse. " +
            "I would not eat them here or there. " +
            "I would not eat them anywhere. " +
            "I would not eat green eggs and ham. " +
            "I do not like them, Sam-I-am. " +
            "Would you? Could you? In a car? " +
            "Eat them! Eat them! Here they are. " +
            "I would not, could not, in a car. " +
            "You may like them. You will see. " +
            "You may like them in a tree! " +
            "I would not, could not in a tree. " +
            "Not in a car! You let me be. " +
            "I do not like them in a box. " +
            "I do not like them with a fox. " +
            "I do not like them in a house. " +
            "I do not like them with a mouse. " +
            "I do not like them here or there. " +
            "I do not like them anywhere. " +
            "I do not like green eggs and ham. " +
            "I do not like them, Sam-I-am. " +
            "A train! A train! A train! A train! " +
            "Could you, would you, on a train? " +
            "Not in a train! Not in a tree! " +
            "Not in a car! Sam! Let me be! " +
            "I would not, could not, in a box. " +
            "I could not, would not, with a fox. " +
            "I will not eat them with a mouse. " +
            "I will not eat them in a house. " +
            "I will not eat them here or there. " +
            "I will not eat them anywhere. " +
            "I do not like green eggs and spam. " +
            "I do not like them, Sam-I-am. " +
            "Say! In the dark? Here in the dark! " +
            "Would you, could you, in the dark? " +
            "I would not, could not, in the dark. " +
            "Would you, could you, in the rain? " +
            "I would not, could not, in the rain. " +
            "Not in the dark. Not on a train. " +
            "Not in a car. Not in a tree. " +
            "I do not like them, Sam, you see. " +
            "Not in a house. Not in a box. " +
            "Not with a mouse. Not with a fox. " +
            "I will not eat them here or there. " +
            "I do not like them anywhere! " +
            "You do not like green eggs and ham? " +
            "I do not like them, Sam-I-am. " +
            "Could you, would you, with a goat? " +
            "I would not, could not, with a goat! " +
            "Would you, could you, on a boat? " +
            "I could not, would not, on a boat. " +
            "I will not, will not, with a goat. " +
            "I will not eat them in the rain. " +
            "I will not eat them on a train. " +
            "Not in the dark! Not in a tree! " +
            "Not in a car! You let me be! " +
            "I do not like them in a box. " +
            "I do not like them with a fox. " +
            "I will not eat them in a house. " +
            "I do not like them with a mouse. " +
            "I do not like them here or there. " +
            "I do not like them anywhere! " +
            "I do not like green eggs and ham! " +
            "I do not like them, Sam-I-am. " +
            "You do not like them. So you say. " +
            "Try them! Try them! And you may. " +
            "Try them and you may, I say. " +
            "Sam! If you will let me be, " +
            "I will try them. You will see. " +
            "Say! I like green eggs and ham! " +
            "I do! I like them, Sam-I-am! " +
            "And I would eat them in a boat. " +
            "And I would eat them with a goat... " +
            "And I will eat them in the rain. " +
            "And in the dark. And on a train. " +
            "And in a car. And in a tree. " +
            "They are so good, so good, you see! " +
            "So I will eat them in a box. " +
            "And I will eat them with a fox. " +
            "And I will eat them in a house. " +
            "And I will eat them with a mouse. " +
            "And I will eat them here and there. " +
            "Say! I will eat them anywhere! " +
            "I do so like green eggs and ham! " +
            "Thank you! Thank you, Sam-I-am!";
    }
    
    
    
    
}
