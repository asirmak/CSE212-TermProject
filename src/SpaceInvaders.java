import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SpaceInvaders{
	
	// Welcome screen for Space Invaders
	
	private static JMenu menuFile;
	private static JMenuItem itemRegister;
	private static JMenuItem itemPlayGame;
	private static JMenuItem itemHighScore;
	private static JMenuItem itemQuit;
	private static JMenu menuHelp;
	private static JMenuItem itemAbout;
	private static JMenuBar menuBar;
	private static JPanel myPanel1;
	private static Icon background;
	private static JLabel label2;
	private static JFrame myFrame;
	private static int level;

	public static void myMenuBar() {
	// Menu Bar ->File
			menuFile = new JMenu("File");
			itemRegister = new JMenuItem("Register");
			itemPlayGame = new JMenuItem("Play Game");
			itemHighScore = new JMenuItem("High Score");
			itemQuit = new JMenuItem("Quit");
			menuFile.add(itemRegister);
			menuFile.add(itemPlayGame);
			menuFile.add(itemHighScore);
			menuFile.add(itemQuit);

			// Menu Bar ->Help
			menuHelp = new JMenu("Help");
			itemAbout = new JMenuItem("About");
			menuHelp.add(itemAbout);
			
			// Menu Bar
			menuBar = new JMenuBar();
			menuBar.add(menuFile);
			menuBar.add(menuHelp);
	}
	
	public static void checkMenuBarActions() {
		// File -> Quit
        itemQuit.addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
               System.exit(0);
            }
        });
        
        // File -> Register
        itemRegister.addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		Register.registerScreen();
        	}
        });
        
        // File -> Play Game
        itemPlayGame.addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		if(Register.isLoggedInR()) {
        			highScoreScreen.setLoggedIn(true);
        			myFrame.dispose();
        			String levelStr = JOptionPane.showInputDialog("Enter Level(1-2-3)");
        			level = Integer.valueOf(levelStr);
        			Game.setCurrentLevel(level);
        			try {
        			    Game playGame = new Game();
        			    playGame.startGame();
        			} catch (Exception e) {
        			    e.printStackTrace();
        			}
        		}
        		else {
        	        JOptionPane.showMessageDialog(null, "You have to sign in before playing game", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
        	        Register.registerScreen();
        		}

        		
        	}
        });
        	
        
        // File -> High Scores
        itemHighScore.addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
    				myFrame.dispose();
        			highScoreScreen screenHS;
        			screenHS = new highScoreScreen();
        			screenHS.showScreen();
        	}
        });	
        
        
			
        // Help -> About
        itemAbout.addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		String about = "About Developer\n"
        				+ "Ali Sencer Irmak\n"
        				+ "20200702096\n"
        				+ "alisencer.irmak@std.yeditepe.edu.tr";
            	JOptionPane.showMessageDialog(null, about, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
	}
	
	public static void main(String[] args) 
	{				
		myMenuBar();
		// Creating panel and add menu bar
		myPanel1 = new JPanel(new BorderLayout());
		myPanel1.setPreferredSize(new Dimension(1280, 720));
		myPanel1.add(menuBar, BorderLayout.NORTH);
		
		// Import welcome screen background and add it to panel
		background = new ImageIcon("welcomeImage.png");
		label2 = new JLabel(background);
		myPanel1.add(label2, BorderLayout.CENTER);		
		
		// Frame
		myFrame = new JFrame("Space Invaders");
		myFrame.add(myPanel1);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(1280, 720);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		myFrame.setResizable(false);    
		checkMenuBarActions();
		
	}
	public static JMenu getMenuFile() {
		return menuFile;
	}

	public static void setMenuFile(JMenu menuFile) {
		SpaceInvaders.menuFile = menuFile;
	}

	public static JMenuItem getItemRegister() {
		return itemRegister;
	}

	public static void setItemRegister(JMenuItem itemRegister) {
		SpaceInvaders.itemRegister = itemRegister;
	}

	public static JMenuItem getItemPlayGame() {
		return itemPlayGame;
	}

	public static void setItemPlayGame(JMenuItem itemPlayGame) {
		SpaceInvaders.itemPlayGame = itemPlayGame;
	}

	public static JMenuItem getItemHighScore() {
		return itemHighScore;
	}

	public static void setItemHighScore(JMenuItem itemHighScore) {
		SpaceInvaders.itemHighScore = itemHighScore;
	}

	public static JMenuItem getItemQuit() {
		return itemQuit;
	}

	public static void setItemQuit(JMenuItem itemQuit) {
		SpaceInvaders.itemQuit = itemQuit;
	}

	public static JMenu getMenuHelp() {
		return menuHelp;
	}

	public static void setMenuHelp(JMenu menuHelp) {
		SpaceInvaders.menuHelp = menuHelp;
	}

	public static JMenuItem getItemAbout() {
		return itemAbout;
	}

	public static void setItemAbout(JMenuItem itemAbout) {
		SpaceInvaders.itemAbout = itemAbout;
	}

	public static JMenuBar getMenuBar() {
		return menuBar;
	}

	public static void setMenuBar(JMenuBar menuBar) {
		SpaceInvaders.menuBar = menuBar;
	}

	public static JPanel getMyPanel1() {
		return myPanel1;
	}

	public static void setMyPanel1(JPanel myPanel1) {
		SpaceInvaders.myPanel1 = myPanel1;
	}

	public static Icon getBackground() {
		return background;
	}

	public static void setBackground(Icon background) {
		SpaceInvaders.background = background;
	}

	public static JLabel getLabel2() {
		return label2;
	}

	public static void setLabel2(JLabel label2) {
		SpaceInvaders.label2 = label2;
	}

	public static JFrame getMyFrame() {
		return myFrame;
	}

	public static void setMyFrame(JFrame myFrame) {
		SpaceInvaders.myFrame = myFrame;
	}	
	
	public static int getLevel() {
		return level;
	}
}