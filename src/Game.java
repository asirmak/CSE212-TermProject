import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
	
public class Game extends JPanel implements KeyListener, ActionListener {
	
	private List<Bullet>bullets;
	private List<Bullet>enemyBullets;
	private List<Bullet>enemyBulletsStable;
	private List<Bullet>enemyBulletsStable1;
	private List<Bullet>enemyBulletsStable2;
	private List<Enemy>enemies;
	private List<Enemy>enemiesRow;
	private List<Enemy>enemiesHorizon;
	private List<Enemy>enemiesStable;
	private LinkedList<hitAnim>hitList;
	private final HashSet<Integer> keyPressedSet = new HashSet<>();
	
	private static int currentLevel;	

	private int alliensScore = (int) (5 *((float) (0.7*currentLevel)));
	
	private int levelPer = 0;
	private int score = 0;
	private String userNamePlaying = Register.getUserNameRN(); 
	
	private boolean gameOver = false;
	private boolean youWin = false;
	
	private JFrame frame;

	private int enemyWidth = 80;
	private int enemyHeight = 80;
	private int enemySpeed = 3;
	private int enemyHealth = 2;
		
	private Image playerImage;
	private Image playerImage2;
	private Image playerImage3;
	private Image playerImageHit;
	
	private Image enemyImage;
	private Image enemyImage2;
	private Image enemyImage3;
	private Image enemyImageHit;

	private Image enemyRowImage;
	private Image enemyRowImage2;
	private Image enemyRowHit;

	private Image enemyHorizontalImage1;
	private Image enemyHorizontalImage2;
	private Image enemyHorizontalImageHit;
	
	private Image enemyStableImage;
	private Image enemyStableImage2;
	private Image enemyStableImage3;
	private Image enemyStableImageHit;
		
	private Image bulletImage;
	private Image boomImage;
	
	private Image backgroundImage;
	private Image gameOverImage;
	private Image youWinImage;
	
	private Image healthImage;

	private ImageIcon playAgainImage;
	private ImageIcon highScoreImage;

	private Image scaledImage1;
	private Image scaledImage2;
	
	private Image enemyBulletImage;
	
	private mainChar player = new mainChar();
	
	private int backgroundY; // to slide bg 
	
	private Timer timer; // timer for game
	private Timer enemySpawnTimer; // spawn enemies random on x-axis
	private Timer enemySpawnTimer1; // spawn enemies in a row
	private Timer enemySpawnTimer3; // spawn enemies horizontal
	private Timer newTimer; // enemy static/animated
	private Timer newTimer1; // player static/animated
	private Timer newTimer2; // enemy damage static/animated
	private Timer newTimer3; // player damage static/animated
	private Timer levelTimer;
	private Timer percentageTimer;
	private Timer enemySpawnTimer4; // enemy stable
	private Timer bulletSTimer;
	
	Sound soundBG  = new Sound(0);
	Sound soundEffects  = new Sound(1);
	
	public Game() throws Exception {
	    setPreferredSize(new Dimension(1280, 720));
	    setFocusable(true);
	    addKeyListener(this);
	    setLayout(null);
	    playMusic(0);
	    
	    try {
	        // Load images
	        playerImage = ImageIO.read(new File("spaceship1.png"));
	        playerImage2 = ImageIO.read(new File("spaceship3.png"));
	        playerImage3 = ImageIO.read(new File("spaceship2.png"));
	        playerImageHit = ImageIO.read(new File("spaceshipHit.png"));
	      
	        enemyImage = ImageIO.read(new File("enemy21.png"));
	        enemyImage2 = ImageIO.read(new File("enemy22.png"));
	        enemyImage3 = ImageIO.read(new File("enemy23.png"));
	        enemyImageHit = ImageIO.read(new File("enemyHit2.png"));
	        
	        enemyRowImage = ImageIO.read(new File("enemyRow1.png"));
	        enemyRowImage2 = ImageIO.read(new File("enemyRow2.png"));
	        enemyRowHit = ImageIO.read(new File("enemyHit.png"));
	        
	        enemyHorizontalImage1 = ImageIO.read(new File("enemy31.png"));
	        enemyHorizontalImage2 = ImageIO.read(new File("enemy32.png"));
	        enemyHorizontalImageHit = ImageIO.read(new File("enemyHit3.png"));

	        enemyStableImage = ImageIO.read(new File("enemyStableL.png"));
	        enemyStableImage2 = ImageIO.read(new File("enemyStableM.png"));
	        enemyStableImage3 = ImageIO.read(new File("enemyStableR.png"));
	        enemyStableImageHit = ImageIO.read(new File("enemyHit4.png"));
	        
	        bulletImage = ImageIO.read(new File("bullet.png"));
	        boomImage = ImageIO.read(new File("bomb.png"));
	        enemyBulletImage = ImageIO.read(new File("enemyBigBullet.png"));
	        
	        backgroundImage = ImageIO.read(new File("background.png"));
	        gameOverImage = ImageIO.read(new File("gameOverScreen.png"));
	        youWinImage = ImageIO.read(new File("youWin.png"));
	        healthImage = ImageIO.read(new File("health.png"));
	        
	        playAgainImage = new ImageIcon("playIcon.png");
	        scaledImage1 = playAgainImage.getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH);
	        playAgainImage = new ImageIcon(scaledImage1);

	        highScoreImage = new ImageIcon("highscoreIcon.png");
	        scaledImage2 = highScoreImage.getImage().getScaledInstance(325, 75, Image.SCALE_SMOOTH);
	        highScoreImage = new ImageIcon(scaledImage2);

	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	    backgroundY = 0;

	    Rectangle playerRect = new Rectangle(player.getMainCharX(), player.getMainCharY(), player.getMainCharWidth(), player.getMainCharHeight());
	    player.setRectangle(playerRect);
	    player.setPlayerImage(playerImage);

	    bullets = new ArrayList<>();
	    enemies = new ArrayList<>();
	    enemiesRow = new ArrayList<>();
	    enemiesStable = new ArrayList<>();
	    hitList = new LinkedList<>();
	    enemiesHorizon = new ArrayList<>();
	    enemyBullets = new ArrayList<>();
	    enemyBulletsStable = new ArrayList<>();
	    enemyBulletsStable1 = new ArrayList<>();
	    enemyBulletsStable2 = new ArrayList<>();
	    
	    timer = new Timer(20, this);
	    timer.start();

	    spawnEnemies();
	    spawnEnemiesInRow();
	    spawnEnemiesHorizontal();
	    enemyStatic();
	    playerStatic();
	    enemyDamageStatic();
	    playerDamaged();
	    levelUp();
	    levelPercentage();
	    spawnEnemiesStable();
	    enemyStableBullet();
	}

	public void startGame() throws Exception {
	    frame = new JFrame("Space Invaders");
	    frame.getContentPane().add(this);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    frame.setResizable(false);
	    setFrame(frame);
	}

	public void setFrame(JFrame myFrame) {
	    this.frame = myFrame;
	}

	public JFrame getFrame() {
	    return frame;
	}
	
	private void spawnEnemies() {
	    enemySpawnTimer = new Timer(1800, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (!gameOver) {
	                boolean intercepted = false;
	                int randomX = (int) (Math.random() * (getWidth() - enemyWidth));
	                Rectangle enemyRect = new Rectangle(randomX, -enemyHeight, enemyWidth, enemyHeight);
	                
	                for (Enemy tempEnemy : enemiesRow) {
	                    if (tempEnemy.getRectangle().intersects(enemyRect)) {
	                        intercepted = true;
	                        break;
	                    }
	                }	                
	                if (!intercepted) {
	                  	enemies.add(new Enemy(enemyImage, (enemyHealth*(currentLevel)) , enemyRect));

	                }
	            }
	        }
	    });
	    enemySpawnTimer.start();
	}
	
	private void spawnEnemiesInRow() {
		
		enemySpawnTimer1 = new Timer(7000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!gameOver) {
					for(int i = getWidth()-enemyWidth-5; i>-40; i-=400/currentLevel) {
						Rectangle enemyRect = new Rectangle((getWidth() - enemyWidth)-i, -enemyHeight, enemyWidth-10, enemyHeight-10);
						enemiesRow.add(new Enemy(enemyRowImage, (enemyHealth*(currentLevel)+1), enemyRect));
					}
				}
			}
		});
		
		enemySpawnTimer1.start();
	}
	
	
	private void spawnEnemiesHorizontal() {
		enemySpawnTimer3 = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!gameOver && enemiesHorizon.size()<1) {
					for(int i = 0; currentLevel > i; i++) {
						Rectangle enemyRect = new Rectangle( enemyWidth*i , 0, enemyWidth, enemyHeight);
						enemiesHorizon.add(new Enemy(enemyHorizontalImage1, enemyHealth*(i+1)+2, enemyRect));
					}
				}
			}
		});
		enemySpawnTimer3.start();		
	}
	
	private void spawnEnemiesStable() {
		enemySpawnTimer4 = new Timer(15000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int stableEnemyWidth = (int) (enemyWidth*1.5);
				int stableEnemyHeight = (int) (enemyHeight*1.5);

	            if (!gameOver) {
	                boolean intercepted = false;
	                Rectangle enemyRect1 = new Rectangle(280, -stableEnemyHeight, stableEnemyWidth, stableEnemyHeight);
	                Rectangle enemyRect2 = new Rectangle(878, -stableEnemyHeight, stableEnemyWidth, stableEnemyHeight);

	                for (Enemy tempEnemy : enemiesRow) {
	                    if (tempEnemy.getRectangle().intersects(enemyRect1) || tempEnemy.getRectangle().intersects(enemyRect2)) {
	                        intercepted = true;
	                        break; // Exit the loop if interception occurs
	                    }
	                }
	                for (Enemy tempEnemy1 : enemies) {
	                    if (tempEnemy1.getRectangle().intersects(enemyRect1) || tempEnemy1.getRectangle().intersects(enemyRect2)) {
	                        intercepted = true;
	                        break; // Exit the loop if interception occurs
	                    }
	                }
	                
	                if (!intercepted) {
	                	enemiesStable.add(new Enemy(enemyStableImage, (enemyHealth*(currentLevel)+2) , enemyRect1));
	                	enemiesStable.add(new Enemy(enemyStableImage, (enemyHealth*(currentLevel)+2) , enemyRect2));
	                }
	                
	            }
	        }
		});
		enemySpawnTimer4.start();		
	}
	
			
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		g.drawImage(backgroundImage, 0, backgroundY, null);
			
		g.drawImage(backgroundImage, 0, backgroundY-720, null);
		
		g.drawImage(player.getPlayerImage(), player.getMainCharX(), player.getMainCharY(), player.getMainCharWidth(), player.getMainCharHeight(), null);
		
		for(Bullet bullet : bullets) {
			g.drawImage(bullet.getBulletImage(), bullet.getRectangle().x, bullet.getRectangle().y, bullet.getRectangle().width, bullet.getRectangle().height, null);
		}
		
		for(Bullet bullet2 : enemyBulletsStable) {
			g.drawImage(bullet2.getBulletImage(), bullet2.getRectangle().x, bullet2.getRectangle().y, bullet2.getRectangle().width, bullet2.getRectangle().height, null);
		}
		for(Bullet bullet2 : enemyBulletsStable1) {
			g.drawImage(bullet2.getBulletImage(), bullet2.getRectangle().x, bullet2.getRectangle().y, bullet2.getRectangle().width, bullet2.getRectangle().height, null);
		}
		for(Bullet bullet2 : enemyBulletsStable2) {
			g.drawImage(bullet2.getBulletImage(), bullet2.getRectangle().x, bullet2.getRectangle().y, bullet2.getRectangle().width, bullet2.getRectangle().height, null);
		}
		
		for(Bullet bullet1 : enemyBullets) {
			g.drawImage(bullet1.getBulletImage(), bullet1.getRectangle().x, bullet1.getRectangle().y, bullet1.getRectangle().width, bullet1.getRectangle().height, null);
		}
		
		Iterator<hitAnim> hitIterator = hitList.iterator();
		List<hitAnim> animationsToRemove = new ArrayList<>();

		while (hitIterator.hasNext()) {
		    hitAnim hit = hitIterator.next();
		    g.drawImage(hit.getEnemyImage(), hit.getHitX(), hit.getHitY()-15, hit.getHitWidth()+30, hit.getHitHeight()+30, null);
		    animationsToRemove.add(hit);
		}

		// Remove the hit animations
		hitList.removeAll(animationsToRemove);

		
		for(Enemy enemy : enemies) {
			g.drawImage(enemy.getEnemyImage(), enemy.getRectangle().x, enemy.getRectangle().y, enemy.getRectangle().width, enemy.getRectangle().height, null);			
		}
		for(Enemy enemy1 : enemiesRow) {
			g.drawImage(enemy1.getEnemyImage(), enemy1.getRectangle().x, enemy1.getRectangle().y, enemy1.getRectangle().width, enemy1.getRectangle().height, null);
		}
		for(Enemy enemy : enemiesHorizon) {
			g.drawImage(enemy.getEnemyImage(), enemy.getRectangle().x, enemy.getRectangle().y, enemy.getRectangle().width, enemy.getRectangle().height, null);
		}
		for(Enemy enemy : enemiesStable) {
			g.drawImage(enemy.getEnemyImage(), enemy.getRectangle().x, enemy.getRectangle().y, enemy.getRectangle().width, enemy.getRectangle().height, null);
		}
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
		g.drawString("Username: "+userNamePlaying, 800, 50);
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
		g.drawString("Level: "+currentLevel, 250, 50);
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
		g.drawString("%"+levelPer, 400, 50);
		
		g.setColor(Color.CYAN);
		g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
		g.drawString(""+getScore(), 615, 50);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 32));
		g.drawString("x "+player.getMainCharHealth(), 85, 50);
		g.drawImage(healthImage, 30, 12, null);

		if(youWin) {
			g.drawImage(youWinImage, 0, 0, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
			g.drawString(""+userNamePlaying, 470, 50);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
			g.drawString(""+getScore(), 790, 50);
		}	
		else if(gameOver) {
			g.drawImage(gameOverImage, 0, 0, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
			g.drawString(""+userNamePlaying, 470, 50);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Cooper Black", Font.PLAIN, 28));
			g.drawString(""+getScore(), 790, 50);
		}
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!gameOver) {
			backgroundY += 1;
			if (backgroundY >= 720) {
				backgroundY = 0;	
			}
		}					
		updateCharacterPosition();
		
		updateBulletPosition();
		
		updateEnemyPosition();
		
		checkCollision();
		
		checkCollision1();
		
		checkCollision2();
		
		checkCollision3();
		
		enemyBulletCreateLevel();
		
		updateEnemyBulletPosition();
		
		checkEnemyBulletCollision();
		
		try {
			checkGameOver();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
					
		repaint();
	}
		

	
	private void updateCharacterPosition() {
	    int resultX = player.getMainCharX();
	    int resultY = player.getMainCharY();

	    if (isKeyPressed(KeyEvent.VK_UP)) {
	        resultY = Math.max(0, resultY - player.getMainCharSpeed());
	    }
	    else if (isKeyPressed(KeyEvent.VK_DOWN)) {
	    	resultY = Math.min(getHeight() - player.getMainCharHeight(), resultY + player.getMainCharSpeed());
	    }
	    else if (isKeyPressed(KeyEvent.VK_LEFT)) {
	        resultX = Math.max(0, resultX - player.getMainCharSpeed());
	    }
	    else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
	        resultX = Math.min(getWidth() - player.getMainCharWidth(), resultX + player.getMainCharSpeed());
	    }

	    Rectangle playerRect = new Rectangle(resultX, resultY, player.getMainCharWidth(), player.getMainCharHeight());
	    player.setRectangle(playerRect);
	    player.setMainCharX(resultX);
	    player.setMainCharY(resultY);
	}

	private void updateEnemyBulletPosition() {
		Iterator<Bullet>bulletIterator2 = enemyBullets.iterator();
		while(bulletIterator2.hasNext()) {
			Bullet bullet2 = bulletIterator2.next();
			bullet2.getRectangle().y += 6;
			
			if(bullet2.getRectangle().y + bullet2.getRectangle().height > 730) {
				bulletIterator2.remove();
			}
		}
		Iterator<Bullet>bulletIterator3 = enemyBulletsStable.iterator();
		while(bulletIterator3.hasNext()) {
			Bullet bullet3 = bulletIterator3.next();
			bullet3.getRectangle().y += 4;
			
			if(bullet3.getRectangle().y + bullet3.getRectangle().height > 730) {
				bulletIterator3.remove();
			}
		}
		Iterator<Bullet>bulletIterator31 = enemyBulletsStable1.iterator();
		while(bulletIterator31.hasNext()) {
			Bullet bullet31 = bulletIterator31.next();
			bullet31.getRectangle().y += 3;
			bullet31.getRectangle().x += 3;
			
			if(bullet31.getRectangle().y + bullet31.getRectangle().height > 730) {
				bulletIterator31.remove();
			}
		}
		Iterator<Bullet>bulletIterator32 = enemyBulletsStable2.iterator();
		while(bulletIterator32.hasNext()) {
			Bullet bullet32 = bulletIterator32.next();
			bullet32.getRectangle().y += 3;
			bullet32.getRectangle().x -= 3;	
			if(bullet32.getRectangle().y + bullet32.getRectangle().height > 730) {
				bulletIterator32.remove();
			}
		}		
	}
	
	private void updateBulletPosition() {
		Iterator<Bullet>bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.getRectangle().y -= 7;
			
			if(bullet.getRectangle().y + bullet.getRectangle().height < 0) {
				bulletIterator.remove();
			}
		}		
	}	
	private void updateEnemyPosition() {		
		Iterator<Enemy> enemyIterator111 = enemies.iterator();
		while(enemyIterator111.hasNext()) {
			Enemy enemyTemp = enemyIterator111.next();
			enemyTemp.getRectangle().y += enemySpeed;

			if (enemyTemp.getRectangle().y > getHeight()) {
				enemyIterator111.remove();
			}
		}
		Iterator<Enemy> enemyIterator2 = enemiesRow.iterator();
		while(enemyIterator2.hasNext()) {
			Enemy enemyTemp1 = enemyIterator2.next();
			enemyTemp1.getRectangle().y += enemySpeed;

			if (enemyTemp1.getRectangle().y > getHeight()) {
				enemyIterator2.remove();
			}
		}
		Iterator<Enemy> enemyIterator3 = enemiesStable.iterator();
		while(enemyIterator3.hasNext()) {
			Enemy enemyTemp2 = enemyIterator3.next();
			enemyTemp2.getRectangle().y += 2;

			if (enemyTemp2.getRectangle().y > getHeight()) {
				enemyIterator3.remove();
			}
		}
		Iterator<Enemy> enemyIterator4 = enemiesHorizon.iterator();
		while(enemyIterator4.hasNext()) {
			Enemy enemyTemp4 = enemyIterator4.next();
				if(enemyTemp4.getRectangle().y >700)
					enemyIterator4.remove();
				if(enemyTemp4.isFlag2()) {
					if((enemyTemp4.getRectangle().x == 0)) {
						enemyTemp4.setFlag2(false);
					}
					if((enemyTemp4.getRectangle().x < 0)) {
						enemyTemp4.setFlag2(false);
						enemyTemp4.getRectangle().x = 0; 
					}if((enemyTemp4.getRectangle().x > 0)) {
						enemyTemp4.getRectangle().x -= enemySpeed;
						enemyTemp4.getRectangle().y += 1;
					}
				}
				if((enemyTemp4.getRectangle().x >= 0) && !enemyTemp4.isFlag2()) {
					if(enemyTemp4.getRectangle().x > 1180) {
						enemyTemp4.setFlag2(true);
					}
					else {
						enemyTemp4.getRectangle().x += enemySpeed;
						enemyTemp4.getRectangle().y += 1;
					}
				}
				
			}	
	}
	
	private void checkCollision() {
		
		Iterator<Bullet> bulletIterator1 = bullets.iterator();
		while(bulletIterator1.hasNext()) {
			Bullet bullet = bulletIterator1.next();		
			Iterator<Enemy> enemyIterator1 = enemies.iterator();
			while(enemyIterator1.hasNext()) {
				Enemy enemy1 = enemyIterator1.next();
				if (bullet.getRectangle().intersects(enemy1.getRectangle())){
					hitList.add(new hitAnim(boomImage, bullet.getRectangle().width, bullet.getRectangle().height, bullet.getRectangle().x, bullet.getRectangle().y));
					enemy1.setDamaged(true);
					enemy1.takeDamage();
					bulletIterator1.remove();
					setScore(getScore() + alliensScore);
					if (enemy1.isDestroyed()) {
						enemyIterator1.remove();
						setScore(getScore() + alliensScore*2);
					}
				}
			}
		}

		for (Enemy enemy11 : enemies) {
			if (enemy11.getRectangle().intersects(player.getRectangle())) {
				if(!enemy11.isCollided()) {
					player.setMainCharHealth(player.getMainCharHealth() - 1);
		            player.setCollided(true);
					playSE(3);
					if(player.getMainCharHealth() <= 0) {
				    	gameOver = true;
				    	break;
				    }
					enemy11.setCollided(true);
				}

		    }
		}
	}
	
	private void checkCollision1() {
		Iterator<Bullet> bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			
			Iterator<Enemy> enemyIterator = enemiesRow.iterator();
			while(enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();
				
				if (bullet.getRectangle().intersects(enemy.getRectangle())){
					hitList.add(new hitAnim(boomImage, bullet.getRectangle().width, bullet.getRectangle().height, bullet.getRectangle().x, bullet.getRectangle().y));
					enemy.setDamaged(true);
					enemy.takeDamage();
					bulletIterator.remove();
					setScore(getScore() + alliensScore);
					if (enemy.isDestroyed()) {
						enemyIterator.remove();
						setScore(getScore() + alliensScore*3);
					}
				}
			}
		}

		for (Enemy enemy1 : enemiesRow) {
		    if (enemy1.getRectangle().intersects(player.getRectangle())) {
		        if (!enemy1.isCollided()) {
		            player.setMainCharHealth(player.getMainCharHealth() - 1);
		            player.setCollided(true);
		            playSE(3);
		            if (player.getMainCharHealth() <= 0) {
		                gameOver = true;
		                break;
		            }
		            
		            enemy1.setCollided(true);
		        }
		    }
		}

	}
	
	private void checkCollision2() {
		Iterator<Bullet> bulletIterator = bullets.iterator();
		List<Bullet> bulletsToRemove = new ArrayList<>();

		while (bulletIterator.hasNext()) {
		    Bullet bullet = bulletIterator.next();

		    Iterator<Enemy> enemyIterator = enemiesHorizon.iterator();
		    while (enemyIterator.hasNext()) {
		        Enemy enemy = enemyIterator.next();

		        if (bullet.getRectangle().intersects(enemy.getRectangle())) {
		            hitList.add(new hitAnim(boomImage, bullet.getRectangle().width, bullet.getRectangle().height, bullet.getRectangle().x, bullet.getRectangle().y));
		            enemy.setDamaged(true);
		            enemy.takeDamage();
		            bulletsToRemove.add(bullet); // Add bullet to the removal list
		            setScore(getScore() + alliensScore);
		            if (enemy.isDestroyed()) {
		                enemyIterator.remove();
		                setScore(getScore() + alliensScore*4);
		            }
		        }
		    }
		}

		// Remove bullets outside the nested loops
		bullets.removeAll(bulletsToRemove);
		for (Enemy enemy1 : enemiesHorizon) {
		    if (enemy1.getRectangle().intersects(player.getRectangle())) {
		        if (!enemy1.isCollided()) {
		            player.setMainCharHealth(player.getMainCharHealth() - 1);
		            player.setCollided(true);
		            playSE(3);
		            if (player.getMainCharHealth() <= 0) {
		                gameOver = true;
		                break;
		            }
		            enemy1.setCollided(true);
		        }
		    }
		}

	}
	
	private void checkCollision3(){
		Iterator<Bullet> bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			
			Iterator<Enemy> enemyIterator = enemiesStable.iterator();
			while(enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();
				
				if (bullet.getRectangle().intersects(enemy.getRectangle())){
					hitList.add(new hitAnim(boomImage, bullet.getRectangle().width, bullet.getRectangle().height, bullet.getRectangle().x, bullet.getRectangle().y));
					enemy.setDamaged(true);
					enemy.takeDamage();
					bulletIterator.remove();
					setScore(getScore() + alliensScore);
					if (enemy.isDestroyed()) {
						enemyIterator.remove();
						setScore(getScore() + alliensScore*6);
					}
				}
			}
		}

		for (Enemy enemy1 : enemiesStable) {
		    if (enemy1.getRectangle().intersects(player.getRectangle())) {
		        if (!enemy1.isCollided()) {
		            player.setMainCharHealth(player.getMainCharHealth() - 1);
		            player.setCollided(true);
		            playSE(3);
		            if (player.getMainCharHealth() <= 0) {
		                gameOver = true;
		                break;
		            }		            
		            enemy1.setCollided(true);
		        }
		    }
		}

	}
	
	private void checkEnemyBulletCollision() {		
		
		Iterator<Bullet> bulletIterator = enemyBullets.iterator();
		while(bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if(bullet.getRectangle().intersects(player.getRectangle())) {
				playSE(3);
				player.setMainCharHealth(player.getMainCharHealth() - 1);
				player.setCollided(true);
				bulletIterator.remove();
				if (player.getMainCharHealth() <= 0) {
	                gameOver = true;
	                break;
	            }
			}
		}
		Iterator<Bullet> bulletIterator1 = enemyBulletsStable.iterator();
		while(bulletIterator1.hasNext()) {
			Bullet bullet1 = bulletIterator1.next();
			if(bullet1.getRectangle().intersects(player.getRectangle())) {
				playSE(3);
				player.setMainCharHealth(player.getMainCharHealth() - 1);
				player.setCollided(true);
				bulletIterator1.remove();
				if (player.getMainCharHealth() <= 0) {
	                gameOver = true;
	                break;
	            }

			}
		}
		Iterator<Bullet> bulletIterator2 = enemyBulletsStable1.iterator();
		while(bulletIterator2.hasNext()) {
			Bullet bullet2 = bulletIterator2.next();
			if(bullet2.getRectangle().intersects(player.getRectangle())) {
				playSE(3);
				player.setMainCharHealth(player.getMainCharHealth() - 1);
				player.setCollided(true);
				bulletIterator2.remove();
				if (player.getMainCharHealth() <= 0) {
	                gameOver = true;
	                break;
	            }

			}
		}
		Iterator<Bullet> bulletIterator3 = enemyBulletsStable2.iterator();
		while(bulletIterator3.hasNext()) {
			Bullet bullet3 = bulletIterator3.next();
			if(bullet3.getRectangle().intersects(player.getRectangle())) {
				playSE(3);
				player.setMainCharHealth(player.getMainCharHealth() - 1);
				player.setCollided(true);
				bulletIterator3.remove();
				if (player.getMainCharHealth() <= 0) {
	                gameOver = true;
	                break;
	            }

			}
		}
	}
	
	private void checkGameOver() throws Exception {
		
		if (gameOver) {
			JButton playGameOneMore = new JButton();
			playGameOneMore.setIcon(playAgainImage);
			playGameOneMore.setBounds(565, 320, 150, 75);
			add(playGameOneMore);
			playGameOneMore.addActionListener(new java.awt.event.ActionListener() {	
				public void actionPerformed(java.awt.event.ActionEvent Event) {
					try {
						stopMusicBG();
						getFrame().dispose();
						String levelStr = JOptionPane.showInputDialog("Enter Level(1-2-3)");
						Game.setCurrentLevel(Integer.valueOf(levelStr));
						Game playGame = new Game();
        			    playGame.startGame();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			JButton goHighScore = new JButton();
			goHighScore.setBounds(475, 425, 325, 75);
			goHighScore.setIcon(highScoreImage);
			add(goHighScore);
			goHighScore.addActionListener(new java.awt.event.ActionListener() {	
				public void actionPerformed(java.awt.event.ActionEvent Event) {
					try {
						stopMusicBG();
						getFrame().dispose();
						highScoreScreen screenHS;
	        			screenHS = new highScoreScreen();
	        			screenHS.showScreen();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			if(youWin) {
				playSE(5);
			}
			else if(gameOver) {
				playSE(2);
			}
			player.setPlayerImage(null);
			enemies.clear();
			enemiesRow.clear();
			enemiesHorizon.clear();
			enemiesStable.clear();
			bullets.clear();
			enemyBullets.clear();
			enemyBulletsStable.clear();
			enemyBulletsStable1.clear();
			enemyBulletsStable2.clear();
			hitList.clear();
			
			String filename = "data.txt";
			String tempFile = "temp.txt"; // temporary file for writing updated data

			BufferedReader reader = new BufferedReader(new FileReader(filename));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			String userN = null;
			String scoreFile = null;
			while ((line = reader.readLine()) != null) {
			    String[] components = line.split(" ");
			    for (String component : components) {
			        String[] keyValue = component.split(":");
			        String key = keyValue[0];
			        String value = keyValue[1];
			        if (key.equals("username")) {
			            userN = value;
			        } else if (key.equals("score")) {
			            scoreFile = value;
			        }
			    }
			    if (userN.equals(userNamePlaying) && Integer.valueOf(scoreFile) < score) {
			        line = line.replace("score:" + scoreFile, "score:" + String.valueOf(score)); // anlik line'ı güncelliyo
			    }
			    writer.write(line + System.lineSeparator()); // her line'ı ve güncel line'ı temp'e yazıyor
			}
			reader.close();
			writer.close();
			
			File originalFile = new File(filename);
			File temp = new File(tempFile);
			Files.copy(temp.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING); //copy
			temp.delete();
			timer.stop();			
		}
		
	}
	
	private void enemyBulletCreateLevel() {
		if(currentLevel >= 2) {
			for(Enemy enemy : enemies) {
				if(enemy.isBulletHas() == false) {
					Rectangle bulletRect2 = new Rectangle (enemy.getRectangle().x + enemy.getRectangle().width / 2 -5, enemy.getRectangle().y+20, 10, 30);
					enemyBullets.add(new Bullet(enemyBulletImage, bulletRect2));
					enemy.setBulletHas(true);
				}
			}
		}
		if(currentLevel == 3) {
			if(enemiesRow.size() != 0) {
				Random random = new Random();
				int randomNumber = random.nextInt(enemiesRow.size()-1);
				int i = randomNumber; 
				
				for(int j = 0; enemiesRow.size()>j; j+=2) {
					enemiesRow.get(randomNumber+1).setBulletHas(true);
					if(enemiesRow.get(i).isBulletHas() == false) {
						Rectangle bulletRect2 = new Rectangle (enemiesRow.get(i).getRectangle().x + enemiesRow.get(i).getRectangle().width / 2 -5, enemiesRow.get(i).getRectangle().y+20, 10, 30);
						enemyBullets.add(new Bullet(enemyBulletImage, bulletRect2));
						enemiesRow.get(i).setBulletHas(true);
					}
					
				}
			}
		}
	}
	
	
	private boolean isKeyPressed(int keyCode) {
		return keyPressedSet.contains(keyCode);
	}	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyPressedSet.add(keyCode);
		
		if (keyCode == KeyEvent.VK_SPACE) {
			Rectangle bulletRect = new Rectangle (player.getMainCharX() + player.getMainCharWidth() / 2 -5, player.getMainCharY(), 10, 30);
			bullets.add(new Bullet(bulletImage, bulletRect));
			playSE(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyPressedSet.remove(keyCode);
	}	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	private void enemyStatic() {		
		newTimer = new Timer(400, e-> {
			
			for(Enemy enemy : enemiesRow) {
				synchronized (enemiesRow) {
					if(enemy.getEnemyImage() == enemyRowImage) {
						enemy.setEnemyImage(enemyRowImage2);
					}
					else if(enemy.getEnemyImage() == enemyRowImage2) {
						enemy.setEnemyImage(enemyRowImage);
					}
					else enemy.setEnemyImage(enemyRowImage);
					
				}
			}
			
			for(Enemy enemy : enemies) {
				synchronized (enemies) {
					if(enemy.isFlag()) {
						enemy.setEnemyImage(enemyImage3);
						enemy.setFlag(false);
					}
					else if(enemy.getEnemyImage() == enemyImage) {
						enemy.setEnemyImage(enemyImage2);
					}
					
					else if( enemy.getEnemyImage() == enemyImage2) {
						enemy.setEnemyImage(enemyImage);
						enemy.setFlag(true);
					}
					else if( enemy.getEnemyImage() == enemyImage3) {
						enemy.setEnemyImage(enemyImage);
					}
					else enemy.setEnemyImage(enemyImage);
				}
			}
			for(Enemy enemy : enemiesHorizon) {
				synchronized (enemiesHorizon) {
					if(enemy.getEnemyImage() == enemyHorizontalImage1) {
						enemy.setEnemyImage(enemyHorizontalImage2);
					}
					else if(enemy.getEnemyImage() == enemyHorizontalImage2) {
						enemy.setEnemyImage(enemyHorizontalImage1);
					}
					else enemy.setEnemyImage(enemyHorizontalImage1);	
				}
			}
			
			for(Enemy enemy : enemiesStable) {
				synchronized (enemiesStable) {
					if(enemy.isFlag()) {
						enemy.setEnemyImage(enemyStableImage2);
						enemy.setFlag(false);
						enemy.setFlagg(true);
					}
					else if(enemy.isFlagg()) {
						enemy.setEnemyImage(enemyStableImage);
						enemy.setFlagg(false);
					}
					else if(enemy.getEnemyImage() == enemyStableImage) {
						enemy.setEnemyImage(enemyStableImage2);
					}
					
					else if( enemy.getEnemyImage() == enemyStableImage2) {
						enemy.setEnemyImage(enemyStableImage3);
						enemy.setFlag(true);
					}
					else enemy.setEnemyImage(enemyStableImage);
				}
			}
			
			
			
		});
		
		newTimer.start();
		
		
	}
	private void playerStatic() {
		newTimer1 = new Timer(100, e-> {
			if(player.getPlayerImage() == playerImage) {
				player.setPlayerImage(playerImage2);
			}
			else if(player.getPlayerImage() == playerImage2) {
				player.setPlayerImage(playerImage3);
			}
			else player.setPlayerImage(playerImage);
		});
		newTimer1.start();
	}
	
	private void enemyDamageStatic() {
		newTimer2 = new Timer(100, e-> {
			for(Enemy enemy : enemiesRow) {
				if(enemy.isDamaged()) {
					synchronized (enemiesRow){
						enemy.setEnemyImage(enemyRowHit);
						enemy.setDamaged(false);
					}
				}
			}
			for(Enemy enemy : enemies) {
				if(enemy.isDamaged()) {
					synchronized (enemies){
						enemy.setEnemyImage(enemyImageHit);
						enemy.setDamaged(false);
					}
				}
			}
			for(Enemy enemy : enemiesHorizon) {
				if(enemy.isDamaged()) {
					synchronized (enemiesHorizon){
						enemy.setEnemyImage(enemyHorizontalImageHit);
						enemy.setDamaged(false);
					}
				}
			}
			for(Enemy enemy : enemiesStable) {
				if(enemy.isDamaged()) {
					synchronized (enemiesStable){
						enemy.setEnemyImage(enemyStableImageHit);
						enemy.setDamaged(false);
					}
				}
			}
			
		});
		
		
		newTimer2.start();
	}
	
	private void playerDamaged() {
		newTimer3 = new Timer(100, e->{
			if(player.getChangeNum() == 0) {
				player.setPlayerImage(playerImage);
				player.setCollided(false);
				player.setChangeNum(10);
			}
			if(player.getChangeNum() > 0 && player.isCollided()) {
				player.setPlayerImage(playerImageHit);
				player.setChangeNum(player.getChangeNum()-1);
			}
		});
		newTimer3.start();		
	}
	
	private void enemyStableBullet() {
		bulletSTimer = new Timer(4000, e->{
			for( Enemy enemyS: enemiesStable) {
				Rectangle bulletRect2 = new Rectangle ((enemyS.getRectangle().x +  enemyS.getRectangle().width / 2 -15) , enemyS.getRectangle().y+enemyS.getRectangle().height-40, 30, 30);
				enemyBulletsStable.add(new Bullet(enemyBulletImage, bulletRect2));
				Rectangle bulletRect3 = new Rectangle ((enemyS.getRectangle().x +  enemyS.getRectangle().width / 2 -15) , enemyS.getRectangle().y+enemyS.getRectangle().height-40, 30, 30);
				enemyBulletsStable1.add(new Bullet(enemyBulletImage, bulletRect3));
				Rectangle bulletRect4 = new Rectangle ((enemyS.getRectangle().x +  enemyS.getRectangle().width / 2 -15) , enemyS.getRectangle().y+enemyS.getRectangle().height-40, 30, 30);
				enemyBulletsStable2.add(new Bullet(enemyBulletImage, bulletRect4));
			}
		});
		bulletSTimer.start();
	}
	
	
	private void levelPercentage() {
		percentageTimer = new Timer(1200, e->{
			levelPer+=1;
			if(currentLevel == 3 && levelPer == 100) {
				youWin = true;
				gameOver = true;
			}
		});
		percentageTimer.start();
	}
	
	
	private void levelUp() {
		levelTimer = new Timer(120000, e->{
			if(currentLevel < 3) {
				currentLevel++;
				alliensScore = (int) (5 *((float) (0.7*currentLevel)));
				levelPer = 0;
			}		
		});
		levelTimer.start();		
	}
	
	private void playMusic(int a) {
		soundBG.setFile(a);
		soundBG.play();
		soundBG.loop();
	}
	
	private void stopMusicBG() {
		soundBG.stop();
	}
	
	private void playSE(int i) {
		soundEffects.setFile(i);
		soundEffects.play();
	}
	
	public static int getCurrentLevel() {
		return currentLevel;
	}

	public static void setCurrentLevel(int currentLevel) {
		Game.currentLevel = currentLevel;
	}
}
