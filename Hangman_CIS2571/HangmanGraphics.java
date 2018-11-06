/**
 Project assignment
 File: HangmanGraphics.java
 Author: Afshin Jamali
 Date: 05/01/2011
 Class: CIS 2571
 Descr: A game of Hangman.
 */

import java.awt.*;
import javax.swing.*;

class HangmanGraphics extends JPanel {

	// Declare variables
	private int part;
	private JPanel textPanel;
	private JFrame aboutWindow, umlWindow;
	private JTextArea textArea;
	private String str;

	// No-arg constructor
	public HangmanGraphics() {
	}

	// Method for drawing graphics
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Create object and set thick stroke for hanging pole
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));

		// Declare variables for drawing
		int radius = 25;
		int x[] = {50, 50, 50, 80, 50, 190, 190, 190};
		int y[] = {450, 100, 150, 100, 100, 100, 100, 170};

		// Set panel options
		setSize(300, 485);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		g.drawArc(12, 450, 80, 2 * radius, 0, 180);
		g.drawPolyline(x, y, x.length);

		// Set thickness level for stick figure
		g2.setStroke(new BasicStroke(3));

		// Case for adding body parts
		switch(part) {
		case 0:
			g.setColor(Color.BLUE);
			g.drawString("\" 'ny last words...", 70, 300);
			g.drawString(" ...before ye hang?!\"", 70, 325);
			break;
		case 1: // add head
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			break;
		case 2: // add neck
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			g.drawLine(190, 220, 190, 245);
			break;
		case 3: // add right arm
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			g.drawLine(190, 220, 190, 245);
			g.drawLine(190, 245, 150, 300);
			break;
		case 4: // add left arm
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			g.drawLine(190, 220, 190, 245);
			g.drawLine(190, 245, 150, 300);
			g.drawLine(190, 245, 230, 300);
			break;
		case 5: // add body
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			g.drawLine(190, 220, 190, 245);
			g.drawLine(190, 245, 150, 300);
			g.drawLine(190, 245, 230, 300);
			g.drawLine(190, 245, 190, 320);
			break;
		case 6: // add left leg
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			g.drawLine(190, 220, 190, 245);
			g.drawLine(190, 245, 150, 300);
			g.drawLine(190, 245, 230, 300);
			g.drawLine(190, 245, 190, 330);
			g.drawLine(190, 330, 140, 400);
			break;
		case 7: // add right leg
			g.setColor(Color.RED);
			g.drawOval(165, 170, 2 * radius, 2 * radius);
			g.drawLine(190, 220, 190, 245);
			g.drawLine(190, 245, 150, 300);
			g.drawLine(190, 245, 230, 300);
			g.drawLine(190, 245, 190, 320);
			g.drawLine(190, 320, 140, 400);
			g.drawLine(190, 320, 230, 400);
			break;
		case -1: // stick figure lives
			g.setColor(Color.BLUE);
			g.drawString("\"Thou art pardoned this time\"", 65, 300);
			break;
		default:
			g.drawString("\"There's a problem\"", 180, 300);
			break;
		}
	}

	// Method for about window
	public void showAboutWindow() {

		// Create frame and set options
		aboutWindow = new JFrame("About");
		aboutWindow.setSize(330, 150);
		aboutWindow.setBackground(Color.WHITE);
		aboutWindow.setLocation(540, 200);

		// Create image icon
		ImageIcon noose = new ImageIcon("image/noose.jpg");
		aboutWindow.add(new JLabel(noose), BorderLayout.WEST);

		// String for message
		str = "Interview with the Hangman.\n" +
		"Version: 1.0\n" +
		"Created by: Afshin Jamali\n" +
		"May 1st, 2011\n" +
	    "Java application\n";

		// Create panel
		textPanel = new JPanel();

		// Create and add textArea to panel
		textArea = new JTextArea(str);
		textArea.setFont(new Font("Serif", Font.BOLD, 16));
		textArea.setForeground(Color.BLUE);
		textArea.setEditable(false);
		textPanel.add(textArea);
		aboutWindow.add(textPanel, BorderLayout.CENTER); // Add panel to frame
		aboutWindow.setVisible(true);
	}

	// Method for UML Diagram window
	public void showUMLDiagram() {

		// Create frame and set options
		umlWindow = new JFrame("UML Diagram");
		umlWindow.setSize(450, 240);
		umlWindow.setBackground(Color.WHITE);
		umlWindow.setLocationRelativeTo(null);

		// Create image icon
		ImageIcon uml = new ImageIcon("image/uml.jpg");

		//JScrollPane  scrollBar = new JScrollPane(uml);


		umlWindow.add(new JLabel(uml), BorderLayout.CENTER);

		umlWindow.setVisible(true);
	}

	// Method to add body part
	public void addPart() {
        part++;
        repaint();
    }

	// Method to indicate a win
    public void escape() {
        part = -1;
        repaint();
    }

    // Method to reset part number
    public void redraw() {
    	part = 0;
    	repaint();
    }
}
