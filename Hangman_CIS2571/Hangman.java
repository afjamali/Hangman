/**
 Project assignment
 File: Hangman.java
 Author: Afshin Jamali
 Date: 05/01/2011
 Class: CIS 2571
 Descr: A game of Hangman.
 */

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Hangman extends JFrame // Inheritance
		implements ActionListener, ItemListener { // Implementation

	// Declare variables
	private HangmanGraphics drawing;
	private WordHandler wordHandler;
	private int chances;
	private String word, hint;
	private char[] wrongGuesses;
	private int wordCounter, score;
	private JButton[] buttons = new JButton[26];
	private char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		'U', 'V', 'W', 'X', 'Y', 'Z'};
	private JPanel wordPanel, usedLettersPanel, messagePanel, wrongGuessesPanel,
				scorePanel, optionsPanel;
	private JLabel[] wordArray, usedLetters;
	private JLabel message, chancesLabel, scoreLabel;
	private Container chancesContainer;
	private JButton nextWord;
	private JMenuBar menuBar;
	private JMenuItem newGame, about, exit, umlDiagram;
	private JCheckBox onTop;
	private JRadioButton yellowB, grayB;
	private Border wordTitleBorder, raisedBevel;
	private JComboBox guessedCombo;

	// Main method
	public static void main(String[] args) throws IOException {

		// Invoke Hangman's no-arg constructor
		new Hangman();
	}

	// No-arg constructor
	public Hangman() throws IOException {

		// Set title
		super("Interview with the Hangman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	    // Set values
	    chances = 7;
	    wordCounter = 0;
	    score = 0;

	    // Create a menu bar
	    menuBar = new JMenuBar();

		// Create menubars and add to frame
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		// Create and add menu items the drop down menus
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this); // Register Listeners
		about = new JMenuItem("About");
		about.addActionListener(this);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		umlDiagram = new JMenuItem("UML Diagram");
		umlDiagram.addActionListener(this);

		fileMenu.add(newGame);
		fileMenu.add(about);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		viewMenu.add(umlDiagram);

	    // Create button panel for the letters and set GridLayout
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 13));
		for (int i = 0; i < letters.length; i++) {
		      buttons[i] = new JButton("" + letters[i]);
		      buttons[i].addActionListener(this); // Register Listener
		      buttons[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
		      buttons[i].setForeground(Color.RED);
		    }

		// Add buttons to the panel
	    for (int i = 0; i < letters.length; i++) {
	      buttonPanel.add(buttons[i]);
	    }

	    // Add button panel to frame
	    buttonPanel.setBackground(Color.LIGHT_GRAY);
	    buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder()); // Raises the border
	    add(buttonPanel, BorderLayout.SOUTH);

	    // Create a panel for the word and add to frame
	    wordPanel = new JPanel();
        wordPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        wordTitleBorder = BorderFactory.createTitledBorder("This is the word");
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        wordPanel.setBorder(BorderFactory.createCompoundBorder(raisedBevel, wordTitleBorder));
        add (wordPanel).setBounds(310, 270, 350, 70);

        // Create used letters panel for wrong guesses
        usedLettersPanel = new JPanel();
        usedLettersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 13, 5));

        // Create container to add chances label
        chancesContainer = new Container();
        chancesContainer.setLayout (new FlowLayout (FlowLayout.RIGHT));

        // Create panel and add chances container and used letters panel
        wrongGuessesPanel = new JPanel();
        wrongGuessesPanel.setLayout (new GridLayout (1, 2));
        wrongGuessesPanel.setBorder (BorderFactory.createTitledBorder ("Wrong Guesses"));
        wrongGuessesPanel.add (usedLettersPanel);
        wrongGuessesPanel.add (chancesContainer);
        add (wrongGuessesPanel).setBounds(310, 425, 400, 60);

        // Create combo box for list of used words and add to frame
	    guessedCombo = new JComboBox();
	    guessedCombo.addItem("Used Words");
	    guessedCombo.setSelectedItem(this); // Register Listener
	    add (guessedCombo).setBounds(350, 67, 150, 30);

        // Create a panel to use for hints and the correct words
        messagePanel = new JPanel();
        messagePanel.setLayout (new FlowLayout (FlowLayout.LEFT));
        add (messagePanel).setBounds(350, 200, 350, 40);

        // Create panel to track score
        scorePanel = new JPanel();
        scorePanel.setLayout (new FlowLayout (FlowLayout.LEFT));
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont (new Font ("Times new Roman", Font.BOLD, 17));
        scoreLabel.setForeground(Color.RED);
        scorePanel.add(scoreLabel);
        add(scorePanel).setBounds(350, 0, 110, 40);

        // Randomize words
		wordHandler = new WordHandler();
	    wordHandler.setRandomNumbers();

        // Create panel and add chebox and radiobuttons
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 1));
        onTop = new JCheckBox("Always on top");
        onTop.addItemListener(this); // Register Listeners
        onTop.setFont (new Font ("Times new Roman", Font.BOLD, 14));
        yellowB = new JRadioButton("Yellow backdrop");
        yellowB.setFont (new Font ("Times new Roman", Font.BOLD, 14));
        grayB = new JRadioButton("Gray backdrop");
        grayB.setSelected(true);
        grayB.setFont (new Font ("Times new Roman", Font.BOLD, 14));

        // Group radiobuttons and add to optionspanel
        ButtonGroup group = new ButtonGroup();
        group.add(yellowB);
        yellowB.addItemListener(this); // Register Listeners
        group.add(grayB);
        grayB.addItemListener(this);
        optionsPanel.add (onTop);
        optionsPanel.add (yellowB);
        optionsPanel.add (grayB);
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        add (optionsPanel).setBounds(535, 0, 170, 100); // Add panel to frame

        // Create button to advance to next word
        nextWord = new JButton("Next Word");
        nextWord.addActionListener(this); // Register Listener
        nextWord.setForeground(Color.RED);
        nextWord.setEnabled(false);
        add(nextWord).setBounds(535, 145, 100, 40);

        // Define and add hanging pole to frame
        drawing = new HangmanGraphics();
        add(drawing);

        // Get next word
        getNextWord();

        // Set frame options
        setBackground(Color.LIGHT_GRAY);
		setSize(710,615);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	// Create method to get next word and reset labels
	public void getNextWord() throws IOException {

		// Get random word and hint
		word = wordHandler.getRandomWord().toUpperCase();
		hint = wordHandler.getHintWord();

		// Create and assign word to label
        wordArray = new JLabel [word.length()];
        for (int i = 0 ; i < wordArray.length ; i++) {
            wordArray[i] = new JLabel("_");
            wordArray[i].setFont(new Font("Times New Roman", Font.BOLD, 26));
            wordPanel.add(wordArray[i]);
        }

        // Create chances label and set number
        chancesLabel = new JLabel (chances + " chances left...");
        chancesLabel.setFont (new Font ("Times new Roman", Font.BOLD, 17));
        chancesLabel.setForeground(Color.RED);
        chancesContainer.add (chancesLabel); // Add to ChancesContainer

        // Create label for wrong guesses
        wrongGuesses = new char[chances];
        usedLetters = new JLabel[wrongGuesses.length];
        for (int i = 0 ; i < usedLetters.length ; i++) {
            usedLetters[i] = new JLabel("::");
            usedLetters[i].setFont(new Font("Times new Roman", Font.BOLD, 18));
            usedLettersPanel.add(usedLetters[i]);
        }

        // Create label for the hint
        message = new JLabel (hint);
        message.setFont (new Font ("TypeWriter", Font.BOLD, 17));
        messagePanel.add (message);
	}

	// A listener method for buttons
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == newGame) {
            try {
            	Hangman.this.dispose();
				new Hangman(); 	// Disposes the current window and invokes new constructor
			} catch (IOException e1) {
				e1.printStackTrace();
				}
        }
		else if (e.getSource() == about) {
			drawing.showAboutWindow(); // Calls about window
		}
		else if (e.getSource() == exit) {
            Hangman.this.dispose();    // Exits game
            System.exit(0);
        }
        else if (e.getSource() == umlDiagram) { // Calls UML window
			drawing.showUMLDiagram();
		}
		else if (e.getSource() == nextWord) {
			nextWord.setEnabled(false);
			wordCounter++;
			guessedCombo.addItem(word); // Add used word to combo box
		    chances = 7; // Reset chances
		    drawing.redraw(); // Call redraw method in HangmanGraphics class to reset drawing
		    wordPanel.removeAll(); // remove all components from these containers
		    usedLettersPanel.removeAll();
		    chancesContainer.removeAll();
		    messagePanel.removeAll();
		    try {
		    	getNextWord(); // Get next word
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < buttons.length; i++) // Enable letter buttons
        		buttons[i].setEnabled(true);
        }
		else { // Perform game logic
			try {
				char input = e.getActionCommand().charAt(0); // Check is letter in word
                boolean letterInWord = false;
                for (int i = 0 ; i < word.length() ; i++) {
                	if (word.charAt(i) == input) {
                    letterInWord = true;
                    break;
                    }
                }

                // if guess is wrong
                if (!letterInWord) {
                	boolean alreadyGuessed = false; // Check if already guessed
                    for (int i = 0 ; i < wrongGuesses.length ; i++) {
                    	if (wrongGuesses[i] == input) {
                        alreadyGuessed = true;
                        break;
                        }
                    }
                    if (!alreadyGuessed) { // Reduce a point if not letter in word
                    	if (score > 0)
                    	score --;
                    	scoreLabel.setText("Score: " + score);
                    	chances--;
                        drawing.addPart(); // Add body part
                        if (chances > 0) { // Add letter to used letters label
                        	wrongGuesses [wrongGuesses.length - chances - 1] = input;
                            usedLetters [usedLetters.length - chances - 1].setText ("" + input);
                            chancesLabel.setText (chances + " chances left...");
                        } else { // if no chances left
                        	usedLetters [usedLetters.length - chances - 1].setText ("" + input);
                            chancesLabel.setText (chances + " chances left...");
                            message.setText ("The word is: " + word); // Show word
                            for (int i = 0; i < buttons.length; i++) // Disable letter buttons
                        		buttons[i].setEnabled(false);
                            nextWord.setEnabled(true); // Enable next word button
                        }
                    }
                }

                // If guess is right
                else {
                	boolean alreadyGuessed = false; // Check if already guessed
                    for (int i = 0; i < wordArray.length; i++) {
                    	if (wordArray[i].getText().charAt(0) == input) {
                        alreadyGuessed = true;
                        break;
                        }
                    }
                    if (!alreadyGuessed) {
                    	score += 2; // Add two points if letter is in word
                		scoreLabel.setText("Score: " + score);
                    	for (int i = 0; i < word.length(); i++) { // Fill word with correct letters
                    		if (word.charAt(0) == input) {
                    			wordArray[0].setText("" + input); // Check if first letter in word (caps)
                    		}
                    		if (word.charAt(i) == input) { // Set remaining letters to lower case
                    			String letter = ("" + input);
                    			wordArray[i].setText (letter.toLowerCase());
                    			}
                    		}
                    	boolean wordComplete = true; // Check if word is complete
                    	for (int i = 0; i < wordArray.length; i++) {
                    		if (!Character.isLetter (wordArray[i].getText().charAt (0))) {
                    			wordComplete = false;
                    			break;
                    			}
                    		}
                    	if (wordComplete) {  // If player completes word
                    		for (int i = 0; i < buttons.length; i++) // Disable letter buttons
                    		buttons[i].setEnabled(false);
                    		chancesLabel.setText ("<==== Nice! ====>"); // Display message
                    		drawing.escape(); // Reset part counter
                    		nextWord.setEnabled(true); // enable next word button
                    		}
                    }
                }
			} catch (Exception x) {	}
		}
	}

	// A listener method for radiobuttons and checkbox
	public void itemStateChanged(ItemEvent e) {
		if (e.getItemSelectable() == yellowB)
			setBackground(Color.YELLOW); // Set background to Yellow

		if (e.getItemSelectable() == grayB)
			setBackground(Color.LIGHT_GRAY); // Set background to Gray

		if (e.getItemSelectable() == onTop)
			Hangman.this.setAlwaysOnTop(true); // Set window always on top

		if (e.getStateChange() == ItemEvent.DESELECTED)
			Hangman.this.setAlwaysOnTop(false);	// Disable window always on top
	}
}