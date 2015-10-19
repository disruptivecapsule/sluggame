import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class Game implements Runnable {

	public final static int SCALE = 10;
	public Dimension dim;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		
		final JFrame frame = new JFrame("SNAKE REMAKE");
		frame.setLocation(800, 720);
		frame.setResizable(false);
		
		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.NORTH);
		final JLabel status = new JLabel("Cherry-hunting...");
		status_panel.add(status);
		
		// Instructions panel
		final JPanel instr_panel = new JPanel();
		frame.add(instr_panel, BorderLayout.SOUTH);
		final JLabel instructions = new JLabel(
				"<html>INSTRUCTIONS:"
				+ "<br>Use arrowkeys to move. Use space bar to pause. "
				+ "Don't hit the wall and beat the High Score!"
				+ "<br>In Two Players Mode, Player 1 starts from top left corner, using arrowkeys; "
				+ "Player 2 starts from <br>lower right corner, using W, S, A, D keys. "
				+ "Remember not to hit each other! :)"
				, SwingConstants.CENTER);
		instr_panel.add(instructions);
		

		// Main playing area
		final GameCourt court = new GameCourt(status);
		frame.add(court, BorderLayout.CENTER);
	
		
		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton singlePlayer = new JButton("Start Single Player");
		singlePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.multiPlayer = false;
				court.startGame();
			}
		});
		status_panel.add(singlePlayer);
		
		final JButton multiPlayer = new JButton("Start Two Players");
		multiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.multiPlayer = true;
				court.startGame();
			}
		});
		status_panel.add(multiPlayer);
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// Start game
		court.startGame();
	}
	
	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

}
