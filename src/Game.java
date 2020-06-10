import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class Game extends JFrame {

    public Game() {
    	final JFrame frame2 = new JFrame("Minesweeper");
		frame2.setLayout(new BorderLayout());
		final JPanel status_panel = new JPanel();
	    frame2.add(status_panel, BorderLayout.SOUTH);
	    final JLabel status = new JLabel("");
	    status_panel.add(status);
	    final JButton retrieve = new JButton("Retrieve");
	    final JButton saveGame = new JButton("Save");
	    InternalBoard IB = new InternalBoard(status, saveGame, retrieve);
		frame2.add(IB, BorderLayout.CENTER);
		final JPanel control_panel = new JPanel();
	    frame2.add(control_panel, BorderLayout.NORTH);
	    retrieve.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	             IB.retrieve();
	        }
	    });
	    control_panel.add(retrieve);
	    final JButton newGame = new JButton("New Game");
	    newGame.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	             IB.newGame();
	        }
	    });
	    control_panel.add(newGame);
	    final JLabel MS = new JLabel("Mine Sweeper");
	    control_panel.add(MS);
	    saveGame.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	             IB.saveGame();
	        }
	    });
	    control_panel.add(saveGame);
	   
	    
		frame2.pack();
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		
		final JFrame frame1 = new JFrame("Instructions");
    	String s = "Hi! Welcome to minesweeper. This is a classic implementation of the game" + "\n"
    			+ " minesweeper. You click with two fingers to flag and you click with one" + "\n"
    			+ "to open a cell that you think does not contain a mine. Note that if you " + "\n"
    			+ "open a cell that has a mine in it, you lose! How do you avoid that? The " + "\n"
    			+ "key is to understand when you click on a square,the number that shows up " + "\n"
    			+ "is the number of mines there are in the 3 by 3 box it's in. So if a cell " + "\n"
    			+ "says 1, it means there's only 1 mine in the 8 boxes around it. One thing " + "\n"
    			+ "this game can do is you can click \"save\" when you are playing the game and " + "\n" 
    			+ "you can save the state of the game and try flagging some mines to see if the " + "\n"
    			+ "position of your flags add up, and if the number of mines don't add up, you " + "\n"
    			+ "can always click \"retrieve\" and go back to the state you saved.";
    	JTextArea instruction = new JTextArea(s);
    	frame1.setLayout(new BorderLayout());
    	frame1.add(instruction, BorderLayout.CENTER);
    	frame1.pack();
		frame1.setLocationRelativeTo(null);
    	frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame1.setVisible(true);
    }


    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game();
			}
        });
    }
}