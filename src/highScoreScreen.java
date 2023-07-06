import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class highScoreScreen extends JPanel implements ActionListener {
	
	private static JFrame frame;
	private JPanel panel;
	
	private List<Enemy>highScoreEnemies;
	private List<Enemy>highScoreEnemiesRow;
	private List<Enemy>highScoreEnemiesHorizon;
	private List<Enemy>highScoreEnemiesStable;

	private int enemyWidth = 80;
	private int enemyHeight = 80;
	
	private Image backgroundImage;
	private Image highScoreImage;
	
	private Image highScoreEnemyImage;
	private Image highScoreEnemyImage2;
	private Image highScoreEnemyImage3;
	
	private Image highScoreEnemyRowImage;
	private Image highScoreEnemyRowImage2;

	private Image highScoreEnemyHorizontalImage1;
	private Image highScoreEnemyHorizontalImage2;

	private Image highScoreEnemyStableImage1;
	private Image highScoreEnemyStableImage2;
	private Image highScoreEnemyStableImage3;
	
	private int backgroundY;

	private Timer timer;
	
	static Sound sound = new Sound(1);
	
	private static int level;
	
	private static boolean isLoggedIn = Register.isLoggedInR();

	public highScoreScreen() {
		setPreferredSize(new Dimension(1280, 720)); // panel size
		try{ // import images
			highScoreEnemyImage = ImageIO.read(new File("enemy21.png"));
			highScoreEnemyImage2 =ImageIO.read(new File("enemy22.png"));
			highScoreEnemyImage3 =ImageIO.read(new File("enemy23.png"));			
			
			highScoreEnemyRowImage = ImageIO.read(new File("enemyRow1.png"));
			highScoreEnemyRowImage2 = ImageIO.read(new File("enemyRow2.png"));			
			
			highScoreEnemyHorizontalImage1 = ImageIO.read(new File("enemy31.png"));
			highScoreEnemyHorizontalImage2 = ImageIO.read(new File("enemy32.png"));
						
			highScoreEnemyStableImage1 = ImageIO.read(new File("enemyStableL.png"));			
			highScoreEnemyStableImage2 = ImageIO.read(new File("enemyStableM.png"));			
			highScoreEnemyStableImage3 = ImageIO.read(new File("enemyStableR.png"));			
			
			backgroundImage = ImageIO.read(new File("background.png"));
			highScoreImage = ImageIO.read(new File("highscoreBG.png"));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		backgroundY = 0; // initially bg y-axis is 0, we need this to move bg (bg: background)
		
		highScoreEnemies = new ArrayList<>();
		highScoreEnemiesRow = new ArrayList<>();
		highScoreEnemiesHorizon = new ArrayList<>();
		highScoreEnemiesStable = new ArrayList<>();
		
		timer = new Timer(20,this);
		timer.start();
		
		spawnEnemies(); // decoration for enemies 
		enemyStatic(); // also animated/static enemies
	}
	
	
	public void showScreen() {
		// creating frame for high score screen and call constructor
		frame = new JFrame("High Score");
		panel = new highScoreScreen();
		frame.getContentPane().add(panel);
		
		try {
			descendOrderData();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		sound.setFile(4); // high score screen sound
		sound.play();
		sound.loop();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
		SpaceInvaders.myMenuBar();  // High Score screen also has menu bar and its items
	    frame.setJMenuBar(SpaceInvaders.getMenuBar());
	    checkMenuBarActions(); // check menu bar actions
	    setFrame(frame); // not to lose frame anyhow
	}
	
	public static void checkMenuBarActions() {
		// File -> Quit
		SpaceInvaders.getItemQuit().addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
               System.exit(0);
            }
        });
		// File -> Register
		SpaceInvaders.getItemRegister().addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		Register.registerScreen();
        	}
        });
        // File -> Play Game
		SpaceInvaders.getItemPlayGame().addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		if(Register.isLoggedInR()) {
        			
        			// delete old things
        			SpaceInvaders.getMyFrame().dispose();
    				getFrame().dispose();
    				sound.stop();
    				
    				
    				String levelStr = JOptionPane.showInputDialog("Enter Level(1-2-3)"); // level selection
        			level = Integer.valueOf(levelStr);
        			Game.setCurrentLevel(level);
        			try {
        			    Game playGame = new Game();
        			    playGame.startGame();
        			}
        			catch (Exception e) {
        			    e.printStackTrace();
        			}
        		}
        		else{
        	        JOptionPane.showMessageDialog(null, "You have to sign in before playing game", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
        	        Register.registerScreen();       		
        		}
        	}
        	});
      		
        // Help -> About
        SpaceInvaders.getItemAbout().addActionListener(new java.awt.event.ActionListener() {
        	
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		String about = "About Developer\n"
        				+ "Ali Sencer Irmak\n"
        				+ "20200702096\n"
        				+ "alisencer.irmak@std.yeditepe.edu.tr";
            	JOptionPane.showMessageDialog(null, about, "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
	}
	
	private void descendOrderData() throws IOException {
		String filename = "data.txt";
		String tempFile = "temp.txt"; // temp file for writing updated data

		List<String> lines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = reader.readLine()) != null) {
		        lines.add(line);
		    }
		}

		Collections.sort(lines, new Comparator<String>() {
		    public int compare(String line1, String line2) {
		        String[] components1 = line1.split(" ");
		        String[] components2 = line2.split(" ");
		        int score1 = 0;
		        int score2 = 0;

		        for (String component : components1) {
		            String[] keyValue = component.split(":");
		            if (keyValue[0].equals("score")) {
		                score1 = Integer.parseInt(keyValue[1]);
		                break;
		            }
		        }

		        for (String component : components2) {
		            String[] keyValue = component.split(":");
		            if (keyValue[0].equals("score")) {
		                score2 = Integer.parseInt(keyValue[1]);
		                break;
		            }
		        }

		        return Integer.compare(score2, score1); // Sort in descending order
		    }
		});

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
		    for (String line : lines) {
		        writer.write(line + System.lineSeparator());
		    }
		}		
		File originalFile = new File(filename);
		File temp = new File(tempFile);
		Files.copy(temp.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING); //copy
		temp.delete();
	}
	
	private void spawnEnemies() {
        Rectangle enemyRect = new Rectangle(enemyWidth/2, 87, enemyWidth, enemyHeight);
        highScoreEnemies.add(new Enemy(highScoreEnemyImage, enemyRect));
        
        Rectangle enemyRect1 = new Rectangle(enemyWidth/2, 242, enemyWidth, enemyHeight);
        highScoreEnemiesRow.add(new Enemy(highScoreEnemyRowImage, enemyRect1));
        
        Rectangle enemyRect2 = new Rectangle(enemyWidth/2, 397, enemyWidth, enemyHeight);
        highScoreEnemiesHorizon.add(new Enemy(highScoreEnemyHorizontalImage1, enemyRect2));
        
        Rectangle enemyRect3 = new Rectangle(enemyWidth/2, 552, enemyWidth, enemyHeight);
        highScoreEnemiesStable.add(new Enemy(highScoreEnemyStableImage1, enemyRect3));
        
        Rectangle enemyRect4 = new Rectangle(1200-enemyWidth/2, 87, enemyWidth, enemyHeight);
        highScoreEnemies.add(new Enemy(highScoreEnemyImage, enemyRect4));
        
        Rectangle enemyRect5 = new Rectangle((1200-enemyWidth/2), 242, enemyWidth, enemyHeight);
        highScoreEnemiesRow.add(new Enemy(highScoreEnemyRowImage, enemyRect5));
        
        Rectangle enemyRect6 = new Rectangle(1200-enemyWidth/2, 397, enemyWidth, enemyHeight);
        highScoreEnemiesHorizon.add(new Enemy(highScoreEnemyHorizontalImage1, enemyRect6));
        
        Rectangle enemyRect7 = new Rectangle(1200-enemyWidth/2, 552, enemyWidth, enemyHeight);
        highScoreEnemiesStable.add(new Enemy(highScoreEnemyStableImage1, enemyRect7));
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(backgroundImage, 0, backgroundY, null);
		
		g.drawImage(backgroundImage, 0, backgroundY-720, null);
		
		g.drawImage(highScoreImage, 140, -20, null);
		
		for(Enemy enemy : highScoreEnemies) {
			g.drawImage(enemy.getEnemyImage(), enemy.getRectangle().x, enemy.getRectangle().y, enemy.getRectangle().width, enemy.getRectangle().height, null);
		}
		for(Enemy enemy1 : highScoreEnemiesRow) {
			g.drawImage(enemy1.getEnemyImage(), enemy1.getRectangle().x, enemy1.getRectangle().y, enemy1.getRectangle().width, enemy1.getRectangle().height, null);
		}
		for(Enemy enemy2 : highScoreEnemiesHorizon) {
			g.drawImage(enemy2.getEnemyImage(), enemy2.getRectangle().x, enemy2.getRectangle().y, enemy2.getRectangle().width, enemy2.getRectangle().height, null);
		}
		for(Enemy enemy3 : highScoreEnemiesStable) {
			g.drawImage(enemy3.getEnemyImage(), enemy3.getRectangle().x, enemy3.getRectangle().y, enemy3.getRectangle().width, enemy3.getRectangle().height, null);
		}
		
		try {
		String filename = "data.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		int lineNum = 0;
		String line;
		String userN = null;
		String scoreFile = null;
		int i = 0;
		int yAx;
			while ((line = reader.readLine()) != null) {
				lineNum++;
				i+=100;
			    String[] components = line.split(" ");
			    for (String component : components) {
			        String[] keyValue = component.split(":");
			        String key = keyValue[0];
			        String value = keyValue[1];
			        if (key.equals("username")) {
			        	userN = value;
			        }
			        else if (key.equals("score")) {
			        	scoreFile = value;
			        }
			    }
			    yAx = 170+i;
			    if(yAx>720) {
			    	break;
			    }
			    if(lineNum == 1) {
			    	g.setColor(Color.YELLOW);
					g.setFont(new Font("Ravie", Font.PLAIN, 48));
					g.drawString(userN, 250, 260);
					
					g.setColor(Color.YELLOW);
					g.setFont(new Font("Snap ITC", Font.PLAIN, 48));
					g.drawString(scoreFile, 750, 260);
			    }
			    
			    else {
				    g.setColor(Color.WHITE);
					g.setFont(new Font("Ravie", Font.PLAIN, 32));
					g.drawString(userN, 250, yAx);
					
					g.setColor(Color.CYAN);
					g.setFont(new Font("Snap ITC", Font.PLAIN, 32));
					g.drawString(scoreFile, 750, yAx);
			    }
			   
			}
			
			reader.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		backgroundY += 1;
		if (backgroundY >= 720) {
			backgroundY = 0;	
		}
		repaint();		
	}
	
	public void enemyStatic() {
		Timer newTimer = new Timer(350, e-> {
			
			for(Enemy enemy : highScoreEnemiesHorizon) {
				synchronized (highScoreEnemiesHorizon) {
					if(enemy.getEnemyImage() == highScoreEnemyHorizontalImage1) {
						enemy.setEnemyImage(highScoreEnemyHorizontalImage2);
					}
					else if(enemy.getEnemyImage() == highScoreEnemyHorizontalImage2) {
						enemy.setEnemyImage(highScoreEnemyHorizontalImage1);
					}
					else enemy.setEnemyImage(highScoreEnemyHorizontalImage1);
				}
			}
			
			for(Enemy enemy : highScoreEnemiesRow) {
				synchronized (highScoreEnemiesRow) {
					if(enemy.getEnemyImage() == highScoreEnemyRowImage) {
						enemy.setEnemyImage(highScoreEnemyRowImage2);
					}
					else if(enemy.getEnemyImage() == highScoreEnemyRowImage2) {
						enemy.setEnemyImage(highScoreEnemyRowImage);
					}
					else enemy.setEnemyImage(highScoreEnemyRowImage);
				}
			}
			
			for(Enemy enemy : highScoreEnemies) {
					if(enemy.getEnemyImage() == highScoreEnemyImage) {
						enemy.setEnemyImage(highScoreEnemyImage2);
					}
					else if( enemy.getEnemyImage() == highScoreEnemyImage2) {
						enemy.setEnemyImage(highScoreEnemyImage3);
					}
					else if( enemy.getEnemyImage() == highScoreEnemyImage3) {
						enemy.setEnemyImage(highScoreEnemyImage);
					}
				
			}
			
			for(Enemy enemy : highScoreEnemiesStable) {
				synchronized (highScoreEnemiesStable) {
					if(enemy.getEnemyImage() == highScoreEnemyStableImage1) {
						enemy.setEnemyImage(highScoreEnemyStableImage2);
					}
					else if( enemy.getEnemyImage() == highScoreEnemyStableImage2) {
						enemy.setEnemyImage(highScoreEnemyStableImage3);
					}
					else if( enemy.getEnemyImage() == highScoreEnemyStableImage3) {
						enemy.setEnemyImage(highScoreEnemyStableImage1);
					}
				}
			}
		});	
		newTimer.start();	
	}

	public static JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		highScoreScreen.frame = frame;
	}
	
	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		highScoreScreen.level = level;
	}

	public static boolean isLoggedIn() {
		return isLoggedIn;
	}

	public static void setLoggedIn(boolean isLoggedIn) {
		highScoreScreen.isLoggedIn = isLoggedIn;
	}
	
}
