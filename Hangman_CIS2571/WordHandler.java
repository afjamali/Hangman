/**
 Project assignment
 File: WordHandler.java
 Author: Afshin Jamali
 Date: 05/01/2011
 Class: CIS 2571
 Descr: A game of Hangman.
 */

import java.io.*;
import java.util.Scanner;

import javax.swing.JFrame;

class WordHandler extends JFrame {    
    
	// Declare variables
    private File wordFile;
    private String[] wordArray, hintArray;
    private int[] rndNumArray;
    private int rndWordIndex, rndHintIndex;
    
    // No-arg constructor
    public WordHandler() throws IOException {
    	
    	int counter = 0; // Counter to set number of words in array
    	
    	// Create no file found try/catch block
    	try {    		
    		wordFile = new File("hangmanWords.txt"); // Create file object
            Scanner input = new Scanner(wordFile); // Create new scanner
            for (counter = 0; input.hasNext(); counter++) { // Count number of words in file            	
            	input.next();        	        	        	
            }
            wordArray = new String [counter]; // Set number of words
            hintArray = new String [counter];
            input.close();
            
            // Get input with comma separator for word and hint
            input = new Scanner(wordFile).useDelimiter("[,\\s]");         
            for (int i = 0; input.hasNext(); i++) {            	
            	wordArray[i] = input.next(); // Assign word to array
            	hintArray[i] = input.next(); // Assign hint to array
            }
            input.close();                		
    	}
    	catch (FileNotFoundException e) { // If file not found
            System.out.println("File does not exist: " + wordFile);
    	}    	
	}
    
    // Method to set random numbers
    public void setRandomNumbers() throws IOException {    	
    	rndWordIndex = 0; // Reset index numbers
    	rndHintIndex = 0;
    	boolean foundDuplicate;
    	rndNumArray = new int[wordArray.length];
    	
    	// Get random number
    	for (int i = 0; i < wordArray.length; i++) {
    		rndNumArray[i] = (int)(Math.random() * wordArray.length);    		
    		
    		// Check for duplicates
    		foundDuplicate = false;
    		for (int j = 0; j < i; j++) {
    			if (rndNumArray[i] == rndNumArray[j])
    				foundDuplicate = true;    			
    		}    		
    		if (foundDuplicate)
    			i--;    			
    	}
    }

    // Method to get random word
    public String getRandomWord() throws IOException {    	
    	String rndWord;    	
    	
    	// Check if number is less than length of array
    	if (rndWordIndex < wordArray.length) {
    		rndWord = wordArray[rndNumArray[rndWordIndex]];
    		rndWordIndex++;
    		return rndWord;
    	}
    	
    	// Randomize numbers if reach the end of array
    	setRandomNumbers();
    	rndWord = wordArray[rndNumArray[rndWordIndex]];
    	rndWordIndex++;
        return rndWord;
    }
    
   // Method to get hint word
    public String getHintWord() throws IOException {    	
    	String rndWord;
    	
    	// Check if number is less than length of array
    	if (rndHintIndex < hintArray.length) {
    		rndWord = hintArray[rndNumArray[rndHintIndex]];
    		rndHintIndex++;
    		return rndWord;
    	}
    	
    	// Randomize numbers if reach the end of array
    	setRandomNumbers();
    	rndWord = hintArray[rndNumArray[rndHintIndex]];
    	rndHintIndex++;
    	
        return rndWord;
    }
    
    // Method to get none-randomized word Array
    public String[] getArray () {
        return wordArray;
    }
}