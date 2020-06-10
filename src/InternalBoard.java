import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class InternalBoard extends JPanel{
	private int numCell = 10;
	private int numMine = 10;
	private int[][] myBoard = new int[numCell][numCell];
	//state reference:
 		//uncovered not mine = 0-8;
		//exploded mine = 9;
		//flagged mine = 10;
		//Wrong flag = 11;
		//covered mine : 12;
		//covered not mine: 13;		
	private final int cellDim = 30;
	private int minesLeft;
	private int savedStatus;
	private Set<OrderedPair> revealed;
	private Set<OrderedPair> revealedSaved;
	private Set<OrderedPair> checkedZero;
	private Set<OrderedPair> checkedZeroSaved;
	private boolean hasSaved;
	private final int boaWid = numCell * cellDim;
	private final int boaHei = numCell * cellDim;
	private JLabel status;
	private JButton save;
	private JButton retrieve;
	private Boolean inGame;
	private Boolean shouldPaint; 
	
	static int[][] newBoard(int numMines, int numCells) {
		int[][] newBoard = new int[numCells][numCells];
		if(numMines > (numCells * numCells)) {
			numMines = numCells * numCells;
		}
		Set<OrderedPair> randomP = new TreeSet<OrderedPair>();
		int i = 0;
		while(i < numMines) {
			int x = (int)(Math.random() * numCells);
			int y = (int)(Math.random() * numCells);
			OrderedPair op = new OrderedPair(x, y);
			if(randomP.add(op)){
				i++;
			}
		}
		for(OrderedPair o: randomP) {
			int x = o.getX();
			int y = o.getY();
			newBoard[x][y] = 12;
		}
		
		for(int j = 0; j < numCells; j++) {
			for(int k = 0; k < numCells; k++) {
				if(newBoard[j][k] != 12) {
				   newBoard[j][k] = 13;
				}
			}
		}
		return newBoard;
	}
	
	public void newGame() {
		myBoard = newBoard(numMine, numCell);
		addMouseListener(new mouseMovement());
		inGame = true;
		shouldPaint = true;
		minesLeft = numMine;
		status.setText("" + minesLeft);
		revealed = new TreeSet<OrderedPair>();
		checkedZero = new TreeSet<OrderedPair>();
		save.setVisible(true);
		retrieve.setVisible(true);
		hasSaved = false;
		repaint();
	}
	
	public void saveGame() {
		String output = "";
		for(int i = 0; i < numCell; i++) {
			for(int j = 0; j < numCell; j++) {
				output += myBoard[i][j] + " ";
			}
			output += "\n";
		}
		try{
			BufferedWriter bw = 
				new BufferedWriter(
						new FileWriter("Save Game.txt"));
			bw.write(output);
			bw.close();
		}catch(IOException e) {
			System.out.println("Can't Save");
		}
		savedStatus = minesLeft;
		hasSaved = true;
		revealedSaved = revealed;
		checkedZeroSaved = checkedZero;
	}
	
	private void zeroProtocol(int i, int j) {
		OrderedPair curr = new OrderedPair(i, j);
		if(i >= 0 && j >= 0 && i < numCell && j < numCell) {
			if(myBoard[i][j] == 12 || myBoard[i][j] == 13) {
				myBoard[i][j] = getNumMines(i, j);
				revealed.add(curr);
			}
			if(myBoard[i][j] == 0 && !checkedZero.contains(curr)) {
				checkedZero.add(curr);
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if (dx != 0 || dy != 0) {
							zeroProtocol(i + dx, j + dy);
						}
					}
				}
			}
		}
	}
	
	public void retrieve() {
		if(hasSaved) {
		String filePath = "Save Game.txt";
		FileLineIterator it = new FileLineIterator(filePath);
		List<String> lines = new LinkedList<String>();
		while(it.hasNext()) {
			lines.add(it.next());
		}
		for(int i = 0; i < lines.size(); i++) {
			String[] ints = lines.get(i).split(" ");
			for(int j = 0; j < ints.length; j++) {
				myBoard[i][j] = Integer.parseInt(ints[j]);
			}
		}
		if(shouldPaint) {
			repaint();
		}
		minesLeft = savedStatus;
		status.setText("" + minesLeft);
		checkedZero = checkedZeroSaved;
		revealed = revealedSaved;
		}
	}
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		for(int i = 0; i < numCell; i++) {
			for(int j = 0; j < numCell; j++) {
				int state = myBoard[i][j];
				int x = i * cellDim;
				int y = j * cellDim;
				gc.drawRect(x, y, cellDim,cellDim);
				String path = null;
				if(inGame && state == 11) {
					path = "files/" + 10 + ".png";
				}else {
					path = "files/" + state + ".png";
				}
				BufferedImage img = null;
				try {img = ImageIO.read(new File(path));
				}catch(IOException e) {
					System.out.println("File Not Found");
				}
				gc.drawImage(img, x, y, cellDim, cellDim, null);
			}
		}
	}
	
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(boaWid, boaHei);
	}
	
	private class mouseMovement extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int i = x / cellDim;
			int j = y / cellDim;
			int state = myBoard[i][j];
			//System.out.println("" + state);
			OrderedPair curr = new OrderedPair(i, j);
			if(e.getButton() == MouseEvent.BUTTON3) {
				if(state == 12) {myBoard[i][j] = 10; minesLeft --; revealed.add(curr);}
				if(state == 13) {myBoard[i][j] = 11; minesLeft --; revealed.add(curr);}
				if(state == 10) {myBoard[i][j] = 12; minesLeft ++; revealed.remove(curr);}
				if(state == 11) {myBoard[i][j] = 13; minesLeft ++; revealed.remove(curr);}
				status.setText("" + minesLeft);
			}else {
				if(state == 12) {
					myBoard[i][j] = 9; 
					lostProtocol();
				}
				if(state == 13) {
					myBoard[i][j] = getNumMines(i, j);
					revealed.add(curr);
					if(myBoard[i][j] == 0) 
						zeroProtocol(i, j);
					}
				}
				if(state <= 8 && state >= 1) {
					int flag = 0;
					for (int dx = -1; dx <= 1; dx++) {
						for (int dy = -1; dy <= 1; dy++) {
							if ((dx != 0 || dy != 0) &&
									   (i + dx >= 0 && i + dx < numCell 
									   && j + dy >= 0 && j + dy < numCell)){
								if(myBoard[i + dx][j + dy] == 10 || myBoard[i + dx][j + dy] == 11) {
									flag ++;
								}
							}
						}
					}
					if (flag == state) {
						for (int dx = -1; dx <= 1; dx++) {
							for (int dy = -1; dy <= 1; dy++) {
								if ((dx != 0 || dy != 0) &&
										   (i + dx >= 0 && i + dx < numCell 
										   && j + dy >= 0 && j + dy < numCell)) {
									if(myBoard[i + dx][j + dy] == 13) {
										myBoard[i + dx][j + dy] = getNumMines(i + dx, j + dy);
										revealed.add(new OrderedPair(i + dx, j + dy));
										if(myBoard[i + dx][j + dy] == 0) {zeroProtocol(i + dx, j + dy);}
									}else if (myBoard[i + dx][j + dy] == 12) {
										myBoard[i + dx][j + dy] = 9; lostProtocol();
									}
								}
							}
						}
					}
				}
			if(revealed.size() == numCell * numCell) {
				inGame = false;
				status.setText("You Win!");
				save.setVisible(false);
				retrieve.setVisible(false);
			}
			if(shouldPaint) {
				repaint();
				if(!inGame) {
					shouldPaint = false;
				}
			}
		}
	}
	
	private int getNumMines(int i, int j) {
		int numMines = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if ((dx != 0 || dy != 0) && 
				(i + dx >= 0 && i + dx < numCell 
				&& j + dy >= 0 && j + dy < numCell)
				&& (myBoard[i + dx][j + dy] == 10
				|| myBoard[i + dx][j + dy] == 12
				|| myBoard[i + dx][j + dy] == 9)) {
				numMines++;
				}
			}
		}
		return numMines;
	}
	
	private void lostProtocol() {
		for(int i = 0; i < numCell; i++) {
			for(int j = 0; j < numCell; j++) {
				int state = myBoard[i][j];
				if(state == 12) {
					myBoard[i][j] = 9;
				}
			}
		}
		inGame = false;
		status.setText("You Lose");
		save.setVisible(false);
		retrieve.setVisible(false);
	}
	
	public InternalBoard(JLabel status, JButton save, JButton retrieve) {
		this.status = status;
		this.save = save;
		this.retrieve = retrieve;
		newGame();
	}
}
	
