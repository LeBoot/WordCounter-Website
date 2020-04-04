/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl.wordcounter.webapp.enigma;

import bl.wordcounter.webapp.enigma.exception.InvalidKeyException;
import bl.wordcounter.webapp.enigma.exception.InvalidPasswordException;
import java.util.Random;

/**
 *
 * @author Boone
 */
public class Enigma {
    
    //PUBLIC METHODS ===========================================================
    //============================================================================================

    /**
     * Auto-generates a password that is built from VALID_CHARACTERS.
     * If the input length is less than 4, the output length will be 4.
     * If the input length is greater than 20, the output length will be 10.
     * If the input length is between 4 and 20 (inclusively), the output length
     *  will be the input length.
     * @param length
     * @return String new password
     */
    public String autogeneratePassword(int length) {
        Random randGen = new Random();
        
        if (length < 4) {
            length = 4; //arbitrary
        } else if (length > 20) {
            length = 10; //arbitrary
        }
        
        int min = 0;
        int max = VALID_CHARACTERS.length - 1;
        
        String password = "";
        while(password.length() < length) {
            int n = randGen.nextInt((max - min) + 1) + min;
            try {
                password += VALID_CHARACTERS[n];
            } catch (Exception e) {}
        }
        
        return password;
    }   
    
    /**
     * Encrypts a password.
     * Validates that the input contains only valid characters.
     *  If false, throws an exception.
     *  If true, validates that the input is between 1 and LENGTH (64) characters
     *   (inclusively).
     * If the input is not within the boundary length, an exception is thrown.
     * If the input is within the length, it is build up to that maximum length
     *  (if necessary).
     * If no exceptions are thrown, it builds an encrypted password (of length
     *  LENGHT [64]) and returns that along with the key that was used to encrypt
     *  it (and that must be used to decrypt or match it).
     * @param input (String to be encrypted)
     * @return String[] array.
     *  -Array[0] is the encrypted password, which is formed of valid characters
     *      and is of length LENGTH.
     *  -Array[1] is the key that was used to encrypt the password and that must
     *   be used to decrypt or match it.
     * @throws InvalidPasswordException
     * @throws InvalidKeyException 
     */
    public String[] encryptPassword(String input) throws InvalidPasswordException, InvalidKeyException {
        String[] returnArray = new String[2];
        try {
            validateCharacters(input);
            String key = buildNewKey(input);
            input = validateLength(input);
            returnArray[0] = buildEncryptedPassword(input, key);
            returnArray[1] = key;
            return returnArray;
        } catch (InvalidPasswordException | InvalidKeyException ex) {
            throw ex;
        }
    }
    
    /**
     * Determines if a password matches the one that was used to create the
     *  encrypted password (using the provided key).
     * @param unencrypted
     * @param encrypted
     * @param key
     * @return Boolean: true if passwords match; false if not.
     */
    public boolean doesPasswordMatch(String unencrypted, String encrypted, String key) {       
        try {
            encrypted = decrypt(encrypted, key);
            return unencrypted.equals(encrypted);
        } catch(InvalidKeyException ex) {
            return false;
        }
    }    
    
    
    //PRIVATE METHODS ==========================================================
    //============================================================================================
    
    /**
     * Takes a 64-character string that is known to contain only valid characters,
     *  and encrypts that string based on the key.
     * @param String s (password to be encrypted)
     * @param String key
     * @return String (the encrypted password)
     * @throws InvalidKeyException 
     */
    private String buildEncryptedPassword(String s, String key) throws InvalidKeyException {
        String[] twiceValidChar = doubleValidCharacters();
        int[] keyAr = dissectKey(key);
        
        String newString = "";
        for (int i = 0; i < s.length(); i++) {
            String sub = s.substring(i, i + 1);
            for (int j = 0; j < VALID_CHARACTERS.length; j++) {
                if (VALID_CHARACTERS[j].equals(sub)) {
                    newString += twiceValidChar[j + keyAr[i]];
                }
            }
        }
        
        return newString;
    }

    /**
     * Generates new keys.
     * The number of tokens for new keys is LENGTH + 2.
     *  Maximum length: (2 digits * (LENGTH + 2) tokens) + ((LENGTH + 2) - 1 delimiters)
     *   = (2 * 66) + (65) = 197.
     *  Minimum length: (1 digit * (LENGTH + 2) tokens) + ((LENGTH + 2) - 1 delimiters)
     *   = 66 + 65 = 131.
     * New tokens are a randomly-generated value between 0 and VALID_CHARACTERS.length - 1.
     * The form of a key is as follows:
     *  [token1]:[token2]: ... :[token63]:[token64]:[originalLength]:[versionCharacters].
     * @param String s
     * @return String (new Key)
     */
    private String buildNewKey(String s) {
        Random randGen = new Random();
        
        String newKey = "";
        int max = VALID_CHARACTERS.length - 1;
        int min = 0;
        
        for (int i = 0; i < LENGTH; i++) {
            int keyToken = randGen.nextInt((max - min) + 1) + min;
            newKey += Integer.toString(keyToken) + DELIMITER;
        }
        
        newKey += Integer.toString(s.length()) + DELIMITER;
        newKey += Integer.toString(1);
        
        return newKey;
    }
    
    /**
     * Returns the LENGTH-(64)-character string that was used to build the encrypted
     *  password, provided that the correct key accompanies it.
     * @param String encrypted
     * @param String key
     * @return String (partially decrypted version of encrypted string)
     * @throws InvalidKeyException 
     */
    private String decrypt(String encrypted, String key) throws InvalidKeyException {
        String[] twiceValidChar = doubleValidCharacters();
        int[] keyAr = dissectKey(key);
        String decrypted = "";
        
        for (int i = 0; i < encrypted.length(); i++) {
            String sub = encrypted.substring(i, i + 1);
            for (int j = 0; j < twiceValidChar.length; j++) {
                if (sub.equals(twiceValidChar[j])) {
                    int originalIndex = j - keyAr[i];
                    if (originalIndex >= 0 && originalIndex <= VALID_CHARACTERS.length - 1) {
                        decrypted += VALID_CHARACTERS[originalIndex];
                        break;
                    }
                }
            }
        }
        
        decrypted = decrypted.substring(0, keyAr[keyAr.length - 2]);
        return decrypted;
    }
    
    /**
     * Takes a key and returns that key as an int[] array, with each index being
     *  a token of the key.
     * @param String key
     * @return int[] array of key tokens
     * @throws InvalidKeyException 
     */
    private int[] dissectKey(String key) throws InvalidKeyException {
        try {
            String[] strAr = key.split(DELIMITER);
            int[] intAr = new int[strAr.length];

            for (int i = 0; i < strAr.length; i++) {
                intAr[i] = Integer.parseInt(strAr[i]);
            }

            return intAr; 
        } catch (Exception ex) {
            throw new InvalidKeyException();
        } 
    }
    
    /**
     * String[] array that is a concatenation of the VALID_CHARACTERS array with
     *  itself.
     * @return String[] array
     */
    private String[] doubleValidCharacters() {
        int l = VALID_CHARACTERS.length;
        String[] doubled = new String[l * 2];
        
        for (int i = 0; i < (l * 2); i++) {
            if (i < l) {
                doubled[i] = VALID_CHARACTERS[i];
            } else {
                doubled[i] = VALID_CHARACTERS[i - l];
            }
        }
        
        return doubled;
    }  
    
    /**
     * Ensures that the incoming string contains only the approved characters.
     *  If not, throws exception
     * @param String s
     * @throws InvalidPasswordException 
     */
    private void validateCharacters(String s) throws InvalidPasswordException {
        for (int i = 0; i < s.length(); i++) {
            String sub = s.substring(i, i + 1);
            boolean isMatched = false;
            for (String validChar : VALID_CHARACTERS) {
                if (sub.equals(validChar)) {
                    isMatched = true;
                    break;
                }
            }
            if (isMatched == false) {
                throw new InvalidPasswordException("Invalid Character: " + sub);
            }
        }
    }
    
    /**
     * Ensures that the length of the incoming string is between 1 and LENGTH (64)
     *  (inclusively).  If false, throws exception.
     * If true, the length of the string is build to 64 characters (if necessary)
     *  by building a new string from multiple full and/or partial copies of the
     *  input string, until the total length is equal to LENGTH.
     * @param String s
     * @return String (of length 64)
     * @throws InvalidPasswordException 
     */
    private String validateLength(String s) throws InvalidPasswordException {
        int l = s.length();
        if (l > LENGTH) {
            throw new InvalidPasswordException("Invalid Length: Too long.");
        }
        if (l < 1) {
            throw new InvalidPasswordException("Invalid Length: Too short.");
        }
        
        while (l < LENGTH) {
            if (l <= (LENGTH/2)) {
                s += s;
            } else {
                s += s.substring(0, (LENGTH - l));
            }
            l = s.length();
        }
        
        return s;
    }
    
    
    //CONSTANTS ================================================================
    //============================================================================================
    
    /**
     * The maximum length of new strings.
     */
    private final int LENGTH = 64;
    
    /**
     * Array of valid characters.  This determines both what characters can be
     * contained in the incoming string, as well as what characters will compose
     * the output (encrypted) string.
     */
    private final String[] VALID_CHARACTERS = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d",
        "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
        "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
        "y", "z", "!", "@", "#", "$", "%", "&", "?"
    };
    
    /**
     * Delimiter for keys.
     */
    private final String DELIMITER = ":";
    
    
    //OTHER ====================================================================
    private String viewOriginalPassword(String encrypted, String key) throws InvalidKeyException {
        return decrypt(encrypted, key);
    }
}
